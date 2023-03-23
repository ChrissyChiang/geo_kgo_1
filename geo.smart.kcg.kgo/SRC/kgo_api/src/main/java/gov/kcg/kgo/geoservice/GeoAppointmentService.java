package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.enums.backend.*;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoAppointmentMain;
import gov.kcg.kgo.geoentity.GeoKgoCaseRentPayment;
import gov.kcg.kgo.geoenum.*;
import gov.kcg.kgo.geomodel.*;
import gov.kcg.kgo.georepository.GeoKgoAppointmentMainRepository;
import gov.kcg.kgo.georepository.GeoKgoRentPaymentRepos;
import gov.kcg.kgo.georepository.custom.GeoCaseSetRentReposCustom;
import gov.kcg.kgo.georepository.custom.GeoKgoAppointmentReposCustom;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAppointmentFormQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.AppointmentBidInstructionHomeRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoAppointmentMainSearchRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoAppointmentOrderQueryRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoAppointmentBidInstructionHomeViewForm;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoAppointmentMainSearchViewForm;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoAppointmentOrderQueryViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoAppointmentInfoQueryByDayRq;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoAppointmentNumbersSearchRq;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoAppointmentOrderQueryByPersonRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoAppointmentInfoQueryByDayRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoAppointmentNumbersSearchRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoAppointmentInfoQueryByDayViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoAppointmentNumbersSearchViewForm;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.util.SsoUtil;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static gov.kcg.kgo.util.DateUtil.PATTEN_FULL_TIME_SLASH;

