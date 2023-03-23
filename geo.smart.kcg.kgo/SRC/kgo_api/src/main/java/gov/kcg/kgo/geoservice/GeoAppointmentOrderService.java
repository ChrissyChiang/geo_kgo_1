package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.dto.KgoKcdDto;
import gov.kcg.kgo.dto.KgoZipF3AreaDto;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.backend.CheckTypeEnum;
import gov.kcg.kgo.enums.backend.IsMustKeyEnum;
import gov.kcg.kgo.enums.common.*;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.common.sso.LoginAuthType;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.*;
import gov.kcg.kgo.geoenum.*;
import gov.kcg.kgo.geomodel.*;
import gov.kcg.kgo.geomodel.dto.GeoAppointmentGroupQueryDataMaxVersionDto;
import gov.kcg.kgo.georepository.*;
import gov.kcg.kgo.georepository.custom.GeoKgoAppointmentReposCustom;
import gov.kcg.kgo.geoutil.GeoStringUtil;
import gov.kcg.kgo.geoviewmodel.backend.GeoAppointmentInfoFormQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.*;
import gov.kcg.kgo.geoviewmodel.backend.rq.data.GeoAppointmentOrderEditColumnData;
import gov.kcg.kgo.geoviewmodel.backend.rs.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.*;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.KgoCountyRepository;
import gov.kcg.kgo.repository.KgoKcdRepository;
import gov.kcg.kgo.repository.KgoZipRepository;
import gov.kcg.kgo.service.ExcelTempExportService;
import gov.kcg.kgo.service.bean.excel.GeoAppointmentExcelVo;
import gov.kcg.kgo.service.impl.helper.CommonServiceHelper;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.*;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetComplexColumnData;
import gov.kcg.kgo.viewModel.compoent.SelectListItem;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveActionCColumnViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveFileViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.OptionViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.ValidationViewForm;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃-預約登錄管理 API Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GeoAppointmentOrderService extends GeoBaseService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoAppointmentOrderService.class);

    @Autowired
    GeoKgoAppointmentMainRepository mainRepository;
    @Autowired
    GeoKgoAppointmentContactUserRepository geoKgoAppointmentContactUserRepository;
    @Autowired
    GeoKgoAppointmentBlockUserRepository geoKgoAppointmentBlockUserRepository;
    @Autowired
    GeoKgoAppointmentDetailRepository geoKgoAppointmentDetailRepository;
    @Autowired
    GeoKgoAppointmentDetailTimeRepository geoKgoAppointmentDetailTimeRepository;
    @Autowired
    GeoKgoAppointmentDetailNumbersRepository geoKgoAppointmentDetailNumbersRepository;
    @Autowired
    GeoKgoAppointmentRepository geoKgoAppointmentRepository;
    @Autowired
    GeoKgoAppointmentColumnRepository geoKgoAppointmentColumnRepository;
    @Autowired
    GeoKgoAppointmentColumnChildRepository geoKgoAppointmentColumnChildRepository;
    @Autowired
    KgoZipRepository kgoZipRepository;
    @Autowired
    KgoKcdRepository kgoKcdRepository;
    @Autowired
    GeoCityExtService geoKcgCityExtService;
    @Autowired
    GeoKgoAppointmentFormDetailRepository geoKgoAppointmentFormDetailRepository;
    private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();
    @Autowired
    private GeoKgoAppointmentReposCustom geoKgoAppointmentReposCustom;
    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private ExcelTempExportService excelTempExportService;
    @Autowired
    private GeoKgoAppointmentMainRepository geoKgoAppointmentMainRepository;
    @Autowired
    private KgoCountyRepository kgoCountyRepository;
    @Autowired
    private GeoKgoAppointmentCheckRepository geoKgoAppointmentCheckRepository;



    /**
     * 後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單
     *
     * @param rq
     * @return
     */
    public GeoAppointmentOrderQueryRs getAppointmentList(GeoAppointmentOrderQueryRq rq) {
        GeoAppointmentOrderQueryRs rs = new GeoAppointmentOrderQueryRs();
        GeoAppointmentOrderQueryViewForm viewForm = new GeoAppointmentOrderQueryViewForm();
        List<GeoKgoAppointmentOrderQueryModel> modelList = new ArrayList<>();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (StringUtils.isBlank(rq.getAppointmentMainId())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            modelList = geoKgoAppointmentReposCustom.getAppointList(rq.getAppointmentMainId(), rq.getDateStart(), rq.getDateEnd(), GeoBooleanType.ENABLED.getCode());
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
    } //appointMainContactUserQuery

    /**
     * 後台-線上預約臨櫃-預約登錄管理:取得該服務預約時段
     *
     * @param rq
     * @return
     */
    public GeoAppointmentOrderDateQueryRs getAppointmentOrderDate(GeoAppointmentOrderDateQueryRq rq) {
        GeoAppointmentOrderDateQueryRs rs = new GeoAppointmentOrderDateQueryRs();
        GeoAppointmentOrderDateQueryViewForm viewForm = new GeoAppointmentOrderDateQueryViewForm();
        List<GeoKgoAppointmentDetailInsertModel> detailModelList = new ArrayList<>();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (StringUtils.isBlank(rq.getAppointmentMainId())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            List<GeoKgoAppointmentDetail> detailList = geoKgoAppointmentDetailRepository.findAllByAppointmentMainIdAndIsEnable(rq.getAppointmentMainId(), GeoBooleanType.ENABLED.getCode());
            if (detailList != null && detailList.size() > 0) {
                detailModelList = GeoKgoAppointmentDetailInsertModel.changeListToQueryDateModel(detailList);
                for (GeoKgoAppointmentDetailInsertModel detail : detailModelList) {
                    List<GeoKgoAppointmentDetailTimeInsertModel> timeModelList = new ArrayList<>();
                    List<GeoKgoAppointmentDetailTime> timeList = geoKgoAppointmentDetailTimeRepository.findAllByAppointmentDetailId(detail.getAppointmentDetailId());
                    if (timeList != null && timeList.size() > 0) {
                        timeModelList = GeoKgoAppointmentDetailTimeInsertModel.changeListToQueryDateModel(timeList);
                    } //if (timeList != null...
                    detail.setDetailTimeList(timeModelList);
                } //for (GeoKgoAppointmentDetailInsertModel...
            } //if (detailList != null
            viewForm.setDetailList(detailModelList);
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
    } //getAppointmentOrderDate

    /**
     * 後台-線上預約臨櫃-預約登錄管理:新增/編輯預約登錄
     *
     * @param rq
     * @return
     */
    public GeoAppointmentOrderEditRs editAppointmentOrder(GeoAppointmentOrderEditRq rq, SystemTypeEnum systemTypeEnum) {
        GeoAppointmentOrderEditRs rs = new GeoAppointmentOrderEditRs();
        GeoAppointmentOrderEditViewForm viewForm = new GeoAppointmentOrderEditViewForm();
        GeoKgoAppointmentOrderEditModel model = null;
        OperationApiMemo memo = null;
        KgoApiException error = null;
        GeoKgoAppointment entity = null;
        GeoKgoAppointmentDetailTime detailTime = null;
        Boolean isEdit = null;
        String appointmentName = StringUtils.EMPTY;
        String appointmentEmail = StringUtils.EMPTY;
        String appointmentPhone = StringUtils.EMPTY;
        String appointmentNameOld = StringUtils.EMPTY;
        String appointmentEmailOld = StringUtils.EMPTY;
        String appointmentPhoneOld = StringUtils.EMPTY;
        String appointmentIdentity;
        rs.setData(viewForm);

        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            String numberName = null;

            appointmentIdentity = getColumnViewForm(rq.getColumnDataList(),"ID");
            appointmentName = getColumnViewForm(rq.getColumnDataList(),"Name");
            appointmentPhone = getColumnViewForm(rq.getColumnDataList(), "CellPhone");
            appointmentEmail = getColumnViewForm(rq.getColumnDataList(), "Email");

            GeoKgoAppointmentDetailTime geoKgoAppointmentDetailTime = null;

            if (StringUtils.isBlank(appointmentEmail) || StringUtils.isBlank(appointmentIdentity) ||
                    StringUtils.isBlank(appointmentPhone) || StringUtils.isBlank(appointmentName)) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            } // if (StringUtils.isBlank...

//            /**
//             * 20220811 GEO add 身分驗證
//             * */
//            //前台登入方式
//            if (systemTypeEnum.equals(SystemTypeEnum.F)) {
//                // 檢核驗證方式式
//                Boolean checkLoginBoolean = getAppointmentLoginCheck(rq.getAppointmentMainId());
//                //不符合該案件驗證方式
//                if (!checkLoginBoolean) {
//                    throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.PERMISSION_DENIED));
//                } //if (!checkLoginBoolean)
//            } //  if (systemTypeEnum.equals...

            //查詢黑名單
            GeoKgoAppointmentBlockUser blockUser = geoKgoAppointmentBlockUserRepository.findByAppointmentMainIdAndIdentity(rq.getAppointmentMainId(),appointmentIdentity);
            detailTime = geoKgoAppointmentDetailTimeRepository.findByAppointmentDetailTimeId(rq.getAppointmentDetailTimeId());

            if (blockUser != null&& detailTime !=null){
                if (blockUser.getBlockStartTime().before(detailTime.getStartTime()) && blockUser.getEditTime().before(detailTime.getEndTime())){
                    LOGGER.info("此人為黑名單故新增失敗");
                    throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_ADD));
                }
            } //if (blockUser != null&& detailTime !=null)

            /**
             * 20220912 GEO add 限定一天只能預約一次相同臨櫃
             * */
            LOGGER.info("editAppointmentOrder rq.getAppointmentDetailTimeId()="+rq.getAppointmentDetailTimeId());
            LOGGER.info("editAppointmentOrder appointmentIdentity="+appointmentIdentity);
            GeoKgoAppointment geoKgoAppointment =null ;
            geoKgoAppointment = geoKgoAppointmentRepository.findByAppointmentDetailTimeIdAndAppointmentIdentity(rq.getAppointmentDetailTimeId(), appointmentIdentity);
            if (geoKgoAppointment !=null ){
                if (geoKgoAppointment.getIsAvailable().equals(GeoBooleanType.ENABLED.getCode())){
                    LOGGER.info("此人今天已預約過無法再預約");
                    throw new KgoApiException(new ErrorResult(KgoBackEndApiError.DATA_IS_REPEATED));
                }
            } //if (geoKgoAppointment !=null )

            /**
             * 20220905 GEO add 最早最晚可預約時段判斷
             * */
            GeoKgoAppointmentDetail geoKgoAppointmentDetail =
                    geoKgoAppointmentDetailRepository.findByAppointmentDetailId(detailTime.getAppointmentDetailId());
            Timestamp appointmentTime = geoKgoAppointmentDetail.getAppointmentDetailDate();
            String appointmentDate = DateUtil.timestampToString(appointmentTime,DateUtil.PATTEN_YEAR_MONTH_DAY_NO_HYPHEN);
            LOGGER.info("appointmentDate="+appointmentDate);

            Date earliest = null;
            Date latest = null;

            //最早可預約天數 && 最早可預約時段
            int earliestDay = 0;
            if (geoKgoAppointmentDetail.getEarliestDay()!=null)earliestDay=geoKgoAppointmentDetail.getEarliestDay();
            String earliestTime = geoKgoAppointmentDetail.getEarliestTime();
            if (earliestTime!=null && !earliestTime.equals("")){
                Calendar ca = Calendar.getInstance();
                ca.setTime(appointmentTime);
                ca.add(Calendar.DATE,-earliestDay);
                //時
                int earliestH = Integer.parseInt(earliestTime.substring(0,2));
                ca.add(Calendar.HOUR,earliestH);
                //分
                int earliestM = Integer.parseInt(earliestTime.substring(3,5));
                ca.add(Calendar.MINUTE,earliestM);
                earliest = ca.getTime();
                LOGGER.info("editAppointmentOrder earliest="+earliest);
            } //if (earliest!=null && !earliest.equals("")

            String latestTime = geoKgoAppointmentDetail.getLatestTime();
            LOGGER.info("latestTime = "+latestTime);
            //最晚可預約天數 && 最晚可預約時段
            int latestDay = 0;
            if (geoKgoAppointmentDetail.getLatestDay()!=null)latestDay=geoKgoAppointmentDetail.getLatestDay();
            if (latestTime!=null && !latestTime.equals("")){
                Calendar ca = Calendar.getInstance();
                ca.setTime(appointmentTime);
                ca.add(Calendar.DATE,-latestDay);
                //時
                int latestH = Integer.parseInt(latestTime.substring(0,2));
                ca.add(Calendar.HOUR,latestH);
                //分
                int latestM = Integer.parseInt(latestTime.substring(3,5));
                ca.add(Calendar.MINUTE,latestM);
                latest = ca.getTime();
                LOGGER.info("editAppointmentOrder latest="+latest);
            } //if (earliest!=null && !earliest.equals("")

            Date currentDate = new Date();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
            dateFormat.format(currentDate);
            LOGGER.info("currentDate="+currentDate);

            if (earliest != null && latest==null){
                if (currentDate.before(earliest)){
                    LOGGER.info("尚未開法預約");
                    throw new KgoApiException(new ErrorResult(KgoBackEndApiError.NOT_OPEN));
                } //if (currentDate.before(earliest))
            }else if (latest !=null && earliest==null){
                if (currentDate.after(latest)){
                    LOGGER.info("已結束預約");
                    throw new KgoApiException(new ErrorResult(KgoBackEndApiError.HAS_ENDED));
                } //if (currentDate.after(latest))
            }else if (earliest !=null && earliest !=null){
                if (currentDate.before(earliest)){
                    LOGGER.info("尚未開法預約");
                    throw new KgoApiException(new ErrorResult(KgoBackEndApiError.NOT_OPEN));
                } //if (currentDate.before(earliest))
                if (currentDate.after(latest)){
                    LOGGER.info("已結束預約");
                    throw new KgoApiException(new ErrorResult(KgoBackEndApiError.HAS_ENDED));
                } //if (currentDate.after(latest))
            } //if (earliest != null && latest==null)

            // 檢核
            GeoAppointmentValidationActionViewForm validationFrom = saveValidation(rq);
            viewForm.setValidationMsg(validationFrom.getValidationMsg());

            if (validationFrom.getValidationMsg().size() == 0) {
                if (StringUtils.isNotBlank(rq.getAppointmentId())) {
                    //編輯
                    isEdit = true;
                    memo = super.genOperationMemo(systemTypeEnum, SysLogExeType.TYPE_U, BackendFunctionCodeEnum.Appointment);
                    entity = geoKgoAppointmentRepository.findByAppointmentId(rq.getAppointmentId());
                    if (entity != null) {
                        appointmentNameOld = entity.getAppointmentName();
                        appointmentEmailOld = entity.getAppointmentEmail();
                        appointmentPhoneOld = entity.getAppointmentPhone();
                        GeoKgoAppointmentDetailNumbers numbers = geoKgoAppointmentDetailNumbersRepository.findByAppointmentDetailNumbersId(entity.getAppointmentDetailNumbersId());
                        entity.setAppointmentIdentity(appointmentIdentity);
                        entity.setAppointmentName(appointmentName);
                        entity.setAppointmentPhone(appointmentPhone);
                        entity.setAppointmentEmail(appointmentEmail);
                        entity.setEditTime(now);
                        if (numbers != null) numberName = numbers.getNumberName();
                    } //if (entity != null)
                } else {
                    //新增
                    isEdit = false;
                    memo = super.genOperationMemo(systemTypeEnum, SysLogExeType.TYPE_A, BackendFunctionCodeEnum.Appointment);
                    entity = new GeoKgoAppointment();

                    if (GeoBooleanType.ENABLED.getCode() == rq.getIsOnline()) {
                        //搜尋號碼牌
                        detailTime = geoKgoAppointmentDetailTimeRepository.findByAppointmentDetailTimeId(rq.getAppointmentDetailTimeId());
                        List<GeoKgoAppointmentDetailNumbers> numbers = geoKgoAppointmentDetailNumbersRepository.findAllByAppointmentDetailTimeIdAndIsUsed(rq.getAppointmentDetailTimeId(), GeoBooleanType.ENABLED.getCode());
                        if (numbers != null && numbers.size() > 0) {
                            entity.setAppointmentDetailNumbersId(numbers.get(0).getAppointmentDetailNumbersId());
                            numbers.get(0).setIsUsed(GeoBooleanType.DISABLED.getCode());
                            numberName = numbers.get(0).getNumberName();
                            geoKgoAppointmentDetailNumbersRepository.save(numbers.get(0));
                        } else {
                            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_GET_NUMBERS));
                        } // if (numbers != null && numbers.size() > 0)
                    } //if (GeoBooleanType.ENABLED.getCode()...

                    //欄位資料放入
                    entity.setAppointmentId(geoKgoAppointmentReposCustom.getNextTableId(
                            GeoStringUtil.APPOINTMENT_PREFIX_CHAR, "GEO_KGO_APPOINTMENT", "appointment_id"));
                    entity.setAppointmentMainId(rq.getAppointmentMainId());
                    entity.setAppointmentDetailTimeId(rq.getAppointmentDetailTimeId());
                    entity.setAppointmentIdentity(appointmentIdentity);
                    entity.setAppointmentName(appointmentName);
                    entity.setAppointmentPhone(appointmentPhone);
                    entity.setAppointmentEmail(appointmentEmail);
                    entity.setIsAvailable(GeoBooleanType.ENABLED.getCode());
                    entity.setIsOnline(rq.getIsOnline());
                    entity.setEditTime(now);
                } //if (!StringUtils.isBlank(appointment.getAppointmentId()))

                //存檔
                entity = geoKgoAppointmentRepository.save(entity);
                saveAppointmentFormDetail(rq, entity);

                if (entity != null) {
                    GeoKgoAppointmentMain main = geoKgoAppointmentMainRepository.findByAppointmentMainId(rq.getAppointmentMainId());
                    detailTime = geoKgoAppointmentDetailTimeRepository.findByAppointmentDetailTimeId(rq.getAppointmentDetailTimeId());
                    GeoKgoAppointmentDetail detail = geoKgoAppointmentDetailRepository.findByAppointmentDetailId(detailTime.getAppointmentDetailId());
                    String emailStr = entity.getAppointmentEmail();
                    if (detailTime != null && StringUtils.isNotBlank(emailStr)) {
                        Timestamp time = detailTime.getStartTime();
                        String appointNameStr = StringUtils.defaultString(main.getAppointmentName(), StringUtils.EMPTY);
                        String yearStr = StringUtils.defaultString(DateUtil.convertTimeStampToTWDate(time, "yyy"), StringUtils.EMPTY);
                        String monthStr = StringUtils.defaultString(DateUtil.convertTimeStampToTWDate(time, "MM"), StringUtils.EMPTY);
                        String dayStr = StringUtils.defaultString(DateUtil.convertTimeStampToTWDate(time, "dd"), StringUtils.EMPTY);
                        String weekStr = StringUtils.defaultString(GeoWeekDayType.valueOfCode(DateUtil.getWeekDay(time)).getLabel(), StringUtils.EMPTY);
                        String startTimeStr = StringUtils.defaultString(DateUtil.dateToString(detailTime.getStartTime(), DateUtil.PATTEN_HOUR_MINUTE), StringUtils.EMPTY);
                        String endTimeStr = StringUtils.defaultString(DateUtil.dateToString(detailTime.getEndTime(), DateUtil.PATTEN_HOUR_MINUTE), StringUtils.EMPTY);
                        String locationStr = StringUtils.EMPTY;
                        String descStr = GeoAppointmentTimeType.MORNING.getActionType(); //預設上午
                        int timeClock = 0;
                        timeClock=Integer.parseInt(startTimeStr.substring(0,2));
                        if (timeClock > 12) descStr = GeoAppointmentTimeType.AFTER.getActionType();
                        if (detail != null)
                            locationStr = StringUtils.defaultString(detail.getLocation(), StringUtils.EMPTY);
                        String numberStr = StringUtils.defaultString(numberName, StringUtils.EMPTY);
                        sendEmailAppointmentCase(appointNameStr, emailStr, GeoAppointmentActionType.SUCCESS_APPOINTMENT.getActionType(),
                                yearStr, monthStr, dayStr, weekStr,descStr,startTimeStr, endTimeStr, numberStr, locationStr);
                        LOGGER.info(">>>>> 預約成功資訊: " + " 線上預約臨櫃名稱為「" + appointNameStr + "」" + "，信箱為「 " + emailStr + "」"
                                + "，已" + GeoAppointmentActionType.SUCCESS_APPOINTMENT.getActionType() + "，時間為" + yearStr + "年" + monthStr + "月" + dayStr + "日"
                                + weekStr + descStr + startTimeStr + "~" + endTimeStr + "，號碼為: " + numberStr + "，線上預約臨櫃地點為「" + locationStr + "」");
                        model = GeoKgoAppointmentOrderEditModel.changeToModel(entity);
                       /** GEO 20211230 add 預約成功 顯示號碼牌、日期、時間 */
                        model.setAppointmentYear(yearStr);
                        model.setAppointmentMonth(monthStr);
                        model.setAppointmentDay(dayStr);
                        model.setAppointmentTime(startTimeStr);
                        model.setAppointmentDetailNumbersName(numberStr);
                        viewForm.setAppointment(model);
                    } //if (detailTime != null)
            } else {
                    if ( StringUtils.isNotBlank(rq.getAppointmentId())) {
                        throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_EDIT));
                    } else {
                        throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE));
                    }//if (rq.getAppointmentId() != null)
                }//if (entity != null)
            } // if (validationFrom.getValidationMsg()...
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE), e);
            if (rq.getAppointmentId() != null) {
                error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_EDIT), e);
            }
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
            /** === 儲存操作紀錄 === */
            if (entity != null && isEdit != null) {
                List<OperationColumn> memoList = new ArrayList<>();
                String memoStr = String.format("線上預約臨櫃編號：");
                if (detailTime != null) {
                    if (!isEdit) {
                        memoStr = String.format("預約民眾:%s %s %s 預約時段:%s-%s 線上預約臨櫃編號：", entity.getAppointmentName()
                                , entity.getAppointmentPhone(), entity.getAppointmentEmail()
                                , DateUtil.dateToString(detailTime.getStartTime(), DateUtil.PATTEN_FULL_TIME_TO_MINUTE)
                                , DateUtil.dateToString(detailTime.getEndTime(), DateUtil.PATTEN_HOUR_MINUTE));
                    } else {
                        memoStr = String.format("預約民眾:%s>>%s %s>>%s %s>>%s 預約時段:%s-%s 線上預約臨櫃編號：", appointmentNameOld, entity.getAppointmentName()
                                , appointmentPhoneOld, entity.getAppointmentPhone(), appointmentEmailOld, entity.getAppointmentEmail(), DateUtil.dateToString(detailTime.getStartTime()
                                        , DateUtil.PATTEN_FULL_TIME_TO_MINUTE), DateUtil.dateToString(detailTime.getEndTime(), DateUtil.PATTEN_HOUR_MINUTE));
                    } //if (!isEdit)
                } //if (detailTime != null
                memoList.add(new OperationColumn(memoStr, entity.getAppointmentId()));
                memo.setMemoList(memoList);
                super.saveOperationLog(memo);
                /** === 儲存操作紀錄 === */
            }
        } //try
        return rs;
    } //editAppointmentOrder

    /**
     * 後台-線上預約臨櫃-預約登錄管理:刪除預約登錄
     *
     * @param rq
     * @return
     */
    public GeoAppointmentDeleteRs deleteAppointmentById(GeoAppointmentDeleteRq rq, SystemTypeEnum systemTypeEnum) {
        GeoAppointmentDeleteRs rs = new GeoAppointmentDeleteRs();
        GeoAppointmentDeleteViewForm viewForm = new GeoAppointmentDeleteViewForm();
        rs.setData(viewForm);
        String casesetAddress = StringUtils.EMPTY;
        String numberSTr = StringUtils.EMPTY;
        KgoApiException error = null;
        OperationApiMemo memo = null;
        GeoKgoAppointment entity = null;

        try {
            if (StringUtils.isBlank(rq.getAppointmentId())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }

            /**
             * 20220811 GEO add 身分驗證
             * */
            //前台登入方式
            if (systemTypeEnum.equals(SystemTypeEnum.F)) {
                // 檢核驗證方式式
                GeoKgoAppointment appointment = geoKgoAppointmentRepository.findByAppointmentId(rq.getAppointmentId());
                Boolean checkLoginBoolean = getAppointmentLoginCheck(appointment.getAppointmentMainId());
                if (!checkLoginBoolean) {
                    throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.PERMISSION_DENIED));
                } //if (!checkLoginBoolean)
            } //  if (systemTypeEnum.equals...

            memo = super.genOperationMemo(systemTypeEnum, SysLogExeType.TYPE_D, BackendFunctionCodeEnum.Appointment);
            entity = geoKgoAppointmentRepository.findByAppointmentId(rq.getAppointmentId());
            if (entity != null) {
                entity.setIsAvailable(GeoBooleanType.DISABLED.getCode());
                if (!StringUtils.isBlank(rq.getAppointmentDetailNumbersId())) {
                    GeoKgoAppointmentDetailNumbers numbers =
                            geoKgoAppointmentDetailNumbersRepository.findByAppointmentDetailNumbersId(rq.getAppointmentDetailNumbersId());
                    if (numbers != null) {
                        numbers.setIsUsed(GeoBooleanType.ENABLED.getCode());
                        geoKgoAppointmentDetailNumbersRepository.save(numbers);
                        entity.setAppointmentDetailNumbersId(null);
                        numberSTr = numbers.getNumberName();
                    } //if (numbers != null)
                } // if (!StringUtils.isBlank...
                geoKgoAppointmentRepository.save(entity);
                GeoKgoAppointmentMain main = geoKgoAppointmentMainRepository.findByAppointmentMainId(entity.getAppointmentMainId());
                GeoKgoAppointmentDetailTime detailTime = geoKgoAppointmentDetailTimeRepository.findByAppointmentDetailTimeId(entity.getAppointmentDetailTimeId());
                if (detailTime != null) {
                    Timestamp time = detailTime.getStartTime();
                    String appointNameStr = StringUtils.defaultString(main.getAppointmentName(), StringUtils.EMPTY);
                    String yearStr = StringUtils.defaultString(DateUtil.convertTimeStampToTWDate(time, "yyy"), StringUtils.EMPTY);
                    String monthStr = StringUtils.defaultString(DateUtil.convertTimeStampToTWDate(time, "MM"), StringUtils.EMPTY);
                    String dayStr = StringUtils.defaultString(DateUtil.convertTimeStampToTWDate(time, "dd"), StringUtils.EMPTY);
                    String weekStr = StringUtils.defaultString(GeoWeekDayType.valueOfCode(DateUtil.getWeekDay(time)).getLabel(), StringUtils.EMPTY);
                    String startTimeStr = StringUtils.defaultString(DateUtil.dateToString(detailTime.getStartTime(), DateUtil.PATTEN_HOUR_MINUTE), StringUtils.EMPTY);
                    String endTimeStr = StringUtils.defaultString(DateUtil.dateToString(detailTime.getEndTime(), DateUtil.PATTEN_HOUR_MINUTE), StringUtils.EMPTY);
                    int timeClock = 0;
                    timeClock=Integer.parseInt(startTimeStr.substring(0,2));
                    String descStr = GeoAppointmentTimeType.MORNING.getActionType(); //預設上午
                    if (timeClock>12) descStr = GeoAppointmentTimeType.AFTER.getActionType();
                    GeoKgoAppointmentDetail detail = geoKgoAppointmentDetailRepository.findByAppointmentDetailId(detailTime.getAppointmentDetailId());
                    if (detail != null) {
                        casesetAddress = StringUtils.defaultString(detail.getLocation(), StringUtils.EMPTY);
                    }
                    sendEmailAppointmentCase(appointNameStr, rq.getAppointmentEmail(), GeoAppointmentActionType.CANCEL_APPOINTMENT.getActionType(),
                            yearStr, monthStr, dayStr, weekStr,descStr, startTimeStr, endTimeStr, numberSTr, casesetAddress);
                    LOGGER.info(">>>>> 取消成功資訊: " + " 線上預約臨櫃名稱為「" + appointNameStr + "」" + "，信箱為「 " + rq.getAppointmentEmail() + "」"
                            + "，已" + GeoAppointmentActionType.CANCEL_APPOINTMENT.getActionType() + "，時間為" + yearStr + "年" + monthStr + "月" + dayStr + "日"
                            + weekStr + descStr + startTimeStr + "~" + endTimeStr + "，號碼為: " + numberSTr + "，線上預約臨櫃地點為「" + casesetAddress + "」");
                }

                viewForm.setMsg(SuccessMsg.DELETE.getMsg());
            } else {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE));
            } // if (appointment != null)
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE), e);
        } finally {
            setResultMessage(null, rs, error);
            /** === 儲存操作紀錄 === */
            if (entity != null) {
                List<OperationColumn> memoList = new ArrayList<>();
                String memoStr = String.format("線上預約臨櫃編號：");
                memoList.add(new OperationColumn(memoStr, entity.getAppointmentId()));
                memo.setMemoList(memoList);
                super.saveOperationLog(memo);
                /** === 儲存操作紀錄 === */
            }
        } //try
        return rs;
    } //deleteAppointmentById

    /**
     * Geo 20211115 Add
     * 寄信給一般民眾(預約).
     */
    public void sendEmailAppointmentCase(String appointNameStr, String emailStr, String actionType, String yearStr, String monthStr, String dayStr,
                                         String weekStr,String descStr, String startTimeStr, String endTimeStr, String numberStr, String locationStr) {
        try {
            if (StringUtils.isNoneBlank(emailStr)) {
                Map<String, Object> model = new HashMap<String, Object>();

                model.put("casesetName", appointNameStr);
                model.put("casesetType", actionType);
                model.put("casesetYear", yearStr);
                model.put("casesetMouth", monthStr);
                model.put("casesetDate", dayStr);
                model.put("casesetWeek", weekStr);
                model.put("casesetdesc", descStr);
                model.put("casesetStartTime", startTimeStr);
                model.put("casesetEndTime", endTimeStr);
                if (StringUtils.isBlank(numberStr)) numberStr = StringUtils.EMPTY;
                else numberStr = "【號碼牌為：" + numberStr + "】";
                model.put("appointmentNumber", numberStr);
                model.put("casesetAddress", locationStr);

//                model.forEach((k, v) -> LOGGER.info("sendEmailAppointmentCase --> " + k + ":" + v));

                // 11.18 modify: 改用util 寄送mail
                MailUtil mailUtil = SpringUtil.getBean(MailUtil.class);
                MessageUtil messageUtil = SpringUtil.getBean(MessageUtil.class);
                mailUtil.sendTemplateMail(new String[]{emailStr}, null, messageUtil.getMessage("kgo.backend.case.handle.review.notification.msgTitle"), model, "AppointmentCase.html");
            }

        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error(String.format("sendEmailAppointmentCase appointName: %s", appointNameStr), e);
        } //try
    } //sendEmailAppointmentCase

    /**
     * GEO 2021115 add
     * 後台-線上預約臨櫃:匯出excel
     *
     * @param rq the rq
     */
    public void exportExcelAction(GeoAppointmentOrderQueryRq rq) {
        try {
            GeoAppointmentOrderQueryRs rs = getAppointmentList(rq);

            String fileName = messageUtil.getMessage("kgo.backend.appointment.excel.fileName");
            String sheetName = messageUtil.getMessage("kgo.backend.appointment.excel.sheet");
            Map<String, Object> data = new HashMap<>();
            data.put("ORDER", messageUtil.getMessage("kgo.backend.appointment.excel.header.order"));
            data.put("NUMBER_NAME", messageUtil.getMessage("kgo.backend.appointment.excel.header.numberName"));
            data.put("APPLY_DATE", messageUtil.getMessage("kgo.backend.appointment.excel.header.applyDate"));
            data.put("APPLY_TIME", messageUtil.getMessage("kgo.backend.appointment.excel.header.applyTime"));
            data.put("STATUS", messageUtil.getMessage("kgo.backend.appointment.excel.header.status"));
            data.put("NAME", messageUtil.getMessage("kgo.backend.appointment.excel.header.appointmentName"));
            data.put("ID", messageUtil.getMessage("kgo.backend.appointment.excel.header.appointmentIdentity"));
            data.put("EMAIL", messageUtil.getMessage("kgo.backend.appointment.excel.header.appointmentEmail"));
            data.put("PHONE", messageUtil.getMessage("kgo.backend.appointment.excel.header.appointmentPhone"));

            List<GeoAppointmentExcelVo> geoAppointmentExcelVos = getLogDataList(rs);
            data.put("DATA_LIST", geoAppointmentExcelVos);
            excelTempExportService.exportAppointmentExcel(fileName, sheetName, data);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("exportExcelAction exportExcelAction error " + e.getMessage(), e);
        } //try catch
    } //exportExcelAction

    /**
     * 20211115 add
     * 後台 - 線上預約臨櫃 - 匯出excel
     *
     * @param queryRs
     * @return
     */
    public List<GeoAppointmentExcelVo> getLogDataList(GeoAppointmentOrderQueryRs queryRs) {
        List<GeoAppointmentExcelVo> appointmentExcelVos = new ArrayList<>();
        List<GeoKgoAppointmentOrderQueryModel> modelList = queryRs.getData().getDataList();
        Integer order = 1;
        for (GeoKgoAppointmentOrderQueryModel model : modelList) {
            GeoAppointmentExcelVo excelVo = new GeoAppointmentExcelVo();
            excelVo.setOrder(order);
            excelVo.setNumberName(model.getNumbersName());
            excelVo.setApplyDate(model.getDate());
            excelVo.setApplyTime(getApplyTimeCombination(model.getDateStart(), model.getDateEnd()));
            excelVo.setStatus(getStatusName(model.getIsOnline()));
            excelVo.setAppointmentName(model.getAppointmentName());
            excelVo.setAppointmentIdentity(model.getAppointmentIdentity());
            excelVo.setAppointmentPhone(model.getAppointmentPhone());
            excelVo.setAppointmentEmail(model.getAppointmentEmail());
            appointmentExcelVos.add(excelVo);
            order++;
        } //for
        return appointmentExcelVos;
    } //getLogDataList

    /**
     * 20211115 add
     * 後台 - 線上預約臨櫃 - 取得線上類型
     *
     * @param status
     * @return
     */
    public String getStatusName(Integer status) {
        String statusName = StringUtils.EMPTY;
        if (status == 0) {
            statusName = messageUtil.getMessage("kgo.backend.appointment.excel.isOnline");
        } else {
            statusName = messageUtil.getMessage("kgo.backend.appointment.excel.other");
        } //if else
        return statusName;
    } //getStatusName

    /**
     * 20211115 add
     * 後台 - 線上預約臨櫃 - 預約時段組合
     *
     * @param start
     * @param end
     * @return
     */
    public String getApplyTimeCombination(String start, String end) {
        String applyTime = StringUtils.EMPTY;
        if (start.isEmpty())
            start = "";
        if (end.isEmpty())
            end = "";
        applyTime = start + "~" + end;
        return applyTime;
    } //getApplyTimeCombination

    /**
     * 後台-線上預約臨櫃-預約登錄管理:新增/編輯取得預約對應表單
     */
    public GeoAppointmentOrderFormQueryRs getAppointmentOrderForm(GeoAppointmentOrderFormQueryRq rq,SystemTypeEnum systemTypeEnum) {
        GeoAppointmentOrderFormQueryRs rs = new GeoAppointmentOrderFormQueryRs();
        GeoAppointmentOrderFormQueryViewForm viewForm = new GeoAppointmentOrderFormQueryViewForm();
        try {
            String appointmentMainId = rq.getAppointmentMainId();
            GeoKgoAppointmentMain geoKgoAppointmentMain = geoKgoAppointmentMainRepository.findByAppointmentMainId(appointmentMainId);

            //該預約服務是否有開啟
            if (ObjectUtils.isEmpty(geoKgoAppointmentMain) || (!geoKgoAppointmentMain.getAppointmentStatus().equalsIgnoreCase(GeoAppointmentMainStatusType.ON.getLabel()))) {
                rs.setError(new ErrorResult(KgoFrontEndApiError.SERVICE_IS_NOT_PROVIDED));
                return rs;
            } //if (ObjectUtils.isEmpty(geoKgoAppointmentMain) |

            /**
             * 20220811 GEO add 身分驗證
             * */
            //前台登入方式
            /**
            if (systemTypeEnum.equals(SystemTypeEnum.F)) {
                // 檢核驗證方式式
                Boolean checkLoginBoolean = getAppointmentLoginCheck(rq.getAppointmentMainId());
                if (!checkLoginBoolean) {
                    throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.PERMISSION_DENIED));
                }
            } //  if (systemTypeEnum.equals...
            **/
            List<GeoAppointmentGroupQueryDataMaxVersionDto> dtoList = geoKgoAppointmentReposCustom.findMaxVersionGroupData(appointmentMainId, StringUtils.EMPTY);
            Integer version = ObjectUtils.isEmpty(dtoList) ? KgoUtil.DEFAULT_VERSION_NUMBER : dtoList.get(0).getVersion();
            viewForm.setVersion(version);

            List<OptionViewForm> options = new ArrayList<OptionViewForm>();
            viewForm.setModelList(getGroupViewForms(appointmentMainId, options,systemTypeEnum));
            viewForm.setOptions(options);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("getAppointmentOrderForm error " + e.getMessage(), e);
        }
        return rs;
    } //getAppointmentOrderForm

    /**
     * 登入方式檢核
     *
     * @param appointmentMainId
     * @return
     */
    public Boolean getAppointmentLoginCheck(String appointmentMainId) {
        LOGGER.info("getAppointmentLoginCheck..."+appointmentMainId);
//        if (this.isDevMode()) return true;
        //預設不符合驗證
        Boolean checkBoolean = false;
        List<GeoKgoAppointmentCheck> checkTypeList = geoKgoAppointmentCheckRepository.findAllByIdAppointmentMainId(appointmentMainId);
        FrontendLoginUserInfo loginUserInfo = this.getLoginUserInfo();
        if (ObjectUtils.isNotEmpty(checkTypeList) && checkTypeList.size() > 0) {
            for (GeoKgoAppointmentCheck geoKgoAppointmentCheck : checkTypeList) {
                CheckTypeEnum checkTypeEnum = CheckTypeEnum.getEnum(geoKgoAppointmentCheck.getId().getCheckType());
                LOGGER.info("getAppointmentLoginCheck checkTypeEnum..."+checkTypeEnum);
                if (ObjectUtils.isNotEmpty(checkTypeEnum) && ObjectUtils.isNotEmpty(checkTypeEnum.getLoginAuthType())) {
                    if (ObjectUtils.isNotEmpty(loginUserInfo)) {
                        checkBoolean = this.getLoginUserInfo().getLoginAuthTokenType() == checkTypeEnum.getLoginAuthType();
                    }
                } else {
                    checkBoolean = true;
                } //if (ObjectUtils.isNotEmpty(checkTypeEnum) && ObjectUtils.isNotEmpty(checkTypeEnum.getLoginAuthType()))
                if (checkBoolean)
                    return checkBoolean;
            } //for (GeoKgoAppointmentCheck geoKgoAppointmentCheck : checkTypeList)
        } else {
            checkBoolean = true;
        } //if (ObjectUtils.isNotEmpty(checkTypeList) && checkTypeList.size() > 0)
        return checkBoolean;
    } //getAppointmentLoginCheck

    /**
     * 目前是否為開發環境
     *
     * @return
     */
    protected Boolean isDevMode() {
        String value = SpringUtil.getProperty("dev.mode");
        return Boolean.parseBoolean(value);
    }


    /**
     * 取得申請表單 Group 資料
     * x
     */
    private List<GeoAppointmentOrderFormQueryDataModel> getGroupViewForms(String appointmentMainId, List<OptionViewForm> options,SystemTypeEnum systemTypeEnum) throws Exception {
        List<GeoAppointmentOrderFormQueryDataModel> modelList = new ArrayList<>();

        List<GeoAppointmentGroupQueryDataMaxVersionDto> data = getAppointmentGroupData(appointmentMainId);
        for (GeoAppointmentGroupQueryDataMaxVersionDto x : data) {
            GeoAppointmentOrderFormQueryDataModel model = new GeoAppointmentOrderFormQueryDataModel();
            model.setGroupSeq(x.getGroupSeq());
            model.setOrderNum(x.getOrderNum());
            model.setColumnDataList(getColumnViewForms(appointmentMainId, x.getGroupSeq(), x.getVersion(), options,systemTypeEnum));
            model.setCheckFrequencyPeriod(x.getCheckFrequencyPeriod() == null ? "" : x.getCheckFrequencyPeriod());
            modelList.add(model);
        } //for (GeoAppointmentGroupQueryDataMaxVersionDto x
        return modelList;
    } //getGroupViewForms


    /**
     * 取得已預約者申請表單 Group 資料
     * x
     */
    private List<GeoAppointmentOrderFormQueryDataModel> getGroupViewForms(String appointmentMainId, List<OptionViewForm> options,String appointmentId) throws Exception {
        List<GeoAppointmentOrderFormQueryDataModel> modelList = new ArrayList<>();

        List<GeoAppointmentGroupQueryDataMaxVersionDto> data = getAppointmentGroupData(appointmentMainId);
        for (GeoAppointmentGroupQueryDataMaxVersionDto x : data) {
            GeoAppointmentOrderFormQueryDataModel model = new GeoAppointmentOrderFormQueryDataModel();
            model.setGroupSeq(x.getGroupSeq());
            model.setOrderNum(x.getOrderNum());
            model.setColumnDataList(getColumnViewForms(appointmentMainId, x.getGroupSeq(), x.getVersion(), options,appointmentId));
            model.setCheckFrequencyPeriod(x.getCheckFrequencyPeriod() == null ? "" : x.getCheckFrequencyPeriod());
            modelList.add(model);
        } //for (GeoAppointmentGroupQueryDataMaxVersionDto x
        return modelList;
    } //getGroupViewForms

    /**
     * 取得預約表單設定畫面群組資料
     */
    private List<GeoAppointmentGroupQueryDataMaxVersionDto> getAppointmentGroupData(String appointmentMainId) {
        return geoKgoAppointmentReposCustom.findMaxVersionGroupData(appointmentMainId, StringUtils.EMPTY);
    } //getAppointmentGroupData

    /**
     * 取得預約表單 Group 的 Column 設定內容清單
     */
    private List<GeoAppointmentColumnViewForm> getColumnViewForms(String appointmentMainId, Integer groupSeq, Integer version, List<OptionViewForm> options,SystemTypeEnum systemTypeEnum) throws Exception {
        List<GeoAppointmentColumnViewForm> viewForms = new ArrayList<>();

        List<GeoKgoAppointmentColumn> columnList = getAppointmentColumns(appointmentMainId, groupSeq, version);
        FrontendLoginUserInfo loginUserInfo = getLoginUserInfo();

        /** KGO 20210817 add for 1999 service **/
        boolean hadKd = false; //有派工欄位
        boolean hadNew = false; //有陳情欄位

        for (GeoKgoAppointmentColumn x : columnList) {
            GeoAppointmentColumnViewForm viewForm = new GeoAppointmentColumnViewForm();
            viewForm.setAppointmentId(x.getId().getAppointmentMainId());
            viewForm.setGroupSeq(x.getGroupSeq());
            viewForm.setVersion(x.getId().getVersion());
            viewForm.setColumnId(StringUtils.defaultString(x.getId().getColumnId(), ""));
            viewForm.setColumnName(StringUtils.defaultString(x.getColumnName(), ""));
            viewForm.setColumnType(StringUtils.defaultString(x.getColumnType(), ""));
            viewForm.setOrderNum(x.getOrderNum());
            viewForm.setLength(x.getLength());
            viewForm.setIsMustKey(x.getMustKey());
            viewForm.setOrderNum(x.getOrderNum());
            viewForm.setMemo(StringUtils.defaultString(x.getMemo(), ""));
            viewForm.setColumnValue(StringUtils.defaultString(x.getColumnValue(), ""));
            viewForm.setFileType(StringUtils.defaultString(x.getFileType(), ""));
            /**
             * GEO 20211019 add
             */
            viewForm.setIsCheckFrequency(String.valueOf(x.getIsCheckFrequency()));
            viewForm.setComplex(getComplex(appointmentMainId, version, x.getId().getColumnId(), x.getColumnType()));

            setDefaultColumnValue(viewForm, loginUserInfo);

            /** KGO 20210817 add for 1999 service **/
            if (x.getColumnType().equals(ColumnTypeEnum.KD_1999.getValue())) hadKd = true;
            if (x.getColumnType().equals(ColumnTypeEnum.NEW_1999.getValue())) hadNew = true;

            viewForms.add(viewForm);
        } //for (GeoKgoAppointmentColumn...

        /** KGO 20210817 change param for 1999 service **/
        setColumnOptions(options, hadKd, hadNew);
        return viewForms;
    } //getColumnViewForms


    /**
     * 取得已預約表單 Group 的 Column 設定內容清單
     */
    private List<GeoAppointmentColumnViewForm> getColumnViewForms(String appointmentMainId, Integer groupSeq, Integer version, List<OptionViewForm> options,String appointmentId) throws Exception {
        List<GeoAppointmentColumnViewForm> viewForms = new ArrayList<>();

        List<GeoKgoAppointmentColumn> columnList = getAppointmentColumns(appointmentMainId, groupSeq, version);
        List<GeoKgoAppointmentFormDetail> geoKgoAppointmentFormDetails = geoKgoAppointmentFormDetailRepository.findByIdAppointmentId(appointmentId);

        /** KGO 20210817 add for 1999 service **/
        boolean hadKd = false; //有派工欄位
        boolean hadNew = false; //有陳情欄位

        for (GeoKgoAppointmentColumn x : columnList) {
            GeoAppointmentColumnViewForm viewForm = new GeoAppointmentColumnViewForm();
            viewForm.setAppointmentId(x.getId().getAppointmentMainId());
            viewForm.setGroupSeq(x.getGroupSeq());
            viewForm.setVersion(x.getId().getVersion());
            viewForm.setColumnId(StringUtils.defaultString(x.getId().getColumnId(), ""));
            viewForm.setColumnName(StringUtils.defaultString(x.getColumnName(), ""));
            viewForm.setColumnType(StringUtils.defaultString(x.getColumnType(), ""));
            viewForm.setOrderNum(x.getOrderNum());
            viewForm.setLength(x.getLength());
            viewForm.setIsMustKey(x.getMustKey());
            viewForm.setOrderNum(x.getOrderNum());
            viewForm.setMemo(StringUtils.defaultString(x.getMemo(), ""));
            viewForm.setColumnValue(StringUtils.defaultString(x.getColumnValue(), ""));
            viewForm.setFileType(StringUtils.defaultString(x.getFileType(), ""));
            /**
             * GEO 20211019 add
             */
            viewForm.setIsCheckFrequency(String.valueOf(x.getIsCheckFrequency()));
            viewForm.setComplex(getComplex(appointmentMainId, version, x.getId().getColumnId(), x.getColumnType()));

            //設定預約者表單資料
            setDefaultColumnValue(viewForm, geoKgoAppointmentFormDetails);

            /** KGO 20210817 add for 1999 service **/
            if (x.getColumnType().equals(ColumnTypeEnum.KD_1999.getValue())) hadKd = true;
            if (x.getColumnType().equals(ColumnTypeEnum.NEW_1999.getValue())) hadNew = true;

            viewForms.add(viewForm);
        } //for (GeoKgoAppointmentColumn...

        /** KGO 20210817 change param for 1999 service **/
        setColumnOptions(options, hadKd, hadNew);
        return viewForms;
    } //getColumnViewForms

    /**
     * 取得預約表單設定畫面欄位
     */
    private List<GeoKgoAppointmentColumn> getAppointmentColumns(String appointmentMainId, Integer version) {
        return geoKgoAppointmentColumnRepository.findByIdAppointmentMainIdAndIdVersionOrderByOrderNumAsc(appointmentMainId, version);
    } //getAppointmentColumns

    /**
     * 取得預約表單設定畫面欄位
     */
    private List<GeoKgoAppointmentColumn> getAppointmentColumns(String appointmentMainId, int groupSeq, Integer version) {
        return geoKgoAppointmentColumnRepository.findByIdAppointmentMainIdAndGroupSeqAndIdVersionOrderByOrderNumAsc(appointmentMainId, groupSeq, version);
    } //getAppointmentColumns

    /**
     * 產生表單複合欄位
     */
    private List<List<CasesetComplexColumnData>> getComplex(String appointmentMainId, Integer version, String columnId, String columnType) {

        List<List<CasesetComplexColumnData>> complexDataList = new ArrayList<List<CasesetComplexColumnData>>();

        if (columnType.equalsIgnoreCase(ColumnTypeEnum.M.getValue())) {
            Map<Integer, List<GeoKgoAppointmentColumnChild>> dataMap = geoKgoAppointmentColumnChildRepository.findByIdAppointmentMainIdAndIdVersionAndIdColumnIdOrderByIdOrderNumAsc(appointmentMainId, version, columnId).stream()
                    .collect(Collectors.groupingBy(GeoKgoAppointmentColumnChild::getRow, HashMap::new, Collectors.toCollection(LinkedList::new)));

            complexDataList = dataMap.keySet().stream().map(i -> {
                return dataMap.get(i).stream().map(cl -> {
                    CasesetComplexColumnData complexData = new CasesetComplexColumnData();
                    complexData.setbText(cl.getBText());
                    complexData.setcColumnId(cl.getId().getcColumnId());
                    complexData.setColumnType(cl.getColumnType());
                    complexData.setColumnValue(cl.getColumnValue());
                    complexData.setfText(cl.getFText());
                    complexData.setLength(cl.getLength());
                    complexData.setIsMustKey(
                            IsMustKeyEnum.getIsMustKeyEnum(cl.getMustKey()).getValue());
                    /**GEO 20211019 add */
                    complexData.setIsCheckFrequency(cl.getIsCheckFrequency());
                    /** GEO 20211102 add 欄位勾選*/
                    complexData.setIsFieldCheck(cl.getIsFieldCheck());

                    complexData.setOrderNum(cl.getId().getOrderNum());
                    complexData.setpColumnId(cl.getPColumnId());
                    complexData.setRow(cl.getRow());
                    complexData.setvGroup(StringUtils.defaultString(String.valueOf(cl.getVGroup()), ""));
                    return complexData;
                }).collect(Collectors.toList());
            }).collect(Collectors.toList());
        } //  if (columnType.equalsIgnoreCase(ColumnTypeEnum.M
        return complexDataList;
    } //getComplex


    /**
     * KGO 20210817 add  param for 1999 service
     * 設定 元件 options
     *
     * @param options
     * @param hadKd
     * @param hadNew
     * @throws Exception
     */
    public void setColumnOptions(List<OptionViewForm> options, boolean hadKd, boolean hadNew) throws Exception {
        options.add(new OptionViewForm("addressCounty", getCountyOptions()));
        options.add(new OptionViewForm("addressZip", getZipOptionsForGroup()));
        options.add(new OptionViewForm("landNumKcnt", getLandNumOption()));
        // landNumOption(options);

        /** GEO 20210817 add DM_1999 NEW_1999 **/
        if (hadKd) {
            List<Geo1999ItemsMainModel> itemList = geoKcgCityExtService.sendGet1999KdApi();
            options.add(new OptionViewForm("kd1999", get1999Options(itemList)));
            options.add(new OptionViewForm("kd1999Sub", get1999OptionsForGroup(itemList)));
        }
        if (hadNew) {
            List<Geo1999ItemsMainModel> itemList = geoKcgCityExtService.sendGet1999NewApi();
            options.add(new OptionViewForm("new1999", get1999Options(itemList)));
            options.add(new OptionViewForm("new1999Sub", get1999OptionsForGroup(itemList)));
        }
    } //setColumnOptions


    /**
     * 縣市選單
     *
     * @return
     */
    private List<SelectListItem> getCountyOptions() {
        /** 選擇區下拉式選單 **/
        List<KgoCounty> itemList = kgoCountyRepository.findAllByOrderBySortAsc();
        String defaultValue = StringUtils.EMPTY;
        return commonServiceHelper.getComboBox(itemList, "countyName", "countyId", defaultValue, ComboBoxStatusEnum.ALL.getCode(), false).getOptions();
    } //getCountyOptions

    /**
     * 鄉鎮市區選單
     *
     * @return
     */
    private List<SelectListItem> getZipOptionsForGroup() {
        /** 選擇區下拉式選單 **/
        List<KgoZip> itemList = kgoZipRepository.findAll();
        String defaultValue = StringUtils.EMPTY;
        return commonServiceHelper.getComboBox(itemList, "ZIPName", "ZIP", "countyId", defaultValue, ComboBoxStatusEnum.ALL.getCode(), false).getOptions();
    } //getZipOptionsForGroup


    /**
     * 設定 元件 段小段 options
     *
     * @return
     */
    private List<SelectListItem> getLandNumOption() {
        List<KgoKcdDto> itemList = kgoKcdRepository.findByAll();
        String defaultValue = StringUtils.EMPTY;
        return commonServiceHelper.getComboBox(itemList, "KCNT", "KCD", "ZIP", defaultValue, ComboBoxStatusEnum.ALL.getCode(), false).getOptions();
    } //getLandNumOption

    /**
     * 20210817 GEO add
     * 1999選單
     *
     * @return
     */
    private List<SelectListItem> get1999Options(List<Geo1999ItemsMainModel> itemList) {
        String defaultValue = StringUtils.EMPTY;
        return commonServiceHelper.getComboBox(itemList, "itemName", "itemId",
                defaultValue, ComboBoxStatusEnum.ALL.getCode(), false).getOptions();
    } //get1999Options

    /**
     * 20210817 GEO add
     * 1999子選單
     *
     * @return
     */
    private List<SelectListItem> get1999OptionsForGroup(List<Geo1999ItemsMainModel> itemList) {
        List<Geo1999ItemsSubsModel> subsList = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getSubsList() != null && itemList.get(i).getSubsList().size() > 0) {
                for (int j = 0; j < itemList.get(i).getSubsList().size(); j++)
                    subsList.add(itemList.get(i).getSubsList().get(j));
            }
        } //for (int i=0; i<itemList.size(); i++)
        String defaultValue = StringUtils.EMPTY;
        return commonServiceHelper.getComboBox(subsList, "itemName", "itemId", "groupId",
                defaultValue, ComboBoxStatusEnum.ALL.getCode(), false).getOptions();
    } //get1999OptionsForGroup

    /**
     * 儲存前驗證Column
     */
    private GeoAppointmentValidationActionViewForm saveValidation(GeoAppointmentOrderEditRq rq) throws Exception {

        GeoAppointmentValidationActionViewForm viewForm = new GeoAppointmentValidationActionViewForm();
        List<GeoAppointmentOrderEditValidationViewForm> vMsg = new ArrayList<>();

        List<GeoKgoAppointmentColumnChild> appointmentColumns = geoKgoAppointmentColumnChildRepository.findByIdAppointmentMainIdAndIdVersion(rq.getAppointmentMainId(), rq.getVersion());
        Map<GeoKgoAppointmentColumnChildPK, GeoKgoAppointmentColumnChild> columnChildMap = getAppointmentColumnChildMap(appointmentColumns);
        Map<String, Map<String, String>> columnPColumnMap = getCasesetColumnChildPColumnIdMap(appointmentColumns);
        Map<String, Map<String, String>> columnVGroupMap = getCasesetColumnChildVGroupMap(appointmentColumns);
        List<KgoZipF3AreaDto> f3List = this.kgoZipRepository.findByZipF3AreaList();

        for (GeoAppointmentOrderEditColumnData data : rq.getColumnDataList()) {
            Map<String, String> pColumnMap = columnPColumnMap.get(data.getColumnId());
            Map<String, String> vGroupMap = columnVGroupMap.get(data.getColumnId());

            GeoKgoAppointmentColumnPK id = new GeoKgoAppointmentColumnPK();
            id.setAppointmentMainId(rq.getAppointmentMainId());
            id.setVersion(rq.getVersion());
            id.setColumnId(data.getColumnId());
            GeoKgoAppointmentColumn appointmentColumn = KgoUtil.getAppointmentColumnFromCashe(id);

            if (ObjectUtils.isNotEmpty(pColumnMap)) {
                if (pColumnMap.containsKey(data.getColumnId())) {
                    pColumnMap.put(data.getColumnId(), data.getValue());
                }
            } // if (ObjectUtils.isNotEmpty(pColumnMap))

            if (ObjectUtils.isNotEmpty(appointmentColumn)) {

                ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(appointmentColumn.getColumnType());
                // 必填檢查
                if (appointmentColumn.getMustKey() && columnTypeEnum != ColumnTypeEnum.M) {
                    if (StringUtils.isBlank(data.getValue())) {
                        vMsg.add(new GeoAppointmentOrderEditValidationViewForm(data.getColumnId(), appointmentColumn.getColumnName(),
                                ErrorResult.getErrorDescI18n(KgoFrontEndApiError.INPUT_IS_EMPTY.getErrorMsgKey(), "")));
                        continue;
                    }
                } //if (appointmentColumn.getMustKey()

                if ((columnTypeEnum == ColumnTypeEnum.ADDRESSTEXT) && ObjectUtils.isNotEmpty(f3List)) {
                    String f3Name = this.commonServiceHelper.getF3Name(data.getValue(), f3List);
                    if (StringUtils.isNoneBlank(f3Name))
                        viewForm.setF3Name(f3Name);
                } // if ((columnTypeEnum == ColumnTypeEnum.ADDRESSTEXT)...

                saveValidationComplex(appointmentColumn, data, columnChildMap, pColumnMap, vGroupMap);
            } // if (ObjectUtils...
        }
        viewForm.setValidationMsg(vMsg.stream().distinct().collect(Collectors.toList()));
        return viewForm;
    } //saveValidation

    /**
     * @param list
     * @return
     */
    private Map<GeoKgoAppointmentColumnChildPK, GeoKgoAppointmentColumnChild> getAppointmentColumnChildMap(List<GeoKgoAppointmentColumnChild> list) {
        return list.stream().collect(Collectors.toMap(x -> x.getId(), c -> c));
    } //getCasesetColumnChildMap

    /**
     * GeoKgoAppointmentColumnChild Id Map
     */
    private Map<String, Map<String, String>> getCasesetColumnChildPColumnIdMap(List<GeoKgoAppointmentColumnChild> list) {
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

        list.stream().map(column -> column.getId().getColumnId()).distinct().forEach(columnId -> {
            Map<String, String> cMap = list.stream().filter(x -> StringUtils.equalsIgnoreCase(columnId, x.getId().getColumnId())).filter(x -> StringUtils.isNotBlank(x.getPColumnId()))
                    .map(x -> x.getPColumnId()).distinct().collect(Collectors.toMap(x -> x, c -> ""));
            map.put(columnId, cMap);
        });
        return map;
    } //getCasesetColumnChildPColumnIdMap

    /**
     * GeoKgoAppointmentColumnChild Group Map
     */
    private Map<String, Map<String, String>> getCasesetColumnChildVGroupMap(List<GeoKgoAppointmentColumnChild> list) {
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

        list.stream().map(column -> column.getId().getColumnId()).distinct().forEach(columnId -> {
            Map<String, String> cMap = list.stream().filter(x -> StringUtils.equalsIgnoreCase(columnId, x.getId().getColumnId())).filter(x -> StringUtils.isNotBlank(x.getVGroup()) && x.getMustKey())
                    .map(x -> x.getVGroup()).distinct().collect(Collectors.toMap(x -> x, c -> ""));
            map.put(columnId, cMap);
        });
        return map;
    } //getCasesetColumnChildVGroupMap

    /**
     * 儲存前驗證複合欄位
     */
    private void saveValidationComplex(GeoKgoAppointmentColumn appointmentColumn, GeoAppointmentOrderEditColumnData columnData, Map<GeoKgoAppointmentColumnChildPK, GeoKgoAppointmentColumnChild> columnChildMap,
                                       Map<String, String> pColumnMap, Map<String, String> vGroupMap) {
        Map<String, Map<String, String>> pColumnDetailsMap = new HashMap<>();
        for (List<SaveActionCColumnViewForm> complexList : columnData.getComplex()) {
            for (SaveActionCColumnViewForm complex : complexList) {
                if (StringUtils.isBlank(complex.getColumnId())) {
                    new ValidationViewForm(columnData.getColumnId(), appointmentColumn.getColumnName(), ErrorResult.getErrorDescI18n(KgoFrontEndApiError.INPUT_IS_EMPTY.getErrorMsgKey(), ""));
                    return;
                } //if (StringUtils.isBlank(complex.getColumnId())
                GeoKgoAppointmentColumnChildPK geoKgoAppointmentColumnChildPK = new GeoKgoAppointmentColumnChildPK();
                geoKgoAppointmentColumnChildPK.setAppointmentMainId(appointmentColumn.getId().getAppointmentMainId());
                geoKgoAppointmentColumnChildPK.setVersion(appointmentColumn.getId().getVersion());
                geoKgoAppointmentColumnChildPK.setColumnId(appointmentColumn.getId().getColumnId());
                geoKgoAppointmentColumnChildPK.setcColumnId(complex.getColumnId());
                geoKgoAppointmentColumnChildPK.setOrderNum(appointmentColumn.getOrderNum());

                if (columnChildMap.containsKey(geoKgoAppointmentColumnChildPK)) {
                    GeoKgoAppointmentColumnChild childColumn = columnChildMap.get(geoKgoAppointmentColumnChildPK);
                    if (ObjectUtils.isNotEmpty(childColumn)) {
                        if (ObjectUtils.isNotEmpty(pColumnMap)) {
                            if (pColumnMap.containsKey(complex.getColumnId())) {
                                if (StringUtils.isBlank(pColumnMap.get(complex.getColumnId()))) {
                                    pColumnMap.put(complex.getColumnId(), complex.getValue());
                                } // if (StringUtils.isBlan
                            } //if (pColumnMap.
                            if (pColumnMap.containsKey(childColumn.getPColumnId()) && StringUtils.isNoneBlank(childColumn.getPColumnId())) {
                                Map<String, String> map = new HashMap<>();
                                map.put(StringUtils.defaultIfBlank(childColumn.getFText(), childColumn.getBText()), complex.getValue());
                                pColumnDetailsMap.put(childColumn.getPColumnId(), map);
                            } // if (pColumnMap.containsKey(chil
                        } //if (ObjectUtils.isNotEmp
                        if (childColumn.getMustKey() && StringUtils.isBlank(childColumn.getVGroup()) && StringUtils.isBlank(complex.getValue())) {
                            ErrorResult.getErrorDescI18n(KgoFrontEndApiError.INPUT_IS_EMPTY.getErrorMsgKey());
                            return;
                        } //if (childColumn.getMustKey() && StringUti
                        if (ObjectUtils.isNotEmpty(vGroupMap)) {
                            if (vGroupMap.containsKey(childColumn.getVGroup())) {
                                if (StringUtils.isBlank(vGroupMap.get(childColumn.getVGroup()))) {
                                    vGroupMap.put(childColumn.getVGroup(), complex.getValue());
                                } // if (StringUtils.isBlank
                            } //if (vGroupMap.containsKey(
                        } //if (ObjectUtils.isNotEmpty(vGr
                    } // if (ObjectUtils.isNotEmpt
                } //if (columnChildMap.containsKey(
            } // for (SaveActionCColumnViewForm complex
        } //  for (List<SaveActionCColumnViewForm> complexList : columnData.getComplex()

        if (ObjectUtils.isNotEmpty(vGroupMap)) {
            for (Map.Entry<String, String> item : vGroupMap.entrySet()) {
                if (StringUtils.isBlank(item.getValue())) {
                    new ValidationViewForm(columnData.getColumnId(), appointmentColumn.getColumnName(), ErrorResult.getErrorDescI18n(KgoFrontEndApiError.INPUT_IS_EMPTY.getErrorMsgKey(), ""));
                    return;
                } //if (StringUtils.
            } // for (Map.Entry<String, String>
        } //if (ObjectUtils.isN

        if (ObjectUtils.isNotEmpty(pColumnMap)) {
            for (Map.Entry<String, String> item : pColumnMap.entrySet()) {
                if (StringUtils.isNoneBlank(item.getValue())) {
                    if (pColumnDetailsMap.containsKey(item.getKey())) {
                        for (Map.Entry<String, String> details : pColumnDetailsMap.get(item.getKey()).entrySet()) {
                            if (StringUtils.isBlank(details.getValue())) {
                                new ValidationViewForm(columnData.getColumnId(), details.getKey(), ErrorResult.getErrorDescI18n(KgoFrontEndApiError.INPUT_IS_EMPTY.getErrorMsgKey(), ""));
                                return;
                            } //if (StringUtils.is
                        } //for (Map.Entry<String, S
                    } //if (pColumnDetailsMap.containsKe
                } //if (StringUtils.isNoneBlan
            } // for (Map.Entry<String, String> item : p
        } //  if (ObjectUtils.isNotEmpty(
    } //saveValidationComplex

    /**
     * 根據不同columnId獲取值
     */
    private String getColumnViewForm(List<GeoAppointmentOrderEditColumnData> dataList, String columnId) {
        return getColumnViewForm(dataList, columnId, false);
    } //getColumnViewForm

    /**
     * 根據不同columnId獲取值
     */
    private String getColumnViewForm(List<GeoAppointmentOrderEditColumnData> dataList, String columnId, Boolean isThrowEx) {
        Optional<GeoAppointmentOrderEditColumnData> viewForm = dataList.stream().filter(x -> x.getColumnId().equalsIgnoreCase(columnId)).findFirst();
        if (viewForm.isPresent()) {
            if (viewForm.get().getColumnType().equalsIgnoreCase(ColumnTypeEnum.M.getValue())) {
                List<SaveActionCColumnViewForm> cColumns = viewForm.get().getComplex().get(0);
                Optional<SaveActionCColumnViewForm> cColumn = cColumns.stream().findFirst();
                if (cColumn.isPresent()) {
                    return cColumn.get().getValue();
                } else {
                    if (isThrowEx) {
                        throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
                    } else {
                        return null;
                    } //if (isThrow
                } //if (cColumn.isPresent())
            } else {
                return viewForm.get().getValue();
            } //if (viewForm.get().getColumn
        } else {
            if (isThrowEx) {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
            } else {
                return null;
            } // if (isThrowEx)
        } //if (viewForm.isPresent()
    } //getColumnViewForm

    /**
     * 新增AppointmentFormDetail
     */
    private void saveAppointmentFormDetail(GeoAppointmentOrderEditRq rq, GeoKgoAppointment appointment) throws ParseException {

        for (GeoAppointmentOrderEditColumnData columnData : rq.getColumnDataList()) {
            GeoKgoAppointmentFormDetail GeoKgoAppointmentFormDetail = new GeoKgoAppointmentFormDetail();

            GeoKgoAppointmentFormDetailPK id = new GeoKgoAppointmentFormDetailPK();
            id.setAppointmentId(appointment.getAppointmentId());
            id.setColumnId(columnData.getColumnId());
            id.setCColumnId("");
            id.setVersion(rq.getVersion());

            GeoKgoAppointmentFormDetail.setId(id);
            GeoKgoAppointmentFormDetail.setColumnValue(columnData.getValue());

            geoKgoAppointmentFormDetailRepository.save(GeoKgoAppointmentFormDetail);

            savaAppointmentDetail(appointment.getAppointmentId(), rq.getVersion(), columnData.getColumnId(), columnData.getComplex());

            if (ObjectUtils.isNotEmpty(columnData.getCasePdf()) && StringUtils.isNoneBlank(columnData.getCasePdf().getFileBase64Str())) {
                columnData.getCasePdf().setFileName(String.format("%s_%s.pdf", appointment.getAppointmentId(), appointment.getAppointmentName()));
                columnData.getFiles().add(columnData.getCasePdf());
            } // if (ObjectUtils.isNotEmpty(co

            saveUploadFile(appointment.getAppointmentId(), columnData.getFiles());
        } //for (GeoAppointmentOrderEditColumnDat
    } //saveKgoCaseDetail

    /**
     * 新增AppointmentFormDetail
     */
    private void savaAppointmentDetail(String appointmentId, Integer version, String columnId, List<List<SaveActionCColumnViewForm>> complexViewForm) throws ParseException {
        if (ObjectUtils.isNotEmpty(complexViewForm)) {
            if (complexViewForm.size() > 0) {
                for (List<SaveActionCColumnViewForm> complexList : complexViewForm) {
                    for (SaveActionCColumnViewForm complex : complexList) {
                        GeoKgoAppointmentFormDetail geoKgoAppointmentFormDetail = new GeoKgoAppointmentFormDetail();

                        GeoKgoAppointmentFormDetailPK id = new GeoKgoAppointmentFormDetailPK();
                        id.setAppointmentId(appointmentId);
                        id.setColumnId(columnId);
                        id.setCColumnId(complex.getColumnId());
                        id.setVersion(version);

                        geoKgoAppointmentFormDetail.setId(id);
                        geoKgoAppointmentFormDetail.setColumnValue(complex.getValue());

                        geoKgoAppointmentFormDetailRepository.save(geoKgoAppointmentFormDetail);
                    } // for (SaveActionCColumnViewForm
                } //for (List<SaveActionCColumnViewForm> c
            } // if (complexViewForm.size() > 0
        } // if (ObjectUtils.isNotEmpty(complexViewFor
    } //savaAppointmentDetail

    /**
     * 儲存實體附件
     */
    private void saveUploadFile(String appointmentId, List<SaveFileViewForm> files) {
        if (files == null) {
            return;
        } //if (files ==

        File fileFolder = new File(KgoUtil.getCaseDownloadUploadBasePath(appointmentId));
        for (SaveFileViewForm form : files) {
            saveFile(fileFolder, form.getFileBase64Str(), form.getFileName());
        } // for (SaveFileViewForm
    } //saveUploadFile

    /**
     * 儲存實體附件
     */
    private void saveFile(File fileFolder, String fileBase64Str, String fileName) {
        try {
            if (StringUtils.isNoneBlank(fileBase64Str)) {
                String base64Str = fileBase64Str;

                String[] dataString = fileBase64Str.split(",");
                if (dataString.length > 1) {
                    base64Str = dataString[1];
                }
                byte[] decoder = Base64.getDecoder().decode(base64Str);
                FileUtil.createFile(fileFolder, fileName, decoder);
            } // if (StringUtils.isNoneBlan
        } catch (Exception e) {
            LOGGER.error("\n >>>>>>>saveFile>>> " + e.getMessage(), e);
        } //try catch
    } //saveFile


    /**
     * 設定 帶預約者資訊
     *
     * @param viewForm
     */
    private void setDefaultColumnValue(GeoAppointmentColumnViewForm viewForm,  List<GeoKgoAppointmentFormDetail> appointmentFormDetails) {
        if (ObjectUtils.isNotEmpty(appointmentFormDetails)) {
            for (GeoKgoAppointmentFormDetail appointmentFormDetail:appointmentFormDetails){
                    if (viewForm.getColumnId().equalsIgnoreCase(appointmentFormDetail.getId().getColumnId())){
                        viewForm.setColumnValue(StringUtils.defaultString(appointmentFormDetail.getColumnValue(), ""));
                    } //if (viewForm.getColumnId().equalsIgnoreCase("ID")
                } //for (GeoKgoAppointmentFormDetail...
            } // if (ObjectUtils.isNotEmpty(appointmentFormDetails))
    } //setDefaultColumnValue


    /**
     * 設定 預設帶登入者資訊
     *
     * @param viewForm
     * @param loginUserInfo
     */
    private void setDefaultColumnValue(GeoAppointmentColumnViewForm viewForm, FrontendLoginUserInfo loginUserInfo) {

        if (ObjectUtils.isNotEmpty(loginUserInfo)) {
            String loginType = loginUserInfo.getLoginAuthTokenType().getType();
            if (loginType.equals(LoginAuthTokenType.MOICA.getType()) || loginType.equals(LoginAuthTokenType.TW_FIDO.getType())) {
                if (viewForm.getColumnId().equalsIgnoreCase("Name")) {
                    viewForm.setColumnValue(loginUserInfo.getName());
                    viewForm.setIsReadonly(StringUtils.isNoneBlank(viewForm.getColumnValue()));
                } // if (viewForm.getColumnId().equalsIgnoreCase("Name"))
                if (viewForm.getColumnId().equalsIgnoreCase("ID")){
                    String id = loginUserInfo.getTwSSn() ;
                    viewForm.setColumnValue( id );
                    viewForm.setIsReadonly((ObjectUtils.isNotEmpty(loginUserInfo.getKcgMoicaCardInfo())) && id.length() == 10 );
                }

            } else if (loginType.equals(LoginAuthTokenType.HCA.getType())) {
                if (viewForm.getColumnId().equalsIgnoreCase("ID")) {
                    viewForm.setColumnValue(loginUserInfo.getTwSSn());
                    viewForm.setIsReadonly(StringUtils.isNoneBlank(viewForm.getColumnValue()));
                } //if (viewForm.getColumnId().equalsIgnoreCase("ID"))
                if (viewForm.getColumnId().equalsIgnoreCase("Name")) {
                    viewForm.setColumnValue(loginUserInfo.getName());
                    viewForm.setIsReadonly(StringUtils.isNoneBlank(viewForm.getColumnValue()));
                } //if (viewForm.getColumnId().equalsIgnoreCase("Name"))

            }else if (loginType.equals(LoginAuthTokenType.TW_FIDO.getType())){
                if (viewForm.getColumnId().equalsIgnoreCase("ID")) {
                    viewForm.setColumnValue(loginUserInfo.getTwSSn());
                    viewForm.setIsReadonly(StringUtils.isNoneBlank(viewForm.getColumnValue()));
                } //if (viewForm.getColumnId().equalsIgnoreCase("ID"))
            }else if (loginType.equals(LoginAuthTokenType.FACEBOOK.getType()) || loginType.equals(LoginAuthTokenType.LINE.getType())
                    || loginType.equals(LoginAuthTokenType.GOOGLE.getType())){
                    if (viewForm.getColumnId().equalsIgnoreCase("Name")) {
                        viewForm.setColumnValue(loginUserInfo.getName());
                        viewForm.setIsReadonly(StringUtils.isNoneBlank(viewForm.getColumnValue()));
                    } //if (viewForm.getColumnId().equalsIgnoreCase("Name"))
                    if (viewForm.getColumnId().equalsIgnoreCase("Email")) {
                        viewForm.setColumnValue(loginUserInfo.getEmail());
                        viewForm.setIsReadonly(StringUtils.isNoneBlank(viewForm.getColumnValue()));
                    } //if (viewForm.getColumnId().equalsIgnoreCase("Email"))
                } //else if ...
            } // if (ObjectUtils.isNotEmpty(loginUserInfo)
    } //setDefaultColumnValue

    private String getValidateInfo(FrontendLoginUserInfo loginUserInfo) {
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
    }

    /**
     * 取得登入資訊
     *
     * @return
     */
    private FrontendLoginUserInfo getLoginUserInfo() {
        try {
            return getFrontendLoginUser();
        } catch (Exception e) {
            return null;
        }
    } //getLoginUserInfo

    /**
     * 取得前台臺 登入使用者資訊.
     *
     * @return the back end login user
     */
    public static FrontendLoginUserInfo getFrontendLoginUser() {
        LOGGER.info("getFrontendLoginUser...");
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            FrontendLoginUserInfo userInfo = (FrontendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.FRONTEND_USER_INO_KEY);
            if (userInfo == null) {
                // 使用者未登入.
                LOGGER.info("getFrontendLoginUser 使用者未登入");
//                throw new KgoApiException(new ErrorResult(KgoCommonApiError.USER_NOT_LOGIN));
            }
            return userInfo;
        } catch (Exception e) {
            // 使用者未登入.
            LOGGER.info("getFrontendLoginUser" + e.getMessage(), e);
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.USER_NOT_LOGIN));
        }
    } //getFrontendLoginUser

    /**
     * 後台-線上預約臨櫃-預約登錄管理:編輯取得預約者表單資料
     */
    public GeoAppointmentOrderFormQueryRs getAppointmentIfoForm(GeoAppointmentInfoFormQueryRq rq, SystemTypeEnum systemTypeEnum) {
        GeoAppointmentOrderFormQueryRs rs = new GeoAppointmentOrderFormQueryRs();
        GeoAppointmentOrderFormQueryViewForm viewForm = new GeoAppointmentOrderFormQueryViewForm();
        try {
            String appointmentId = rq.getAppointmentId();
            GeoKgoAppointment geoKgoAppointmentMain =geoKgoAppointmentRepository.findByAppointmentId(appointmentId);

            //是否有該筆預約資料
            if (ObjectUtils.isEmpty(geoKgoAppointmentMain)){
                rs.setError(new ErrorResult(KgoFrontEndApiError.DATA_NOT_EXIST));
                return rs;
            } //if (ObjectUtils.isEmpty(geoKgoAppointmentMain) |

            List<GeoAppointmentGroupQueryDataMaxVersionDto> dtoList = geoKgoAppointmentReposCustom.findMaxVersionGroupData(geoKgoAppointmentMain.getAppointmentMainId(), StringUtils.EMPTY);
            Integer version = ObjectUtils.isEmpty(dtoList) ? KgoUtil.DEFAULT_VERSION_NUMBER : dtoList.get(0).getVersion();
            viewForm.setVersion(version);

            //取得預約者填寫資料
            List<OptionViewForm> options = new ArrayList<OptionViewForm>();
            viewForm.setModelList(getGroupViewForms(geoKgoAppointmentMain.getAppointmentMainId(), options,appointmentId));
            viewForm.setOptions(options);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("getAppointmentOrderForm error " + e.getMessage(), e);
        }
        return rs;
    } //getAppointmentOrderForm
}
