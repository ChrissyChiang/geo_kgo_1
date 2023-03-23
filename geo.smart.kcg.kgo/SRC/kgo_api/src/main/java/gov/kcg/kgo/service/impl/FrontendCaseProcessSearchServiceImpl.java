package gov.kcg.kgo.service.impl;

import java.io.File;
import java.time.*;
import java.util.Date;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.kcg.kgo.dto.SaCaseViewOrganFormDetailColumnQueryDto;
import gov.kcg.kgo.enums.backend.*;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.geoentity.GeoKgoCaseRentRelation;
import gov.kcg.kgo.geoenum.CaseSetCategoryEnum;
import gov.kcg.kgo.geoenum.GeoBooleanType;
import gov.kcg.kgo.geomodel.CaseSetRentalCategoryGrid;
import gov.kcg.kgo.georepository.GeoCaseRentRelationRepos;
import gov.kcg.kgo.georepository.custom.GeoKgoCasesetDetailOrganReposCustom;
import gov.kcg.kgo.geoservice.GeoCaseSetRentService;
import gov.kcg.kgo.geoutil.GeoApi1999Properties;
import gov.kcg.kgo.geoutil.GeoStringUtil;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.service.impl.helper.CaseHandleServiceHelper;
import gov.kcg.kgo.util.*;
import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseViewCaseHistoryDataGrid;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean.CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean.CaseHandleCaseViewSaCaseOrganFormApplyDataDataGridComplex;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.dto.CaseCorrectDataDto;
import gov.kcg.kgo.dto.CaseProcessSearchDto;
import gov.kcg.kgo.dto.SaCaseViewQueryDto;
import gov.kcg.kgo.dto.Activiti.HistoryDataDto;
import gov.kcg.kgo.enums.common.ColumnTypeEnum;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.common.YesNoFlag;
import gov.kcg.kgo.enums.error.CaseProcessSearchApiError;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.front.FrontendFunctionCodeEnum;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.service.ActivitiService;
import gov.kcg.kgo.service.FrontendCaseProcessSearchService;
import gov.kcg.kgo.service.PushService;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rq.CaseProcessSearchDetailCheckRq;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rq.CaseProcessSearchDetailRq;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rq.CaseProcessSearchDetailSaveRq;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rq.CaseProcessSearchQueryRq;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rq.CaseProcessSearchValidateRq;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rq.bean.CaseProcessSearchDetailSaveColumn;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.CaseProcessSearchDetailCheckRs;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.CaseProcessSearchDetailRs;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.CaseProcessSearchDetailSaveRs;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.CaseProcessSearchHomeRs;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.CaseProcessSearchQueryRs;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.CaseProcessSearchValidateRs;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseMainStatus;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchDetailCaseset;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchDetailCasesetColumn;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchDetailCasesetColumnChild;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchDetailCasesetGroup;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchDetailCheckViewForm;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchDetailViewForm;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchHistoryDataGrid;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchHomeViewForm;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchQueryViewForm;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchValidateDataGrid;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchValidateViewForm;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

