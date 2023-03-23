package gov.kcg.kgo.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import gov.kcg.kgo.geoentity.GeoKgoEmailLog;
import gov.kcg.kgo.georepository.GeoKgoEmailLogRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.dto.PushMsgDto;
import gov.kcg.kgo.dto.SaCaseViewQueryDto;
import gov.kcg.kgo.enums.backend.ApplyTypeEnum;
import gov.kcg.kgo.enums.backend.CaseFlowTypeEnum;
import gov.kcg.kgo.enums.backend.CaseMainStatusEnum;
import gov.kcg.kgo.enums.backend.FormType;
import gov.kcg.kgo.enums.common.BaseColumnEnum;
import gov.kcg.kgo.enums.common.CaseTypeEnum;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.common.YesNoFlag;
import gov.kcg.kgo.enums.error.CityApiError;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCaseDetail;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.model.KgoCasesetColumn;
import gov.kcg.kgo.model.KgoCasesetColumnPK;
import gov.kcg.kgo.model.KgoCasesetForm;
import gov.kcg.kgo.model.KgoCasesetMemo;
import gov.kcg.kgo.model.KgoHoliday;
import gov.kcg.kgo.model.KgoUseLog;
import gov.kcg.kgo.model.KgoUser;
import gov.kcg.kgo.repository.KgoCaseDetailRepository;
import gov.kcg.kgo.repository.KgoCaseMainRepository;
import gov.kcg.kgo.repository.KgoCasesetColumnRepository;
import gov.kcg.kgo.repository.KgoCasesetFormRepository;
import gov.kcg.kgo.repository.KgoCasesetMemoRepository;
import gov.kcg.kgo.repository.KgoCasesetRepository;
import gov.kcg.kgo.repository.KgoHolidayRepository;
import gov.kcg.kgo.repository.KgoUserLogRepository;
import gov.kcg.kgo.repository.KgoUserRepository;
import gov.kcg.kgo.repository.KgoZipRepository;
import gov.kcg.kgo.repository.custom.condition.KgoCaseMainQueryCondition;
import gov.kcg.kgo.service.ActivitiService;
import gov.kcg.kgo.service.BackEndKgoCaseLogService;
import gov.kcg.kgo.service.BackEndKgoLogService;
import gov.kcg.kgo.service.CaseHandleService;
import gov.kcg.kgo.service.KcgCityExtService;
import gov.kcg.kgo.service.PushService;
import gov.kcg.kgo.service.bean.excel.KgoCaseLogExportExcelVo;
import gov.kcg.kgo.service.bean.excel.KgoLogExportExcelVo;
import gov.kcg.kgo.service.impl.helper.KcgCityExtServiceHelper;
import gov.kcg.kgo.util.CryptUtil;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.JsonUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.MailUtil;
import gov.kcg.kgo.util.MessageUtil;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.commonApi.common.rs.bean.CaseColumn;
import gov.kcg.kgo.viewModel.commonApi.correctStatus.rq.CorrectStatusActionRq;
import gov.kcg.kgo.viewModel.commonApi.correctStatus.rs.CorrectStatusActionRs;
import gov.kcg.kgo.viewModel.commonApi.flow.b22.getCaseInfoAction.rq.FlowB22GetCaseInfoActionRq;
import gov.kcg.kgo.viewModel.commonApi.flow.b22.getCaseInfoAction.rs.FlowB22GetCaseInfoActionRs;
import gov.kcg.kgo.viewModel.commonApi.flow.c22.getCaseInfoAction.rq.FlowC22GetCaseInfoActionRq;
import gov.kcg.kgo.viewModel.commonApi.flow.c22.getCaseInfoAction.rs.FlowC22GetCaseInfoActionRs;
import gov.kcg.kgo.viewModel.commonApi.flow.c23.getCaseInfoListAction.rq.FlowC23GetCaseInfoActionRq;
import gov.kcg.kgo.viewModel.commonApi.flow.c23.getCaseInfoListAction.rs.FlowC23GetCaseInfoListActionRs;
import gov.kcg.kgo.viewModel.commonApi.flow.c23.getCaseInfoListAction.rs.bean.FlowC23GetCaseInfoActionViewForm;
import gov.kcg.kgo.viewModel.commonApi.flow.c3.addCaseAction.rq.FlowC3AddCaseActionRq;
import gov.kcg.kgo.viewModel.commonApi.flow.c3.addCaseAction.rs.FlowC3AddCaseActionRs;
import gov.kcg.kgo.viewModel.commonApi.flow.c3.addCaseAction.rs.bean.FlowC3AddCaseActionViewForm;
import gov.kcg.kgo.viewModel.commonApi.flow.common.rs.bean.CaseColumnDetail;
import gov.kcg.kgo.viewModel.commonApi.flow.common.rs.bean.FlowGetCaseInfoViewForm;
import gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rq.GenGeneralCaseActionRq;
import gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rq.bean.CaseSetForm;
import gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rq.bean.CaseSetMemo;
import gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rs.GenGeneralCaseActionRs;
import gov.kcg.kgo.viewModel.commonApi.getCaseId.rq.GetCaseIdActionRq;
import gov.kcg.kgo.viewModel.commonApi.getCaseId.rs.GetCaseIdActionRs;
import gov.kcg.kgo.viewModel.commonApi.getCaseId.rs.bean.GetCaseIdActionViewForm;
import gov.kcg.kgo.viewModel.commonApi.getCaseLogJson.rq.GetCaseLogJsonActionRq;
import gov.kcg.kgo.viewModel.commonApi.getCaseLogJson.rs.GetCaseLogJsonActionRs;
import gov.kcg.kgo.viewModel.commonApi.getCaseLogJson.rs.bean.GetCaseLogJsonActionViewForm;
import gov.kcg.kgo.viewModel.commonApi.getGeneralColumn.rq.GetGeneralColumnActionRq;
import gov.kcg.kgo.viewModel.commonApi.getGeneralColumn.rs.GetGeneralColumnActionRs;
import gov.kcg.kgo.viewModel.commonApi.getGeneralColumn.rs.bean.GetGeneralColumnActionViewForm;
import gov.kcg.kgo.viewModel.commonApi.getUserLogJson.rq.GetUserLogJsonActionRq;
import gov.kcg.kgo.viewModel.commonApi.getUserLogJson.rs.GetUserLogJsonActionRs;
import gov.kcg.kgo.viewModel.commonApi.getUserLogJson.rs.bean.GetUserLogJsonActionViewForm;
import gov.kcg.kgo.viewModel.commonApi.getstatus.rq.GetCaseStatusActionRq;
import gov.kcg.kgo.viewModel.commonApi.getstatus.rs.GetCaseStatusActionRs;
import gov.kcg.kgo.viewModel.commonApi.getstatus.rs.bean.GetCaseStatusActionViewForm;
import gov.kcg.kgo.viewModel.commonApi.notify.rq.QueryStatusActionRq;
import gov.kcg.kgo.viewModel.commonApi.notify.rs.QueryStatusActionRs;
import gov.kcg.kgo.viewModel.commonApi.notify.rs.bean.QueryStatasInfo;
import gov.kcg.kgo.viewModel.commonApi.notify.rs.bean.QueryStatusActionViewForm;
import gov.kcg.kgo.viewModel.commonApi.updateCaseStatus.rq.UpdateCaseStatusActionRq;
import gov.kcg.kgo.viewModel.commonApi.updateCaseStatus.rq.bean.UpdateCase;