/**
 * GEO 20211015 add
 * 前台-線上預約臨櫃 API Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GeoAppointmentService extends GeoBaseService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoAppointmentService.class);

    @Autowired
    private GeoKgoAppointmentReposCustom geoKgoAppointmentReposCustom;

    @Autowired
    private GeoKgoAppointmentMainRepository geoKgoAppointmentMainRepository;

    @Autowired
    private GeoAppointmentSettingService geoAppointmentSettingService;

    @Autowired
    private GeoCaseSetRentReposCustom geoCaseSetRentReposCustom;

    @Autowired
    private GeoKgoRentPaymentRepos geoKgoRentPaymentRepos;
    /**
     * 前台-線上預約臨櫃:搜尋線上預約臨櫃服務清單
     *
     * @return
     */
    public GeoAppointmentMainSearchRs getAppointmentMainListByStatus() {
        GeoAppointmentMainSearchRs rs = new GeoAppointmentMainSearchRs();
        GeoAppointmentMainSearchViewForm viewForm = new GeoAppointmentMainSearchViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            List<GeoKgoAppointmentMainQueryModel> modelList = geoKgoAppointmentReposCustom.getAppointMainListByKeyword(GeoAppointmentMainStatusType.ON.getLabel(), null, null, null);
            viewForm.setDataList(modelList);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(null, rs, error);
        } //try
        return rs;
    } //appointMainContactUserQuery

    /**
     * 前台-線上預約臨櫃:搜尋線上預約臨櫃服務清單
     *
     * @return
     */
    public GeoAppointmentNumbersSearchRs searchAppointmentNumbers(GeoAppointmentNumbersSearchRq rq) {
        GeoAppointmentNumbersSearchRs rs = new GeoAppointmentNumbersSearchRs();
        GeoAppointmentNumbersSearchViewForm viewForm = new GeoAppointmentNumbersSearchViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            Date firstMonthDay = null;
            Date lastMonthDay = null;
            Calendar cal = Calendar.getInstance();
            LOGGER.info("searchAppointmentNumbers rq.getDateStr()="+rq.getDateStr());
            if (StringUtils.isNotBlank(rq.getDateStr())) {
                firstMonthDay = DateUtil.strToDate(rq.getDateStr(),DateUtil.PATTEN_YEAR_MONTH_DAY);
                cal.setTime(DateUtil.strToDate(rq.getDateStr(), DateUtil.PATTEN_YEAR_MONTH_DAY));
            }else {
                //用現在時間查詢
                Date currentDate = new Date();
                SimpleDateFormat dateFormat= new SimpleDateFormat(DateUtil.PATTEN_YEAR_MONTH_DAY);
                dateFormat.format(currentDate);
                firstMonthDay = currentDate;
                cal.setTime(currentDate);
            }
            lastMonthDay = DateUtil.getLastMonthDay(cal);
            String lastDay =DateUtil.dateToString(lastMonthDay,PATTEN_FULL_TIME_SLASH); //獲取最後一天
            lastMonthDay = DateUtil.getEndOfDay(lastDay, PATTEN_FULL_TIME_SLASH, PATTEN_FULL_TIME_SLASH);//得到最後一天的最大時間 2022-03-31 23:59:59.0
            List<GeoKgoAppointmentNumbersSearchModel> modelList = geoKgoAppointmentReposCustom.searchAppointmentNumbers(rq.getAppointmentMainId(),firstMonthDay ,lastMonthDay);
            viewForm.setDataList(modelList);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(null, rs, error);
        } //try
        return rs;
    } //searchAppointmentNumbers


    /**
     * 前台-線上預約臨櫃:取得單日預約資料
     */
    public GeoAppointmentInfoQueryByDayRs queryAppointmentInfoByDay(GeoAppointmentInfoQueryByDayRq rq) {
        GeoAppointmentInfoQueryByDayRs rs = new GeoAppointmentInfoQueryByDayRs();
        GeoAppointmentInfoQueryByDayViewForm viewForm = new GeoAppointmentInfoQueryByDayViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            List<GeoKgoAppointmentInfoQueryByDayModel> modelList = geoKgoAppointmentReposCustom.queryAppointmentInfoByDay(rq.getAppointmentDetailId());

            //20221216 GEO 前台線上預約臨櫃 判斷無法預約案件鎖定顯示灰色
            modelList.stream().forEach(model->{
                try {
                    Timestamp now = DateUtil.getCurrentTimestamp();
                    Timestamp earliestTime = DateUtil.strToTimestamp(StringUtils.defaultIfBlank(model.getEarliestTime(),model.getAppointmentDetailDate()+" 00:00"), "yyyy-MM-dd HH:mm");
                    Timestamp lastestTime = DateUtil.strToTimestamp(StringUtils.defaultIfBlank(model.getLatestTime(),model.getAppointmentDetailDate()+" 00:00"), "yyyy-MM-dd HH:mm");
                    if(now.compareTo(earliestTime) < 0 || now.compareTo(lastestTime) > 0 ){
                        model.setLock(true);
                    }else{
                        model.setLock(false);
                    }
                }catch (Exception e){
                    LOGGER.info("queryAppointmentInfoByDay find lock Case Error detailTimeId : " + model.getAppointmentDetailTimeId());
                    model.setLock(true);
                }
            });

            viewForm.setDataList(modelList);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(null, rs, error);
        } //try
        return rs;
    } //queryAppointmentInfoByDay

    /**
     * 前台-線上預約臨櫃:搜尋該民眾已登錄預約單
     *
     * @param rq
     * @return
     */
    public GeoAppointmentOrderQueryRs getAppointmentList(GeoAppointmentOrderQueryByPersonRq rq) {
        GeoAppointmentOrderQueryRs rs = new GeoAppointmentOrderQueryRs();
        GeoAppointmentOrderQueryViewForm viewForm = new GeoAppointmentOrderQueryViewForm();
        List<GeoKgoAppointmentOrderQueryModel> modelList = new ArrayList<>();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (StringUtils.isBlank(rq.getAppointmentEmail())
                    || StringUtils.isBlank(rq.getAppointmentIdentity())
                    || StringUtils.isBlank(rq.getAppointmentName())
                    || StringUtils.isBlank(rq.getAppointmentPhone())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            List<String> category = rq.getCasesetCategory();
            if(category.contains(CaseSetCategoryEnum.ORGAN.getValue())) { //臨櫃預約
                List<GeoKgoAppointmentOrderQueryModel> countList = geoKgoAppointmentReposCustom.queryAppointmentListByPerson(
                        rq.getAppointmentName(), rq.getAppointmentIdentity(), rq.getAppointmentEmail(),
                        rq.getAppointmentPhone(), new Timestamp(System.currentTimeMillis()), rq.getCasesetCategory());
                countList.stream().forEach(model->{
                        model.setCaseSetCategory("臨櫃預約");
                        List<Map<String,String>> funcList = new ArrayList<>();
                        funcList.add(new HashMap<String,String>(){{
                            put("name",RentalFuncEnum.CANCEL.getLabel());
                            put("type","cancel");
                            put("url","/appointment/appointmentDelete");
                        }});
                        model.setFuncList(funcList);
                    }
                );
                modelList.addAll(countList);
            }
            if(category.contains(CaseSetCategoryEnum.SITE.getValue()) || category.contains(CaseSetCategoryEnum.ACTIVITY.getValue())) {
                List<GeoKgoAppointmentOrderQueryModel> rentCaseList = geoCaseSetRentReposCustom.queryAppointRentalList(
                        rq.getAppointmentName(), rq.getAppointmentIdentity(), rq.getAppointmentEmail(),
                        rq.getAppointmentPhone(), rq.getCasesetCategory()
                );

                for(GeoKgoAppointmentOrderQueryModel data :rentCaseList){
                    //案件狀態
                    CaseMainStatusEnum caseStatus = CaseMainStatusEnum.getCaseMainStatusEnum(data.getCaseStatus());
                    data.setCaseStatus(caseStatus.getLabel());
                    //預約狀態
                    RentStatusEnum rentStatus = RentStatusEnum.getRentStatusEnum(data.getRentalStatus()) == null?
                                                RentStatusEnum.PROC  :   RentStatusEnum.getRentStatusEnum(data.getRentalStatus());
                    //繳費狀態
                    RentStatusEnum paymentStatus = data.getNeedPay() == false ? RentStatusEnum.FREE :
                                                   RentStatusEnum.getRentStatusEnum(data.getPaymentStatus()) == null ?
                                                   RentStatusEnum.YET   :   RentStatusEnum.getRentStatusEnum(data.getPaymentStatus()) ;
                    //動態生成按鈕
                    List<Map<String,String>> funcList = new ArrayList<>();
                    createBtn(funcList,RentalFuncEnum.VIEW);
                    funcList = funcSetting(funcList,caseStatus,paymentStatus,rentStatus,data);
                    data.setFuncList(funcList);
                    data.setRentalStatus(rentStatus.getLabel());
                    data.setPaymentStatus(paymentStatus.getLabel());
                    GeoKgoCaseRentPayment payEntity = geoKgoRentPaymentRepos.findByCaseId(data.getCaseId());
                    if(payEntity != null){
                        data.setPayDeadline(DateUtil.timestampToString(payEntity.getPayDeadline(),DateUtil.PATTEN_FULL_TIME_TO_MINUTE));
                    }
                }
                modelList.addAll(rentCaseList);
            }
            viewForm.setDataList(modelList);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //getAppointmentList

    public GeoAppointmentOrderQueryRs getAppointByQRCode(String caseId){
        LOGGER.info("GeoAppointmentService getAppointByQRCode start ...");
        GeoAppointmentOrderQueryRs rs = new GeoAppointmentOrderQueryRs();
        GeoAppointmentOrderQueryViewForm viewForm = new GeoAppointmentOrderQueryViewForm();
        List<GeoKgoAppointmentOrderQueryModel> modelList = new ArrayList<>();
        rs.setData(viewForm);
        KgoApiException error = null;
        try{
            GeoKgoAppointmentOrderQueryModel data = geoCaseSetRentReposCustom.queryAppointByCaseId(caseId);
            if(data == null){
                rs.setMsg("查無此案件資料");
                return rs;
            }
            CaseMainStatusEnum caseStatus = CaseMainStatusEnum.getCaseMainStatusEnum(data.getCaseStatus());
            data.setCaseStatus(caseStatus.getLabel());
            //預約狀態
            RentStatusEnum rentStatus = RentStatusEnum.getRentStatusEnum(data.getRentalStatus()) == null ?
                    RentStatusEnum.PROC  :   RentStatusEnum.getRentStatusEnum(data.getRentalStatus());
            //繳費狀態
            RentStatusEnum paymentStatus = data.getNeedPay() == false ? RentStatusEnum.FREE :
                                           RentStatusEnum.getRentStatusEnum(data.getPaymentStatus()) == null ?
                                           RentStatusEnum.YET   :   RentStatusEnum.getRentStatusEnum(data.getPaymentStatus()) ;
            Timestamp timeStart = DateUtil.strToTimestamp(data.getDateStart(),DateUtil.PATTEN_FULL_TIME);

            //動態生成按鈕
            List<Map<String,String>> funcList = new ArrayList<>();
            data.setFuncList(funcSetting(funcList,caseStatus,paymentStatus,rentStatus,data));
            data.setRentalStatus(rentStatus.getLabel());
            data.setPaymentStatus(paymentStatus.getLabel());
            GeoKgoCaseRentPayment payEntity = geoKgoRentPaymentRepos.findByCaseId(data.getCaseId());
            if(payEntity != null) {
                data.setPayDeadline(DateUtil.timestampToString(payEntity.getPayDeadline(),DateUtil.PATTEN_FULL_TIME_TO_MINUTE));
            }

            modelList.add(data);
            viewForm.setDataList(modelList);
            rs.setMsg(SuccessMsg.UNKNOW.getMsg());
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.CAN_NOT_FIND_CASE), e);
        } finally {
            if (error != null) rs.setError(error.getErrorResult());
        }
        return rs;
    }
    /**
     * 20220811 GEO add
     * 前台-線上預約臨櫃:取得同意說明頁
     *
     */
    public AppointmentBidInstructionHomeRs bidInstructionHome(GeoAppointmentFormQueryRq rq) {
        AppointmentBidInstructionHomeRs appointmentBidInstructionHomeRs = new AppointmentBidInstructionHomeRs();
        GeoAppointmentBidInstructionHomeViewForm viewForm = new GeoAppointmentBidInstructionHomeViewForm();
        try {
            String appointmentMainId = rq.getAppointmentMainId();
            GeoKgoAppointmentMain appointmentMain = geoKgoAppointmentMainRepository.findByAppointmentMainId(appointmentMainId);
            GeoAppointmentBidInstructionModel model = new GeoAppointmentBidInstructionModel();
            model.setAppointmentName(appointmentMain.getAppointmentName());
            model.setServiceHtmlContent(appointmentMain.getServiceHtmlContent());
            model.setServiceHtml(appointmentMain.getServiceHtml());
            List<CheckBox> identityVerifyCheckBox = geoAppointmentSettingService.getVerifyCheckBox(appointmentMainId);
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

            //未設定驗證的方式的判斷
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
                model.setIdentityVerifyCheckBox(identityVerifyCheckBox);
            } //if (isDefault)
            viewForm.setGeoAppointmentBidInstructionModel(model);
            appointmentBidInstructionHomeRs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoFrontEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("BidInstructionHome error " + e.getMessage(), e);
        }
        return appointmentBidInstructionHomeRs;
    } //bidInstructionHome

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


    private List<Map<String,String>> funcSetting(List<Map<String,String>> funcList, CaseMainStatusEnum caseStatus, RentStatusEnum paymentStatus , RentStatusEnum rentStatus , GeoKgoAppointmentOrderQueryModel data) throws Exception{
        if(RentStatusEnum.PROC.equals(rentStatus)){
            if(!CaseMainStatusEnum.J3.equals(caseStatus) && !CaseMainStatusEnum.S3.equals(caseStatus)){
                createBtn(funcList,RentalFuncEnum.CANCEL);

                if(CaseMainStatusEnum.F3.equals(caseStatus)){
                    if(RentStatusEnum.YET.equals(paymentStatus)){
                        createBtn(funcList,RentalFuncEnum.ONLINEPAY);
                    }
                }
            }
        }else if (RentStatusEnum.SUS.equals(rentStatus)) {
            Timestamp now = DateUtil.getCurrentTimestamp();
            Timestamp timeStart = DateUtil.strToTimestamp(data.getDateStart(),DateUtil.PATTEN_FULL_TIME);
            if(now.getTime() < timeStart.getTime()){
                createBtn(funcList,RentalFuncEnum.CANCEL);
            }
            if (RentStatusEnum.FIN.equals(paymentStatus) && data.getNeedPay() ){//已繳費
                if( !GeoPaymentTypeEnum.COMMON.getPayTypeCode().equals(data.getPayType()) //非一般繳費且超過退費期限
                        &&  now.compareTo(DateUtil.modifyDate(timeStart, 0, 0, -data.getRefundDeadline())) < 0 ) {
                    String refundUrl = String.format("/apply/info/%s?applyType=E", SpringUtil.getProperty("applyRefund.caseset"));
                    funcList.add(new HashMap<String, String>() {{
                        put("name", "線上退費");
                        put("type", "refund");
                        put("url", refundUrl);
                    }});
                }
            }
        }
        return funcList;
    }
    private void createBtn(List funcList, RentalFuncEnum funcEnum ){
        funcList.add(new HashMap<String,String>(){{
            put("name",funcEnum.getLabel());
            put("type",funcEnum.getValue());
            put("url",funcEnum.getUrl());
        }});
    }
}