@Service
public class FrontendCaseProcessSearchServiceImpl extends KgoFrontEndServiceImpl implements FrontendCaseProcessSearchService {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FrontendCaseProcessSearchServiceImpl.class);

	private CaseHandleServiceHelper caseHandleServiceHelper = CaseHandleServiceHelper.getInstance();

	@Autowired
	private KgoCaseMainRepository kgoCaseMainRepository;

	@Autowired
	private KgoCasesetRepository kgoCasesetRepository;

	@Autowired
	private KgoCaseDetailRepository kgoCaseDetailRepository;

	@Autowired
	private KgoCasesetColumnRepository kgoCasesetColumnRepository;

	@Autowired
	private KgoCaseMainResendFlowRepository kgoCaseMainResendFlowRepository;

	@Autowired
	GeoKgoCasesetDetailOrganReposCustom geoKgoCasesetDetailOrganReposCustom;

	@Autowired
	private KgoParamSetRepository kgoParamSetRepository;

	@Autowired
	private CaseHandleServiceImpl caseHandleService;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private PushService pushService;

	@Autowired
	private MailUtil mailUtil;

	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ActivitiService activitiService;

	@Autowired
	private KgoCorrectRecordRepository kgoCorrectRecordRepository;

	@Autowired
	private KgoUserRepository kgoUserRepository;

	@Autowired
	private GeoCaseSetRentService geoCaseSetRentService;

	@Value("${fontend.case.search.link}")
	private String fontendCaseSearchLink;

	@Value("${backend.case.search.link}")
	private String backendCaseSearchLink;

	/** GEO 20211010 add for 1999 service **/
	@Autowired
	private GeoApi1999Properties geoApi1999Properties;

	@Autowired
	GeoCaseRentRelationRepos geoCaseRentRelationRepos;

	@Override
	public CaseProcessSearchHomeRs homeAction() {
		CaseProcessSearchHomeRs caseProcessSearchHomeRs = new CaseProcessSearchHomeRs();
		try {
			// 取得狀態下拉選單
			CaseProcessSearchHomeViewForm caseProcessSearchHomeViewForm = new CaseProcessSearchHomeViewForm();
			Map<String, String> values = Arrays.asList(CaseMainStatusEnum.values()).stream()
					.collect(Collectors.groupingBy(CaseMainStatusEnum::getLabel, Collectors.mapping(CaseMainStatusEnum::getValue, Collectors.joining(","))));
			;

			List<CaseMainStatus> caseMainStatusList = new ArrayList<>();
			for (Map.Entry<String, String> value : values.entrySet()) {
				CaseMainStatus caseMainStatus = new CaseMainStatus();
				caseMainStatus.setLabel(value.getKey());
				caseMainStatus.setValue(value.getValue());
				caseMainStatusList.add(caseMainStatus);
			}
			caseProcessSearchHomeViewForm.setCaseMainStatusList(caseMainStatusList);
			caseProcessSearchHomeRs.setData(caseProcessSearchHomeViewForm);
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("homeAction error " + e.getMessage(), e);
		}
		return caseProcessSearchHomeRs;
	}

	@Override
	public CaseProcessSearchValidateRs validateAction(CaseProcessSearchValidateRq rq) {
		CaseProcessSearchValidateRs caseProcessSearchValidateRs = new CaseProcessSearchValidateRs();
		CaseProcessSearchValidateViewForm caseProcessSearchValidateViewForm = new CaseProcessSearchValidateViewForm();
		try {
			// 按照案件編號、身分證字號後四碼取得案件
			KgoCaseMain kgoCaseMain = kgoCaseMainRepository.findByCaseIdAndIdCheck(rq.getCaseId(), rq.getIdCheck());
			if (null != kgoCaseMain) {
				Optional<KgoCaseset> kgoCasesetOptional = kgoCasesetRepository.findById(kgoCaseMain.getCaseSetId());
				if (kgoCasesetOptional.isPresent()) {
					KgoCaseset kgoCaseset = kgoCasesetOptional.get();
					// 轉換為前端物件
					CaseProcessSearchValidateDataGrid caseProcessSearchValidateDataGrid = new CaseProcessSearchValidateDataGrid();
					caseProcessSearchValidateDataGrid.setCaseSetName(kgoCaseset.getCaseSetName());
					caseProcessSearchValidateDataGrid.setApplyDate(DateUtil.dateToString(kgoCaseMain.getApplyDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
					caseProcessSearchValidateDataGrid.setCaseId(kgoCaseMain.getCaseId());
					CaseMainStatusEnum caseMainStatusEnum = CaseMainStatusEnum.getCaseMainStatusEnum(kgoCaseMain.getStatus());
					if (null != caseMainStatusEnum) {
						caseProcessSearchValidateDataGrid.setStatus(caseMainStatusEnum.getLabel());
					}
					caseProcessSearchValidateViewForm.setGrids(Collections.singletonList(caseProcessSearchValidateDataGrid));
				}
			} else {
				caseProcessSearchValidateViewForm.setGrids(new ArrayList<>());
			}
			caseProcessSearchValidateRs.setData(caseProcessSearchValidateViewForm);
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("validateAction error " + e.getMessage(), e);
		}
		return caseProcessSearchValidateRs;
	}

	@Override
	public CaseProcessSearchQueryRs queryAction(CaseProcessSearchQueryRq rq) {
		CaseProcessSearchQueryRs caseProcessSearchValidateRs = new CaseProcessSearchQueryRs();
		CaseProcessSearchQueryViewForm caseProcessSearchValidateViewForm = new CaseProcessSearchQueryViewForm();
		KgoApiException error = null;
		OperationApiMemo memo = null;
		try {
			// 前台、案件查詢
			memo = super.genOperationMemo(SystemTypeEnum.F, null, FrontendFunctionCodeEnum.CaseQry);
			// 驗證參數並設定分頁預設值
			this.validateParameter(rq);
			// 取得分頁請求
//			PageRequest pageRequest = PageRequest.of(rq.getPageNumber(), rq.getPageSize());
			// 取得登入者資訊
			FrontendLoginUserInfo frontendLoginUser = KgoUtil.getFrontendLoginUser();

			LOGGER.warn("登入者資訊:{}", new ObjectMapper().writeValueAsString(frontendLoginUser));

			String twSSn = frontendLoginUser.getTwSSn();
			String email = frontendLoginUser.getEmail();
			String userAccount = frontendLoginUser.getUserAccount();
//			if (null != frontendLoginUser && KgoUtil.isMoicaLogin()) {
//				// 如果是自然人憑證登入，則取得twSsn
//				moicaUserTwSsn = frontendLoginUser.getKcgMoicaCardInfo().getMoicaUserTwSsn();
//			}
//            else {
//                // 如果不是自然人憑證登入，則從前端取得twSsn
//                moicaUserTwSsn = rq.getMoicaUserTwSsn();
//            }
			if (!StringUtils.isEmpty(twSSn) || !StringUtils.isEmpty(email) || !StringUtils.isEmpty(userAccount)) {
				String applyDateStart = null;
				String applyDateEnd = null;
				if(ObjectUtils.isNotEmpty(rq.getApplyDate())){
					String[] applyDate = rq.getApplyDate();
					 applyDateStart = applyDate[0] + " 00:00:00";
					 applyDateEnd = applyDate[1] + " 23:59:59";
				}

				/**
				 * 20221214 GEO 變更前台申請日期查詢為起訖日
				 */
//				Timestamp now = DateUtil.getCurrentTimestamp();
				// 系統日減 系統參數設定 案件暫存區保存期限 時間單位
//				LocalDateTime subTsLDateTime = DateUtil.subTsDateBySysParam(now);
//				if (!StringUtils.isEmpty(applyDate)) {
//					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//					LocalDate dt = LocalDate.parse(applyDate, formatter);
//					if (dt.atStartOfDay().isBefore(subTsLDateTime)) {
//						LocalDate localDate = LocalDate.parse(applyDate, formatter);
//						applyDateStart = df.format(subTsLDateTime);
//						LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
//						applyDateEnd = df.format(endOfDay);
//					} else {
//						LocalDate localDate = LocalDate.parse(applyDate, formatter);
//						LocalDateTime startOfDay = localDate.atStartOfDay();
//						applyDateStart = df.format(startOfDay);
//						LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
//						applyDateEnd = df.format(endOfDay);
//					}
//				} else {
//					applyDateStart = df.format(subTsLDateTime);
//					applyDateEnd = df.format(LocalDateTime.now());
//				}
				// 未超過案件暫存區保存期限
				// 依條件查詢
				List<CaseProcessSearchDto> caseProcessSearchDto = new ArrayList<>();
				String status = rq.getStatus();
				List<String> statusList = null;
				if (!StringUtils.isEmpty(status)) {
					statusList = Arrays.asList(status.split(","));
				}
				if (!StringUtils.isEmpty(twSSn)) {
					List<CaseProcessSearchDto> caseProcessSearchDtoTemp = kgoCaseMainRepository.findByUserId(twSSn, rq.getCaseId(), rq.getCaseSetName(), applyDateStart, applyDateEnd, statusList);
					caseProcessSearchDto.addAll(caseProcessSearchDtoTemp);
				}
				if (!StringUtils.isEmpty(email)) {
					List<CaseProcessSearchDto> caseProcessSearchDtoTemp = kgoCaseMainRepository.findByEmail(email, rq.getCaseId(), rq.getCaseSetName(), applyDateStart, applyDateEnd, statusList);
					caseProcessSearchDto.addAll(caseProcessSearchDtoTemp);
				}
				if (!StringUtils.isEmpty(userAccount)) {
					List<CaseProcessSearchDto> caseProcessSearchDtoTemp = kgoCaseMainRepository.findByUserAccount(userAccount, rq.getCaseId(), rq.getCaseSetName(), applyDateStart, applyDateEnd, statusList);
					caseProcessSearchDto.addAll(caseProcessSearchDtoTemp);
				}
				caseProcessSearchDto = caseProcessSearchDto.stream().filter(distinctByKey(CaseProcessSearchDto::getCaseId)).collect(Collectors.toList());
				Collections.sort(caseProcessSearchDto, (o1, o2) -> o2.getApplyDate().compareTo(o1.getApplyDate()));

				// 轉換為前端物件
				List<CaseProcessSearchValidateDataGrid> caseProcessSearchValidateDataGrids = caseProcessSearchDto.stream().map(item -> {
					CaseProcessSearchValidateDataGrid caseProcessSearchValidateDataGrid = new CaseProcessSearchValidateDataGrid();
					caseProcessSearchValidateDataGrid.setCaseSetName(item.getCaseSetName());
					caseProcessSearchValidateDataGrid.setApplyDate(DateUtil.dateToString(item.getApplyDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
					caseProcessSearchValidateDataGrid.setCaseId(item.getCaseId());
					CaseMainStatusEnum caseMainStatusEnum = CaseMainStatusEnum.getCaseMainStatusEnum(item.getStatus());
					if (null != caseMainStatusEnum) {
						caseProcessSearchValidateDataGrid.setStatus(caseMainStatusEnum.getLabel());
					}
					return caseProcessSearchValidateDataGrid;
				}).collect(Collectors.toList());
				caseProcessSearchValidateViewForm.setGrids(caseProcessSearchValidateDataGrids);
				caseProcessSearchValidateRs.setData(caseProcessSearchValidateViewForm);
			}
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			error = new KgoApiException("queryAction error " + e.getMessage(), e);
		} finally {
			/** === 儲存操作紀錄 === */
			if (!StringUtils.isEmpty(rq.getCaseId())) {
				List<OperationColumn> memoList = new ArrayList<>();
				memoList.add(new OperationColumn("案件編號：", rq.getCaseId()));
				memo.setMemoList(memoList);
				super.saveOperationLog(memo);
				/** === 儲存操作紀錄 === */
			}
			if (error != null) {
				throw error;
			}
		}
		return caseProcessSearchValidateRs;
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	private void validateParameter(CaseProcessSearchQueryRq rq) {
		String[] applyDate = rq.getApplyDate();
		if (ObjectUtils.isNotEmpty(applyDate)) {
			for(String dateStr : applyDate ){
				boolean validFormat = DateUtil.isValidFormat("yyyy/MM/dd", dateStr, Locale.TAIWAN);
				if (!validFormat) {
					throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, messageUtil.getMessage("kgo.frontend.case.process.search.applyDate")));
				}
			}
		}
	}

	/** 申請日期 < 系統日 - 系統參數設定 不顯示. */
//    Predicate<CaseProcessSearchDto> validateApplyDate = caseProcessSearchDto -> {
//    	Timestamp now = DateUtil.getCurrentTimestamp();
//    	// 申請日期
//		Date applyDateTs = caseProcessSearchDto.getApplyDate();
//
//		// 系統日減 系統參數設定 案件暫存區保存期限 時間單位
//		LocalDateTime subTsLDateTime = DateUtil.subTsDateBySysParam(now);
//
//		Date subTsDate = Date.from(subTsLDateTime.atZone(ZoneId.systemDefault()).toInstant());
//
//		// 申請日期 超過案件暫存區保存期限 不顯示
//		if(applyDateTs.before(subTsDate)) {
//			// 超過案件暫存區保存期限
//			return false;
//		}
//		return true;
//    };

	@Override
	@Transactional
	public CaseProcessSearchDetailSaveRs detailSaveAction(CaseProcessSearchDetailSaveRq rq) {
		try {
			// 確認是否登入
//			FrontendLoginUserInfo frontendLoginUser = KgoUtil.getFrontendLoginUser();
			List<CaseProcessSearchDetailSaveColumn> caseProcessSearchDetailSaveColumnList = rq.getCaseProcessSearchDetailSaveColumnList();
			String caseId = rq.getCaseId();

			// 取得群組、欄位資料
			Optional<KgoCaseMain> kgoCaseMainOptional = kgoCaseMainRepository.findById(caseId);
			List<KgoCaseDetail> kgoCaseDetailList = kgoCaseDetailRepository.findByIdCaseId(caseId);
			if (kgoCaseMainOptional.isPresent() && !CollectionUtils.isEmpty(kgoCaseDetailList)) {

				KgoCaseMain kgoCaseMain = kgoCaseMainOptional.get();

				// 驗證參數
				validateColumn(caseProcessSearchDetailSaveColumnList, caseId, kgoCaseMain);

				Task task = taskService.createTaskQuery().processInstanceId(kgoCaseMain.getProcessId()).singleResult();
				if (null != task) {
					KgoCaseDetail kgoCaseDetail = kgoCaseDetailList.stream().filter(item -> "Name".equals(item.getId().getColumnId())).findAny().orElse(new KgoCaseDetail());
					activitiService.addCommentByTaskId("", task.getId(), CaseMainStatusEnum.P.getLabel(),
							String.format(messageUtil.getMessage("kgo.backend.case.handle.correct.comment"), kgoCaseDetail.getColumnValue()), "", kgoCaseDetail.getColumnValue(), CaseStatusEnum.CO);

					/** === 2020.12.15 補正作業 不用跑下個流程 === */
					caseId = kgoCaseMain.getCaseId();
					List<KgoCaseMainResendFlow> resendFlowList = kgoCaseMainResendFlowRepository.findByCaseId(caseId);
					Optional<KgoCaseset> casesetOpt = kgoCasesetRepository.findById(kgoCaseMain.getCaseSetId());
					KgoCaseset kgoCaseset = casesetOpt.get();
					// 是否為動態流程
					boolean isDynamic = false;
					if (!StringUtils.isEmpty(kgoCaseset.getFlowId()) || !CollectionUtils.isEmpty(resendFlowList)) {
						isDynamic = true;
					}

					// 動態流程 不跑下個flow
					if (!isDynamic) {
						activitiService.correctWithTaskId(task.getId());
					}
					/** === 2020.12.15 補正作業 不用跑下個流程 === */

				}
				// 儲存資料
				List<KgoCaseDetail> kgoCaseDetailListFiltered = kgoCaseDetailList.stream().filter(item -> Boolean.TRUE.equals(item.getIsCorrect())).collect(Collectors.toList());
				kgoCaseMain = saveData(caseProcessSearchDetailSaveColumnList, caseId, kgoCaseDetailListFiltered);
				List<CaseProcessSearchDetailSaveColumn> collect = caseProcessSearchDetailSaveColumnList.stream().filter(item -> item.getCcolumnId() == null).collect(Collectors.toList());
				KgoCaseMain kgoCaseMain2 = kgoCaseMainRepository.findByCaseId(caseId);
				if (!CollectionUtils.isEmpty(kgoCaseDetailList)) {
					KgoCorrectRecord kgoCorrectRecord = new KgoCorrectRecord();
					List<KgoCasesetColumn> kgoCasesetColumnList = kgoCasesetColumnRepository.findByIdCaseSetIdAndIdVersion(kgoCaseMain2.getCaseSetId(), kgoCaseDetailList.get(0).getId().getVersion());
					String columnNames = kgoCasesetColumnList.stream()
							.filter(column -> collect.stream().filter(columnItem -> column.getId().getColumnId().equals(columnItem.getColumnId())).findAny().orElse(null) != null)
							.map(item -> item.getColumnName()).collect(Collectors.joining("、"));
					kgoCorrectRecord.setMemo("由民眾補正" + columnNames);
					kgoCorrectRecord.setStatus(CaseMainStateEnum.F.getValue());
					kgoCorrectRecord.setHandler(kgoCaseMain.getApplyUserName());
					kgoCorrectRecord.setHandleTime(new Timestamp(System.currentTimeMillis()));
					kgoCorrectRecord.setCaseId(kgoCaseMain.getCaseId());
					kgoCorrectRecordRepository.save(kgoCorrectRecord);
				}
				// 儲存後通知
				doNotify(caseId, kgoCaseMain);
			} else {
				throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, "caseId"));
			}
			return new CaseProcessSearchDetailSaveRs();
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("detailSaveAction error " + e.getMessage(), e);
		}
	}

	private KgoCaseMain saveData(List<CaseProcessSearchDetailSaveColumn> caseProcessSearchDetailSaveColumnList, String caseId, List<KgoCaseDetail> kgoCaseDetailList) {
		// 保存kgoCaseDetail
		// 將補正前的資料存於補正前值(CorrectBValue)，補正後的資料Update於欄位值(columnValue)
		kgoCaseDetailList.forEach(item -> {
			CaseProcessSearchDetailSaveColumn caseProcessSearchDetailSaveColumn = caseProcessSearchDetailSaveColumnList.stream().filter(detail -> (
			// 複合欄位
			(detail.getColumnId().equals(item.getId().getColumnId()) && null != detail.getCcolumnId() && detail.getCcolumnId().equals(item.getId().getCColumnId())) ||
			// 一般欄位
			(detail.getColumnId().equals(item.getId().getColumnId()) && null == detail.getCcolumnId()))).findAny().orElse(new CaseProcessSearchDetailSaveColumn());
			caseProcessSearchDetailSaveColumnList.forEach(detail -> {
				if (StringUtils.isEmpty(item.getId().getCColumnId())) {
					if (item.getId().getColumnId().equals(detail.getColumnId())) {
						if (org.apache.commons.lang3.StringUtils.isNoneBlank(detail.getFileBase64Str())) {
							String base64Str = detail.getFileBase64Str();
							String[] dataString = detail.getFileBase64Str().split(",");
							if (dataString.length > 1) {
								base64Str = dataString[1];
							}
							byte[] decoder = Base64.getDecoder().decode(base64Str);
							String caseFilePath = KgoUtil.getCaseDownloadUploadBasePath(caseId);
							File fileFolder = new File(caseFilePath);
							File correctFile = new File(KgoUtil.getCaseDownloadUploadBasePathCorrect(caseId));
							if (!correctFile.exists() || !correctFile.isDirectory()) {
								correctFile.mkdirs();
							}
							new File(caseFilePath + item.getColumnValue()).renameTo(new File(KgoUtil.getCaseDownloadUploadBasePathCorrect(caseId) + item.getColumnValue()));
							FileUtil.createFile(fileFolder, caseProcessSearchDetailSaveColumn.getColumnValue(), decoder);
						}
					}
				}
			});
			item.setCorrectBValue(item.getColumnValue());
			item.setColumnValue(caseProcessSearchDetailSaveColumn.getColumnValue());
		});
		kgoCaseDetailRepository.saveAllBatch(kgoCaseDetailList);

		// 案件主檔：案件狀態改為處理中
		KgoCaseMain kgoCaseMain = kgoCaseMainRepository.findByCaseId(caseId);
		kgoCaseMain.setStatus(CaseMainStatusEnum.P.getValue());
		kgoCaseMain.setState("3");
		kgoCaseMainRepository.save(kgoCaseMain);
		return kgoCaseMain;
	}

	/**
	 * 發送推播.
	 *
	 * @param caseId      the case id
	 * @param kgoCaseMain the kgo case main
	 */
	@Override
	public void doNotify(String caseId, KgoCaseMain kgoCaseMain) {
		// 發送mail及推播
		String caseSetId = kgoCaseMain.getCaseSetId();
		Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(caseSetId);
		if (kgoCaseset.isPresent()) {
			try {
				// 寄出email
				List<KgoCaseDetail> kgoCaseDetails = kgoCaseDetailRepository.findByIdCaseIdAndIdColumnId(caseId, "Email");
				Optional<KgoCaseDetail> max = kgoCaseDetails.stream().max(Comparator.comparing(item -> item.getId().getVersion()));
				if (max.isPresent()) {
					String columnValue = max.get().getColumnValue();
					if (!StringUtils.isEmpty(columnValue)) {
						sendEmailApplyCase(kgoCaseset.get(), columnValue, caseId);
					} else {
						LOGGER.info("caseProcessSearch detailSaveAction doNotify no email to send empty String");
					}
				} else {
					LOGGER.info("caseProcessSearch detailSaveAction doNotify no email to send empty");
				}
			} catch (Exception e) {
				LOGGER.error(KgoBackEndApiError.FAIL_TO_EMAIL.getErrorMsgKey());
			}
			try {
				// 寄出推播
				List<KgoCaseDetail> kgoCaseDetailsID = kgoCaseDetailRepository.findByIdCaseIdAndIdColumnId(caseId, "ID");
				Optional<KgoCaseDetail> maxID = kgoCaseDetailsID.stream().max(Comparator.comparing(item -> item.getId().getVersion()));
				if (maxID.isPresent()) {
					String columnValue = maxID.get().getColumnValue();
					if (!StringUtils.isEmpty(columnValue)) {
						pushNotificationApplyCase(kgoCaseset.get(), columnValue, kgoCaseMain);
					} else {
						LOGGER.info("caseProcessSearch detailSaveAction doNotify no notification to send empty String");
					}
				} else {
					LOGGER.info("caseProcessSearch detailSaveAction doNotify no notification to send empty");
				}
			} catch (Exception e) {
				LOGGER.error(KgoBackEndApiError.FAIL_TO_NOTIFY.getErrorMsgKey());
			}
		}

		try {
			String caseOfficer = kgoCaseMain.getCaseOfficer();
			if (kgoCaseset.get().getCaseFlowType().equals(CaseFlowTypeEnum.B3.getValue())) {
				KgoUser kgoUser = kgoUserRepository.getOne(caseOfficer);
				sendEmailApplyCaseOfficer(kgoCaseset.get(), kgoCaseMain, kgoUser.getEmail());
			}

		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_EMAIL.getErrorMsgKey());
		}
	}

	@Override
	public CaseProcessSearchDetailCheckRs detailCheckAction(CaseProcessSearchDetailCheckRq rq) {
		LOGGER.info("detailCheckAction start...");
		CaseProcessSearchDetailCheckRs caseProcessSearchDetailCheckRs = new CaseProcessSearchDetailCheckRs();
		String gstrCaseId = rq.getGstrCaseId();
		LOGGER.info("detailCheckAction gstrCaseId="+gstrCaseId);
		String idCheck = rq.getIdCheck();
		LOGGER.info("detailCheckAction idCheck="+idCheck);
		String idCheckFull = rq.getIdCheckFull();
		LOGGER.info("detailCheckAction idCheckFull="+idCheckFull);
		String phone = rq.getPhone();
		LOGGER.info("detailCheckAction phone="+phone);
		KgoCaseMain kgoCaseMain = null;
		if (StringUtils.isEmpty(gstrCaseId)) {
			// 未傳案件編號
			throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, "gstrCaseId"));
		}
		try {
			CaseProcessSearchDetailCheckViewForm caseProcessSearchDetailCheckViewForm = new CaseProcessSearchDetailCheckViewForm();
			//預設找不到
			caseProcessSearchDetailCheckViewForm.setResult(false);

			kgoCaseMain = kgoCaseMainRepository.findByCaseId(gstrCaseId);
			LOGGER.info("detailCheckAction ertyuio:{}", JsonUtil.toJSONString(kgoCaseMain));
			if (kgoCaseMain ==null) {
				caseProcessSearchDetailCheckViewForm.setResult(false);
				caseProcessSearchDetailCheckRs.setData(caseProcessSearchDetailCheckViewForm);
				return caseProcessSearchDetailCheckRs;
			}

			if (!StringUtils.isEmpty(idCheck) || !StringUtils.isEmpty(idCheckFull)){
				LOGGER.info("detailCheckAction 身份驗證欄位不為空="+idCheck);
				if (kgoCaseMain.getApplyUser()==null){
					LOGGER.info("detailCheckAction kgoCaseMain.getApplyUser()==null");
					List<KgoCaseDetail> KgoCaseDetailList = kgoCaseDetailRepository.findByIdCaseId(gstrCaseId);
					for (KgoCaseDetail detail:KgoCaseDetailList){
						LOGGER.info("detailCheckAction detail.getColumnValue()="+detail.getColumnValue());
						if (idCheckFull!=null){
							LOGGER.info("detailCheckAction detail.getColumnValue() idCheckFull!=null="+detail.getColumnValue());
							if (detail.getColumnValue().equals(idCheckFull)) caseProcessSearchDetailCheckViewForm.setResult(true);
						} //if (idCheckFull!=null)

						if (Strings.isNotBlank(idCheck)){
							if (detail.getColumnValue().endsWith(idCheck)) caseProcessSearchDetailCheckViewForm.setResult(true);
							LOGGER.info("detailCheckAction getColumnValue().indexOf(idCheck) != -1="+detail.getColumnValue());
						} //if (idCheck !=null)
					} //for (KgoCaseDetail detail:KgoCaseDetailList)
				}else {
					LOGGER.info("detailCheckAction kgoCaseMain.getApplyUser()="+kgoCaseMain.getApplyUser());
					if (idCheckFull!=null){
						LOGGER.info("detailCheckAction else idCheckFull!=null");
						if (kgoCaseMain.getApplyUser().indexOf(idCheckFull)!= -1) caseProcessSearchDetailCheckViewForm.setResult(true);
					} //if (idCheckFull!=null)

					if (idCheck !=null){
						LOGGER.info("detailCheckAction else idCheck!=null");
						if (kgoCaseMain.getApplyUser().endsWith(idCheck)) caseProcessSearchDetailCheckViewForm.setResult(true);
					} //if (idCheck !=null)
				} //if (kgoCaseMain.getApplyUser()==null)
			} //if (!StringUtils.isEmpty(idCheck)

			//電話不為空
			if (!StringUtils.isEmpty(phone)){
				List<KgoCaseDetail> KgoCaseDetailList = kgoCaseDetailRepository.findByIdCaseId(gstrCaseId);
				for (KgoCaseDetail detail:KgoCaseDetailList){
					if (detail.getColumnValue().equals(phone)) caseProcessSearchDetailCheckViewForm.setResult(true);
				} //for (KgoCaseDetail detail:KgoCaseDetailList)
			} //if (!StringUtils.isEmpty(phone))
			caseProcessSearchDetailCheckRs.setData(caseProcessSearchDetailCheckViewForm);
		} catch (KgoApiException e) {
//       // 未登入
//       LOGGER.info("detailAction user not login");
//       // 用傳入身分證後四碼去查
//       if (!StringUtils.isEmpty(idCheck)) {
//          kgoCaseMain = kgoCaseMainRepository.findByCaseIdAndIdCheck(gstrCaseId, idCheck);
//       } else if (!StringUtils.isEmpty(idCheckFull)) {
//          // 身分證全部查詢
//          kgoCaseMain = kgoCaseMainRepository.findByCaseIdAndApplyUser(gstrCaseId, idCheckFull);
//       } else if (!StringUtils.isEmpty(phone)) {
//          // 用電話查
//          kgoCaseMain = kgoCaseMainRepository.findByCaseIdAndCellPhone(gstrCaseId, phone);
//       } else {
//           未登入皆未傳入
			throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, "idCheck,idCheckFull,phone"));
