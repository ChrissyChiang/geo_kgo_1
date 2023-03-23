package gov.kcg.kgo.service.impl;

import java.io.File;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gov.kcg.kgo.enums.backend.CaseMainStatusEnum;
import gov.kcg.kgo.geoentity.*;
import gov.kcg.kgo.geoenum.RentStatusEnum;
import gov.kcg.kgo.geomodel.GeoCityForDepartmentCivil;
import gov.kcg.kgo.geomodel.GeoCityForDepartmentCivilPerson;
import gov.kcg.kgo.georepository.*;
import gov.kcg.kgo.georepository.custom.GeoKgoMyDataFileCustom;
import gov.kcg.kgo.geoservice.GeoCityExtService;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.util.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import gov.kcg.kgo.dto.kgoUnit.KgoUnitDto;
import gov.kcg.kgo.enums.error.CoinCityApiError;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.service.CallKcgCityApiService;
import gov.kcg.kgo.service.ScheduledService;
import gov.kcg.kgo.service.bean.kgo.city.api.viewModel.landnum.rs.LandNumRs;
import gov.kcg.kgo.service.bean.kgo.city.api.viewModel.landnum.rs.bean.DataModel;
import gov.kcg.kgo.service.bean.myaccount.viewModel.KcgDeptList.rs.KcgDeptInfo;
import gov.kcg.kgo.service.bean.myaccount.viewModel.KcgDeptList.rs.KcgDeptList;
import gov.kcg.kgo.service.bean.myaccount.viewModel.KcgUserList.rs.KcgUserInfo;
import gov.kcg.kgo.service.bean.myaccount.viewModel.KcgUserList.rs.KcgUserList;
import gov.kcg.kgo.service.bean.scheduled.viewModel.holiday.rs.HolidayInfo;
import gov.kcg.kgo.service.impl.helper.CallKcgCityApiServiceHelper;
import gov.kcg.kgo.service.impl.transaction.ScheduledServiceTransaction;

import javax.swing.*;

@Service("ScheduledService")
public class ScheduledServiceImpl implements ScheduledService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledServiceImpl.class);

	/** 機關編號 397210000A:高雄市政府. */
	private final static String ORG = "397210000A";

	/** The Constant SERVICE_ID INF_HR27. */
	private final static String SERVICE_ID = "INF_HR27";

	@Autowired
	private KgoOrganRepository kgoOrganRepository;

	@Autowired
	private KgoUnitRepository kgoUnitRepository;

//	@Autowired
//	private OrganizerRepository organizerRepository;
//	
//	@Autowired
//	private EmployeeRepository employeeRepository;

	@Autowired
	private KgoUserRepository kgoUserRepository;

	@Autowired
	private KgoKcdRepository kgoKcdRepository;

	@Autowired
	private KgoHolidayRepository kgoHolidayRepository;

	@Autowired
	private KgoApiLogRepository kgoApiLogRepository;

	@Autowired
	private CallKcgCityApiService callKcgCityApiService;

	@Autowired
	GeoKgoMyDataFileCustom geoKgoMyDataFileCustom;

	@Autowired
	KgoMydataFileRepository kgoMydataFileRepository;

	@Autowired
	GeoCaseRentRelationRepos geoCaseRentRelationRepos;

	@Autowired
	GeoKgoRentPaymentRepos geoKgoRentPaymentRepos;
	private CallKcgCityApiServiceHelper callKcgCityApiServiceHelper = CallKcgCityApiServiceHelper.getInstance();

	@Qualifier("myAccountTemplate")
	@Autowired
	RestTemplate myAccountTemplate;

	@Autowired
	GeoCityExtService geoKcgCityExtService;

	@Autowired
	GeoKgoBlackListRepository geoKgoBlackListRepository;

	@Autowired
	GeoCaseSetSiteMainFileRepos geoCaseSetSiteMainFileRepos;

	@Autowired
	GeoKgoCasesetRentTimeRepository geoKgoCasesetRentTimeRepository;


	/**
	 * 更新機關、單位資料.
	 */