/**
 * 對外API 高雄程式資料平台 API Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service("KcgCityExtService")
public class KcgCityExtServiceImpl implements KcgCityExtService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KcgCityExtServiceImpl.class);

	/** helper. */
	private KcgCityExtServiceHelper helper = KcgCityExtServiceHelper.getInstance();

	@Autowired
	private KgoCaseMainRepository kgoCaseMainRepository;

	@Autowired
	private KgoCasesetRepository kgoCasesetRepository;

	@Autowired
	private KgoCasesetColumnRepository kgoCasesetColumnRepository;

	@Autowired
	private KgoCaseDetailRepository kgoCaseDetailRepository;

	@Autowired
	private KgoCasesetMemoRepository kgoCasesetMemoRepository;

	@Autowired
	private KgoCasesetFormRepository kgoCasesetFormRepository;

	@Autowired
	private KgoZipRepository kgoZipRepository;

	@Autowired
	private ActivitiService activitiService;

	@Autowired
	private BackEndKgoLogService backEndKgoLogService;

	@Autowired
	private KgoUserLogRepository kgoUserLogRepository;

	@Autowired
	private BackEndKgoCaseLogService backEndKgoCaseLogService;

	@Autowired
	private CaseHandleService caseHandleService;

	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private MailUtil mailUtil;

	@Value("${fontend.case.search.link}")
	private String frontendCaseSearchLink;

	@Value("${backend.case.search.link}")
	private String backendCaseSearchLink;

	@Value("${backend.url}")
	private String backendUrl;

	@Autowired
	private PushService pushService;

	@Autowired
	private KgoUserRepository kgoUserRepository;

	@Autowired
	private KgoHolidayRepository kgoHolidayRepository;

	@Autowired
	private CaseFormServiceImpl caseFormService;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private GeoKgoEmailLogRepository geoKgoEmailLogRepository;

	/**
	 * 提供既有案件服務撈取案件資料API，B-2流程_2.
	 *
	 * @param rq the rq
	 * @return the flow B 22 get case info action rs
	 */
	@Override
	public FlowB22GetCaseInfoActionRs flowB22GetCaseInfoAction(FlowB22GetCaseInfoActionRq rq) {
		FlowB22GetCaseInfoActionRs rs = new FlowB22GetCaseInfoActionRs();
		String caseId = rq.getCaseId();
		KgoApiException error = null;
		try {
			// 取得 提供既有案件服務撈取案件資料 共通 rs.
			rs = helper.getCaseInfoCommonRs(caseId, rs);

		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
		} finally {
			// 設置 成功/失敗訊息.
			setResultMessage(rq, rs, error);
		}
		return rs;
	}

	/**
	 * 提供既有案件服務撈取案件資料API，C-2流程_2.
	 *
	 * @param rq the rq
	 * @return the flow C 22 get case info action rs
	 */
	@Override
	public FlowC22GetCaseInfoActionRs flowC22GetCaseInfoAction(FlowC22GetCaseInfoActionRq rq) {
		FlowC22GetCaseInfoActionRs rs = new FlowC22GetCaseInfoActionRs();
		String caseId = rq.getCaseId();
		KgoApiException error = null;
		try {
			// 取得 提供既有案件服務撈取案件資料 共通 rs
			rs = helper.getCaseInfoCommonRs(caseId, rs);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
		} finally {
			// 設置 成功/失敗訊息.
			setResultMessage(rq, rs, error);
		}
		return rs;
	}

	/**
	 * 提供單一案件類型下所有案件編號給稅捐，C-2_3稅捐.
	 *
	 * @return the flow C 23 get case info list action rs
	 */
	@Override
	public FlowC23GetCaseInfoListActionRs flowC23GetCaseInfoAction(FlowC23GetCaseInfoActionRq rq) {
		FlowC23GetCaseInfoListActionRs rs = new FlowC23GetCaseInfoListActionRs();
		FlowC23GetCaseInfoActionViewForm viewForm = new FlowC23GetCaseInfoActionViewForm();
		rs.setData(viewForm);
		KgoApiException error = null;
		try {
			// 依案件狀態:(W:待處理") 查找 "稅捐稽徵處"案件
			final String organName = "稅捐稽徵處";
			final String organId = "397030500D";
			String caseSetId = rq.getCaseSetId();
			List<KgoCaseMain> flowC23CaseList = kgoCaseMainRepository.findFlowCaseAndStatus(caseSetId, CaseMainStatusEnum.W.getValue());

			if (CollectionUtils.isNotEmpty(flowC23CaseList)) {
				List<String> caseIdList = flowC23CaseList.stream().map(f -> f.getCaseId()).collect(Collectors.toList());
				List<SaCaseViewQueryDto> flowC23CaseDtoList = kgoCaseMainRepository.getSaCaseViewDataList(caseIdList);

				// 取得所有欄位資料
//				List<SaCaseColumnDetailDto> caseMainDtoList = kgoCasesetColumnRepository.getSaCaseExtraDataByCaseIdList(caseIdList);
//				Map<String, List<SaCaseColumnDetailDto>> caseColumnMap = caseMainDtoList.stream().collect(Collectors.groupingBy(d -> d.getId().getCaseId()));

				List<FlowGetCaseInfoViewForm> caseInfoList = new ArrayList<>();
				for (SaCaseViewQueryDto caseMainDto : flowC23CaseDtoList) {
					String caseId = caseMainDto.getCaseId();
//					List<SaCaseColumnDetailDto> columnDtoList = caseColumnMap.get(caseMainDto.getCaseId());

//					if (CollectionUtils.isNotEmpty(columnDtoList)) {
					// 取得既有案件服務撈取案件資料 (共通Form 含myData資料).
//						FlowGetCaseInfoViewForm caseInfo = helper.getFlowGetCaseInfoViewForm(caseMainDto, kgoZipF3AreaDotList, FlowGetCaseInfoViewForm.class);
//						caseInfoList.add(caseInfo);
//					}

					// modify : 2021.01.28 增加覆核欄位資料 改由 getFlowGetCaseInfoViewForm() 撈出覆核欄位值
					// 取得既有案件服務撈取案件資料 (稅捐)
					Boolean isFLowC23 = true;
					FlowGetCaseInfoViewForm caseInfo = helper.getFlowGetCaseInfoViewForm(caseMainDto, FlowGetCaseInfoViewForm.class, isFLowC23);
					caseInfoList.add(caseInfo);

//					CaseHandleCaseViewSaCaseHomeRq caseHomeRq = new CaseHandleCaseViewSaCaseHomeRq();
//					caseHomeRq.setCaseId(caseId);
//					CaseHandleCaseViewSaCaseHomeRs caseHomeRs = caseHandleService.caseHandleCaseViewSaCaseHome2(caseHomeRq);
//					CaseHandleCaseViewSaCaseHomeViewForm caseHomeViewForm = caseHomeRs.getData();
//					List<CaseHandleCaseViewSaCaseApplyDataDataGrid> applyDataList = caseHomeViewForm.getApplyData();

//					InputStream chartDOpinionDataFile = new ClassPathResource("/templates/cathay360/pdf/evalResult/evalResultReport_chartD_opinion.jasper").getInputStream();
//					JasperReport chartDOpinionDataReport = (JasperReport) JRLoader.loadObject(chartDOpinionDataFile);
//					parameter.put("SUBREPORT_D_OPINION", chartDOpinionDataReport);
				}

				viewForm.setCaseInfoList(caseInfoList);

				// 查無案件
			} else {

				throw new KgoApiException(new ErrorResult(CityApiError.CAN_NOT_FIND_CASE));
			}
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION));
		} finally {
			// 設置 成功/失敗訊息.
			setResultMessage(rq, rs, error);
		}
		return rs;
	}

	/**
	 * 提供平台案件處理區呼叫使用，C-3流程 新增案件.
	 *
	 * @param rq the rq
	 * @return the flow C 3 add case action rs
	 */
	@Override
	public FlowC3AddCaseActionRs flowC3AddCaseAction(FlowC3AddCaseActionRq rq) {
		KgoApiException error = null;
		FlowC3AddCaseActionRs rs = new FlowC3AddCaseActionRs();
		FlowC3AddCaseActionViewForm viewForm = new FlowC3AddCaseActionViewForm();
		rs.setData(viewForm);
		String caseSetId = rq.getCaseSetId();

		try {
			// 案件服務編號 檢核.
			KgoCaseset kgoCaseset = validateCaseSetId(caseSetId);

			// 明細欄位必填驗證
			validateColumn(rq.getCaseColumns(), Arrays.asList(BaseColumnEnum.ID, BaseColumnEnum.EMAIL, BaseColumnEnum.NAME, BaseColumnEnum.CELL_PHONE));

			// 限辦期限
			Integer limitedPeriod = kgoCaseset.getLimitedPeriod() == null ? 0 : kgoCaseset.getLimitedPeriod();
			// 申辦日期
			Date applayDate = DateUtil.strToDate(rq.getApplyDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH);

			// 取得限辦日期
			Date deadlineDate = helper.getDeadlineDate(applayDate, limitedPeriod);

			// 新案件 案件編號
			String newCaseId = KgoUtil.getNextCaseId(CaseTypeEnum.SA);
			viewForm.setCaseId(newCaseId);

			Timestamp now = DateUtil.getCurrentTimestamp();
			// 儲存案件主檔
			KgoCaseMain newKgoCaseMain = new KgoCaseMain();
			newKgoCaseMain.setCaseId(newCaseId);
			newKgoCaseMain.setCaseSetId(caseSetId);
			newKgoCaseMain.setApplyUser(rq.getApplyUser());
			newKgoCaseMain.setCreateUser(rq.getApplyUser());
			newKgoCaseMain.setUpdateUser(rq.getApplyUser());
			newKgoCaseMain.setStatus(CaseMainStatusEnum.W.getValue());
			newKgoCaseMain.setApplyUserName(rq.getApplyUserName());
			newKgoCaseMain.setCaseOrgan(rq.getCaseOrgan());
			newKgoCaseMain.setApplyDate(applayDate);
			newKgoCaseMain.setDeadlineDate(deadlineDate);
			newKgoCaseMain.setCreateTime(now);
			newKgoCaseMain.setUpdateTime(now);
			newKgoCaseMain = kgoCaseMainRepository.save(newKgoCaseMain);

			List<CaseColumnDetail> columnDetailList = rq.getCaseColumns();
			if (CollectionUtils.isNotEmpty(columnDetailList)) {
				// 撈取最大版本
				Integer maxVerson = kgoCasesetColumnRepository.getMaxVersonByCaseSetId(caseSetId);

				// 取得欄位設定map 判斷欄位資料是否有對應值
				Map<KgoCasesetColumnPK, KgoCasesetColumn> caseCouumnMap = helper.getCaseColumnMap(columnDetailList, caseSetId, maxVerson);

				// 提供平台案件處理區呼叫使用，C-3流程 detail資料處理.
				List<KgoCaseDetail> detailList = helper.generalSaveDetailList(newCaseId, caseSetId, maxVerson, columnDetailList, caseCouumnMap);
				kgoCaseDetailRepository.saveAll(detailList);
			}

			// 一般民眾 - 申請人 email
			String applyEmail = getColVal(columnDetailList, BaseColumnEnum.EMAIL.getColumnId());

			// 案件儲存共通作法 - 發送email 、通知、KCG API
			caseFormService.doSaveCaseCommonNotify(newKgoCaseMain, kgoCaseset, applyEmail, rq.getApplyDate());

//			// 一般民眾寄信
//			// 申請人 email
//			this.sendEmailApplyCase(newCaseId, kgoCaseset, applyEmail, rq.getApplyDate());
//
//			// 依流程類型發信給市府人員
//			CaseFlowTypeEnum caseFlowTypeE = CaseFlowTypeEnum.getCaseFlowTypeEnum(kgoCaseset.getCaseFlowType());
//			String emails = StringUtils.EMPTY;
//			// "B3", "由本平台完整申辦" 寄信
//			if (CaseFlowTypeEnum.B3.equals(caseFlowTypeE)) {
//				// 寄信給 承辦人 or 管理者
//				AcceptSetEnum acceptSetEnum = AcceptSetEnum.getEnum(kgoCaseset.getAcceptSet());
//				
//				// 承辦人
//				if (acceptSetEnum.equals(AcceptSetEnum.OFFICER)) {
//					emails = kgoUserRepository.findOfficerEmailsByCaseSetId(kgoCaseset.getCaseSetId());
//				
//				// 機關分文
//				} else if (acceptSetEnum.equals(AcceptSetEnum.UNIT)) {
//					emails = kgoUserRepository.findUnitEmailsByCaseSetId(kgoCaseset.getCaseSetId());
//				
//				// 區機關 
//				} else if (acceptSetEnum.equals(AcceptSetEnum.AREA)) {
//					emails = kgoUserRepository.findAreaEmailsByCaseSetId(kgoCaseset.getCaseSetId());
//				}
//				
//			// A("A", "站外連結"),  B1("B1", "案件暫存區(入案通知)"),  B2("B2", "案件暫存區(自行取案)") 寄信方式
//			} else {
//				// String email = kgoUserRepository.findEmailsByOrgan(kgoCaseset.getOwnerOrgan());
//				emails = kgoUserRepository.findEmailsByCaseSetId(kgoCaseset.getCaseSetId());
//			}
//			this.sendEmailSignDispatch(newCaseId, kgoCaseset.getCaseSetName(), emails);
//
//			// 發送推播
//			pushService.pushMessage(newKgoCaseMain.getApplyUser(), PushCaseStatusEnum.W.getValue(), newKgoCaseMain.getCaseId(), kgoCaseset.getCaseSetName(), rq.getApplyDate());

		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
		} finally {
			// 設置 成功/失敗訊息.
			setResultMessage(rq, rs, error);
		}
		return rs;
	}

	/**
	 * 欄位驗證.
	 *
	 * @param detailList the detail list
	 * @param columnId   the column id
	 * @param isThrowEx  the is throw ex
	 * @return the column
	 */
	private void validateColumn(List<CaseColumnDetail> columnDetailList, List<BaseColumnEnum> baseColumnEnumList) {
		for (BaseColumnEnum baseColumnEnum : baseColumnEnumList) {
			String baseColId = baseColumnEnum.getColumnId();
			// 取得欄位資料
			String colValue = getColVal(columnDetailList, baseColId);

			if (StringUtils.isBlank(colValue)) {
				LOGGER.info(">>>>> {} 欄位未填寫", baseColId);
				throw new KgoApiException(new ErrorResult(CityApiError.COL_REQUIRED, new Object[] { baseColId }));
			}
		}
	}

	/**
	 * 取得欄位資料.
	 *
	 * @param columnDetailList the column detail list
	 * @param baseColId        the base col id
	 * @return the col val
	 */
	private String getColVal(List<CaseColumnDetail> columnDetailList, String baseColId) {
		Optional<CaseColumnDetail> detail = columnDetailList.stream().filter(x -> x.getColumnId().equalsIgnoreCase(baseColId)).findFirst();
		if (detail.isPresent()) {
			return StringUtils.isNotBlank(detail.get().getColumnValue()) ? detail.get().getColumnValue() : StringUtils.EMPTY;
		} else {
			return StringUtils.EMPTY;
		}
	}