//       }
		}
		return caseProcessSearchDetailCheckRs;
	} //detailCheckAction

	private void sendEmailApplyCaseOfficer(KgoCaseset kgoCaseset, KgoCaseMain kgoCaseMain, String email) throws Exception {
		Map<String, Object> model = new HashMap<>();
		model.put("casesetName", kgoCaseset.getCaseSetName());
		model.put("caseId", kgoCaseMain.getCaseId());
		model.put("caseUrl", backendCaseSearchLink);
		mailUtil.sendTemplateMail(new String[] { email }, null, String.format(messageUtil.getMessage("kgo.frontend.case.process.search.officer.mail.title"), kgoCaseMain.getCaseId()), model,
				"correctCaseOfficer.html");
	}

	private void validateColumn(List<CaseProcessSearchDetailSaveColumn> caseProcessSearchDetailSaveColumnList, String caseId, KgoCaseMain kgoCaseMain) {
		List<CaseCorrectDataDto> caseCorrectDataDtos = kgoCaseDetailRepository.listCaseCorrectData(caseId, kgoCaseMain.getCaseSetId());
		Integer integer1 = 1;
		caseCorrectDataDtos = caseCorrectDataDtos.stream().filter(item -> integer1.equals(item.getKcdIsCorrect()) || integer1.equals(item.getKcd2IsCorrect())).collect(Collectors.toList());
		List<CaseCorrectDataDto> finalCaseCorrectDataDtos = caseCorrectDataDtos;
		caseCorrectDataDtos.forEach(item -> {
			String kccColumnName = item.getKccColumnName();
			if ("M".equals(item.getKccColumnType())) {
				// 複合欄位
				List<CaseProcessSearchDetailSaveColumn> caseProcessSearchDetailSaveColumns = caseProcessSearchDetailSaveColumnList.stream()
						.filter(innerItem -> item.getKccColumnId().equals(innerItem.getColumnId()) && item.getKcccCColumnId().equals(innerItem.getCcolumnId())).collect(Collectors.toList());
				Integer kcccIsMustKey = item.getKcccIsMustKey();
				String kcccPColumnId = item.getKcccPColumnId();
				Integer kcccLength = item.getKcccLength();
				if (caseProcessSearchDetailSaveColumns.size() > 1) {
					// 前端輸入多個相同複合欄位
					// 拋出exception
					throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, kccColumnName));
				} else if (CollectionUtils.isEmpty(caseProcessSearchDetailSaveColumns)) {
					// 無輸入此欄位
					if (integer1.equals(kcccIsMustKey)) {
						// 必填無輸入
						// 拋出exception
						throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, kccColumnName));
					} else if (!StringUtils.isEmpty(kcccPColumnId)) {
						CaseCorrectDataDto pDto = finalCaseCorrectDataDtos.stream().filter(innerItem -> kcccPColumnId.equals(innerItem.getKcccCColumnId())).findAny().orElse(new CaseCorrectDataDto());
						CaseProcessSearchDetailSaveColumn caseProcessSearchDetailSaveColumn = caseProcessSearchDetailSaveColumnList.stream()
								.filter(innerItem -> kcccPColumnId.equals(innerItem.getCcolumnId())).findAny().orElse(new CaseProcessSearchDetailSaveColumn());
						if (!StringUtils.isEmpty(pDto.getKcdColumnValue()) || !StringUtils.isEmpty(caseProcessSearchDetailSaveColumn.getColumnValue())) {
							// 父欄位有填子欄位無填，且子欄位需要填
							// 拋出exception
							throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, kccColumnName));
						}
					}
				} else {
					// 有輸入一次此欄位
					CaseProcessSearchDetailSaveColumn caseProcessSearchDetailSaveColumn = caseProcessSearchDetailSaveColumns.get(0);
					String columnValue = caseProcessSearchDetailSaveColumn.getColumnValue();
					if (StringUtils.isEmpty(columnValue)) {
						// 輸入為空值
						if (1 == kcccIsMustKey) {
							// 拋出exception
							throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, kccColumnName));
						} else if (!StringUtils.isEmpty(kcccPColumnId)) {
							CaseCorrectDataDto pDto = finalCaseCorrectDataDtos.stream().filter(innerItem -> kcccPColumnId.equals(innerItem.getKcccCColumnId())).findAny()
									.orElse(new CaseCorrectDataDto());
							CaseProcessSearchDetailSaveColumn pCaseProcessSearchDetailSaveColumn = caseProcessSearchDetailSaveColumnList.stream()
									.filter(innerItem -> kcccPColumnId.equals(innerItem.getCcolumnId())).findAny().orElse(new CaseProcessSearchDetailSaveColumn());
							if (!StringUtils.isEmpty(pDto.getKcdColumnValue()) || !StringUtils.isEmpty(pCaseProcessSearchDetailSaveColumn.getColumnValue())) {
								// 父欄位有填子欄位無填，且子欄位需要填
								// 拋出exception
								throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, kccColumnName));
							}
						}
					} else {
						if (null != kcccLength && 0 != kcccLength) {
							// 有輸入值
							if (columnValue.length() > kcccLength) {
								// 欄位資料長度過長
								// 拋出exception
								throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, kccColumnName));
							}
						}
					}
				}
			} else {
				// 非複合欄位
				List<CaseProcessSearchDetailSaveColumn> caseProcessSearchDetailSaveColumns = caseProcessSearchDetailSaveColumnList.stream()
						.filter(innerItem -> item.getKccColumnId().equals(innerItem.getColumnId()) && StringUtils.isEmpty(item.getKcccCColumnId())).collect(Collectors.toList());
				Integer kccIsMustKey = item.getKccIsMustKey();
				Integer kccLength = item.getKccLength();
				if (caseProcessSearchDetailSaveColumns.size() > 1) {
					// 前端輸入多個相同非複合欄位
					// 拋出exception
					throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, kccColumnName));
				} else if (CollectionUtils.isEmpty(caseProcessSearchDetailSaveColumns)) {
					// 無輸入此欄位
					if (1 == kccIsMustKey) {
						// 必填無輸入
						// 拋出exception
						throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, kccColumnName));
					}
				} else {
					// 有輸入一次此欄位
					CaseProcessSearchDetailSaveColumn caseProcessSearchDetailSaveColumn = caseProcessSearchDetailSaveColumns.get(0);
					String columnValue = caseProcessSearchDetailSaveColumn.getColumnValue();
					if (StringUtils.isEmpty(columnValue)) {
						// 輸入為空值
						if (1 == kccIsMustKey) {
							// 必填無輸入
							// 拋出exception
							throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, kccColumnName));
						}
					} else {
						if (null != kccLength && 0 != kccLength) {
							// 有輸入值
							if (columnValue.length() > kccLength) {
								// 欄位資料長度過長
								// 拋出exception
								throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, kccColumnName));
							}
						}
					}
				}
			}
		});
	}

	private void sendEmailApplyCase(KgoCaseset kgoCaseset, String email, String caseId) throws Exception {
		Map<String, Object> model = new HashMap<>();
		model.put("casesetName", kgoCaseset.getCaseSetName());
		model.put("caseUrl", String.format(fontendCaseSearchLink, caseId));
		mailUtil.sendTemplateMail(new String[] { email }, null, messageUtil.getMessage("kgo.frontend.case.process.search.mail.title"), model, "correctCase.html");
	}

	private void pushNotificationApplyCase(KgoCaseset kgoCaseset, String id, KgoCaseMain kgoCaseMain) {
//		List<PushMsgDto> pushDataList = new ArrayList<>();
//		PushMsgDto pushMsg = new PushMsgDto();
//		pushMsg.setCustNum(id);
//		pushMsg.setMsgTitle(messageUtil.getMessage("kgo.frontend.case.process.search.notification.msgTitle"));
//		pushMsg.setMsgBody(
//				String.format(messageUtil.getMessage("kgo.frontend.case.process.search.notification.msgBody"),
//						kgoCaseset.getCaseSetName()));
//		pushMsg.setClickDetail(
//				String.format(messageUtil.getMessage("kgo.frontend.case.process.search.notification.clickDetail"),
//						kgoCaseset.getCaseSetName()));
//		pushDataList.add(pushMsg);

		pushService.pushMessage(id, PushCaseStatusEnum.C.getValue(), kgoCaseMain.getCaseId(), kgoCaseset.getCaseSetName(), String.valueOf(kgoCaseMain.getApplyDate()));

//        this.pushService.pushMessage(pushDataList, kgoCaseset.getOwnerOrgan());
	}

	@Override
	public CaseProcessSearchDetailRs detailAction(CaseProcessSearchDetailRq rq) {
		LOGGER.info("detailAction rq="+rq);
		CaseProcessSearchDetailRs caseProcessSearchDetailRs = new CaseProcessSearchDetailRs();
		String gstrCaseId = rq.getGstrCaseId();
		String idCheck = rq.getIdCheck();
		String idCheckFull = rq.getIdCheckFull();
		String phone = rq.getPhone();
		KgoCaseMain kgoCaseMain = null;
		KgoApiException error = null;
		OperationApiMemo memo = null;

		/** GEO 20211010 add for 1999 service **/
		boolean hadKd = false; //有派工欄位
		boolean hadNew = false; //有陳情欄位
		String search1999Email = "";
		String search1999Url = "";

		if (StringUtils.isEmpty(gstrCaseId)) {
			// 未傳案件編號
			throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, "gstrCaseId"));
		} //if (StringUtils.isEmpty(gstrCaseId))

			// 前台、新增、登入
			memo = super.genOperationMemo(SystemTypeEnum.F, null, FrontendFunctionCodeEnum.CaseQry);

		     kgoCaseMain = kgoCaseMainRepository.findByCaseId(gstrCaseId);
			 LOGGER.info("detailAction ertyuio:{}", JsonUtil.toJSONString(kgoCaseMain));
			 if (kgoCaseMain==null) throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, "驗證欄位錯誤"));

			// 確認是否登入
				FrontendLoginUserInfo frontendLoginUser = getFrontendLoginUser();
			    LOGGER.info("detailAction frontendLoginUser="+frontendLoginUser);
				if (frontendLoginUser != null) {
					String twSSn = frontendLoginUser.getTwSSn();
					String email = frontendLoginUser.getEmail();
					String userAccount = frontendLoginUser.getUserAccount();
					if (null == kgoCaseMain && !StringUtils.isEmpty(twSSn)) {
						kgoCaseMain = kgoCaseMainRepository.findByCaseIdAndApplyUser(gstrCaseId, twSSn);
						LOGGER.info("detailAction kgoCaseMain twssn :{}", kgoCaseMain);
					}
					if (null == kgoCaseMain && !StringUtils.isEmpty(email)) {
						kgoCaseMain = kgoCaseMainRepository.findByCaseIdAndEmail(gstrCaseId, email);
						LOGGER.info("detailAction kgoCaseMain email :{}", kgoCaseMain);
					}
					if (null == kgoCaseMain && !StringUtils.isEmpty(userAccount)) {
						kgoCaseMain = kgoCaseMainRepository.findByCaseIdAndAccount(gstrCaseId, userAccount);
						LOGGER.info("detailAction kgoCaseMain userAccount :{}", kgoCaseMain);
					}
				}else {
					//身分證不為空
					if (!StringUtils.isEmpty(idCheck) || !StringUtils.isEmpty(idCheckFull)){
						if (kgoCaseMain.getApplyUser()==null){
							List<KgoCaseDetail> KgoCaseDetailList = kgoCaseDetailRepository.findByIdCaseId(gstrCaseId);
							String value ="";
							for (KgoCaseDetail detail:KgoCaseDetailList){
								if (idCheckFull!=null){
									if (detail.getColumnValue().equals(idCheckFull)) value = detail.getColumnValue();
								} //if (idCheckFull!=null)
								if (idCheck !=null){
									if (detail.getColumnValue().indexOf(idCheck) != -1)value = detail.getColumnValue();
								} //if (idCheck !=null)
							} //for (KgoCaseDetail detail:KgoCaseDetailList)
							if (value.equals(""))throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, "驗證欄位錯誤"));
						} //if (kgoCaseMain.getApplyUser()==null)
					} //if (!StringUtils.isEmpty(idCheck)

					LOGGER.info("detailAction frontendLoginUser phone="+phone);

					//電話不為空
					if (!StringUtils.isEmpty(phone)){
						List<KgoCaseDetail> KgoCaseDetailList = kgoCaseDetailRepository.findByIdCaseId(gstrCaseId);
						String value ="";
						for (KgoCaseDetail detail:KgoCaseDetailList){
							LOGGER.info("detailAction frontendLoginUser detail.getColumnValue()="+detail.getColumnValue());
							if (detail.getColumnValue().equals(phone)) value = phone ;
						} //for (KgoCaseDetail detail:KgoCaseDetailList)
						if (value.equals(""))throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, "驗證欄位錯誤"));
					} //if (!StringUtils.isEmpty(phone))
				} //if (frontendLoginUser != null)
		 try {
			CaseProcessSearchDetailViewForm caseProcessSearchDetailViewForm = new CaseProcessSearchDetailViewForm();
			if (null != kgoCaseMain) {
				String caseSetId = kgoCaseMain.getCaseSetId();
				LOGGER.info("detailAction caseSetId="+caseSetId);
				Optional<KgoCaseset> kgoCasesetOptional = kgoCasesetRepository.findById(caseSetId);
				if (kgoCasesetOptional.isPresent()) {
					// 取得案件資料
					KgoCaseset kgoCaseset = kgoCasesetOptional.get();
					CaseProcessSearchDetailCaseset caseProcessSearchDetailCaseset = new CaseProcessSearchDetailCaseset();
					caseProcessSearchDetailCaseset.setCasesetName(kgoCaseset.getCaseSetName());
					caseProcessSearchDetailCaseset.setApplyDate(DateUtil.dateToString(kgoCaseMain.getApplyDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
					caseProcessSearchDetailCaseset.setCaseId(kgoCaseMain.getCaseId());

					CaseMainStatusEnum caseMainStatusEnum = CaseMainStatusEnum.getCaseMainStatusEnum(kgoCaseMain.getStatus());
					caseProcessSearchDetailCaseset.setStatus(kgoCaseMain.getStatus());

					if (ObjectUtils.isNotEmpty(caseMainStatusEnum)) {
						if (caseMainStatusEnum == CaseMainStatusEnum.O) {
							caseProcessSearchDetailCaseset.setStatusDscr(String.format("%s (%s)", caseMainStatusEnum.getLabel(), kgoCaseMain.getStatusDesc()));
						} else {
							caseProcessSearchDetailCaseset.setStatusDscr(caseMainStatusEnum.getLabel());
						}
					} else {
						caseProcessSearchDetailCaseset.setStatusDscr(CaseMainStatusEnum.OTHERS.getLabel());
					}

					caseProcessSearchDetailViewForm.setCaseProcessSearchDetailCaseset(caseProcessSearchDetailCaseset);

					// 取得群組、欄位資料
					List<CaseCorrectDataDto> caseCorrectDataDtos = kgoCaseDetailRepository.listCaseCorrectData(kgoCaseMain.getCaseId(), caseSetId);
					// 按照群組分
					Map<Integer, List<CaseCorrectDataDto>> groupByGroupSeq = caseCorrectDataDtos.stream()
							.collect(Collectors.groupingBy(CaseCorrectDataDto::getKcgGroupSeq, LinkedHashMap::new, Collectors.toList()));
					List<CaseProcessSearchDetailCasesetGroup> caseProcessSearchDetailCasesetGroups = new ArrayList<>();
					for (Map.Entry<Integer, List<CaseCorrectDataDto>> entry : groupByGroupSeq.entrySet()) {
						List<CaseCorrectDataDto> value = entry.getValue();
						if (!CollectionUtils.isEmpty(value)) {
							CaseCorrectDataDto caseCorrectDataDtoGroupByGroupSeq = value.get(0);
							CaseProcessSearchDetailCasesetGroup caseProcessSearchDetailCasesetGroup = new CaseProcessSearchDetailCasesetGroup();
							caseProcessSearchDetailCasesetGroup.setGroupId(String.valueOf(caseCorrectDataDtoGroupByGroupSeq.getKcgGroupSeq()));
							caseProcessSearchDetailCasesetGroup.setGroupName(caseCorrectDataDtoGroupByGroupSeq.getKcgMemo());
							// 按照欄位分
							Map<String, List<CaseCorrectDataDto>> groupByColumnId = value.stream()
									.collect(Collectors.groupingBy(CaseCorrectDataDto::getKccColumnId, LinkedHashMap::new, Collectors.toList()));
							List<CaseProcessSearchDetailCasesetColumn> caseProcessSearchDetailCasesetColumns = new ArrayList<>();
							for (Map.Entry<String, List<CaseCorrectDataDto>> columnEntry : groupByColumnId.entrySet()) {
								List<CaseCorrectDataDto> columnValue = columnEntry.getValue();
								if (!CollectionUtils.isEmpty(columnValue)) {
									CaseCorrectDataDto caseCorrectDataDto = columnValue.get(0);
									CaseProcessSearchDetailCasesetColumn caseProcessSearchDetailCasesetColumn = new CaseProcessSearchDetailCasesetColumn();
									caseProcessSearchDetailCasesetColumn.setCorrectMemo(caseCorrectDataDto.getKcdCorrectMemo());
									// modify: 是否補正 2020.12.03
									caseProcessSearchDetailCasesetColumn
											.setIsCorrect(caseCorrectDataDto.getKcdIsCorrect() != null ? YesNoFlag.getFlag(caseCorrectDataDto.getKcdIsCorrect()).getFlag() : YesNoFlag.N.getFlag());
									caseProcessSearchDetailCasesetColumn.setColumnDetailValue(caseCorrectDataDto.getKcdColumnValue());
									caseProcessSearchDetailCasesetColumn.setColumnValue(caseCorrectDataDto.getKccColumnValue());
									caseProcessSearchDetailCasesetColumn.setColumnType(caseCorrectDataDto.getKccColumnType());
									caseProcessSearchDetailCasesetColumn.setColumnName(caseCorrectDataDto.getKccColumnName());
									caseProcessSearchDetailCasesetColumn.setColumnId(caseCorrectDataDto.getKccColumnId());
									caseProcessSearchDetailCasesetColumn.setIsMustKey(Objects.toString(caseCorrectDataDto.getKccIsMustKey(), null));
									caseProcessSearchDetailCasesetColumn.setLength(caseCorrectDataDto.getKccLength());
									caseProcessSearchDetailCasesetColumn.setOrderNum(caseCorrectDataDto.getKccOrderNum());

									/** GEO 20211010 add for 1999 service **/
									if (ColumnTypeEnum.TEXT_EMAIL.getValue().equals(caseCorrectDataDto.getKccColumnType())) {
										search1999Email = caseProcessSearchDetailCasesetColumn.getColumnDetailValue();
									}

									if (ColumnTypeEnum.M.getValue().equals(caseCorrectDataDto.getKccColumnType())) {
										// 取得複合欄位
										Map<Integer, List<CaseCorrectDataDto>> dataMap = columnValue.stream()
												.collect(Collectors.groupingBy(CaseCorrectDataDto::getKcccRow, HashMap::new, Collectors.toCollection(LinkedList::new)));
										List<List<CaseProcessSearchDetailCasesetColumnChild>> caseProcessSearchDetailCasesetColumnChildren = dataMap.keySet().stream().map(i -> {
											return dataMap.get(i).stream().map(innerItem -> {

												// List<CaseProcessSearchDetailCasesetColumnChild>
												// caseProcessSearchDetailCasesetColumnChildren =
												// columnValue.stream().map(innerItem -> {
												CaseProcessSearchDetailCasesetColumnChild caseProcessSearchDetailCasesetColumnChild = new CaseProcessSearchDetailCasesetColumnChild();
												caseProcessSearchDetailCasesetColumnChild.setcColumnId(innerItem.getKcccCColumnId());
												caseProcessSearchDetailCasesetColumnChild.setColumnDetailValue(innerItem.getKcd2ColumnValue());
												caseProcessSearchDetailCasesetColumnChild.setColumnType(innerItem.getKcccColumnType());
												caseProcessSearchDetailCasesetColumnChild.setColumnValue(innerItem.getKcccColumnValue());
												caseProcessSearchDetailCasesetColumnChild.setFText(innerItem.getKcccFText());
												caseProcessSearchDetailCasesetColumnChild.setBText(innerItem.getKcccBText());
												caseProcessSearchDetailCasesetColumnChild.setCorrectMemo(innerItem.getKcd2CorrectMemo());
												caseProcessSearchDetailCasesetColumn.setCorrectMemo(innerItem.getKcd2CorrectMemo());

												// modify: 是否補正 2020.12.03
												caseProcessSearchDetailCasesetColumnChild
														.setIsCorrect(innerItem.getKcd2IsCorrect() != null ? YesNoFlag.getFlag(innerItem.getKcd2IsCorrect()).getFlag() : YesNoFlag.N.getFlag());
												if (caseProcessSearchDetailCasesetColumnChild.getIsCorrect().equalsIgnoreCase("Y")) {
													caseProcessSearchDetailCasesetColumn.setIsCorrect(caseProcessSearchDetailCasesetColumnChild.getIsCorrect());
												}
												caseProcessSearchDetailCasesetColumnChild.setIsMustKey(Objects.toString(innerItem.getKcccIsMustKey(), null));
												caseProcessSearchDetailCasesetColumnChild.setLength(innerItem.getKcccLength());
												caseProcessSearchDetailCasesetColumnChild.setPcolumnId(innerItem.getKcccPColumnId());
												caseProcessSearchDetailCasesetColumnChild.setvGroup(innerItem.getKcccVGroup());
												caseProcessSearchDetailCasesetColumnChild.setRow(innerItem.getKcccRow());
												caseProcessSearchDetailCasesetColumnChild.setOrderNum(innerItem.getKcccOrderNum());
												return caseProcessSearchDetailCasesetColumnChild;
											}).collect(Collectors.toList());
										}).collect(Collectors.toList());
										caseProcessSearchDetailCasesetColumn.setCaseProcessSearchDetailCasesetColumnChildren(caseProcessSearchDetailCasesetColumnChildren);
									}
									caseProcessSearchDetailCasesetColumns.add(caseProcessSearchDetailCasesetColumn);
								}
							}
							caseProcessSearchDetailCasesetGroup.setCaseProcessSearchDetailCasesetColumns(caseProcessSearchDetailCasesetColumns);
							caseProcessSearchDetailCasesetGroups.add(caseProcessSearchDetailCasesetGroup);
						}
					}
					caseProcessSearchDetailViewForm.setCaseProcessSearchDetailCasesetGroups(caseProcessSearchDetailCasesetGroups);

					/** GEO 20211224 add 機關審核表單：請於前台 案件進度查詢顯示 */
					SaCaseViewQueryDto dto = kgoCaseMainRepository.getSaCaseViewData(kgoCaseMain.getCaseId());

					/** ========= 處理歷程 ========= **/
					List<String> processIds = new ArrayList<>();
					/**處理歷程新增案件流程重送 歷程記錄 */
					String nowProcessId = dto.getProcessId();
					processIds.add(nowProcessId);

					/** ========= 機關審核表單 ========= **/
					List<CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid> organFormData = new ArrayList<>();
					if(ObjectUtils.isNotEmpty(kgoCaseMain.getIsOpenOrganForm())){
						if (kgoCaseMain.getIsOpenOrganForm() == GeoBooleanType.ENABLED.getCode()){
							Optional<KgoParamSet> kgoParamSetOptional = kgoParamSetRepository.findById(ParamSetEnum.TS.getType());
							List<SaCaseViewOrganFormDetailColumnQueryDto> organDtoList = geoKgoCasesetDetailOrganReposCustom.getSaCaseDetailData(kgoCaseMain.getCaseId());
							if (organDtoList != null && organDtoList.size() > 0) {
								for (SaCaseViewOrganFormDetailColumnQueryDto saCaseViewDetailColumnQueryDto : organDtoList) {
									entityManager.detach(saCaseViewDetailColumnQueryDto);
								} //for (SaCaseViewOrganFormDetailColumnQueryDto
								Integer isShow = geoKgoCasesetDetailOrganReposCustom.findIsShowByCasesetIdAndCaseFormVersion(kgoCaseMain.getCaseSetId(),organDtoList.get(0).getCaseFormVersion());
								if (ObjectUtils.isNotEmpty(isShow)){
									if (isShow == GeoBooleanType.ENABLED.getCode()){
										caseProcessSearchDetailViewForm.setShow(true);
									}else {
										caseProcessSearchDetailViewForm.setShow(false);
									} //if (isShow == GeoBooleanType
								}else {
									caseProcessSearchDetailViewForm.setShow(false);
								} //if (ObjectUtils.isNotEmpty(isShow))
								List<SaCaseViewOrganFormDetailColumnQueryDto> organFilterDto = organDtoList.stream().filter(item -> ObjectUtils.isEmpty(item.getCcolumnRowNum())).collect(Collectors.toList());
								Map<String, List<SaCaseViewOrganFormDetailColumnQueryDto>> organDtoMap = organDtoList.stream().filter(item -> ObjectUtils.isNotEmpty(item.getCcolumnRowNum()))
										.collect(Collectors.groupingBy(SaCaseViewOrganFormDetailColumnQueryDto::getSetColumnId));
								organFilterDto = organFilterDto.stream().sorted(Comparator.comparingInt(SaCaseViewOrganFormDetailColumnQueryDto::getColumnOrderNum)).collect(Collectors.toList());
								organFormData = organFilterDto.stream().map(l -> {
									CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid dg = new CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid();
									String columnValue = l.getRealColumnValue();
									String columnName = l.getColumnName();
									String setColumnValue = l.getSetColumnValue();

									String columnType = l.getSetColumnType();
									if (new Integer(1).equals(l.getIsSource()) && org.apache.commons.lang3.StringUtils.isNotBlank(columnValue) && (columnType.equalsIgnoreCase(ColumnTypeEnum.CHECKBOX.getValue())
											|| columnType.equalsIgnoreCase(ColumnTypeEnum.RADIO.getValue()) || columnType.equalsIgnoreCase(ColumnTypeEnum.DRP.getValue()))) {
										columnValue = caseHandleServiceHelper.getColumnMappingValue(setColumnValue, columnValue);
									}
									dg.setColumnName(columnName);
									if ((CaseMainStatusEnum.F.getValue().equals(dto.getStatus()) || CaseMainStatusEnum.F3.getValue().equals(dto.getStatus())
											|| CaseMainStatusEnum.J3.getValue().equals(dto.getStatus()) || CaseMainStatusEnum.S3.getValue().equals(dto.getStatus()))
											&& ("ID".equals(l.getSetColumnId()) || "Name".equals(l.getSetColumnId())) && !CaseFlowTypeEnum.A.getValue().equals(dto.getCaseFlowType())) {
										Timestamp fdate = l.getFdate();
										if (null != fdate) {
											LocalDateTime localDateTime = null;
											if (kgoParamSetOptional.isPresent()) {
												KgoParamSet kgoParamSet = kgoParamSetOptional.get();
												String value = kgoParamSet.getValue();
												String detailType = kgoParamSet.getDetailType();
												LocalDateTime fDateLocalDateTime = fdate.toLocalDateTime();
												if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
													localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
												} else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
													localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
												} else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
													localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
												} else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
													localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
												} else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
													localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
												} //if (detailType.equals(ParamSetTypeEnum.Y.getCode()))...
												if (localDateTime.isBefore(LocalDateTime.now())) {
													if ("Name".equals(l.getSetColumnId())) {
														columnValue = KgoUtil.hideName(columnValue);
													} else if ("ID".equals(l.getSetColumnId())) {
														columnValue = KgoUtil.hideID(columnValue);
													}
												} //if (localDateTime.isBefore(LocalDateTime.now()))
											} //if (kgoParamSetOptional.isPresent())
										} // if (null != fdate)
									} else if (CaseMainStatusEnum.A.getValue().equals(dto.getStatus()) && ("ID".equals(l.getSetColumnId()) || "Name".equals(l.getSetColumnId()))
											&& CaseFlowTypeEnum.A.getValue().equals(dto.getCaseFlowType())) {
										Date applyDateTemp = dto.getApplyDate();
										if (null != applyDateTemp) {
											Instant instant = applyDateTemp.toInstant();
											ZoneId zoneId = ZoneId.systemDefault();
											LocalDateTime applyDateTempLocalDateTime = instant.atZone(zoneId).toLocalDateTime();
											LocalDateTime localDateTime = null;
											if (kgoParamSetOptional.isPresent()) {
												KgoParamSet kgoParamSet = kgoParamSetOptional.get();
												String value = kgoParamSet.getValue();
												String detailType = kgoParamSet.getDetailType();
												LocalDateTime fDateLocalDateTime = applyDateTempLocalDateTime;
												if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
													localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
												} else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
													localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
												} else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
													localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
												} else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
													localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
												} else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
													localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
												} //if (detailType.equals(ParamSetTypeEnum.Y.getCode()))

												if (localDateTime.isBefore(LocalDateTime.now())) {
													if ("Name".equals(l.getSetColumnId())) {
														columnValue = KgoUtil.hideName(columnValue);
													} else if ("ID".equals(l.getSetColumnId())) {
														columnValue = KgoUtil.hideID(columnValue);
													}
												} //if (localDateTime.isBefore(LocalDateTime.now()))
											} // if (kgoParamSetOptional.isPresent())
										} //if (null != applyDateTemp)
									} //if ((CaseMainStatusEnum.F.getValue().equals(dto.getStatus())...

									List<List<CaseHandleCaseViewSaCaseOrganFormApplyDataDataGridComplex>> organComplexDataList = new ArrayList<List<CaseHandleCaseViewSaCaseOrganFormApplyDataDataGridComplex>>();
									if (organDtoMap.containsKey(l.getSetColumnId())) {
										List<SaCaseViewOrganFormDetailColumnQueryDto> value = organDtoMap.get(l.getSetColumnId());
										Map<Integer, List<SaCaseViewOrganFormDetailColumnQueryDto>> organDataMap = value.stream()
												.collect(Collectors.groupingBy(SaCaseViewOrganFormDetailColumnQueryDto::getCcolumnRowNum, HashMap::new, Collectors.toCollection(LinkedList::new)));
										organComplexDataList = organDataMap.keySet().stream().map(i -> {
											return organDataMap.get(i).stream().map(cl -> {
												CaseHandleCaseViewSaCaseOrganFormApplyDataDataGridComplex complexData = new CaseHandleCaseViewSaCaseOrganFormApplyDataDataGridComplex();
												complexData.setbText(org.apache.commons.lang3.StringUtils.defaultString(cl.getBtext(), ""));
												complexData.setcColumnId(org.apache.commons.lang3.StringUtils.defaultString(cl.getSetCcolumnId(), ""));
												complexData.setColumnType(org.apache.commons.lang3.StringUtils.defaultString(cl.getSetCcolumnType(), ""));
												String CcolumnValue = cl.getRealColumnValue();
												if (org.apache.commons.lang3.StringUtils.isNotBlank(CcolumnValue) && (cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_RADIO.getValue())
														|| cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_CHECKBOX.getValue())
														|| cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.DRP.getValue()))) {
													CcolumnValue = caseHandleServiceHelper.getColumnMappingValue(cl.getSetCcolumnValue(), CcolumnValue);
												}
												complexData.setColumnValue(org.apache.commons.lang3.StringUtils.defaultString(cl.getSetCcolumnValue(), ""));
												complexData.setfText(org.apache.commons.lang3.StringUtils.defaultString(cl.getFtext(), ""));
												complexData.setOrderNum(cl.getCcolumnOrderNum());
												complexData.setRow(cl.getCcolumnRowNum());
												complexData.setValue(org.apache.commons.lang3.StringUtils.defaultString(CcolumnValue, ""));
												return complexData;
											}).collect(Collectors.toList()); //return organDataMap.get(i).stream().map(cl
										}).collect(Collectors.toList()); //organComplexDataList = organDataMap.keySet().stream().map
									} //if (organDtoMap.containsKey(l.getSetColumnId()))

									dg.setComplex(organComplexDataList);
									dg.setColumnValue(columnValue);
									dg.setColumnId(l.getSetColumnId());
									dg.setCorrectBValue(l.getCorrectBValue());
									dg.setCorrectMemo(l.getCorrectMemo());
									dg.setColumnType(columnType);
									dg.setCommentId(l.getCommentId());
									return dg;
								}).collect(Collectors.toList()); //organFormData = organFilterDto.stream().map

								if (CollectionUtils.isEmpty(organFormData)) {
									if (CaseMainStatusEnum.A.getValue().equals(dto.getStatus()) && CaseFlowTypeEnum.A.getValue().equals(dto.getCaseFlowType())) {
										String applyUserName = dto.getApplyUserName();
										Date applyDateTemp = dto.getApplyDate();
										if (null != applyDateTemp) {
											Instant instant = applyDateTemp.toInstant();
											ZoneId zoneId = ZoneId.systemDefault();
											LocalDateTime applyDateTempLocalDateTime = instant.atZone(zoneId).toLocalDateTime();
											LocalDateTime localDateTime = null;
											if (kgoParamSetOptional.isPresent()) {
												KgoParamSet kgoParamSet = kgoParamSetOptional.get();
												String value = kgoParamSet.getValue();
												String detailType = kgoParamSet.getDetailType();
												LocalDateTime fDateLocalDateTime = applyDateTempLocalDateTime;
												if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
													localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
												} else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
													localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
												} else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
													localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
												} else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
													localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
												} else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
													localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
												}
												if (localDateTime.isBefore(LocalDateTime.now())) {
													applyUserName = KgoUtil.hideName(dto.getApplyUserName());
												}
											} // if (kgoParamSetOptional.isPresent())
										} //if (null != applyDateTemp)
										CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid dg = new CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid();
										dg.setColumnName(messageUtil.getMessage("kcg.common.name"));
										dg.setColumnValue(applyUserName);
										if (null != organFormData) {
											organFormData.add(dg);
										} else {
											organFormData = Arrays.asList(dg);
										}
									}
								} // if (CollectionUtils.isEmpty(organFormData))
							}else {
								caseProcessSearchDetailViewForm.setShow(false);
							} // if (organDtoList != null && organDtoList.size() > 0)
						}else {
							caseProcessSearchDetailViewForm.setShow(false);
						} //if (kgoCaseMain.getIsOpenOrganForm
					}else {
						caseProcessSearchDetailViewForm.setShow(false);
					} //if(ObjectUtils.isNotEmpty...

				// 新增案件流程重送 歷程記錄
					List<KgoCaseMainResendFlow> resendFlowList = kgoCaseMainResendFlowRepository.findByCaseId(dto.getCaseId());
					processIds.addAll(resendFlowList.stream().map(r -> r.getProcessId()).distinct().collect(Collectors.toList()));
					List<HistoryDataDto> historyDataDto = activitiService.getHistoryData(processIds);
					List<CaseHandleCaseViewCaseHistoryDataGrid> historyData = caseHandleService.castToHistoryDataGrid(historyDataDto,organFormData);
					caseProcessSearchDetailViewForm.setHistoryData(historyData);

					/** GEO 20211010 add for 1999 service **/
					if (kgoCaseset.getCaseSetName().contains(geoApi1999Properties.getKcg1999KdCaseSetName())) hadKd = true;
					if (kgoCaseset.getCaseSetName().contains(geoApi1999Properties.getKcg1999NewCaseSetName())) hadNew = true;
					if (hadKd || hadNew) {
						String validUntil= DateUtil.getAfterTime(new Date(),Integer.parseInt(geoApi1999Properties.getKgo1999ValidUntil()), DateUtil.PATTEN_FULL_TIME_NO_HYPHEN);
						String originalStr = String.format(geoApi1999Properties.getKgo1999OriginalFormatStr(), kgoCaseMain.getCaseId(), kgoCaseMain.getApplyUserName(), search1999Email, validUntil);
						String urlPrefix = geoApi1999Properties.getKgo1999KdSearchUrl();
						if (hadNew) urlPrefix = geoApi1999Properties.getKgo1999NewSearchUrl();

						String ivStr = GeoStringUtil.generateIvStr();
						String base64IvStr = GeoStringUtil.encodeIvStrToBase64(ivStr);
						String aesEncodeStr = GeoStringUtil.aesEncrypt(originalStr, geoApi1999Properties.getKgo1999AesKey(), GeoStringUtil.generateIv(ivStr));
						search1999Url = String.format(urlPrefix + geoApi1999Properties.getKgo1999UrlFormatStr(),
								GeoStringUtil.urlEncode(aesEncodeStr), GeoStringUtil.urlEncode(base64IvStr));
						LOGGER.info("FrontendCaseProcessSearchServiceImpl detailAction: \noriginalStr: "+originalStr+"\nbase64IvStr: "+base64IvStr+"\naesEncodeStr: "+aesEncodeStr+"\nsearch1999Url: "+search1999Url);
					} //if (hadKd || hadNew)
					caseProcessSearchDetailViewForm.setSearch1999Url(search1999Url);
					//GEO 20221121 for rentalcase
					if(kgoCaseset.getCasesetCategory() != null ){
						CaseSetCategoryEnum category = CaseSetCategoryEnum.getApplyTypeEnum(kgoCaseset.getCasesetCategory());
						if(CaseSetCategoryEnum.SITE.equals(category) || CaseSetCategoryEnum.ACTIVITY.equals(category) ){
							CaseSetRentalCategoryGrid categoryGrid = geoCaseSetRentService.findRentalCaseDetail(kgoCaseMain);
							caseProcessSearchDetailViewForm.setCategoryGrid(categoryGrid);
						}
					}
				} //if (kgoCasesetOptional.isPresent())
			} //if (null != kgoCaseMain)
			caseProcessSearchDetailRs.setData(caseProcessSearchDetailViewForm);
		} catch (KgoApiException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey(), e);
			throw new KgoApiException("detailAction error " + e.getMessage(), e);
		} finally {
			/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
			memoList.add(new OperationColumn("案件編號", rq.getGstrCaseId()));
			memo.setMemoList(memoList);
			super.saveOperationLog(memo);
			/** === 儲存操作紀錄 === */

			if (error != null) {
				throw error;
			}
		}
		return caseProcessSearchDetailRs;
	}

	public static FrontendLoginUserInfo getFrontendLoginUser() {
		LOGGER.info("KgoUtil getFrontendLoginUser...");
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			FrontendLoginUserInfo userInfo = (FrontendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.FRONTEND_USER_INO_KEY);
			LOGGER.info("FrontendLoginUserInfo userInfo="+userInfo);
			return userInfo;
	} //getFrontendLoginUser

	private List<CaseProcessSearchHistoryDataGrid> castToHistoryDataGrid(List<HistoryDataDto> historyDataDto) {
		List<CaseProcessSearchHistoryDataGrid> histories = new ArrayList<>();
		historyDataDto.forEach(item -> {
			CaseProcessSearchHistoryDataGrid grid = new CaseProcessSearchHistoryDataGrid();
			grid.setStatus(item.getStatus());
			grid.setOrgan(item.getOrganName());
			grid.setContent(item.getContent());
			grid.setTaker(item.getOfficer());
			grid.setDealTime(item.getDealTime());
			histories.add(grid);
		});
		// sort by dealTime(DESC)
		histories.sort(Collections.reverseOrder((entity1, entity2) -> entity1.getDealTime().compareTo(entity2.getDealTime())));
		return histories;
	}
}