//	@Scheduled(cron = "00 17 14 ? * *")
	public void updateUnitData() {
		try {
			// 1. 取得單位資料 授權token
			String token = getUnitAuthToken();

			// 2 取得所有機關 單位資料(Base64)
			String unitDataBase64Str = getUnitData(token);

//			String unitDataBase64Str = FileUtils.readFileToString(new File("d:/unitData.txt"), "UTF-8");

			// 解碼 取得單位資料
			String unitData = new String(Base64.getDecoder().decode(unitDataBase64Str), "UTF-8");
//	        LOGGER.info(">>>>> unitData : " + unitData);

			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<KgoUnitDto>>() {
			}.getType();
			List<KgoUnitDto> kgoUnitDtoList = gson.fromJson(unitData, listType);

			// 機關 PkList
			List<String> kgoOrganIdList = new ArrayList<>();
			// 單位取得的資料list
			List<KgoOrgan> kgoOrganDataList = new ArrayList<>();
			// 單位 PkList
			List<KgoUnitPK> kgoUnitPkList = new ArrayList<>();
			// 單位取得的資料list
			List<KgoUnit> kgoUnitDataList = new ArrayList<>();

			// API資料整理 dto -> entity
			for (KgoUnitDto kgoUnitDto : kgoUnitDtoList) {
				// 設置機關 entity
				kgoOrganDataList.add(setKgoOrgan(kgoOrganIdList, kgoUnitDto));

				// 設置單位表 entity
				kgoUnitDataList.add(setKgoUnit(kgoUnitPkList, kgoUnitDto));
			}

			// 儲存不存在DB的機關.
			saveKgoOrganList(kgoOrganIdList, kgoOrganDataList);

			// 儲存不存在DB的單位.
			saveKgoUnitList(kgoUnitPkList, kgoUnitDataList);

			LOGGER.info(">>>>> ScheduledServiceImpl updateUnitData() 更新機關、單位資料 SUCCESS ");
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			// LOGGER.error(e.getMessage(), e.fillInStackTrace());
			throw new KgoApiException("updateUnitData 更新機關、單位資料 error " + e.getMessage(), e);
		}
	}
