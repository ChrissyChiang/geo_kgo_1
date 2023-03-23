package gov.kcg.kgo.service.impl.helper;

import static gov.kcg.kgo.constant.GeneralConstant.USERNAME_SINGLE_SIGN_ON;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.georepository.GeoKgoHcaOrganRepository;
import gov.kcg.kgo.georepository.custom.GeoBaseReposCustom;
import gov.kcg.kgo.georepository.custom.GeoKgoCasesetAllowOrganCustom;
import gov.kcg.kgo.georepository.custom.GeoKgoUserCustom;
import gov.kcg.kgo.geoutil.GeoStringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.dto.KgoSysMenuDto;
import gov.kcg.kgo.dto.kgoEmp.KgoEmpDto;
import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoCasesetAllowOrgan;
import gov.kcg.kgo.model.KgoOrgan;
import gov.kcg.kgo.model.KgoUnit;
import gov.kcg.kgo.model.KgoUnitPK;
import gov.kcg.kgo.model.KgoUser;
import gov.kcg.kgo.model.KgoUserRole;
import gov.kcg.kgo.model.KgoUserRolePK;
import gov.kcg.kgo.repository.KgoOrganRepository;
import gov.kcg.kgo.repository.KgoUnitRepository;
import gov.kcg.kgo.repository.KgoUserRepository;
import gov.kcg.kgo.repository.KgoUserRoleRepository;
import gov.kcg.kgo.service.AuthService;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.HttpRequest;
import gov.kcg.kgo.util.JsonUtil;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.util.XmlUtil;
import gov.kcg.kgo.viewModel.backend.auth.getSysMenu.rs.bean.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 授權、身分驗證等 ServiceHelper.
 */