//	/**
//	 * 一般民眾寄信.
//	 *
//	 * @param applyDate        the apply date
//	 * @param caseId           the case id
//	 * @param kgoCaseset       the kgo caseset
//	 * @param columnDetailList the column detail list
//	 * @throws Exception the exception
//	 */
//	private void sendEmailApplyCase(String applyDate, String caseId, KgoCaseset kgoCaseset, List<CaseColumnDetail> columnDetailList) throws Exception {
//
//		try {
//			Map<String, Object> model = new HashMap<String, Object>();
//
//			model.put("casesetName", kgoCaseset.getCaseSetName());
//			model.put("caseId", caseId);
//			model.put("applyDate", applyDate);
//
//			// 申請人 email
//			String emsil = getColVal(columnDetailList, BaseColumnEnum.EMAIL.getColumnId());
//
//			// 11.18 modify: 改用util 寄送mail
//			// 高雄市便民一路通訊息通知
//			mailUtil.sendTemplateMail(new String[] { emsil }, null, messageUtil.getMessage("kgo.backend.case.handle.review.notification.msgTitle"), model, "applyCase.html");
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			LOGGER.error(String.format("sendEmailApplyCase caseId: %s", caseId), e);
//		}
//	}

	/**
	 * 寄信給管理者.
	 *
	 * @param caseId the case id
	 * @param email  the email
	 * @throws Exception the exception
	 */
	private void sendEmailSignDispatch(String caseId, String casesetName, String emails) throws Exception {
		String[] sendTo = new String[] {};

		if (StringUtils.isNoneBlank(emails)) {
			if (StringUtils.contains(emails, ",")) {
				sendTo = StringUtils.split(emails, ",");
			}

			Map<String, Object> model = new HashMap<>();
			model.put("caseId", caseId);
			model.put("backendUrl", backendUrl);
			model.put("casesetName", casesetName);

			mailUtil.sendTemplateMail(sendTo, null, messageUtil.getMessage("kgo.backend.case.handle.pending.sign.dispatch.title"), model, "pendingSignDispatch.html");
		}
	}

	/**
	 * 通用型入案作業.
	 *
	 * @param rq the rq
	 * @return the gen general case action rs
	 */
	@Override
	public GenGeneralCaseActionRs genGeneralCaseAction(GenGeneralCaseActionRq rq) {
		GenGeneralCaseActionRs rs = new GenGeneralCaseActionRs();
		String caseSetId = rq.getCaseSetId();
		KgoApiException error = null;
		try {
			// 案件服務編號 檢核.
			validateCaseSetId(caseSetId);

			// 網路申辦(E)
			CaseSetMemo rqMemoE = rq.getCaseSetMemoE();
			KgoCasesetMemo casesetMemoE = new KgoCasesetMemo();

			casesetMemoE.setCaseSetId(caseSetId);
			casesetMemoE.setApplyType(ApplyTypeEnum.E.getValue());
			casesetMemoE.setTitle(rqMemoE.getTitle());
			casesetMemoE.setFileName(rqMemoE.getFileName());
			casesetMemoE.setContentHtml(rqMemoE.getContent());
			kgoCasesetMemoRepository.save(casesetMemoE);

			// 臨櫃申辦(C)
			CaseSetMemo rqMemoC = rq.getCaseSetMemoC();
			KgoCasesetMemo casesetMemoC = new KgoCasesetMemo();
			casesetMemoC.setCaseSetId(caseSetId);
			casesetMemoC.setApplyType(ApplyTypeEnum.C.getValue());
			casesetMemoC.setTitle(rqMemoC.getTitle());
			casesetMemoC.setFileName(rqMemoC.getFileName());
			casesetMemoC.setContentHtml(rqMemoC.getContent());
			kgoCasesetMemoRepository.save(casesetMemoC);

			// 書表維護
			CaseSetForm rqCaseForm = rq.getCaseSetForm();
			KgoCasesetForm kgoCasesetForm = new KgoCasesetForm();
			kgoCasesetForm.setTitle(rqCaseForm.getTitle());

			// 書表維護類檢核
			FormType formType = FormType.getFormType(rqCaseForm.getType());
			if (FormType.UNKNOW.equals(formType)) {
				// 書表維護類別錯誤
				error = new KgoApiException(new ErrorResult(CityApiError.CASE_SET_FORM_TYPE_ERROR));
			}
			kgoCasesetForm.setCaseSetId(caseSetId);
			kgoCasesetForm.setType(formType.getFormType());
			kgoCasesetForm.setFileName(rqCaseForm.getFileName());
			kgoCasesetFormRepository.save(kgoCasesetForm);

			// 通用型入案作業 檔案上傳 網路申辦(E)、臨櫃(C)、書表(L).
			helper.genGeneralCaseUploadFile(rq);

		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
		} finally {
			// 設置 成功/失敗訊息.
			setResultMessage(rq, rs, error);
		}
		return rs;
	}

	/**
	 * 通用型查詢欄位作業.
	 *
	 * @param rq the rq
	 * @return the generic column action
	 */
	@Override
	public GetGeneralColumnActionRs getGeneralColumnAction(GetGeneralColumnActionRq rq) {
		GetGeneralColumnActionRs rs = new GetGeneralColumnActionRs();
		GetGeneralColumnActionViewForm viewForm = new GetGeneralColumnActionViewForm();
		List<CaseColumn> columns = new ArrayList<>();
		viewForm.setColumns(columns);
		rs.setData(viewForm);
		KgoApiException error = null;
		String caseSetId = rq.getCaseSetId();
		try {
			// 案件服務編號 檢核.
			validateCaseSetId(caseSetId);

			// 撈取最大版本
			Integer maxVerson = kgoCasesetColumnRepository.getMaxVersonByCaseSetId(caseSetId);

			// 案件欄位設定清單
			List<KgoCasesetColumn> caseColumnList = kgoCasesetColumnRepository.findByIdCaseSetIdAndIdVersion(caseSetId, maxVerson);
			// 抓取對應欄位資料(columnId, columnName)
			for (KgoCasesetColumn column : caseColumnList) {
				CaseColumn caseColumn = new CaseColumn(column.getId().getColumnId(), column.getColumnName());
				columns.add(caseColumn);
			}

		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
		} finally {
			// 設置 成功/失敗訊息.
			setResultMessage(rq, rs, error);
		}
		return rs;
	}

	/**
	 * 查詢案件狀態，自動通知API.
	 *
	 * @param rq the rq
	 * @return the notify action rs
	 */
	@Override
	public QueryStatusActionRs queryStatusAction(QueryStatusActionRq rq) {
		QueryStatusActionRs rs = new QueryStatusActionRs();
		QueryStatusActionViewForm viewForm = new QueryStatusActionViewForm();
		List<QueryStatasInfo> infoList = new ArrayList<>();
		viewForm.setQueryStatasInfo(infoList);
		rs.setData(viewForm);
		KgoApiException error = null;
		try {
			String caseId = rq.getCaseId();
			String applyUser = rq.getApplyUser();
			String cellPhone = rq.getCellPhone();

			// 查詢條件不可為空
			if (StringUtils.isBlank(caseId) && StringUtils.isBlank(applyUser) && StringUtils.isBlank(cellPhone)) {
				error = new KgoApiException(new ErrorResult(CityApiError.REQUIRED_QUERY_CONDITION));
			}

			KgoCaseMainQueryCondition condition = new KgoCaseMainQueryCondition();
			condition.setCaseId(caseId);
			condition.setApplyUser(applyUser);
			condition.setCellPhone(cellPhone);
			List<KgoCaseMain> mainList = kgoCaseMainRepository.findByCondition(condition);
			// 查無案件
			if (CollectionUtils.isEmpty(mainList)) {
				error = new KgoApiException(new ErrorResult(CityApiError.CAN_NOT_FIND_CASE));
			} else {
				for (KgoCaseMain m : mainList) {
					QueryStatasInfo info = new QueryStatasInfo();
					// 申請人員、案件狀態
					info.setApplyUser(m.getApplyUser());
					info.setStauts(m.getStatus());
					infoList.add(info);
				}
			}
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
		} finally {
			// 設置 成功/失敗訊息.
			setResultMessage(rq, rs, error);
		}
		return rs;
	}

	/**
	 * 更新案件狀態.
	 *
	 * @param rq the rq
	 * @return the api base response
	 */
	@Override
	public ApiBaseResponse<BaseViewForm> updateCaseStatusAction(UpdateCaseStatusActionRq rq) {
//		long start = System.currentTimeMillis();
		ApiBaseResponse<BaseViewForm> rs = new ApiBaseResponse<>();
		KgoApiException error = null;
		List<String> successCaseIds = new ArrayList<>();
		List<String> errorMsgs = new ArrayList<>();
		try {
			//獲取rq內的所有案件編號
			List<String> caseIdList = rq.getCaseList().stream().map(c -> c.getCaseId()).collect(Collectors.toList());
			// 案件編號錯誤
			if (CollectionUtils.isEmpty(caseIdList)) {
				throw new KgoApiException(new ErrorResult(CityApiError.CASE_ID_ERROR));
			}
			List<KgoCaseMain> kgoCaseMainList = kgoCaseMainRepository.findAllById(caseIdList);

			// 查無案件
			if (CollectionUtils.isEmpty(kgoCaseMainList)) {
				throw new KgoApiException(new ErrorResult(CityApiError.CAN_NOT_FIND_CASE));
			} else {
				//rq內的案件清單
				Map<String, UpdateCase> rqUpdateCaseMap = rq.getCaseList().stream().collect(Collectors.toMap(c -> c.getCaseId(), c -> c));
				//資料庫的案件清單
				Map<String, KgoCaseMain> caseMainMap = kgoCaseMainList.stream().collect(Collectors.toMap(c -> c.getCaseId(), c -> c));

				List<KgoCaseMain> successList = new ArrayList<>();
				for (String caseId : caseIdList) {
//					LOGGER.info("call updateCaseStatusAction caseId="+caseId);
					KgoCaseMain caseMain = caseMainMap.get(caseId);

					UpdateCase updateCase = rqUpdateCaseMap.get(caseId);

					// 案件狀態
					CaseMainStatusEnum statusEnum = CaseMainStatusEnum.getCaseMainStatusEnum(updateCase.getStatus());
					if (caseMain == null) {
						errorMsgs.add(caseId + " 查無案件資料");
						continue;
					} else if (statusEnum == null) {
						errorMsgs.add(caseId + " 更新狀態為必填或狀態不在規範內");
						continue;
					} else {
						try {
							// 驗證: 只限定 A、B1、B2 流程.
							validateCaseFlowType(caseMain.getCaseSetId());
						} catch (Exception e) {
							errorMsgs.add(caseId + " 整合流程分類須為站外連結或案件暫存區(A,B1,B2)");
							continue;
						}

						try {
							// 驗證: 狀態說明(status=O 時，此欄為必填).
							validateStatusO(statusEnum, updateCase.getStatusDesc());
						} catch (Exception e) {
							errorMsgs.add(caseId + " 狀態說明(status=O 時，此欄為必填)");
							continue;
						}

						caseMain.setStatus(updateCase.getStatus());
						caseMain.setUpdateTime(DateUtil.getCurrentTimestamp());
						caseMain.setUpdateUser("updateCaseStatusAction");
						caseMain.setCaseOfficer(updateCase.getCaseOfficer());

						// 原系統案件編號
						if (StringUtils.isNotBlank(updateCase.getoCaseId())) {
							caseMain.setoCaseId(updateCase.getoCaseId());
						}

						// 申請人證號
						if (StringUtils.isNotBlank(updateCase.getApplyUser())) {
							caseMain.setApplyUser(updateCase.getApplyUser());
							caseMain.setIdSHA256(CryptUtil.SHA256Encrypt(updateCase.getApplyUser()));
						}

						if (StringUtils.isNotBlank(updateCase.getfDate())) {
							caseMain.setfDate(DateUtil.strToTimestamp(updateCase.getfDate(), DateUtil.PATTEN_FULL_TIME_NO_HYPHEN));
						}

						caseMain.setStatusDesc(updateCase.getStatusDesc());
						successList.add(caseMain);
						successCaseIds.add(caseId);

						// activiti 案件歷程
//						String processId = caseMain.getProcessId();
//						if (StringUtils.isNoneBlank(caseMain.getProcessId())) {
//							String loginUserId = StringUtils.EMPTY;
//							// TODO 寫入內容 待確認
//							String caseComment = "更新案件狀態";
//							activitiService.addComment(loginUserId, processId, statusEnum.getLabel(), caseComment, KgoUtil.getOrganName(caseMain.getCaseOrgan()), caseMain.getCaseOfficer());
//						}

						// 是否通知申請人 Y/N
						// 6. 更新成功後，需判斷是否發送狀態異動通知給申請人(Email/推播)
						YesNoFlag notifyFlag = YesNoFlag.getFlag(updateCase.getIsNotify());
						if (YesNoFlag.Y.equals(notifyFlag)) {
							Boolean isSend = geoKgoEmailLogRepository.existsByCaseIdAndStatuteLike(updateCase.getCaseId(),updateCase.getStatus());
							if(!isSend){
								// TODO 確認 發送推播. 是否同樣內容
								doNotifyEmail(caseMain);
								Timestamp now = new Timestamp(System.currentTimeMillis());
								/** === 儲存寄信紀錄 === */
								GeoKgoEmailLog geoKgoEmailLog = new GeoKgoEmailLog();
								geoKgoEmailLog.setStatute(updateCase.getStatus());
								geoKgoEmailLog.setCaseId(updateCase.getCaseId());
								geoKgoEmailLog.setSendTime(now);
								geoKgoEmailLogRepository.save(geoKgoEmailLog);
								/** === 儲存寄信紀錄 === */
//								LOGGER.info(String.format("任務執行成功,耗時{%s}毫秒",System.currentTimeMillis()-start));
							}else {
								LOGGER.info("已寄信過，不重複發信");
							} //if(isSend)
						} //if (YesNoFlag.Y.equals(notifyFlag))
					}
				}
				kgoCaseMainRepository.saveAll(successList);
			}
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
		} finally {
			// 設置 成功/失敗訊息.
			setUpdateStatusResultMessage(rs, successCaseIds, errorMsgs, error);
		}
		return rs;
	}

	/**
	 * 發送信件與推播
	 *
	 * @param kgoCaseMain the kgo case main
	 */
	private void doNotifyEmail (KgoCaseMain kgoCaseMain) {
		LOGGER.info("call doNotifyEmail...");
		// 發送mail及推播
		String caseSetId = kgoCaseMain.getCaseSetId();
		Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(caseSetId);
		if (kgoCaseset.isPresent()) {
			try {
				// 寄出email
				List<KgoCaseDetail> kgoCaseDetails = kgoCaseDetailRepository.findByIdCaseIdAndIdColumnId(kgoCaseMain.getCaseId(), BaseColumnEnum.EMAIL.getColumnId());
				Optional<KgoCaseDetail> max = kgoCaseDetails.stream().max(Comparator.comparing(item -> item.getId().getVersion()));
				if (max.isPresent()) {
					String columnValue = max.get().getColumnValue();
					if (!org.springframework.util.StringUtils.isEmpty(columnValue)) {
						Map<String, Object> model = new HashMap<>();
						model.put("caseId", kgoCaseMain.getCaseId());

						CaseMainStatusEnum caseMainStatusEnum = CaseMainStatusEnum.getCaseMainStatusEnum(kgoCaseMain.getStatus());
						if (ObjectUtils.isNotEmpty(caseMainStatusEnum)) {
							if (caseMainStatusEnum == CaseMainStatusEnum.O) {
								model.put("statusDesc", String.format("%s (%s)", caseMainStatusEnum.getLabel(), kgoCaseMain.getStatusDesc()));
							} else {
								model.put("statusDesc", caseMainStatusEnum.getLabel());
							}
						} else {
							model.put("statusDesc", CaseMainStatusEnum.OTHERS.getLabel());
						}
						model.put("caseUrl", String.format(frontendCaseSearchLink, kgoCaseMain.getCaseId()));
						model.put("caseSetName", kgoCaseset.get().getCaseSetName());
						mailUtil.sendTemplateMail(new String[] { columnValue }, null, messageUtil.getMessage("kcg.city.change.status.mail.title"), model, "cityChangeStatusNotify.html");
					} else {
						LOGGER.info("updateCaseStatusAction doNotify no email to send empty String");
					}
				} else {
					LOGGER.info("updateCaseStatusAction doNotify no email to send empty");
				}
			} catch (Exception e) {
				ErrorResult er = new ErrorResult(KgoBackEndApiError.FAIL_TO_EMAIL);
				LOGGER.error(er.getErrorDesc());
			}
			//LOGGER.info("call doNotifyPush start...");
			doNotifyPush(kgoCaseMain,kgoCaseset);
			//LOGGER.info("call doNotifyPush end...");
		}
	}

	/**
	 * 推播
	 * */
	public void doNotifyPush(KgoCaseMain kgoCaseMain, Optional<KgoCaseset> kgoCaseset){
		LOGGER.info("call doNotifyPush...");
		try {
			// 寄出推播
			List<KgoCaseDetail> kgoCaseDetailsID = kgoCaseDetailRepository.findByIdCaseIdAndIdColumnId(kgoCaseMain.getCaseId(), "ID");
			Optional<KgoCaseDetail> maxID = kgoCaseDetailsID.stream().max(Comparator.comparing(item -> item.getId().getVersion()));
			if (maxID.isPresent()) {
				String columnValue = maxID.get().getColumnValue();
				if (!org.springframework.util.StringUtils.isEmpty(columnValue)) {
					List<PushMsgDto> pushDataList = new ArrayList<>();
					PushMsgDto pushMsg = new PushMsgDto();
					pushMsg.setCustNum(columnValue);
					pushMsg.setMsgTitle(messageUtil.getMessage("kcg.city.change.notification.msgTitle"));
					pushMsg.setMsgBody(String.format(messageUtil.getMessage("kcg.city.change.notification.msgBody"), kgoCaseset.get().getCaseSetName(),
							CaseMainStatusEnum.getCaseMainStatusEnum(kgoCaseMain.getStatus()).getLabel(), kgoCaseMain.getCaseId()));
					pushMsg.setClickDetail(String.format(messageUtil.getMessage("kcg.city.change.notification.clickDetail"), kgoCaseset.get().getCaseSetName(),
							CaseMainStatusEnum.getCaseMainStatusEnum(kgoCaseMain.getStatus()).getLabel(), kgoCaseMain.getCaseId()));
					pushDataList.add(pushMsg);
					this.pushService.pushAsyncMessage(pushDataList, kgoCaseset.get().getOwnerOrgan());
				} else {
					LOGGER.info("updateCaseStatusAction doNotify no notification to send empty String");
				}
			} else {
				LOGGER.info("updateCaseStatusAction doNotify no notification to send empty");
			}
		} catch (Exception e) {
			ErrorResult er = new ErrorResult(KgoBackEndApiError.FAIL_TO_NOTIFY);
			LOGGER.error(er.getErrorDesc());
		}
	} //doNotify()

	/**
	 * 設置更新案件狀態 成功/失敗訊息.
	 *
	 * @param <T>            the generic type
	 * @param rs             the rs
	 * @param successCaseIds the success case ids
	 * @param errorCaseIds   the error case ids
	 * @param error          the error
	 */
	private <T extends ApiBaseResponse<?>> void setUpdateStatusResultMessage(T rs, List<String> successCaseIds, List<String> errorMsgs, KgoApiException error) {
		// 成功 或有案件 處理失敗
		if (error == null) {
			String joinSuccessIds = StringUtils.join(successCaseIds, "、");
			String joinErrorMsgs = StringUtils.join(errorMsgs, "、");

			String[] joinResult = new String[] { joinSuccessIds, joinErrorMsgs };
			// 成功清單{0} ; 失敗清單{1}
			rs.setMsg(messageUtil.getMessage("kcg.city.receive.result.success.and.error.list", joinResult));
		} else {
			// 失敗
			rs.setError(error.getErrorResult());
		}
	}

	/**
	 * 寫入補正狀態.
	 *
	 * @param rq the rq
	 * @return the correct status action rs
	 */
	@Override
	public CorrectStatusActionRs correctStatusAction(CorrectStatusActionRq rq) {
		CorrectStatusActionRs rs = new CorrectStatusActionRs();
		KgoApiException error = null;
		try {
			// 案件編號錯誤
			if (StringUtils.isBlank(rq.getCaseId())) {
				throw new KgoApiException(new ErrorResult(CityApiError.CASE_ID_ERROR));
			}

			KgoCaseMain kgoCaseMain = kgoCaseMainRepository.findByCaseId(rq.getCaseId());
			// 查無案件
			if (kgoCaseMain == null) {
				throw new KgoApiException(new ErrorResult(CityApiError.CAN_NOT_FIND_CASE));
			} else {
				Optional<KgoCaseset> kgoCasesetOptional = kgoCasesetRepository.findById(kgoCaseMain.getCaseSetId());
				if (kgoCasesetOptional.isPresent()) {
					KgoCaseset kgoCaseset = kgoCasesetOptional.get();

					// 寫入 補正期限CorrectDeadline
					kgoCaseMain.setCorrectDeadline(rq.getCorrectDeadline());

					// modify 2021.01.08 調整:caseFlowType B1 B2 -> C； B3 -> C3, 更新Status = "C3" OR
					// "C"
					CaseFlowTypeEnum caseFlowTypeE = CaseFlowTypeEnum.getCaseFlowTypeEnum(kgoCaseset.getCaseFlowType());
					if (CaseFlowTypeEnum.B1.equals(caseFlowTypeE) || CaseFlowTypeEnum.B2.equals(caseFlowTypeE)) {
						kgoCaseMain.setStatus(CaseMainStatusEnum.C.getValue());
					}

					if (CaseFlowTypeEnum.B3.equals(caseFlowTypeE)) {
						kgoCaseMain.setStatus(CaseMainStatusEnum.C3.getValue());
					}

					kgoCaseMain.setUpdateTime(DateUtil.getCurrentTimestamp());
					kgoCaseMainRepository.save(kgoCaseMain);

					List<KgoCaseDetail> kgoCaseDetailListOri = kgoCaseDetailRepository.findByIdCaseId(rq.getCaseId());
					if (!CollectionUtils.isEmpty(kgoCaseDetailListOri)) {
						kgoCaseDetailListOri.forEach(item -> item.setIsCorrect(false));
						List<KgoCaseDetail> kgoCaseDetailList = kgoCaseDetailListOri.stream().filter(item -> StringUtils.isEmpty(item.getId().getCColumnId())).collect(Collectors.toList());

						// KGO_CASE_DETAIL:以ColumnId 寫入補正說明 CorrectMemo,IsCorrect=1"
						Map<String, CaseColumn> map = rq.getCorrectColumns().stream().collect(Collectors.toMap(c -> c.getColumnId(), c -> c));
						List<KgoCaseDetail> correctDetailList = new ArrayList<>();
						for (KgoCaseDetail detail : kgoCaseDetailList) {
							CaseColumn caseColumn = map.get(detail.getId().getColumnId());
							if (caseColumn != null) {
								detail.setCorrectMemo(caseColumn.getColumnName());
								detail.setIsCorrect(true);
								correctDetailList.add(detail);
							}
						}
						kgoCaseDetailRepository.saveAll(kgoCaseDetailListOri);

						List<KgoCasesetColumnPK> idList = correctDetailList.stream().map(item -> {
							KgoCasesetColumnPK kgoCasesetColumnPK = new KgoCasesetColumnPK();
							kgoCasesetColumnPK.setVersion(item.getId().getVersion());
							kgoCasesetColumnPK.setColumnId(item.getId().getColumnId());
							kgoCasesetColumnPK.setCaseSetId(kgoCaseset.getCaseSetId());
							return kgoCasesetColumnPK;
						}).collect(Collectors.toList());
						Map<KgoCasesetColumnPK, KgoCasesetColumn> columnMap = kgoCasesetColumnRepository.findAllByIdBatch(idList).stream().collect(Collectors.toMap(c -> c.getId(), c -> c));
						List<Map<String, Object>> correctList = new ArrayList<>();
						for (KgoCaseDetail kgoCaseDetail : correctDetailList) {
							KgoCasesetColumnPK kgoCasesetColumnPK = new KgoCasesetColumnPK();
							kgoCasesetColumnPK.setCaseSetId(kgoCaseset.getCaseSetId());
							kgoCasesetColumnPK.setColumnId(kgoCaseDetail.getId().getColumnId());
							kgoCasesetColumnPK.setVersion(kgoCaseDetail.getId().getVersion());
							KgoCasesetColumn kgoCasesetColumn = columnMap.get(kgoCasesetColumnPK);
							Map<String, Object> correctMap = new HashMap<>();
							correctMap.put("columnName", kgoCasesetColumn.getColumnName());
							correctMap.put("columnMemo", kgoCaseDetail.getCorrectMemo());
							correctList.add(correctMap);
						}
						Timestamp correctDate = this.getCorrectDate(rq.getCorrectDeadline());
						DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						String tsStr = sdf.format(correctDate);
						// 發送推播 && 寄信
						doNotifyCorrect(kgoCaseMain, tsStr, correctList);
					}
				} else {
					throw new KgoApiException(new ErrorResult(CityApiError.CASE_ID_ERROR));
				}
			}
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
		} finally {
			// 設置 成功/失敗訊息.
			setResultMessage(rq, rs, error);
		}
		return rs;
	}

	private Timestamp getCorrectDate(Integer correctDeadLine) {
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate today = LocalDate.now();
		ZonedDateTime zdt = today.atStartOfDay(zoneId);
		Date date = Date.from(zdt.toInstant());
		LocalDate firstDeadLine = today.plusDays(correctDeadLine);
		ZonedDateTime zdt2 = firstDeadLine.atStartOfDay(zoneId);
		Date date2 = Date.from(zdt2.toInstant());
		List<KgoHoliday> holidayList = kgoHolidayRepository.findHolidayList(date, date2);
		if (!org.springframework.util.CollectionUtils.isEmpty(holidayList)) {
			firstDeadLine = firstDeadLine.plusDays(holidayList.size());
		}
		Timestamp correctDate = Timestamp.valueOf(firstDeadLine.atStartOfDay());
		return correctDate;
	}

	private void doNotifyCorrect(KgoCaseMain kgoCaseMain, String correctDeadlineDate, List<Map<String, Object>> correctList) {
		// 發送mail及推播
		String caseSetId = kgoCaseMain.getCaseSetId();
		Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(caseSetId);
		if (kgoCaseset.isPresent()) {
			try {
				// 寄出email
				List<KgoCaseDetail> kgoCaseDetails = kgoCaseDetailRepository.findByIdCaseIdAndIdColumnId(kgoCaseMain.getCaseId(), "Email");
				Optional<KgoCaseDetail> max = kgoCaseDetails.stream().max(Comparator.comparing(item -> item.getId().getVersion()));
				if (max.isPresent()) {
					String columnValue = max.get().getColumnValue();
					if (!org.springframework.util.StringUtils.isEmpty(columnValue)) {
						sendEmailApplyCorrect(kgoCaseset.get(), columnValue, correctDeadlineDate, correctList, kgoCaseMain.getCaseId());
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
				List<KgoCaseDetail> kgoCaseDetailsID = kgoCaseDetailRepository.findByIdCaseIdAndIdColumnId(kgoCaseMain.getCaseId(), "ID");
				Optional<KgoCaseDetail> maxID = kgoCaseDetailsID.stream().max(Comparator.comparing(item -> item.getId().getVersion()));
				if (maxID.isPresent()) {
					String columnValue = maxID.get().getColumnValue();
					if (!org.springframework.util.StringUtils.isEmpty(columnValue)) {
						pushNotificationApplyCorrect(kgoCaseset.get(), columnValue, correctDeadlineDate);
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
	}

	private void sendEmailApplyCorrect(KgoCaseset kgoCaseset, String email, String correctDeadlineDate, List<Map<String, Object>> correctList, String caseId) throws Exception {
		Map<String, Object> model = new HashMap<>();
		model.put("casesetName", kgoCaseset.getCaseSetName());
		model.put("correctDeadlineDate", correctDeadlineDate);
		model.put("correctList", correctList);
		model.put("caseUrl", String.format(frontendCaseSearchLink, caseId));
		mailUtil.sendTemplateMail(new String[] { email }, null, messageUtil.getMessage("kgo.backend.case.handle.correct.mail.title"), model, "notifyCorrectCase.html");
	}

	private void pushNotificationApplyCorrect(KgoCaseset kgoCaseset, String id, String correctDeadlineDate) {
		List<PushMsgDto> pushDataList = new ArrayList<>();
		PushMsgDto pushMsg = new PushMsgDto();
		pushMsg.setCustNum(id);
		pushMsg.setMsgTitle(messageUtil.getMessage("kgo.backend.case.handle.correct.notification.msgTitle"));
		pushMsg.setMsgBody(String.format(messageUtil.getMessage("kgo.backend.case.handle.correct.notification.msgBody"), kgoCaseset.getCaseSetName()));
		pushMsg.setClickDetail(String.format(messageUtil.getMessage("kgo.backend.case.handle.correct.notification.clickDetail"), kgoCaseset.getCaseSetName(), correctDeadlineDate));
		pushDataList.add(pushMsg);
		this.pushService.pushMessage(pushDataList, kgoCaseset.getOwnerOrgan());
	}

	/**
	 * 案件進度狀態查詢.
	 *
	 * @param rq the rq
	 * @return the case status action
	 */
	@Override
	public GetCaseStatusActionRs getCaseStatusAction(GetCaseStatusActionRq rq) {
		GetCaseStatusActionRs rs = new GetCaseStatusActionRs();
		GetCaseStatusActionViewForm viewForm = new GetCaseStatusActionViewForm();
		rs.setData(viewForm);
		KgoApiException error = null;
		try {
			// 案件編號錯誤
			if (StringUtils.isBlank(rq.getCaseId())) {
				throw new KgoApiException(new ErrorResult(CityApiError.CASE_ID_ERROR));
			}

			KgoCaseMain kgoCaseMain = kgoCaseMainRepository.findByCaseId(rq.getCaseId());
			// 查無案件
			if (kgoCaseMain == null) {
				throw new KgoApiException(new ErrorResult(CityApiError.CAN_NOT_FIND_CASE));
			} else {

				viewForm.setStauts(kgoCaseMain.getStatus());

				// 狀態說明(status=O時，狀態說明有值)
				if (StringUtils.equals(CaseMainStatusEnum.O.getValue(), kgoCaseMain.getStatus())) {
					viewForm.setStatusDesc(kgoCaseMain.getStatusDesc());
				}
			}
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
		} finally {
			// 設置 成功/失敗訊息.
			setResultMessage(rq, rs, error);
		}
		return rs;
	}

	/**
	 * 取得案件編號.
	 *
	 * @param rq the rq
	 * @return the case id action
	 */
	@Override
	public GetCaseIdActionRs getCaseIdAction(GetCaseIdActionRq rq) {
		GetCaseIdActionRs rs = new GetCaseIdActionRs();
		GetCaseIdActionViewForm viewForm = new GetCaseIdActionViewForm();
		rs.setData(viewForm);
		KgoApiException error = null;
		try {
			String caseSetId = rq.getCaseSetId();
			// 案件服務編號 檢核.
			validateCaseSetId(caseSetId);

			String newCaseId = KgoUtil.getNextCaseId(CaseTypeEnum.SA);
			KgoCaseMain main = new KgoCaseMain();
			main.setCaseId(newCaseId);
			main.setCaseSetId(caseSetId);
			// TODO 待確認 先insert 一筆 , 再用排程 刪除無認領案件
			main = kgoCaseMainRepository.save(main);
			viewForm.setCaseId(main.getCaseId());

		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
		} finally {
			// 設置 成功/失敗訊息.
			setResultMessage(rq, rs, error);
		}
		return rs;
	}

	/**
	 * 案件服務編號 檢核.
	 *
	 * @param caseSetId the case set id
	 */
	private KgoCaseset validateCaseSetId(String caseSetId) {
		KgoCaseset kgoCaseset = null;
		if (StringUtils.isBlank(caseSetId)) {
			// 案件服務編號錯誤
			throw new KgoApiException(new ErrorResult(CityApiError.CASE_SET_ID_ERROR));
		}
		Optional<KgoCaseset> kgoCasesetOptional = kgoCasesetRepository.findById(caseSetId);
		if (!kgoCasesetOptional.isPresent()) {
			// 案件服務編號錯誤
			throw new KgoApiException(new ErrorResult(CityApiError.CASE_SET_ID_ERROR));
		}
		kgoCaseset = kgoCasesetOptional.get();
		return kgoCaseset;
	}

	/**
	 * 提供操作前/後臺 軌跡紀錄json log.
	 *
	 * @param rq          the rq
	 * @param stystemType the stystem type
	 * @return the user log json action
	 */
	@Override
	public GetUserLogJsonActionRs getUserLogJsonAction(GetUserLogJsonActionRq rq, SystemTypeEnum systemTypeEnum) {
		GetUserLogJsonActionRs rs = new GetUserLogJsonActionRs();
		GetUserLogJsonActionViewForm viewForm = new GetUserLogJsonActionViewForm();
		rs.setData(viewForm);
		KgoApiException error = null;
		Timestamp startDate = null;
		Timestamp endDate = null;
		try {
			try {
				// 取得某天最小時間 00:00:00
				startDate = DateUtil.getStartOfDay(rq.getStartDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH, DateUtil.PATTEN_FULL_TIME_SLASH);
				// 取得某天最大時間 23:59:59
				endDate = DateUtil.getEndOfDay(rq.getEndDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH, DateUtil.PATTEN_FULL_TIME_SLASH);

			} catch (Exception e) {
				// 日期格式錯誤
				throw new KgoApiException(new ErrorResult(CityApiError.TIME_FORMAT_ERROR));
			}

			List<KgoUseLog> logList = new ArrayList<>();
			// 後台 查詢 過濾機關
			if (SystemTypeEnum.B.equals(systemTypeEnum)) {
				String organId = rq.getOrganId();
				if (StringUtils.isBlank(organId)) {
					// 請輸入查詢機關代碼
					throw new KgoApiException(new ErrorResult(CityApiError.REQUIRED_ORGANID));
				}

				List<KgoUser> kgoUserList = kgoUserRepository.findByOrgan(organId);
				Map<String, List<KgoUser>> kgoUserMap = kgoUserList.stream().collect(Collectors.groupingBy(KgoUser::getOrgan));
				List<KgoUser> organUserList = kgoUserMap.get(organId);
				if (CollectionUtils.isNotEmpty(organUserList)) {
					List<String> organUserIdList = organUserList.stream().map(u -> u.getUserId()).collect(Collectors.toList());
					logList = kgoUserLogRepository.findLogbySystemTypeAndCreateTimeAndCreateUsers(systemTypeEnum.getSystemType(), startDate, endDate, organUserIdList);
				}
			}

			// 前台
			if (SystemTypeEnum.F.equals(systemTypeEnum)) {
				// 依系統別、新增時間查詢
				logList = kgoUserLogRepository.findLogbySystemTypeAndCreateTime(systemTypeEnum.getSystemType(), startDate, endDate);
			}

			// 查無案件
			if (CollectionUtils.isEmpty(logList)) {
				throw new KgoApiException(new ErrorResult(CityApiError.CAN_NOT_FIND_CASE));
			}

			List<KgoLogExportExcelVo> voList = backEndKgoLogService.getLogDataList(logList, systemTypeEnum);

			viewForm.setLogList(voList);

		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
		} finally {
			// 設置 成功/失敗訊息.
			setResultMessage(rq, rs, error);
		}
		return rs;
	}

	/**
	 * 提供案件軌跡紀錄json log.
	 *
	 * @param rq the rq
	 * @return the case log json action
	 */
	@Override
	public GetCaseLogJsonActionRs getCaseLogJsonAction(GetCaseLogJsonActionRq rq) {
		GetCaseLogJsonActionRs rs = new GetCaseLogJsonActionRs();
		GetCaseLogJsonActionViewForm viewForm = new GetCaseLogJsonActionViewForm();
		rs.setData(viewForm);
		KgoApiException error = null;
		Timestamp startDate = null;
		Timestamp endDate = null;
		try {
			String organId = rq.getOrganId();
			try {
				// 取得某天最小時間 00:00:00
				startDate = DateUtil.getStartOfDay(rq.getStartDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH, DateUtil.PATTEN_FULL_TIME_SLASH);
				// 取得某天最大時間 23:59:59
				endDate = DateUtil.getEndOfDay(rq.getEndDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH, DateUtil.PATTEN_FULL_TIME_SLASH);

			} catch (Exception e) {
				// 日期格式錯誤
				throw new KgoApiException(new ErrorResult(CityApiError.TIME_FORMAT_ERROR));
			}

			if (StringUtils.isBlank(organId)) {
				// 請輸入查詢機關代碼
				throw new KgoApiException(new ErrorResult(CityApiError.REQUIRED_ORGANID));
			}

			KgoCaseMainQueryCondition condition = new KgoCaseMainQueryCondition();
			condition.setStartDate(startDate);
			condition.setEndDate(endDate);
			condition.setOrganId(organId);
			List<KgoCaseMain> caseList = kgoCaseMainRepository.findByCondition(condition);
			// 查無案件
			if (CollectionUtils.isEmpty(caseList)) {
				throw new KgoApiException(new ErrorResult(CityApiError.CAN_NOT_FIND_CASE));
			}
			List<KgoCaseLogExportExcelVo> voList = backEndKgoCaseLogService.getLogDataList(caseList);

			viewForm.setLogList(voList);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
		} finally {
			// 設置 成功/失敗訊息.
			setResultMessage(rq, rs, error);
		}
		return rs;
	}

	/**
	 * 設置 成功/失敗訊息.
	 *
	 * @param <T>   the generic type
	 * @param rs    the rs
	 * @param error the error
	 */
	private <D extends ApiRequest, T extends ApiBaseResponse<?>> void setResultMessage(D rq, T rs, KgoApiException error) {
		try {
			LOGGER.info(">>>>> rq :" + JsonUtil.toJSONString(rq));
		} catch (Exception e) {
			LOGGER.error(">>>>> rq pasing toJSONString error ");
		}

		if (error == null) {
			// 成功
			rs.setMsg(messageUtil.getMessage("kcg.city.receive.result.success"));
		} else {
			// 失敗
			rs.setError(error.getErrorResult());
		}
	}

	/**
	 * 驗證: 狀態說明(status=O 時，此欄為必填).
	 *
	 * @param statusEnum the status enum
	 * @param statusDesc the status desc
	 */
	private void validateStatusO(CaseMainStatusEnum statusEnum, String statusDesc) {
		LOGGER.info("call validateStatusO...");
		if (statusEnum == null) {
			// 案件狀態不可為空
			throw new KgoApiException(new ErrorResult(CityApiError.REQUIRED_STATUS));
		}

		// 狀態說明(status=O 時，此欄為必填)
		if (CaseMainStatusEnum.O.equals(statusEnum)) {
			if (StringUtils.isBlank(statusDesc)) {
				// Status= O(其他)時，狀態說明「狀態說明」欄位為必填 .
				throw new KgoApiException(new ErrorResult(CityApiError.REQUIRED_STATUS_DESC));
			}
		}
	}

	/**
	 * 驗證: 是否符合 A,B1,B2 流
	 *
	 * @param caseSetId
	 */
	private void validateCaseFlowType(String caseSetId) {
		LOGGER.info("call validateCaseFlowType...");
		Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(caseSetId);
		if (kgoCaseset.isPresent()) {
			CaseFlowTypeEnum caseFlowTypeEnum = CaseFlowTypeEnum.getCaseFlowTypeEnum(kgoCaseset.get().getCaseFlowType());
			switch (caseFlowTypeEnum) {
			case A:
			case B1:
			case B2:
				return;
			default:
				throw new KgoApiException(KgoFrontEndApiError.RQ_NOT_COMPLETED.getErrorMsgKey());
			}
		} else {
			throw new KgoApiException(KgoFrontEndApiError.RQ_NOT_COMPLETED.getErrorMsgKey());
		}
	}
}