//	
//	@Scheduled(cron = "00 58 12 ? * *")
//	@Transactional(rollbackFor = Exception.class)
//	public void test() {
//		LOGGER.info(">>>>> test "); 
//		
//		Map<Integer, Organizer> organizerMap = organizerRepository.findAll().stream().collect(Collectors.toMap(o -> o.getSeq(), o -> o));
//		List<Employee> empList = employeeRepository.findAll();
//		
//		Timestamp now = DateUtil.getCurrentTimestamp();
//		List<KgoUser> kgoUserList = new ArrayList<>();
//		for(Employee emp : empList) {
//			KgoUser ku = new KgoUser();
//			ku.setUserId(emp.getLoginID());
//			ku.setName(emp.getUserName());
//			ku.setCreateTime(now);
//			ku.setUpdateTime(now);
//			
//			Organizer og = organizerMap.get(emp.getOrganizer().getSeq());
//			String organ = og.getCompetentAuthorityCode();
//			// 組織為空 代表 同機關 單位
//			if (StringUtils.isEmpty(organ)) {
//				organ = og.getCode();
//			}
//			ku.setOrgan(organ);
//			ku.setUnit(og.getCode());
//			kgoUserList.add(ku);
//		}
//
//		LOGGER.info(">>>>> kgoUserList " + kgoUserList.size()); 
//		 // 單位大量 save 方法.
//        ScheduledServiceTransaction transaction = SpringUtil.getBean(ScheduledServiceTransaction.class);
//        transaction.saveKgoUserList(kgoUserList);
//        
//        LOGGER.info(">>>>> 儲存成功 "); 
//	}

	/**
	 * 設置單機關 entity.
	 *
	 * @param kgoUnitDto the new unit
	 */
	private KgoOrgan setKgoOrgan(List<String> kgoOrganIdList, KgoUnitDto kgoUnitDto) {
		String organId = kgoUnitDto.getB02SORCOD();
		// add 查詢pk
		kgoOrganIdList.add(organId);

		KgoOrgan kgoOrgan = new KgoOrgan();
		kgoOrgan.setOrganId(kgoUnitDto.getB02SORCOD());
		kgoOrgan.setOrganName(kgoUnitDto.getB02SORCOD());
		Timestamp now = DateUtil.getCurrentTimestamp();
		kgoOrgan.setCreateTime(now);
		kgoOrgan.setUpdateTime(now);

		return kgoOrgan;
	}

	/**
	 * 設置單位表 entity.
	 *
	 * @param kgoUnitDto the new unit
	 */
	private KgoUnit setKgoUnit(List<KgoUnitPK> kgoUnitPkList, KgoUnitDto kgoUnitDto) {
		KgoUnitPK pk = new KgoUnitPK();
		pk.setOrganId(kgoUnitDto.getB02SORCOD());
		pk.setUnitId(kgoUnitDto.getB02UNICOD());
		// add 查詢pk
		kgoUnitPkList.add(pk);

		KgoUnit kgoUnit = new KgoUnit();
		kgoUnit.setId(pk);
		kgoUnit.setUnitName(kgoUnitDto.getB02UNICOD());
		kgoUnit.setCreateTime(DateUtil.getCurrentTimestamp());
		kgoUnit.setUnitName(kgoUnitDto.getB02UNIT());
		return kgoUnit;
	}

	/**
	 * 儲存不存在DB的機關.
	 *
	 * @param kgoOrganIdList   the kgo organ id list
	 * @param kgoOrganDataList the kgo organ data list
	 */
	private void saveKgoOrganList(List<String> kgoOrganIdList, List<KgoOrgan> kgoOrganDataList) {
		List<KgoOrgan> existOrganList = new ArrayList<>();
		// 查找機關表 大量查詢 findAll 方法
		existOrganList = kgoOrganRepository.findAllByIdBatch(kgoOrganIdList);

		LOGGER.info(">>>>> existOrganList size = " + existOrganList.size());
		List<String> existOrganIdList = existOrganList.stream().map(o -> o.getOrganId()).collect(Collectors.toList());

		// 過濾掉已存在的機關
		Predicate<KgoOrgan> filterExistOrgan = unit -> {
			return !existOrganIdList.contains(unit.getOrganId());
		};

		// 儲存機關資料.
		List<KgoOrgan> saveOrganList = kgoOrganDataList.stream().filter(filterExistOrgan).collect(Collectors.toList());
		LOGGER.info(">>>>> saveOrganList size = " + saveOrganList.size());

//        List<KgoOrgan> saveOrganList = new ArrayList<>();
//        KgoOrgan ko = new KgoOrgan();
//        ko.setOrganId("66666688111");
//        ko.setParent_OrganId("456457");
//        saveOrganList.add(ko);

		// 單位大量 save 方法.
		ScheduledServiceTransaction transaction = SpringUtil.getBean(ScheduledServiceTransaction.class);
		transaction.saveOrganList(saveOrganList);
	}

	/**
	 * 儲存不存在DB的單位.
	 *
	 * @param kgoUnitPkList   the kgo unit pk list
	 * @param kgoUnitDataList the kgo unit data list
	 * @return the save unit list
	 */
	private void saveKgoUnitList(List<KgoUnitPK> kgoUnitPkList, List<KgoUnit> kgoUnitDataList) {
		List<KgoUnit> existUnitList = new ArrayList<>();
		// 查找單位表 大量查詢 findAll 方法
		existUnitList = kgoUnitRepository.findAllByIdBatch(kgoUnitPkList);

		LOGGER.info(">>>>> existUnitList size = " + existUnitList.size());
		List<KgoUnitPK> existUnitPkList = existUnitList.stream().map(u -> u.getId()).collect(Collectors.toList());

		// 過濾掉已存在的單位
		Predicate<KgoUnit> filterExistUnit = unit -> {
			return !existUnitPkList.contains(unit.getId());
		};

		// 儲存不存在DB的單位
		List<KgoUnit> saveUnitList = kgoUnitDataList.stream().filter(filterExistUnit).collect(Collectors.toList());
		LOGGER.info(">>>>> saveUnitList size = " + saveUnitList.size());

		// 儲存單位資料..
		ScheduledServiceTransaction transaction = SpringUtil.getBean(ScheduledServiceTransaction.class);
		transaction.saveUnitList(saveUnitList);
//        kgoUnitRepository.saveAllBatch(saveUnitList);
	}

	/**
	 * 取得單位資料 授權token.
	 *
	 * @return the auth token
	 * @throws Exception the exception
	 */
	private String getUnitAuthToken() throws Exception {
		HttpRequest httpRequest = new HttpRequest();

		// 1.取得授權token
		String qryString = "Org=" + ORG + "&ServiceID=" + SERVICE_ID;
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Content-Length", "" + qryString.getBytes().length);

		String tokenXmlStr = httpRequest.sendPost("https://ikpd.kcg.gov.tw/ckpd/PCSPService.asmx/GetAuth", qryString, headers);
		LOGGER.info(">>>>> updateUnitData() tokenXmlStr = " + tokenXmlStr);

		// 解析KcgService xml 內容.
		String token = XmlUtil.loadKcgServiceXMLStr(tokenXmlStr);
		return token;
	}

	/**
	 * 取得所有機關 單位資料(Base64).
	 *
	 * @return the unit data
	 * @throws Exception
	 */
	private String getUnitData(String token) throws Exception {
		HttpRequest httpRequest = new HttpRequest();
		String qryString = "Token=" + token + "&ServiceID=" + SERVICE_ID + "&QryPara={}";
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Content-Length", "" + qryString.getBytes().length);

		String unitDataXmlStr = httpRequest.sendPost("https://ikpd.kcg.gov.tw/ckpd/PCSPService.asmx/GetDATA", qryString, headers);
		// 解析KcgService xml 內容.
		String unitData = XmlUtil.loadKcgServiceXMLStr(unitDataXmlStr);
		return unitData;
	}

	/** test Code 匯入機關資料 */