public class AuthServiceHelper extends KgoServiceHelper {
	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceHelper.class);
	@Autowired
	private GeoKgoHcaOrganRepository geoKgoHcaOrganRepository;
	@Autowired
	private GeoKgoCasesetAllowOrganCustom geoKgoCasesetAllowOrganCustom;

	/**
	 * 更新市府員工資料.
	 */
	public KgoUser updateKgoEmpData(BackendLoginUserInfo beloginUser) {
		LOGGER.info("AuthServiceHelper updateKgoEmpData...");
//		String idNo = "E122520953";
		// 員工ID (市府員工帳號)
		String userId = beloginUser.getUserId();

		// 身分證號
		String twSSn = beloginUser.getKcgUserBasicInfo().getAppUserTwSSn();
		KgoUser kgoUser = null;

		try {
			KgoUserRepository kgoUserRepository = SpringUtil.getDao(KgoUserRepository.class);
			Optional<KgoUser> optional = kgoUserRepository.findById(userId);

			// 市府員工資料存在
			if (optional.isPresent()) {
				kgoUser = optional.get();
//				 return kgoUser;
			}
			// 信箱為空，更新信箱 UserId+@kcg.gov.tw
			if (ObjectUtils.isNotEmpty(kgoUser) && StringUtils.isBlank(kgoUser.getEmail())) {
				String email = String.format("%s@kcg.gov.tw", kgoUser.getUserId());
				kgoUser.setEmail(email);
				kgoUser = kgoUserRepository.save(kgoUser);
				LOGGER.info("UpdateKgoUserEmailAndPublicEmail : " + email);
			}
			if (StringUtils.isBlank(twSSn)) {
				LOGGER.error("updateKgoEmpData 更新市府員工資料 error, 身分證號為空");
				return kgoUser;
			}

			// TODO 暫不 更新市府員工資料
			if (!this.isDevMode()) {
				LOGGER.info(">>>>> updateKgoEmpData() 更新市府員工資料 userId = {} , twSSn = {}", userId, twSSn);
				// 1. 取得授權token
				String token = getAuthToken();

				// 2. 取得市府員工資料 (Base64)
				String empDataBase64Str = getEmpData(token, twSSn);

				// 解碼 取得市府員工資料 json string
				String empJsonData = new String(Base64.getDecoder().decode(empDataBase64Str), "UTF-8");

				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<KgoEmpDto>>() {
				}.getType();
				List<KgoEmpDto> kgoEmpDtoList = gson.fromJson(empJsonData, listType);

				if (CollectionUtils.isEmpty(kgoEmpDtoList)) {
					LOGGER.info(">>>>> KgoEmpDto : 無 kgoEmpDtoList");
					// return null;
					// 登入錯誤 - 更新市府員工資料失敗.
//		        	throw new KgoApiException(new ErrorResult(KgoBackEndApiError.UPDATE_KGO_USER_ERROR));
				} else {
					// rs 只有一筆員工資料
					KgoEmpDto kgoEmpDto = kgoEmpDtoList.get(0);
					LOGGER.info(">>>>> KgoEmpDto : {}", JsonUtil.toJSONString(kgoEmpDto));
					Timestamp now = DateUtil.getCurrentTimestamp();

					// 資料不存在，新增至資料庫
					if (kgoUser == null) {
						kgoUser = new KgoUser();
						kgoUser.setUserId(userId);
						kgoUser.setCreateTime(now);
						kgoUser.setCreateUser(USERNAME_SINGLE_SIGN_ON);
						kgoUser.setOrgan(kgoEmpDto.getB02SORCOD());
						kgoUser.setUnit(kgoEmpDto.getB02UNICOD());

						// 更新至資料庫
					} else {
						kgoUser.setOrgan(kgoEmpDto.getB02SORCOD());
						kgoUser.setUnit(kgoEmpDto.getB02UNICOD());
						kgoUser.setUpdateTime(now);
						kgoUser.setUpdateUser(USERNAME_SINGLE_SIGN_ON);
					}
					kgoUser = kgoUserRepository.save(kgoUser);

					addKgoOrgan(kgoEmpDto.getB02SORCOD());
					addKgoUnit(kgoEmpDto.getB02SORCOD(), kgoEmpDto.getB02UNICOD());
				}

				LOGGER.info(">>>>>" + this.getClass().getName() + " updateKgoEmpData() 更新市府員工資料,  userId: " + userId);
			}
			return kgoUser;
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			return kgoUser;
//			throw apiE;
		} catch (Exception e) {
			LOGGER.error("updateKgoEmpData 更新市府員工資料 error, userId: " + userId + " " + e.getMessage());
			return kgoUser;
//            throw new KgoApiException("updateKgoEmpData 更新市府員工資料 error, twSSn: " + twSSn + " " + e.getMessage(), e);
		}
	}

	/**
	 * GEO 20211115 Add
	 * 確認登入的非市府員工資料.
	 * @param beloginUser
	 * @return
	 */
	public KgoUser checkCityMemberData(BackendLoginUserInfo beloginUser) {
		LOGGER.info("AuthServiceHelper checkCityMemberData...");
		String userValidate = getUserValidate(beloginUser);
		String userLoginType = beloginUser.getLoginAuthTokenType().getType();
		KgoUser kgoUser = null;
		String userId = null;
		try {
			KgoUserRepository kgoUserRepository = SpringUtil.getDao(KgoUserRepository.class);
			kgoUser= kgoUserRepository.findByUserValidateAndUserLoginType(userValidate, userLoginType);
			// 資料不存在，新增至資料庫
			LOGGER.info("AuthServiceHelper checkCityMemberData kgoUser: " + kgoUser);
			if (kgoUser == null) {
				LOGGER.info("AuthServiceHelper checkCityMemberData kgoUser == null: " + (kgoUser == null));
				//儲存 該非市府員工 KGO_USER資料
				Timestamp now = DateUtil.getCurrentTimestamp();
				kgoUser = new KgoUser();
				GeoKgoUserCustom geoBaseReposCustom = SpringUtil.getBean(GeoKgoUserCustom.class);
				userId = geoBaseReposCustom.getNextTableId(GeoStringUtil.BACKEND_USER_PREFIX_CHAR, "KGO_USER","UserId");
				LOGGER.info("AuthServiceHelper checkCityMemberData userId: " + userId);

				kgoUser.setUserId(userId);
				kgoUser.setCreateTime(now);
				kgoUser.setCreateUser(userId);
				kgoUser.setOrgan("");
				kgoUser.setUnit("");
				kgoUser.setUserValidate(userValidate);
				kgoUser.setUserLoginType(userLoginType);
				kgoUser = kgoUserRepository.save(kgoUser);
				LOGGER.info("AuthServiceHelper checkCityMemberData kgoUser: " + kgoUser);
				
				//給予內建好孕行得通健保卡登入-非市府單位之機關值
	            List<GeoKgoCasesetAllowOrgan> listOrgans = geoKgoCasesetAllowOrganCustom.findAllowOrganByCasesetId(SpringUtil.getProperty("pregnantTraffic.caseset"), null, true);
	            List<String> allowOrgans = new ArrayList<>();
	            if(listOrgans.size() != 0) {                
	                for(int i=0;i<listOrgans.size();i++) {
	                    allowOrgans.add(listOrgans.get(i).getOrganId());
	                } 
	            }    
                if(LoginAuthTokenType.HCA == beloginUser.getLoginAuthTokenType()) {
                        String cardNumber = beloginUser.getKcgHcaCardSsoInfo().getHcaCardNumber();
                        if(cardNumber != null) {
                            List<String> myOrgans = geoKgoHcaOrganRepository.findByHcaCardNumber(cardNumber).stream().map(x -> x.getOrganId()).collect(Collectors.toList());
                            if(myOrgans.size() != 0) {
                                if (allowOrgans.contains(myOrgans.get(0))) { 
                                	kgoUser.setOrgan(myOrgans.get(0));
                                	LOGGER.info("#設定非市府員工登入之機關:"+myOrgans.get(0));
                                }
                            }    
                        }   
                }

				//儲存 該非市府員工 預設角色
				KgoUserRoleRepository kgoUserRoleRepository = SpringUtil.getDao(KgoUserRoleRepository.class);
				String roleUnitU = KgoRoleEnum.UNIT_U.getValue();
				KgoUserRole userRole = new KgoUserRole();
				KgoUserRolePK pk = new KgoUserRolePK();
				userRole.setId(pk);
				pk.setUserId(userId);
				pk.setRoleId(roleUnitU);
				kgoUserRoleRepository.save(userRole);
				LOGGER.info("AuthServiceHelper checkCityMemberData roleUnitU: " + roleUnitU);
			} //if (kgoUser == null)
			return kgoUser;
		} catch (Exception e) {
			LOGGER.error("updateKgoEmpData 更新市府員工資料 error, userId: " + userId + " " + e.getMessage());
			LOGGER.info("AuthServiceHelper checkCityMemberData e: " + e.getMessage());
			return kgoUser;
//            throw new KgoApiException("updateKgoEmpData 更新市府員工資料 error, twSSn: " + twSSn + " " + e.getMessage(), e);
		}
	}//checkCityMemberData

	/**
	 * 取得 儲存非市府員工至KGO_USER的驗證字串
	 * @param loginUserInfo
	 * @return
	 */
	private String getUserValidate(BackendLoginUserInfo loginUserInfo) {
		String str = null;
		switch (loginUserInfo.getLoginAuthTokenType()) {
			case BASIC:
				str = loginUserInfo.getKcgUserBasicInfo().getAppUserLoginId() +
						loginUserInfo.getKcgUserBasicInfo().getAppUserTwSSn();
				break;
			case HCA:
				str = loginUserInfo.getKcgHcaCardSsoInfo().getHcaUserName() +
						loginUserInfo.getKcgHcaCardSsoInfo().getHcaUserTwSsn();
				break;
			case EGOV:
				str = loginUserInfo.getKcgEgovInfo().getEgovUserCn() +
						loginUserInfo.getKcgEgovInfo().getEgovUserUid();
				break;
			case LINE:
				str = loginUserInfo.getLineInfo().getLineUserId();
				break;
			case MOICA:
				str = loginUserInfo.getKcgMoicaCardInfo().getMoicaUserName() +
						loginUserInfo.getKcgMoicaCardInfo().getMoicaUserTwSsn();
				break;
			case GOOGLE:
				str = loginUserInfo.getKcgGoogleSsoInfo().getGoogleUserAccount();
				break;
			case TW_FIDO:
				str = loginUserInfo.getKcgTwFidoSsoInfo().getTwfidoUserTwSSn();
				break;
			case FACEBOOK:
				str = loginUserInfo.getKcgFacebookSsoInfo().getFacebookUserAccount();
				break;
		}
		return str;
	} //getUserValidate



	/**
	 * 新增組織
	 * 
	 * @param organId
	 */
	private void addKgoOrgan(String organId) {
		KgoOrganRepository repository = SpringUtil.getDao(KgoOrganRepository.class);
		Optional<KgoOrgan> optional = repository.findById(organId);
		if (!optional.isPresent()) {
			KgoOrgan kgoOrgan = new KgoOrgan();
			kgoOrgan.setOrganId(organId);
			kgoOrgan.setOrganName(organId);
			kgoOrgan.setCreateUser(USERNAME_SINGLE_SIGN_ON);
			kgoOrgan.setCreateTime(DateUtil.getCurrentTimestamp());

			repository.save(kgoOrgan);
		}
	}

	/**
	 * 新增 單位
	 * 
	 * @param organId
	 * @param unitId
	 */
	private void addKgoUnit(String organId, String unitId) {
		KgoUnitRepository repository = SpringUtil.getDao(KgoUnitRepository.class);
		KgoUnitPK kgoUnitPK = new KgoUnitPK();
		kgoUnitPK.setOrganId(organId);
		kgoUnitPK.setUnitId(unitId);
		Optional<KgoUnit> optional = repository.findById(kgoUnitPK);
		if (!optional.isPresent()) {
			KgoUnit kgoUnit = new KgoUnit();
			kgoUnit.setId(kgoUnitPK);
			kgoUnit.setUnitName(unitId);
			kgoUnit.setCreateUser(USERNAME_SINGLE_SIGN_ON);
			kgoUnit.setCreateTime(DateUtil.getCurrentTimestamp());

			repository.save(kgoUnit);
		}

	}

	/**
	 * 取得授權token.
	 *
	 * @return the auth token
	 * @throws Exception the exception
	 */
	private String getAuthToken() throws Exception {
		HttpRequest httpRequest = new HttpRequest();

		// 1.取得授權token
		String qryString = "Org=397210000A&ServiceID=INF_HR26";
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Content-Length", "" + qryString.getBytes().length);

		String tokenXmlStr = httpRequest.sendPost("https://ikpd.kcg.gov.tw/ckpd/PCSPService.asmx/GetAuth", qryString, headers);

		// 解析KcgService xml 內容.
		String token = XmlUtil.loadKcgServiceXMLStr(tokenXmlStr);
		return token;
	}

	/**
	 * 取得單位資料.
	 *
	 * @return the unit data
	 * @throws Exception
	 */
	private String getEmpData(String token, String b01IdNo) throws Exception {

		HttpRequest httpRequest = new HttpRequest();
		String qryString = "Token=" + token + "&ServiceID=INF_HR26&QryPara={\"B01IDNO\":\"" + b01IdNo + "\"}";
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Content-Length", "" + qryString.getBytes().length);

		String empDataXmlStr = httpRequest.sendPost("https://ikpd.kcg.gov.tw/ckpd/PCSPService.asmx/GetDATA", qryString, headers);
		// 解析KcgService xml 內容.
		String empData = XmlUtil.loadKcgServiceXMLStr(empDataXmlStr);
		return empData;
	}

	/**
	 *
	 * 設置 市府員工資料 (市府人員 姓名、機關、單位、角色清單).
	 *
	 * @param beloginUser the belogin user
	 * @return the backend login user info
	 */
	public BackendLoginUserInfo setBeUserDetail(BackendLoginUserInfo beloginUser) {
		String userId = beloginUser.getUserId();
		KgoUser kgoUser;

		/** GEO 20211115 add 非市府員工登入後臺 */
        if(LoginAuthTokenType.BASIC.equals(beloginUser.getLoginAuthTokenType())){
            kgoUser = updateKgoEmpData(beloginUser); // 更新市府員工資料.
        }else{
			kgoUser = checkCityMemberData(beloginUser);// 確認登入的非市府員工資料.
			userId = kgoUser.getUserId();
			beloginUser.setUserId(userId);
		}

		if (kgoUser != null) {
			LOGGER.info("#後台設定session-name:"+kgoUser.getName());
			LOGGER.info("#後台設定session-Organ:"+kgoUser.getOrgan());
			// 市府 & 非市府人員 姓名、機關、單位 存入session
			beloginUser.setName(kgoUser.getName());
			beloginUser.setOrgan(kgoUser.getOrgan());
			beloginUser.setUnit(kgoUser.getUnit());
			
		}
		// 設置後台使用者角色清單.
		beloginUser.setRoles(getUserRoles(userId));
		return beloginUser;
	} //setBeUserDetail

	/**
	 * 取得 後台使用者角色清單.
	 *
	 * @param userId the user id
	 * @return the user roles
	 */
	public List<String> getUserRoles(String userId) {
		KgoUserRoleRepository kgoUserRoleRepository = SpringUtil.getDao(KgoUserRoleRepository.class);
		List<KgoUserRole> userRoles = kgoUserRoleRepository.findAllByIdUserId(userId);
		List<String> roles = userRoles.stream().map(r -> r.getId().getRoleId()).collect(Collectors.toList());

		/** 2020.12.11 角色為空 就預設角色為承辦人員 */
		if (CollectionUtils.isEmpty(roles)) {
			String roleUnitU = KgoRoleEnum.UNIT_U.getValue();
			KgoUserRole userRole = new KgoUserRole();
			KgoUserRolePK pk = new KgoUserRolePK();
			userRole.setId(pk);
			pk.setUserId(userId);
			pk.setRoleId(roleUnitU);
			kgoUserRoleRepository.save(userRole);
			roles.add(roleUnitU);
		}
		return roles;
	}

	/**
	 * 取得系統樹狀選單.
	 *
	 * @param menuDtoList the sys func list
	 * @return the menu list
	 */
	public List<SysMenu> getMenuList(List<KgoSysMenuDto> menuDtoList) {
		List<SysMenu> list = new ArrayList<SysMenu>();
		for (KgoSysMenuDto dto : menuDtoList) {
			SysMenu rootMenu = new SysMenu();

			if (dto.getfSeq() == 0) {
				rootMenu.setSeq(dto.getSeq());
				rootMenu.setfSeq(dto.getfSeq());
				rootMenu.setName(dto.getName());
				rootMenu.setFunctionId(dto.getFunctionId());
				rootMenu.setUrl(dto.getUrl());
				rootMenu.setOrderNum(dto.getOrderNum());
				rootMenu.setChilds(menuChild(dto.getSeq(), menuDtoList));

				list.add(rootMenu);
			}
		}
		return list;
	}

	/**
	 * 設置子選單.
	 *
	 * @param seq         the f seq
	 * @param menuDtoList the sys func list
	 * @return the list
	 */
	private List<SysMenu> menuChild(int seq, List<KgoSysMenuDto> menuDtoList) {
		List<SysMenu> lists = new ArrayList<SysMenu>();
		for (KgoSysMenuDto dto : menuDtoList) {
			SysMenu child = new SysMenu();
			if (dto.getfSeq() == seq) {
				child.setSeq(dto.getSeq());
				child.setfSeq(dto.getfSeq());
				child.setName(dto.getName());
				child.setFunctionId(dto.getFunctionId());
				child.setUrl(dto.getUrl());
				child.setOrderNum(dto.getOrderNum());
				child.setChilds(menuChild(dto.getSeq(), menuDtoList));
				lists.add(child);
			}
		}
		return lists;
	}

	/**
	 * Instantiates a new common service helper.
	 */
	public AuthServiceHelper() {

	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final AuthServiceHelper instance = new AuthServiceHelper();
	}

	/**
	 * Gets the single instance of CommonServiceHelper.
	 *
	 * @return single instance of CommonServiceHelper
	 */
	public static AuthServiceHelper getInstance() {
		return Loader.instance;
	}
}
