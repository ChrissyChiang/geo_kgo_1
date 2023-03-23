package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.dto.CasesetMemoAndCasesetQueryDto;
import gov.kcg.kgo.dto.KgoCasesetFormQueryDto;
import gov.kcg.kgo.enums.backend.*;
import gov.kcg.kgo.enums.common.CaseTypeEnum;
import gov.kcg.kgo.enums.common.SysLogExeType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.common.kcgCityApi.KcgCityApiServiceType;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoenum.GeoBooleanType;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.model.KgoCasesetCheck;
import gov.kcg.kgo.model.KgoGroupLevel;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.service.BidInstructionService;
import gov.kcg.kgo.service.CallKcgCityApiService;
import gov.kcg.kgo.service.CommonService;
import gov.kcg.kgo.service.impl.KgoFrontEndServiceImpl;
import gov.kcg.kgo.service.impl.helper.CommonServiceHelper;
import gov.kcg.kgo.service.impl.helper.InternetApplyServiceHelper;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.*;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.download.rq.BidInstructionDownloadRq;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.callcity.rq.CallCityGetCaseIdRq;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.callcity.rs.CallCityGetCaseIdRs;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.rq.BidInstructionGetLinkRq;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.rs.BidInstructionGetLinkRs;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.rs.bean.BidInstructionGetLinkViewForm;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rq.BidInstructionHomeRq;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rs.BidInstructionHomeRs;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rs.bean.*;
import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rq.ServiceAnnounceHomeRq;
import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rs.ServiceAnnounceHomeRs;
import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rs.bean.ServiceAnnounceHomeViewForm;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GEO 20211120 add 府內線上服務
 * 後台-府內線上服務 API Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GeoOrganCaseFormService extends KgoFrontEndServiceImpl implements BidInstructionService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoOrganCaseFormService.class);

    private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();

    @Autowired
    private KgoCasesetRepository kgoCasesetRepository;

    @Autowired
    private KgoCasesetFormRepository kgoCasesetFormRepository;

    @Autowired
    private KgoCasesetMemoRepository kgoCasesetMemoRepository;

    @Autowired
    private KgoCasesetCheckRepository kgoCasesetCheckRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CallKcgCityApiService callKcgCityApiService;

    @Autowired
    private KgoCaseMainRepository kgoCaseMainRepository;

    @Autowired
    private KgoGroupLevelRepository kgoGroupLevelRepository;

    @Autowired
    InternetApplyServiceHelper internetApplyServiceHelper;

    /**
     * 申辦說明頁-檢查案件是否上架
     * @param caseSetId
     * @return
     */
    public boolean checkStatusIsOn(String caseSetId) {
        try {
            boolean isStatusOn = true;
            KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(caseSetId);
            if (kgoCaseset == null) isStatusOn = false;
            if (kgoCaseset != null && kgoCaseset.getIsOpenForOrgan() == GeoBooleanType.DISABLED.getCode()) isStatusOn = false;
            if (this.commonServiceHelper.getCaseSetStatusOff(kgoCaseset.getCaseSetId(), kgoCaseset.getStatus())) isStatusOn = false;
            return isStatusOn;
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("GeoOrganCaseServiceBack checkStatusIsOn error " + e.getMessage(), e);
        }
    } //checkStatusIsOn

    /**
     * 申辦說明頁-初始畫面
     *
     * @return
     */
    public BidInstructionHomeRs bidInstructionHome(BidInstructionHomeRq rq) {
        BidInstructionHomeRs rs = new BidInstructionHomeRs();
        BidInstructionHomeViewForm viewForm = new BidInstructionHomeViewForm();
        try {
            String caseSetId = rq.getCaseSetId();
            boolean isApply = false;
            String dateStart = StringUtils.EMPTY;
            String dateEnd = StringUtils.EMPTY;
            String caseSetName = StringUtils.EMPTY;
            String caseFlowType = StringUtils.EMPTY;
            boolean isLink = false;
            boolean isOpen = false;

            BidInstructionApplyTypeCData cData = new BidInstructionApplyTypeCData(ApplyTypeEnum.C.getValue());
            BidInstructionApplyTypeEData eData = new BidInstructionApplyTypeEData(ApplyTypeEnum.E.getValue());
            BidInstructionApplyTypeLData lData = new BidInstructionApplyTypeLData(ApplyTypeEnum.L.getValue());

            // Get KgoCaseset info for 臨櫃申請(C)&線上申請(E)
            KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(caseSetId);
            if (PublishStateEnum.ACCEPT.getValue().equals(kgoCaseset.getStatus())) {
                isApply = true;
            } else if (PublishStateEnum.ON.getValue().equals(kgoCaseset.getStatus())) {
                dateStart = StringUtils.isBlank(kgoCaseset.getDateStart()) ? StringUtils.EMPTY : kgoCaseset.getDateStart();
                dateEnd = StringUtils.isBlank(kgoCaseset.getDateEnd()) ? StringUtils.EMPTY : kgoCaseset.getDateStart();
            } else {
                //非府內線上服務
                if (kgoCaseset.getIsOpenForOrgan()== GeoBooleanType.DISABLED.getCode()) {
                    rs.setError(new ErrorResult(KgoBackEndApiError.SERVICE_IS_NOT_PROVIDED_FOR_ORGAN));
                    return rs;
                }
                // 已下架:此服務已下架‧，請選擇其他服務
                if (this.commonServiceHelper.getCaseSetStatusOff(kgoCaseset.getCaseSetId(), kgoCaseset.getStatus())) {
                    rs.setError(new ErrorResult(KgoBackEndApiError.SERVICE_IS_NOT_PROVIDED));
                    return rs;
                }
            }
            caseSetName = kgoCaseset.getCaseSetName();
            caseFlowType = kgoCaseset.getCaseFlowType();
            isOpen = LinkTypeEnum.OPEN.getValue().equalsIgnoreCase(kgoCaseset.getLinkType());
            isLink = !StringUtils.isBlank(kgoCaseset.getLinkUrl());

            // Get KgoCasesetMemo info for 書表下載(L) modify 2020.12.24 調整sql語句 過濾無勾選的申辦類型
            List<CasesetMemoAndCasesetQueryDto> kgoCasesetMemoList = kgoCasesetMemoRepository.getCaseSetMemoAndCasesetData(caseSetId);
            kgoCasesetMemoList.forEach(l -> {
                String applyType = l.getApplyType();
                if (ApplyTypeEnum.C.getValue().equalsIgnoreCase(applyType)) {
                    List<BidInstructionApplyTypeCEDetailDataGrid> cGridList = ObjectUtils.isEmpty(cData.getGrid()) ? new LinkedList<BidInstructionApplyTypeCEDetailDataGrid>() : cData.getGrid();
                    BidInstructionApplyTypeCEDetailDataGrid dg = new BidInstructionApplyTypeCEDetailDataGrid();
                    dg.setSeq(l.getSeq());
                    dg.setContentHtml(l.getContentHtml());
                    dg.setTitle(l.getTitle());
                    dg.setFileName(l.getFileName());
                    dg.setCanDownload(!StringUtils.isBlank(l.getFileName()));
                    cGridList.add(dg);
                    cData.setGrid(cGridList);
                } else if (ApplyTypeEnum.E.getValue().equalsIgnoreCase(applyType)) {
                    List<BidInstructionApplyTypeCEDetailDataGrid> eGridList = ObjectUtils.isEmpty(eData.getGrid()) ? new LinkedList<BidInstructionApplyTypeCEDetailDataGrid>() : eData.getGrid();
                    BidInstructionApplyTypeCEDetailDataGrid dg = new BidInstructionApplyTypeCEDetailDataGrid();
                    dg.setSeq(l.getSeq());
                    dg.setContentHtml(l.getContentHtml());
                    dg.setTitle(l.getTitle());
                    dg.setFileName(l.getFileName());
                    dg.setCanDownload(!StringUtils.isBlank(l.getFileName()));
                    eGridList.add(dg);
                    eData.setGrid(eGridList);
                }
            });

            // KGO_CASESET_MEMO for 書表下載(L)
            Map<String, List<KgoCasesetFormQueryDto>> dataMap = kgoCasesetFormRepository.getFormDownloadDetailData(caseSetId, CodeTypeEnum.DOC_TYPE.getValue()).stream()
                    .collect(Collectors.groupingBy(KgoCasesetFormQueryDto::getTypeName, Collectors.toList()));

            List<BidInstructionApplyTypeLTypeDataGrid> gridList = dataMap.keySet().stream().map(s -> {
                BidInstructionApplyTypeLTypeDataGrid typeDataGrid = new BidInstructionApplyTypeLTypeDataGrid();

                List<BidInstructionApplyTypeLDetailDataGrid> detailDataGridList = dataMap.get(s).stream().map(l -> {
                    BidInstructionApplyTypeLDetailDataGrid detailDataGrid = new BidInstructionApplyTypeLDetailDataGrid();
                    detailDataGrid.setSeq(l.getSeq());
                    detailDataGrid.setTitle(l.getTitle());
                    detailDataGrid.setType(l.getType());
                    detailDataGrid.setTypeName(l.getTypeName());
                    detailDataGrid.setFileName(l.getFileName());

                    return detailDataGrid;
                }).collect(Collectors.toList());

                typeDataGrid.setTypeName(s);
                typeDataGrid.setGrid(detailDataGridList);

                return typeDataGrid;
            }).collect(Collectors.toList());
            lData.setGrid(gridList);

            viewForm.setCaseSetName(caseSetName);
            viewForm.setDateStart(dateStart);
            viewForm.setDateEnd(dateEnd);
            viewForm.setIsApply(isApply);
            viewForm.setCaseFlowType(caseFlowType);
            viewForm.setIsOpen(isOpen);
            viewForm.setIsLink(isLink);
            viewForm.seteData(eData);
            viewForm.setcData(cData);
            viewForm.setlData(lData);

            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("GeoOrganCaseServiceBack error " + e.getMessage(), e);
        }
        return rs;
    } //bidInstructionHome

    /**
     * 服務宣告-初始畫面
     *
     * @param rq
     * @return
     */
    public ServiceAnnounceHomeRs serviceAnnounceHome(ServiceAnnounceHomeRq rq) {
        ServiceAnnounceHomeRs rs = new ServiceAnnounceHomeRs();
        ServiceAnnounceHomeViewForm viewForm = new ServiceAnnounceHomeViewForm();
        try {
            KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(rq.getCaseSetId());
            String caseSetName = kgoCaseset.getCaseSetName();
            boolean isServiceHtml = kgoCaseset.getIsServiceHtml() != null && kgoCaseset.getIsServiceHtml();
            String serviceHtml = isServiceHtml ? kgoCaseset.getServiceHtml() : StringUtils.EMPTY;

            //獲取該服務身份驗證
            List<CheckBox> identityVerifyCheckBox = getVerifyCheckBox(rq.getCaseSetId());
            Boolean isLogin = false; //預設不可登入
            FrontendLoginUserInfo userInfo = getFrontendLoginUser();

            //有設定驗證方式時的判斷
            if (userInfo!=null){
                for (CheckBox c: identityVerifyCheckBox){
                    if (c.getValue().equals(userInfo.getLoginAuthTokenType().getType())){
                        if (c.getSelected()){
                            isLogin = true;
                            break;
                        } //if (c.getSelected())
                    } //if (c.getValue().equals
                } // for (CheckBox c: identityVerifyCheckBox)
            }else {
                for (CheckBox c: identityVerifyCheckBox){
                    if (c.getValue().equals("NAN")){
                        if (c.getSelected()){
                            isLogin = true;
                            break;
                        } //if (c.getSelected())
                    } //if (c.getValue().equals
                } // for (CheckBox c: identityVerifyCheckBox)
            } //if (userInfo!=null)

            // 未設定驗證的方式的判斷
            Boolean isDefault = true;
            for (CheckBox c: identityVerifyCheckBox){
                if ( c.getSelected()){
                    isDefault = false;
                } //if ( c.getSelected())
            } //for (CheckBox c: identityVerifyCheckBox)
            if (isDefault){
                viewForm.setLogin(true);
            }else {
                viewForm.setLogin(isLogin);
                viewForm.setIdentityVerifyCheckBox(identityVerifyCheckBox);
            } //if (isDefault)

            viewForm.setCaseSetName(caseSetName);
            viewForm.setServiceHtml(serviceHtml);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoFrontEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("bidServiceMenuHome error " + e.getMessage(), e);
        }
        return rs;
    } //serviceAnnounceHome

    //身份驗證
    public List<CheckBox> getVerifyCheckBox(String caseSetId) {
        KgoCasesetCheckRepository kgoCasesetCheckRepository = SpringUtil.getDao(KgoCasesetCheckRepository.class);
        List<KgoCasesetCheck> kgoCasesetChecks = kgoCasesetCheckRepository.findAllByIdCaseSetId(caseSetId);
        List<String> casesetChecks = kgoCasesetChecks.stream().map(check -> check == null ? "UNKNOWN_CHECK_TYPE" : check.getId().getCheckType()).collect(Collectors.toList());
        List<CheckBox> identityVerifyCheckBox = new LinkedList<CheckBox>();
        CheckBox checkBox = null;
        for (CheckTypeEnum checkType : CheckTypeEnum.values()) {
            if (casesetChecks.contains(checkType.getValue())) {
                checkBox = internetApplyServiceHelper.getCheckBox(checkType.getLabel(), checkType.getValue(), true);
            } else {
                checkBox = internetApplyServiceHelper.getCheckBox(checkType.getLabel(), checkType.getValue());
            } //if (casesetChecks.contains(checkType.getValue()))
            identityVerifyCheckBox.add(checkBox);
        } //for (CheckTypeEnum checkType : CheckTypeEnum.values())
        return identityVerifyCheckBox;
    } //getVerifyCheckBox

    //獲取前臺登入者資訊
    public static FrontendLoginUserInfo getFrontendLoginUser() {
        LOGGER.info("KgoUtil getFrontendLoginUser...");
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            FrontendLoginUserInfo userInfo = (FrontendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.FRONTEND_USER_INO_KEY);
            return userInfo;
        } catch (Exception e) {
            // 使用者未登入.
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.USER_NOT_LOGIN));
        }
    } //getFrontendLoginUser

    /**
     * 申辦說明頁-檔案下載
     * @param rq
     */
    public void bidInstructionDownload(BidInstructionDownloadRq rq) {
        try {
            String caseSetId = rq.getCaseSetId();
            String applyType = rq.getApplyType();
            Integer seq = rq.getSeq();
            String downloadBasePath = StringUtils.EMPTY;
            String fileName = StringUtils.EMPTY;

            if (ApplyTypeEnum.L.getValue().equalsIgnoreCase(applyType)) {
                // 書表下載
                downloadBasePath = commonService.getFormDownloadUploadBasePath(caseSetId);
                fileName = kgoCasesetFormRepository.getOne(seq).getFileName();
            } else {
                // 其他
                downloadBasePath = commonService.getAttachmentUploadBasePath(caseSetId, applyType);
                fileName = kgoCasesetMemoRepository.getOne(seq).getFileName();
            }
            File file = new File(downloadBasePath, fileName);
            if (!file.exists()) {
                throw new Exception("Can find file on path = " + downloadBasePath + fileName);
            }
            commonService.downloadFileAction(file);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_DOWNLOAD.getErrorMsgKey());
            throw new KgoApiException("bidInstructionDownload error " + e.getMessage(), e);
        }
    } //bidInstructionDownload


    /**
     * 申辦說明頁-取得站外連結
     *
     * @param rq
     * @return
     */
    public BidInstructionGetLinkRs bidInstructionGetLink(BidInstructionGetLinkRq rq) {
        BidInstructionGetLinkRs rs = new BidInstructionGetLinkRs();
        BidInstructionGetLinkViewForm viewForm = new BidInstructionGetLinkViewForm();
        KgoApiException error = null;
        OperationApiMemo memo = null;
        String logCaseId = null;
        String caseSetName = null;
        try {
            // 前台、新增、申辦案件
            memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_A, BackendFunctionCodeEnum.CaseApply);
            String caseSetId = rq.getCaseSetId();
            String caseId = StringUtils.EMPTY;

            KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(caseSetId);

            String linkUrl = kgoCaseset.getLinkUrl();
            String linkType = kgoCaseset.getLinkType();
            caseSetName = kgoCaseset.getCaseSetName();
            if (kgoCaseset == null) throw new KgoApiException("查無此案件");
            if (kgoCaseset.getIsOpenForOrgan()== GeoBooleanType.DISABLED.getCode()) throw new KgoApiException(new ErrorResult(KgoBackEndApiError.SERVICE_IS_NOT_PROVIDED_FOR_ORGAN));
            if (!PublishStateEnum.ON.getValue().equals(kgoCaseset.getStatus())) throw new KgoApiException("此案件已下架");

            /** 2020.12.07 調整: A流程 呼叫城市資料平台 取得案件編號 (打回自己API) */
            /** GEO 20210815 note : 先呼叫城市資料平台取得案件編號, 如失敗就改由依自己的資料庫取號 **/
            try {
                CallCityGetCaseIdRq callCityRq = new CallCityGetCaseIdRq();
                callCityRq.setCaseSetId(caseSetId);
                // 便民一路通─取得案件編號 (A流程)
                CallCityGetCaseIdRs callCityRs = callKcgCityApiService.getKcgCityApiJsonStrNoMoicaLogin(KcgCityApiServiceType.KGO_GET_CASEID, CallCityGetCaseIdRs.class, callCityRq);
                caseId = callCityRs.getData().getData().getCaseId();
                LOGGER.info("CaseId = {}", caseId);
            } catch (Exception apiE) {
                LOGGER.error(">>>>> 呼叫城市資料平台 取得案件編號失敗, 改由自己取號");
                caseId = KgoUtil.getNextCaseId(CaseTypeEnum.SA);
            }

            if (CaseFlowTypeEnum.A.getValue().equalsIgnoreCase(kgoCaseset.getCaseFlowType())) {
                /** 先行建立新的 KGO_CASE_MAIN 資料 */
                KgoCaseMain kgoCaseMain = new KgoCaseMain();
                kgoCaseMain.setCaseSetId(caseSetId);
                kgoCaseMain.setCaseId(caseId);
                kgoCaseMain.setStatus(CaseMainStatusEnum.A.getValue());
                kgoCaseMain.setIp(KgoUtil.getClientIp());
                kgoCaseMain.setCreateTime(DateUtil.getCurrentTimestamp());
                kgoCaseMain.setRedirectTime(DateUtil.getCurrentTimestamp());
                kgoCaseMain.setApplyDate(DateUtil.getCurrentTimestamp());
                kgoCaseMain.setUpdateTime(DateUtil.getCurrentTimestamp());
                logCaseId = caseId;
                try {
                    BackendLoginUserInfo loginUserInfo = KgoUtil.getBackendLoginUser();
                    if (loginUserInfo != null) {
                        /** GEO 20211102 add 申請人登入方式 */
                        kgoCaseMain.setApplyUserLoginType(loginUserInfo.getLoginAuthTokenType().getType());
                        if (StringUtils.isNotBlank(loginUserInfo.getName())) {
                            kgoCaseMain.setApplyUserName(loginUserInfo.getName());
                        }

                        if (StringUtils.isNotBlank(loginUserInfo.getUserId())) {
                            String userAccount = loginUserInfo.getUserId();
                            kgoCaseMain.setAccount(userAccount);
                        }

                        if (StringUtils.isNotBlank(loginUserInfo.getUserId())) {
                            kgoCaseMain.setApplyUser(loginUserInfo.getUserId());
                        }
                    }

                } catch (KgoApiException e) {
                    // TODO: handle exception
                    LOGGER.error("User not logged in..." + e.getErrorResult().getErrorDesc());
                } catch (Exception ex) {
                    LOGGER.error("User not logged in...", ex);
                }

                kgoCaseMainRepository.save(kgoCaseMain);

                linkUrl = UriComponentsBuilder.fromUriString(linkUrl).queryParam("CaseId", Base64Utils.encodeToString(caseId.getBytes())).build().toString();
            }
            viewForm.setLinkUrl(linkUrl);
            viewForm.setLinkType(linkType);
            rs.setData(viewForm);

            try {
                BackendLoginUserInfo feUserInfo = KgoUtil.getBackendLoginUser();
                if (feUserInfo != null) {
                    /** 使用者資訊 寫檔至file */
                    String jsonStr = JsonUtil.toJSONString(feUserInfo);
                    // TODO 路徑改成設定檔
                    File file = new File(KgoUtil.getCaseDownloadUploadBasePath(caseId) + "userInfo/" + caseId + ".json");
                    FileUtils.writeStringToFile(file, jsonStr, "UTF-8");

                    callCityApiForStartCase(kgoCaseset, feUserInfo, caseId);
                }
            } catch (KgoApiException apiE) {
                LOGGER.info(">>>>> error: 使用者未登入, 使用者資訊 寫檔至file ");
            }
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            error = new KgoApiException("bidInstructionGetLink error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn(String.format("%s申請，案件編號：", caseSetName), logCaseId));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return rs;
    } //bidInstructionGetLink

    /**
     * A流，轉址前 Call 城市資料平台 Api
     *
     * @param kgoCaseset
     * @param feUserInfo
     * @param caseId
     */
    private void callCityApiForStartCase(KgoCaseset kgoCaseset, BackendLoginUserInfo feUserInfo, String caseId) {

        Optional<KgoGroupLevel> kgoGroupLevel = kgoGroupLevelRepository.findById(NumberUtils.toInt(kgoCaseset.getOrgan()));

        if (kgoGroupLevel.isPresent() && ObjectUtils.isNotEmpty(feUserInfo)) {
            // 民政局
            Map<String, Object> userInfoMap = null;
            if (kgoGroupLevel.get().getOrganId().equalsIgnoreCase("397020000A")) {
               switch (feUserInfo.getLoginAuthTokenType()) {
                   case BASIC:
                       userInfoMap = JsonUtil.getObject( JsonUtil.toJSONString(feUserInfo.getKcgUserBasicInfo()), Map.class);
                       break;
                   case EGOV:
                       userInfoMap = JsonUtil.getObject( JsonUtil.toJSONString(feUserInfo.getKcgEgovInfo()), Map.class);
                       break;
                   case HCA:
                       userInfoMap = JsonUtil.getObject( JsonUtil.toJSONString(feUserInfo.getKcgHcaCardSsoInfo()), Map.class);
                       break;
                   case MOICA:
                       userInfoMap = JsonUtil.getObject( JsonUtil.toJSONString(feUserInfo.getKcgMoicaCardInfo()), Map.class);
                       break;
                   case GOOGLE:
                       userInfoMap = JsonUtil.getObject( JsonUtil.toJSONString(feUserInfo.getKcgGoogleSsoInfo()), Map.class);
                       break;
                   case LINE:
                       userInfoMap = JsonUtil.getObject( JsonUtil.toJSONString(feUserInfo.getLineInfo()), Map.class);
                       break;
                   case TW_FIDO:
                       userInfoMap = JsonUtil.getObject( JsonUtil.toJSONString(feUserInfo.getKcgTwFidoSsoInfo()), Map.class);
                       break;
                   case FACEBOOK:
                       userInfoMap = JsonUtil.getObject( JsonUtil.toJSONString(feUserInfo.getKcgTwFidoSsoInfo()), Map.class);
               }

                if (userInfoMap.containsKey("PUBLISH_INFO_CONTENT")) {
                    Map<String, Object> map = new HashedMap<>();
                    map.put("CASE_ID", caseId);
                    map.put("PUBLISH_INFO_CONTENT", userInfoMap.get("PUBLISH_INFO_CONTENT"));
                    // TODO Call 民政局起案API
                    // callKcgCityApiService.getKcgCityApiJsonStrNoMoicaLogin(KcgCityApiServiceType.CITIZEN_MEMBER_INFO,
                    // Map.class, map);
                }
            }
        }
    }

}