//	List<String> orgListStrList =  FileUtils.readLines(new File("d:/行政院所屬中央及地方機關代碼.csv"), "UTF-8");
//	List<KgoOrgan> kgoOrganDataList = new ArrayList<>();
//	for(String line : orgListStrList) {
//		String[] strArr = StringUtils.split(line, ",");
//		
//		KgoOrgan organ = new KgoOrgan();
//		organ.setOrganId(strArr[0]);
//		organ.setOrganName(strArr[1]);
//		organ.setParentOrganId(strArr[5]);
//		
//    	Timestamp now = DateUtil.getCurrentTimestamp();
//    	organ.setCreateTime(now);
//    	organ.setUpdateTime(now);
//    	kgoOrganDataList.add(organ);
//	}
//	
////    // 單位大量 save 方法.
//    ScheduledServiceTransaction transaction = SpringUtil.getBean(ScheduledServiceTransaction.class);
//    transaction.saveOrganList(kgoOrganDataList);
	/** test Code 匯入機關資料 */

	/**
	 * 更新段小段
	 */
	@Scheduled(cron = "00 30 14 ? * *")
	public void updateKgoKcdTable() {
		LOGGER.info(">>>>> ScheduledServiceImpl updateKgoKcdTable() Begin ");
		try {
			LandNumRs kcdData = callKcgCityApiService.landNumKcgCityApi();

			if (ObjectUtils.isNotEmpty(kcdData) && ObjectUtils.isNotEmpty(kcdData.getData())) {
				for (DataModel item : kcdData.getData()) {
					if (StringUtils.isNotBlank(item.getKcdE_2())) {
						KgoKcd kgoKcd = new KgoKcd();
						kgoKcd.setKcd(item.getKcdE_2());
						kgoKcd.setKcnt(item.getKcnt());
						kgoKcd.setKrmk(item.getKrmk());
						kgoKcdRepository.save(kgoKcd);
					} else {
						LOGGER.info(">>>>> updateKgoKcdTable() 異常資料 " + JsonUtil.toJSONString(item));
					}
				}
			}

			LOGGER.info(">>>>> ScheduledServiceImpl updateKgoKcdTable() 更新段小段 SUCCESS ");
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.fillInStackTrace());
			throw new KgoApiException("updateKgoKcdTable 更新段小段 error " + e.getMessage(), e);
		}

	}

	/**
	 * reTry 起案 Api
	 */
	@Scheduled(fixedDelay = 1000 * 60 * 60)
	public void reTryCallApiLog() {
		LOGGER.info(">>>>> Scheduled reTryCallApiLog() begin ");
		List<KgoApiLog> dataList = this.kgoApiLogRepository.findByReCountLessThanEqualAndIsSuccessFalse(10);

		for (KgoApiLog kgoApiLog : dataList) {
			String response = "";

			try {
				kgoApiLog.setReCount(kgoApiLog.getReCount().intValue() + 1);

				Map<String, String> paramsMap = new HashMap<String, String>();
				if (StringUtils.isNotBlank(kgoApiLog.getRequest())) {
					paramsMap = JsonUtil.getObject(kgoApiLog.getRequest(), HashMap.class);
				}

				response = this.callKcgCityApiServiceHelper.getCityApiDataWithMoicaLogin(kgoApiLog.getUrl(), paramsMap);
				kgoApiLog.setResponse(response);
				kgoApiLog.setSuccess(true);
				LOGGER.info(">>>>> ScheduledServiceImpl reTryCallApiLog() SUCCESS ");
			} catch (KgoApiException apiE) {
				LOGGER.error(apiE.getMessage());
				kgoApiLog.setResponse(apiE.getMessage());
				kgoApiLog.setSuccess(false);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e.fillInStackTrace());
				kgoApiLog.setResponse(e.getMessage());
				kgoApiLog.setSuccess(false);
			} finally {
				this.kgoApiLogRepository.save(kgoApiLog);
			}
		}

		LOGGER.info(">>>>> Scheduled reTryCallApiLog() End ");
	}

	/**
	 * 更新機關名稱
	 */
	@Scheduled(cron = "00 17 15 ? * *")
	public void updateKgoOrganName() {
		LOGGER.info(">>>>> Scheduled updateKgoOrganName() begin ");
		try {

			String url = "https://myaccount.kcg.gov.tw/download/kcg_dept_list.json";
			HttpRequest httpRequest = new HttpRequest();
			Map<String, String> headersMap = new HashMap<>();
			String responseStr = httpRequest.sendGet(url);
			KcgDeptList rs = JsonUtil.getObject(responseStr, KcgDeptList.class);

			for (KcgDeptInfo item : rs.getDeptList()) {
				if (item.getStatus() == 1) {
					KgoOrgan kgoOrgan;
					Optional<KgoOrgan> optionalkgoOrgan = kgoOrganRepository.findById(item.getDeptCode());
					Timestamp now = DateUtil.getCurrentTimestamp();
					if (optionalkgoOrgan.isPresent()) {
						kgoOrgan = optionalkgoOrgan.get();
						kgoOrgan.setUpdateTime(now);
						kgoOrgan.setUpdateUser("updateKgoOrganName");
					} else {
						kgoOrgan = new KgoOrgan();
						kgoOrgan.setOrganId(item.getDeptCode());
						if (item.getLevelNO().equals("1")) {
							kgoOrgan.setParentOrganId(ORG);
						} else {
							kgoOrgan.setParentOrganId(item.getParentDeptCode());
						}

						kgoOrgan.setCreateUser("updateKgoOrganName");
						kgoOrgan.setCreateTime(now);
					}
					kgoOrgan.setOrganName(item.getDeptName());

					kgoOrganRepository.save(kgoOrgan);
				}
			}
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(CoinCityApiError.FAIL_TO_SEND.getErrorMsgKey());
			throw new KgoApiException("updateKgoOrganName error " + e.getMessage(), e);
		}

		LOGGER.info(">>>>> updateKgoOrganName() End ");
	}

	/**
	 * 每日04:00AM、20:00PM 更新高市府單位人員清單
	 */
	@Scheduled(cron = "0 0 4,20 * * *") // FIXME: 待確認
	public void updateKgoUser() {

		String UPDATER = "updateKgoUser";
		String USER_LIST_URL = "https://accounts-api.kcg.gov.tw/download/kcg_user_list.json";
		
		LOGGER.info("Get user data from {}", USER_LIST_URL);
		try {
			ResponseEntity<KcgUserList> rs = myAccountTemplate.exchange(USER_LIST_URL, HttpMethod.GET, null, KcgUserList.class);
			List<KgoUser> updateUserList = new ArrayList<>();

			if (HttpStatus.OK.equals(rs.getStatusCode()) && rs.hasBody()) {
				LOGGER.info("Get user data success...");
				KcgUserList kcgUserList = rs.getBody();
				List<KcgUserInfo> syncUserList = kcgUserList.getUserList();
				List<KgoUser> kgoUserList = kgoUserRepository.findAll();

				for (KcgUserInfo syncUserInfo : syncUserList) {
					if (syncUserInfo.getStatus().equals("0"))
						continue; // 跳過狀態為停用

					Optional<KgoUser> hasKgoUser = kgoUserList.stream().filter(user -> user.getUserId().equals(syncUserInfo.getUserLoginID())).findFirst();
					if (!hasKgoUser.isPresent()) {
						KgoUser kgoUser = new KgoUser();
						kgoUser.setUserId(syncUserInfo.getUserLoginID());
						kgoUser.setCreateUser(UPDATER);
						kgoUser.setCreateTime(DateUtil.getCurrentTimestamp());
						kgoUser.setOrgan(syncUserInfo.getDeptCode());
						kgoUser.setUnit("");
						kgoUser.setName(syncUserInfo.getUserName());

						updateUserList.add(kgoUser);
					}else {
						KgoUser kgoUser = hasKgoUser.get();
						try {
							
							boolean isSave = false;
							if (kgoUser != null && syncUserInfo != null) {
								if (kgoUser.getName() != null && !kgoUser.getName().equals(syncUserInfo.getUserName())) {
									kgoUser.setName(syncUserInfo.getUserName());
									isSave = true;
								}
										
								
								if (kgoUser.getOrgan() != null && !kgoUser.getOrgan().equals(syncUserInfo.getDeptCode())) {
									kgoUser.setOrgan(syncUserInfo.getDeptCode());
									kgoUser.setUnit("");
									isSave = true;
								}
								
								if (isSave) {
									kgoUser.setUpdateUser(UPDATER);
									kgoUser.setUpdateTime(DateUtil.getCurrentTimestamp());
									updateUserList.add(kgoUser);
								}
							}
						} catch (Exception e) {
							LOGGER.info(String.format("Get user data failed...(%s)", syncUserInfo.getUserLoginID()));
						}
						
					}
				}
				kgoUserRepository.saveAll(updateUserList);
				LOGGER.info(String.format("update user data completed (%d)", updateUserList.size()));

			} else {
				LOGGER.info("Get user data failed...");
			}

		} catch (Exception e) {
			LOGGER.error("Update KgoUser by Scheduling failed ...", e);
		}
	}

	@Scheduled(cron = "00 00 01 ? * *") // FIXME: 待確認
	public void updateKgoHoliday() {
		LOGGER.info(">>>>> Scheduled updateKgoHoliday() begin ");
		try {
			int page = 0;
			while (true) {
				String url = String.format("https://data.ntpc.gov.tw/api/datasets/308DCD75-6434-45BC-A95F-584DA4FED251/json?page=%s&size=1000", page);
				HttpRequest httpRequest = new HttpRequest();
				Map<String, String> headersMap = new HashMap<>();
				String responseStr = httpRequest.sendGet(url);
				HolidayInfo[] rs = JsonUtil.getObject(responseStr, HolidayInfo[].class);

				if (ObjectUtils.isEmpty(rs)) {
					break;
				} else {
					LOGGER.info(String.format(">>>>> Scheduled updateKgoHoliday(page:%s) ,Count: %s ", page, rs.length));
				}

				for (HolidayInfo item : rs) {
					if (item.getIsHoliday().equalsIgnoreCase("是")) {
						Date holidayDate = DateUtil.strToDate(item.getDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH);
						KgoHoliday kgoHoliday;
						Optional<KgoHoliday> optionalKgoHoliday = kgoHolidayRepository.findByHolidayDate(holidayDate);
						if (optionalKgoHoliday.isPresent()) {
							kgoHoliday = optionalKgoHoliday.get();
						} else {
							kgoHoliday = new KgoHoliday();
						}

						kgoHoliday.setHolidayDate(holidayDate);
						kgoHoliday.setName(StringUtils.firstNonBlank(item.getName(), item.getHolidayCategory()));

						kgoHolidayRepository.save(kgoHoliday);
					}
				}

				page++;
			}
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(CoinCityApiError.FAIL_TO_SEND.getErrorMsgKey());
			throw new KgoApiException("updateKgoHoliday error " + e.getMessage(), e);
		}

		LOGGER.info(">>>>> Scheduled updateKgoHoliday() End ");
	}

	/**
	 * GEO 20211201 add
	 * 每日03:00AM 刪除 myData附件檔案
	 */
	@Scheduled(cron = "0 0 3 * * *")
	public void deleteKgoMyDataFile() {

		LOGGER.info(">>>>> Scheduled deleteKgoMyDataFile() begin ");
		try {
			String UPDATER = "updateKgoUser";
			Timestamp now = DateUtil.getCurrentTimestamp();
			Timestamp beforeTime =  DateUtil.modifyDate(now,-2,0,0);
			LOGGER.info("deleteKgoMyDataFile beforeTime: " + DateUtil.timestampToString(beforeTime, DateUtil.PATTEN_YEAR_MONTH_DAY));
			List<KgoMydataFile> fileList = geoKgoMyDataFileCustom.findExpiredKgoMyDataFile(CaseMainStatusEnum.F3.getValue(),
					DateUtil.timestampToString(beforeTime, DateUtil.PATTEN_YEAR_MONTH_DAY));
			if (fileList != null && fileList.size() >0) {
				for (KgoMydataFile file : fileList) {
					file.setMyDataFile(null);
					file.setUpdateTime(now);
					file.setUpdateUser(UPDATER);
					LOGGER.info(">>>>> Scheduled deleteKgoMyDataFile() TxId: " + file.getTxId());
				}
				kgoMydataFileRepository.saveAll(fileList);
			}
		} catch (Exception e) {
			LOGGER.error("deleteKgoMyDataFile By Scheduling failed ...", e);
		}
		LOGGER.info(">>>>> Scheduled deleteKgoMyDataFile() End ");
	} //deleteKgoMyDataFile

	/**
	 * GEO 20221028 add
	 * 更新 民政局黑名單資料 來源 城市資料平台
	 * 每日01:00
	 */
	@Scheduled(cron = "0 0 1 * * *")
	public void updateBlackList() {
		try {
			LOGGER.info("updateBlackList ...民政局黑名單更新結束 start");
			//取得城市資料平台授權
			GeoCityForDepartmentCivil civil = geoKcgCityExtService.sendPostApiForCity();
			if (civil!=null){
				if (civil.getBlackList()!=null){
					//刪除後再更新
					geoKgoBlackListRepository.deleteAll();
					for (GeoCityForDepartmentCivilPerson p:civil.getBlackList()){
						GeoKgoBlackList kgoBlackList = new GeoKgoBlackList();
						//進行解密
						String name = AESUtil.cbcPkcs7Decrypt(p.getName(),GeoCityForDepartmentCivil.getAesKey(),civil.getIvStr());
						String id  = AESUtil.cbcPkcs7Decrypt(p.getIdentityId(),GeoCityForDepartmentCivil.getAesKey(),civil.getIvStr());
						kgoBlackList.setUserId(id);
						kgoBlackList.setUserName(name);
						kgoBlackList.setCreateTime(new Timestamp(System.currentTimeMillis()));
						geoKgoBlackListRepository.save(kgoBlackList);
					} //for (GeoCityForDepartmentCivilPerson p:civil.getBlackList())
				} //if (civil.getBlackList()!=null)
			} //if (civil!=null)
			LOGGER.info("updateBlackList ...民政局黑名單更新結束 end");
		} catch (Exception e) {
			LOGGER.error("Update updateBlackList by Scheduling failed ...", e);
		} //f (civil!=null)
	} //public void updateBlackList()


	/**
	 * 更新每日繳費狀態
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void updateRentalCaseStatus(){
		try{
			//繳費狀態:待繳費 且 預約狀態：待處理 - 須繳費案件於預約結案日起算，超過繳費期限設定日期則取消預約
			LOGGER.info("updateRentalCaseStatus ...更新每日繳費狀態 start");
			List<String> overtimeCaseIds = geoKgoRentPaymentRepos.findRentCaseOverPayDeadLine().stream()
					.map(GeoKgoCaseRentPayment::getCaseId)
					.collect(Collectors.toList());
			List<GeoKgoCaseRentRelation> rentRelationList = overtimeCaseIds.stream()
					.map(caseId ->geoCaseRentRelationRepos.findByCaseId(caseId))
					.filter(relationEntity ->relationEntity.getRentStatus().equals(RentStatusEnum.PROC.getValue()))
					.map(entity->{
						entity.setRentStatus(RentStatusEnum.FAIL.getValue());
						entity.setEditTime(new Timestamp(System.currentTimeMillis()));
						return entity;
					}).collect(Collectors.toList());
			String[] cases = rentRelationList.stream().map(GeoKgoCaseRentRelation::getCaseId).toArray(String[]::new);
			LOGGER.info("update rentalCase status to FAIL ...caseId :" + Arrays.toString(cases));
			geoCaseRentRelationRepos.saveAll(rentRelationList);
			LOGGER.info("updateRentalCaseStatus ... end ");
			//TODO:變更為預約失敗之案件通知

		}catch (Exception e ){
			LOGGER.error("Update updateRentalCaseStatus by Scheduling failed ...", e);
		}
	}

	/**
	 * 刪除檔案及db廢棄資料(每月執行一次)
	 */
	@Scheduled(cron = "0 0 0 L * ?")
	public void cleanAbandonData(){
		//清除已刪除之場地附件檔案
		LOGGER.info(">>>>>  schedule monthly cleanAbandonData start   >>>>>");
		List<GeoKgoCaseSetSiteMainFile> siteFileList = geoCaseSetSiteMainFileRepos.findByIsDelete(true);
		LOGGER.info("clean File List size :" + siteFileList.size());
		for(GeoKgoCaseSetSiteMainFile siteFile : siteFileList){
			String filedir = MessageFormat.format(SpringUtil.getProperty("caseSet.site.upload.file.path"),siteFile.getSiteMainId());
			try {
				File deleteFile = new File(filedir, siteFile.getFileName());
				deleteFile.delete();
			}catch(Exception e){
				LOGGER.info("clean siteFile ID:" + siteFile.getSiteMainId() + " Name:" + siteFile.getFileName() + " fail");
			}
		}

		//刪除廢棄預約時段
		List<GeoKgoCasesetRentTime> timeList = geoKgoCasesetRentTimeRepository.findByIsAbandon(1);
		LOGGER.info("clean abandon timeList size:" + timeList.size());
		for(GeoKgoCasesetRentTime timeEntity : timeList){
			List bookedList = geoCaseRentRelationRepos.checkBookedRentTime(timeEntity.getRentTimeId());
			if(bookedList.isEmpty()){
				geoKgoCasesetRentTimeRepository.delete(timeEntity);
			}
		}


	}

}
