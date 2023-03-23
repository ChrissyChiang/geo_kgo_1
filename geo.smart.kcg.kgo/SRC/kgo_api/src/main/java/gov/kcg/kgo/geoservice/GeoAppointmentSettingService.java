package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.enums.backend.*;
import gov.kcg.kgo.enums.common.ColumnTypeEnum;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.*;
import gov.kcg.kgo.geoenum.GeoAppointmentColumnTypeEnum;
import gov.kcg.kgo.geoenum.GeoAppointmentMainStatusType;
import gov.kcg.kgo.geoenum.GeoBooleanType;
import gov.kcg.kgo.geoenum.GeoDistrictOfficeType;
import gov.kcg.kgo.geomodel.*;
import gov.kcg.kgo.geomodel.dto.GeoAppointmentGroupQueryDataMaxVersionDto;
import gov.kcg.kgo.georepository.*;
import gov.kcg.kgo.georepository.custom.GeoKgoAppointmentMainReposCustom;
import gov.kcg.kgo.georepository.custom.GeoKgoAppointmentReposCustom;
import gov.kcg.kgo.geoutil.GeoStringUtil;
import gov.kcg.kgo.geoviewmodel.backend.rq.*;
import gov.kcg.kgo.geoviewmodel.backend.rq.data.GeoAppointColumnData;
import gov.kcg.kgo.geoviewmodel.backend.rq.data.GeoAppointGroupColumnData;
import gov.kcg.kgo.geoviewmodel.backend.rs.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.*;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.KgoCasesetCheckRepository;
import gov.kcg.kgo.repository.KgoCasesetColumnChildTemplateRepository;
import gov.kcg.kgo.repository.KgoCasesetColumnTemplateRepository;
import gov.kcg.kgo.repository.KgoCasesetTemplateRepository;
import gov.kcg.kgo.service.impl.helper.CommonServiceHelper;
import gov.kcg.kgo.service.impl.helper.InternetApplyServiceHelper;
import gov.kcg.kgo.service.impl.helper.OrganUnitManagementServiceHelper;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetComplexColumnData;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAppointmentFormQueryRq;
import gov.kcg.kgo.viewModel.backend.internetApply.identityVerify.home.rs.InternetApplyIdentityVerifyHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.identityVerify.home.rs.bean.InternetApplyIdentityVerifyHomeViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.ColumnViewForm;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static gov.kcg.kgo.enums.common.ColumnTypeEnum.FIL;
import static gov.kcg.kgo.util.DateUtil.PATTEN_YEAR_MONTH_DAY;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃 API Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GeoAppointmentSettingService extends GeoBaseService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoAppointmentSettingService.class);

    private final Integer WEEK_DAY = 7;

    private OrganUnitManagementServiceHelper organUnitManagementServiceHelper = OrganUnitManagementServiceHelper
            .getInstance();

    private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();

    @Autowired
    private GeoKgoAppointmentReposCustom geoKgoAppointmentReposCustom;

    @Autowired
    GeoKgoAppointmentMainRepository mainRepository;

    @Autowired
    GeoKgoAppointmentContactUserRepository contactUserRepository;

    @Autowired
    GeoKgoAppointmentBlockUserRepository blockUserRepository;

    @Autowired
    GeoKgoAppointmentDetailRepository detailRepository;

    @Autowired
    GeoKgoAppointmentDetailTimeRepository detailTimeRepository;

    @Autowired
    GeoKgoAppointmentDetailNumbersRepository detailNumbersRepository;

    @Autowired
    GeoKgoAppointmentMainReposCustom geoKgoAppointmentMainReposCustom;

    @Autowired
    KgoCasesetTemplateRepository kgoCasesetTemplateRepository;

    @Autowired
    KgoCasesetColumnTemplateRepository kgoCasesetColumnTemplateRepository;

    @Autowired
    KgoCasesetColumnChildTemplateRepository kgoCasesetColumnChildTemplateRepository;

    @Autowired
    GeoKgoAppointmentColumnChildRepository geoKgoAppointmentColumnChildRepository;

    @Autowired
    GeoKgoAppointmentColumnRepository geoKgoAppointmentColumnRepository;

    @Autowired
    GeoCityExtService geoKcgCityExtService;

    @Autowired
    GeoKgoAppointmentRepository geoKgoAppointmentRepository;

    @Autowired
    InternetApplyServiceHelper internetApplyServiceHelper;

    @Autowired
    GeoKgoAppointmentCheckRepository geoKgoAppointmentCheckRepository;

    private static final String IS_SERVICE_HTML_LABEL = "啟用服務宣告";
    private static final String IS_SERVICE_HTML_VALUE = "T";

    /**
     * 機關科室管理-初始畫面
     */
    public GeoAppointmentHomeRs appointmentSettingHome() {
        GeoAppointmentHomeViewForm viewForm = new GeoAppointmentHomeViewForm();
        GeoAppointmentHomeRs rs = new GeoAppointmentHomeRs();

        try {
            BackendLoginUserInfo loginUser = KgoUtil.getBackendLoginUser();

            // 機關下拉式選單
            ComboBox organComboBox = organUnitManagementServiceHelper
                    .getOrganComboBoxWithUserRoleLimit(loginUser.getUserId());

            // 科室下拉式選單
            ComboBox unitComboBox = organUnitManagementServiceHelper
                    .getUnitComboBoxByOrganId(organComboBox.getSelectedVal(), null, ComboBoxStatusEnum.ALL.getCode());

            viewForm.setOrganComboBox(organComboBox);
            viewForm.setUnitComboBox(unitComboBox);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("OrganUnitManagementUpdateHome error " + e.getMessage(), e);
        }

        return rs;
    } //appointmentSettingHome

    /**
     * 新增編輯預約主檔
     *
     * @param rq       the rq
     * @param userInfo the userInfo
     */
    public GeoAppointmentMainInsertRs editAppointmentMain(GeoAppointmentMainInsertRq rq, BackendLoginUserInfo userInfo) {
        GeoAppointmentMainInsertRs rs = new GeoAppointmentMainInsertRs();
        GeoAppointmentMainInsertViewForm viewForm = new GeoAppointmentMainInsertViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            GeoKgoAppointmentMain entity = editAppointmentMain(rq.getAppointmentMain(), userInfo);
            GeoKgoAppointmentMainModel model = GeoKgoAppointmentMainModel.changeToModel(entity);
            viewForm.setAppointmentMain(model);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //editAppointmentMain

    private GeoKgoAppointmentMain editAppointmentMain(GeoKgoAppointmentMainModel main, BackendLoginUserInfo userInfo) {
        GeoKgoAppointmentMain entity = null;
        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (StringUtils.isBlank(main.getAppointmentName()) ||
                StringUtils.isBlank(main.getOrganId()) ||
                StringUtils.isBlank(main.getUnitId())) {
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
        } //if (StringUtils.isBlank(main.getAppointmentName())...

        if (!StringUtils.isEmpty(main.getAppointmentMainId())) {
            //編輯
            entity = mainRepository.findByAppointmentMainId(main.getAppointmentMainId());
            if (entity == null) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_EDIT));
            } //if (entity == null)
            entity.setAppointmentStatus(main.getAppointmentStatus());
        } else {
            //新增
            entity = new GeoKgoAppointmentMain();
            entity.setAppointmentMainId(geoKgoAppointmentReposCustom.getNextTableId(
                    GeoStringUtil.APPOINTMENT_MAIN_ID_PREFIX_CHAR, "GEO_KGO_APPOINTMENT_MAIN", "appointment_main_id"));
            entity.setAppointmentStatus(GeoAppointmentMainStatusType.OFF.getLabel());
        } //if (!StringUtils.isEmpty(main.getAppointmentMainId()))
        entity.setAppointmentName(main.getAppointmentName());
        entity.setUnitId(main.getUnitId());
        entity.setOrganId(main.getOrganId());
        entity.setMydataUrl(main.getMyDataUrl());
        entity.setEditOrgan(userInfo.getOrgan());
        entity.setEditUser(userInfo.getUserId());
        entity.setEditTime(now);
        entity.setServiceHtml(main.getServiceHtml());
        entity.setServiceHtmlContent(main.getServiceHtmlContent());

        entity = mainRepository.save(entity);
        String checkTypeStr = main.getCheckType();
        String appointmentMainId = entity.getAppointmentMainId();

        if (StringUtils.isNotBlank(checkTypeStr)) {
            geoKgoAppointmentCheckRepository.deleteByIdAppointmentMainId(appointmentMainId);
            List<GeoKgoAppointmentCheck> entityList = Arrays.asList(checkTypeStr.split(",")).stream().map(s -> {
                GeoKgoAppointmentCheckPK id = new GeoKgoAppointmentCheckPK();
                id.setAppointmentMainId(appointmentMainId);
                id.setCheckType(s);
                GeoKgoAppointmentCheck dto = new GeoKgoAppointmentCheck();
                dto.setId(id);
                return dto;
            }).collect(Collectors.toList());
            geoKgoAppointmentCheckRepository.saveAllBatch(entityList);
        } //if (StringUtils.isNotBlank(checkTypeStr))

        LOGGER.info("insertAppointmentMain entity: " + entity);
        return entity;
    } //insertAppointmentMain

    /**
     * 後台-線上預約臨櫃:承辦人帳號搜尋
     *
     * @param rq
     * @return
     */
    public GeoAppointmentContactUserQueryRs getContactUserList(GeoAppointmentContactUserQueryRq rq) {
        GeoAppointmentContactUserQueryRs rs = new GeoAppointmentContactUserQueryRs();
        GeoAppointmentContactUserQueryViewForm viewForm = new GeoAppointmentContactUserQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            List<GeoKgoAppointmentContactUserModel> modelList = geoKgoAppointmentReposCustom.findAccountQueryData(
                    rq.getOrganId(), rq.getUnitId(), rq.getName());
            if (modelList != null) {
//                LOGGER.info("getContactUserList modelList.size() : " +modelList.size());
                List<GeoKgoAppointmentContactUser> entityList =
                        contactUserRepository.findAllByAppointmentMainId(rq.getAppointmentMainId());
                for (GeoKgoAppointmentContactUserModel m : modelList) {
                    String appointmentMainId = StringUtils.EMPTY;
                    if (entityList != null && entityList.size() > 0) {
                        for (GeoKgoAppointmentContactUser entity : entityList) {
                            if (entity.getUserId().equals(m.getUserId())) {
                                appointmentMainId = entity.getAppointmentMainId();
                                m.setAppointmentMainId(appointmentMainId);
                                entityList.remove(entity);
                                break;
                            }
                        } //for (GeoKgoAppointmentContactUser...
                    } //if (entityList != null && entityList.size() > 0 )
                    m.setAppointmentMainId(appointmentMainId);
                } //for (GeoKgoAppointmentContactUserModel...
            } // if (modelList != null)
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
     * 後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單
     *
     * @param rq
     * @return
     */
    public GeoAppointmentMainSearchRs getAppointmentMainList(GeoAppointmentMainSearchRq rq) {
        GeoAppointmentMainSearchRs rs = new GeoAppointmentMainSearchRs();
        GeoAppointmentMainSearchViewForm viewForm = new GeoAppointmentMainSearchViewForm();
        List<GeoKgoAppointmentMainQueryModel> modelList = new ArrayList<>();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (StringUtils.isBlank(rq.getOrganId())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            modelList = geoKgoAppointmentReposCustom.getAppointMainListByKeyword(GeoAppointmentMainStatusType.DELETE.getLabel(), rq.getOrganId(), rq.getUnitId(), rq.getAppointmentName());
            if (modelList != null) {
                for (GeoKgoAppointmentMainQueryModel m : modelList) {
                    List<GeoKgoAppointmentContactUser> entityList = contactUserRepository.findAllByAppointmentMainId(m.getAppointmentMainId());
                    if (entityList != null) {
                        m.setContactUserList(GeoKgoAppointmentContactUserModel.changeListToModel(entityList));
                    } //if (entityList != null)
                    /** GEO 20211230 add for 已填寫預約單不可刪除 **/
                    Boolean isExist = geoKgoAppointmentRepository.existsGeoKgoAppointmentByAppointmentMainId(m.getAppointmentMainId());
                    if (isExist) {
                        m.setAllowDelete(false);
                    } else {
                        m.setAllowDelete(true);
                    } //if (isExist
                } //for (GeoKgoAppointmentMainQueryModel
            } // if (modelList != null)
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
     * 後台-線上預約臨櫃:新增黑名單
     *
     * @param rq
     * @return
     */
    public GeoAppointmentBlockUserEditRs editAppointmentBlockUser(GeoAppointmentBlockUserEditRq rq, BackendLoginUserInfo userInfo) {
        GeoAppointmentBlockUserEditRs rs = new GeoAppointmentBlockUserEditRs();
        GeoAppointmentBlockUserEditViewForm viewForm = new GeoAppointmentBlockUserEditViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (StringUtils.isBlank(rq.getBlockUserName()) || StringUtils.isBlank(rq.getBlockUserIdentity())
                    || StringUtils.isBlank(rq.getAppointmentMainId()) || rq.getIsTriggerBlock() == null) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            GeoKgoAppointmentBlockUser entity = null;
            Timestamp now = new Timestamp(System.currentTimeMillis());
            entity = blockUserRepository.findByAppointmentMainIdAndIdentity(rq.getAppointmentMainId(), rq.getBlockUserIdentity());
            LOGGER.info("editAppointmentBlockUser entity="+entity);
            if (entity == null) {
                entity = new GeoKgoAppointmentBlockUser();
                entity.setBlockUserId(geoKgoAppointmentReposCustom.getNextTableId(
                        GeoStringUtil.APPOINTMENT_BLOCK_USER_ID_PREFIX_CHAR, "GEO_KGO_APPOINTMENT_BLOCK_USER", "block_user_id"));
            }
            entity.setAppointmentMainId(rq.getAppointmentMainId());
            entity.setBlockUseName(rq.getBlockUserName());
            entity.setBlockUserIdentity(rq.getBlockUserIdentity());
            entity.setBlockStartTime(DateUtil.strToTimestamp(rq.getBlockStartTime(), PATTEN_YEAR_MONTH_DAY));
            entity.setBlockEndTime(DateUtil.strToTimestamp(rq.getBlockEndTime(), PATTEN_YEAR_MONTH_DAY));
            entity.setIsTriggerBlock(rq.getIsTriggerBlock());
            entity.setEditOrgan(userInfo.getOrgan());
            entity.setEditUser(userInfo.getUserId());
            entity.setEditTime(now);
            entity = blockUserRepository.save(entity);
            GeoKgoAppointmentBlockUserModel model = GeoKgoAppointmentBlockUserModel.changeToModel(entity);
            viewForm.setBlockUser(model);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE), e);
            if (rq.getIsTriggerBlock() == GeoBooleanType.DISABLED.getCode()) {
                error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_DELETE), e);
            }
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //editAppointmentBlockUser

    /**
     * 後台-線上預約臨櫃:刪除黑名單
     *
     * @param rq
     * @return
     */
    public GeoAppointmentBlockUserDeleteRs deleteAppointmentBlockUser(GeoAppointmentBlockUserDeleteRq rq, BackendLoginUserInfo userInfo) {
        GeoAppointmentBlockUserDeleteRs rs = new GeoAppointmentBlockUserDeleteRs();
        GeoAppointmentBlockUserDeleteViewForm viewForm = new GeoAppointmentBlockUserDeleteViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            GeoKgoAppointmentBlockUser entity = blockUserRepository.findByBlockUserId(rq.getBlockUserId());
            if (entity == null) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_DELETE));
            }
            entity.setIsTriggerBlock(GeoBooleanType.DISABLED.getCode());
            entity.setEditOrgan(userInfo.getOrgan());
            entity.setEditUser(userInfo.getUserId());
            entity.setEditTime(now);
            blockUserRepository.save(entity);
            viewForm.setMsg(SuccessMsg.DELETE.getMsg());
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_DELETE), e);
        } finally {
            setResultMessage(rq, rs, error); // 設置 成功/失敗訊息.
        } //try
        return rs;
    } //editQuestionnaireCaseSet

    /**
     * 後台-線上預約臨櫃:儲存預約主檔、細節
     *
     * @param rq
     * @return
     */
    public GeoAppointmentInfoInsertRs editAppointmentInfo(GeoAppointmentInfoInsertRq rq, BackendLoginUserInfo userInfo) {
        GeoAppointmentInfoInsertRs rs = new GeoAppointmentInfoInsertRs();
        GeoAppointmentInfoInsertViewForm viewForm = new GeoAppointmentInfoInsertViewForm();
        List<GeoKgoAppointmentContactUserInsertModel> contactUserList = new ArrayList<>();
        List<GeoKgoAppointmentDetailInsertModel> detailList = new ArrayList<>();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            GeoKgoAppointmentMain entity = editAppointmentMain(rq.getAppointmentMain(), userInfo);
            GeoKgoAppointmentMainModel kgoAppointmentMainModel = GeoKgoAppointmentMainModel.changeToModel(entity);
            viewForm.setAppointmentMain(kgoAppointmentMainModel);

            if (rq.getContactUserList() != null) {
                List<GeoKgoAppointmentContactUser> entityList = contactUserRepository.findAllByAppointmentMainId(kgoAppointmentMainModel.getAppointmentMainId());
                if (entityList != null) contactUserRepository.deleteAll(entityList);
                for (GeoKgoAppointmentContactUserInsertModel contactUser : rq.getContactUserList()) {
                    GeoKgoAppointmentContactUser contactUserEntity = insertContactUser(kgoAppointmentMainModel.getAppointmentMainId(), contactUser, userInfo);
                    contactUserList.add(GeoKgoAppointmentContactUserInsertModel.changeToModel(contactUserEntity));
                } //for (GeoKgoAppointmentContactUserInsertModel contactUser
            } //if (rq.getContactUserList() != null)
            viewForm.setContactUserList(contactUserList);

            //從資料庫拿出已設定的舊時段
            List<GeoKgoAppointmentDetailTimeInsertModel> oldDetailTimes = new ArrayList<>();
            List<GeoKgoAppointmentDetailTimeInsertModel> totalDataTimes = new ArrayList<>();
            List<GeoKgoAppointmentDetailTimeInsertModel> newDetailTimes = new ArrayList<>();
            String firstDay = rq.getDetailList().get(0).getAppointmentDetailDate(); //抓第一天
            String lastDay  = rq.getDetailList().get(rq.getDetailList().size()-1).getAppointmentDetailDate(); //抓最後一天
            for (GeoKgoAppointmentDetailInsertModel detail:rq.getDetailList()){
                //資料庫拿出舊的時段
                oldDetailTimes = geoKgoAppointmentMainReposCustom.findSetTimeListRangeInfo(firstDay,lastDay,detail.getAppointmentDetailId());
                for (GeoKgoAppointmentDetailTimeInsertModel old:oldDetailTimes){
                    totalDataTimes.add(old);
                }
                //拿出這次rq異動的時段
                for (GeoKgoAppointmentDetailTimeInsertModel model:detail.getDetailTimeList()){
                    newDetailTimes.add(model);
                } //for (GeoKgoAppointmentDetailTimeInsertModel
            } //GeoKgoAppointmentDetailInsertModel

            //比對舊時段和新時段，如果舊時段不再新時段內就刪除。
            if (totalDataTimes.size()>0){
                for (GeoKgoAppointmentDetailTimeInsertModel oldModel:totalDataTimes){
                    Boolean isExit = false;
                    for (GeoKgoAppointmentDetailTimeInsertModel newModel:newDetailTimes){
                        LOGGER.info("oldModel.getAppointmentDetailTimeId()="+oldModel.getAppointmentDetailTimeId());
                        LOGGER.info("oldModel.getAppointmentDetailTimeId()="+newModel.getAppointmentDetailTimeId());
                        if (oldModel.getAppointmentDetailTimeId().equals(newModel.getAppointmentDetailTimeId())){
                            isExit =true;
                            break;
                        } //if (oldModel.getAppointmentDetailTimeId()...
                        LOGGER.info("內 isExit="+isExit);
                    } //for (GeoKgoAppointmentDetailTimeInsertModel newModel...
                    LOGGER.info("外 isExit="+isExit);
                    if (!isExit){
                        LOGGER.info("刪除 oldModel.getAppointmentDetailTimeId()="+oldModel.getAppointmentDetailTimeId());
                        detailNumbersRepository.deleteByAppointmentDetailTimeId(oldModel.getAppointmentDetailTimeId());
                        detailTimeRepository.deleteByAppointmentDetailTimeId(oldModel.getAppointmentDetailTimeId());
                    } //if (!isExit)
                } //for (GeoKgoAppointmentDetailTimeInsertModel oldModel:oldDetailTimes)
            } //if (oldDetailTimes.size()>0)

            //一次送一個禮拜的時間，拿出每天的線上預約臨櫃細節
            for (GeoKgoAppointmentDetailInsertModel detail : rq.getDetailList()) {
                GeoKgoAppointmentDetail detailEntity = editAppointmentDetail(kgoAppointmentMainModel.getAppointmentMainId(), detail, userInfo);
                GeoKgoAppointmentDetailInsertModel detailModel = GeoKgoAppointmentDetailInsertModel.changeToModel(detailEntity);
                List<GeoKgoAppointmentDetailTimeInsertModel> detailTimeList = new ArrayList<>();
                detailModel.setDetailTimeList(detailTimeList);
                if (detail.getDetailTimeList() != null && detail.getDetailTimeList().size() > 0) {
                    for (GeoKgoAppointmentDetailTimeInsertModel detailTime : detail.getDetailTimeList()) {
                        GeoKgoAppointmentDetailTime detailTimeEntity = editAppointmentDetailTime(detailModel.getAppointmentDetailId(), detailTime);
                        GeoKgoAppointmentDetailTimeInsertModel detailTimeModel = GeoKgoAppointmentDetailTimeInsertModel.changeToModel(detailTimeEntity);
                        List<GeoKgoAppointmentDetailNumbersInsertModel> detailNumbersList = new ArrayList<>();
                        detailTimeModel.setDetailNumbersList(detailNumbersList);
                        List<GeoKgoAppointmentDetailNumbersInsertModel> numberList = detailTime.getDetailNumbersList();
                        if (numberList != null) {
                            // 20221216 GEO 修正預約人數與號碼牌設定數量不合問題
                            if( detailTime.getAvailableUserQuota() != numberList.size() ) {
                                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.APPOINTMENT_QOUTA_SETTING_ERROR));
                            }
                            //找出重新設定後刪除的號碼牌,已預約拋出不能變更的錯誤,否則刪除舊號碼牌
                            Map<String, String> newNumberIdList = numberList.stream()
                                    .filter(model->StringUtils.isNotBlank(model.getAppointmentDetailNumbersId()))
                                    .collect(Collectors.toMap(GeoKgoAppointmentDetailNumbersInsertModel::getAppointmentDetailNumbersId, GeoKgoAppointmentDetailNumbersInsertModel::getNumberName));
                            List<GeoKgoAppointmentDetailNumbers> oldNumbers = detailNumbersRepository.findAllByAppointmentDetailTimeId(detailTime.getAppointmentDetailTimeId());
                            oldNumbers.stream().filter(old-> !newNumberIdList.containsKey(old.getAppointmentDetailNumbersId()))
                                               .forEach(rest->{
                                                    if(geoKgoAppointmentRepository.existsByAppointmentDetailNumbersId(rest.getAppointmentDetailNumbersId())){
                                                        LOGGER.info("numbersId: "+rest.getAppointmentDetailNumbersId()+"has appointed ");
                                                        throw new KgoApiException(new ErrorResult(KgoBackEndApiError.APPOINTMENT_NUMBERS_EXSIST_ERROR));
                                                    }else{
                                                        detailNumbersRepository.delete(rest);
                                                    }
                                               });
                            for (GeoKgoAppointmentDetailNumbersInsertModel numbers : numberList) {
                                GeoKgoAppointmentDetailNumbers detailNumbersEntity = editAppointmentDetailNumbers(detailTimeModel.getAppointmentDetailTimeId(), numbers);
                                if (detailNumbersEntity != null) {
                                    GeoKgoAppointmentDetailNumbersInsertModel detailNumbersModel = GeoKgoAppointmentDetailNumbersInsertModel.changeToModel(detailNumbersEntity);
                                    detailNumbersList.add(detailNumbersModel);
                                } //if (detailNumbersEntity!=null)
                            } // for (GeoKgoAppointmentDetailNumbersInsertModel...
                        } //if (detailTime.getDetailNumbersList() != null)
                        detailTimeList.add(detailTimeModel);
                    } //for (GeoKgoAppointmentDetailTimeInsertModel...
                } //if (detail.getDetailTimeList() != null)
                detailList.add(detailModel);
            } //for (GeoKgoAppointmentDetailInsertModel...
            viewForm.setDetailList(detailList);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //editAppointmentInfo

    private GeoKgoAppointmentContactUser insertContactUser(
            String appointmentMainId, GeoKgoAppointmentContactUserInsertModel model, BackendLoginUserInfo userInfo) {
        GeoKgoAppointmentContactUser entity = new GeoKgoAppointmentContactUser();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (StringUtils.isBlank(appointmentMainId)) {
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
        }
        entity.setUserId(model.getUserId());
        entity.setAppointmentMainId(appointmentMainId);
        entity.setOrganId(model.getOrganId());
        entity.setUnitId(model.getUnitId());
        entity.setUserName(model.getName());
//        entity.setEditOrgan(userInfo.getOrgan());
//        entity.setEditUser(userInfo.getUserId());
        entity.setEditTime(now);
        entity = contactUserRepository.save(entity);
        return entity;
    } //insertContactUser

    private GeoKgoAppointmentDetail editAppointmentDetail(
            String appointmentMainId, GeoKgoAppointmentDetailInsertModel model, BackendLoginUserInfo userInfo) throws Exception {
        GeoKgoAppointmentDetail entity = null;
        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (!StringUtils.isEmpty(model.getAppointmentDetailId())) {
            LOGGER.info("editAppointmentDetail 編輯線上預約臨櫃");
            //編輯
            entity = detailRepository.findByAppointmentDetailId(model.getAppointmentDetailId());
            if (entity == null) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_EDIT));
            }
        } else {
            LOGGER.info("editAppointmentDetail 新增線上預約臨櫃");
            //新增
            entity = new GeoKgoAppointmentDetail();
            entity.setAppointmentDetailId(geoKgoAppointmentReposCustom.getNextTableId(
                    GeoStringUtil.APPOINTMENT_DETAIL_PREFIX_CHAR, "GEO_KGO_APPOINTMENT_DETAIL", "appointment_detail_id"));
            entity.setAppointmentMainId(appointmentMainId);
        } //if (rq.getQuestionId()!=null)
        entity.setAppointmentDetailDate(DateUtil.strToTimestamp(model.getAppointmentDetailDate(), PATTEN_YEAR_MONTH_DAY));
        entity.setEarliestDay(model.getEarliestDay());
        entity.setEarliestTime(StringUtils.defaultIfBlank(model.getEarliestTime(),"00:00:00"));
        entity.setLatestDay(model.getLatestDay());
        entity.setLatestTime(StringUtils.defaultIfBlank(model.getLatestTime(),"00:00:00"));
        entity.setIsEnable(model.getIsEnable());
        entity.setLocation(model.getLocation());
//        entity.setEditOrgan(userInfo.getOrgan());
//        entity.setEditUser(userInfo.getUserId());
        entity.setEditTime(now);
        entity = detailRepository.save(entity);
        return entity;
    } //editAppointmentDetail

    private GeoKgoAppointmentDetailTime editAppointmentDetailTime(String appointmentDetailId, GeoKgoAppointmentDetailTimeInsertModel model) throws Exception {
        GeoKgoAppointmentDetailTime entity = null;
        //編輯
        if (!StringUtils.isEmpty(model.getAppointmentDetailTimeId())) {
            LOGGER.info("editAppointmentDetailTime 編輯 model.getAppointmentDetailTimeId()="+(model.getAppointmentDetailTimeId()));
            entity = detailTimeRepository.findByAppointmentDetailTimeId(model.getAppointmentDetailTimeId());
        }
        //新增
        if (entity == null) {
            LOGGER.info("editAppointmentDetailTime 新增 model.getAppointmentDetailTimeId()="+(model.getAppointmentDetailTimeId()));
            entity = new GeoKgoAppointmentDetailTime();
            entity.setAppointmentDetailTimeId(geoKgoAppointmentReposCustom.getNextTableId(
                    GeoStringUtil.APPOINTMENT_DETAIL_TIME_PREFIX_CHAR, "GEO_KGO_APPOINTMENT_DETAIL_TIME", "appointment_detail_time_id"));
            entity.setAppointmentDetailId(appointmentDetailId);
        } //if (entity==null)
        entity.setAvailableUserQuota(model.getAvailableUserQuota());
        entity.setStartTime(DateUtil.strToTimestamp(model.getStartTime(), DateUtil.PATTEN_FULL_TIME_TO_MINUTE));
        entity.setEndTime(DateUtil.strToTimestamp(model.getEndTime(), DateUtil.PATTEN_FULL_TIME_TO_MINUTE));
        entity = detailTimeRepository.save(entity);
        return entity;
    } //editAppointmentDetailTime

    private GeoKgoAppointmentDetailNumbers editAppointmentDetailNumbers(String appointmentDetailTimeId, GeoKgoAppointmentDetailNumbersInsertModel model) {
        GeoKgoAppointmentDetailNumbers entity = null;
        if (!model.getNumberName().isEmpty()){
            //編輯
            if (!StringUtils.isEmpty(model.getAppointmentDetailNumbersId())) {
                entity = detailNumbersRepository.findByAppointmentDetailNumbersId(model.getAppointmentDetailNumbersId());
            }
            //新增
            if (entity == null) {
                entity = new GeoKgoAppointmentDetailNumbers();
                entity.setAppointmentDetailNumbersId(geoKgoAppointmentReposCustom.getNextTableId(
                        GeoStringUtil.APPOINTMENT_DETAIL_NUMBERS_PREFIX_CHAR, "GEO_KGO_APPOINTMENT_DETAIL_NUMBERS", "appointment_detail_numbers_id"));
                entity.setIsUsed(GeoBooleanType.ENABLED.getCode());
                entity.setAppointmentDetailTimeId(appointmentDetailTimeId);
            } //if (entity == null)
            entity.setNumberName(model.getNumberName());
            entity = detailNumbersRepository.save(entity);
        }
        return entity;
    } //editAppointmentDetailNumbers

    /**
     * 後台-線上預約臨櫃-編輯:取得該預約單資料
     *
     * @param rq
     * @return
     */
    public GeoAppointmentInfoQueryRs getAppointmentInfo(GeoAppointmentInfoQueryRq rq) {
        GeoAppointmentInfoQueryRs rs = new GeoAppointmentInfoQueryRs();
        GeoAppointmentInfoQueryViewForm viewForm = new GeoAppointmentInfoQueryViewForm();
        GeoKgoAppointmentMainQueryModel model = null;
        List<GeoKgoAppointmentDetailInsertModel> detailModelList = null;
        List<GeoKgoAppointmentContactUserInsertModel> contactUserModelList = null;
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (StringUtils.isBlank(rq.getAppointmentMainId())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            model = geoKgoAppointmentReposCustom.getAppointMainListById(GeoAppointmentMainStatusType.DELETE.getLabel(), rq.getAppointmentMainId());
            viewForm.setAppointmentMain(model);

            if (model != null) {
                detailModelList = new ArrayList<>();
                contactUserModelList = new ArrayList<>();
                List<GeoKgoAppointmentDetail> detailList = detailRepository.findAllByAppointmentMainId(rq.getAppointmentMainId());
                if (detailList != null && detailList.size() > 0) {
                    detailModelList = GeoKgoAppointmentDetailInsertModel.changeListToModel(detailList);
                    for (GeoKgoAppointmentDetailInsertModel detail : detailModelList) {
                        List<GeoKgoAppointmentDetailTimeInsertModel> timeModelList = new ArrayList<>();
                        List<GeoKgoAppointmentDetailTime> timeList = detailTimeRepository.findAllByAppointmentDetailId(detail.getAppointmentDetailId());
                        if (timeList != null && timeList.size() > 0) {
                            timeModelList = GeoKgoAppointmentDetailTimeInsertModel.changeListToModel(timeList);
                            for (GeoKgoAppointmentDetailTimeInsertModel time : timeModelList) {
                                List<GeoKgoAppointmentDetailNumbersInsertModel> numbersModelList = new ArrayList<>();
                                List<GeoKgoAppointmentDetailNumbers> numbersList = detailNumbersRepository.findAllByAppointmentDetailTimeId(time.getAppointmentDetailTimeId());
                                if (numbersList != null && numbersList.size() > 0) {
                                    numbersModelList = GeoKgoAppointmentDetailNumbersInsertModel.changeListToModel(numbersList);
                                }
                                time.setDetailNumbersList(numbersModelList);
                            }
                        } //if (timeList != null...
                        detail.setDetailTimeList(timeModelList);
                    } //for (GeoKgoAppointmentDetailInsertModel...
                } //if (detailList != null

                List<GeoKgoAppointmentContactUser> contactUserList = contactUserRepository.findAllByAppointmentMainId(rq.getAppointmentMainId());
                if (contactUserList != null && contactUserList.size() > 0) {
                    contactUserModelList = GeoKgoAppointmentContactUserInsertModel.changeListToModel(contactUserList);
                }
            } //if (model != null)
            viewForm.setDetailList(detailModelList);
            viewForm.setContactUserList(contactUserModelList);
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

    public GeoAppointmentBlockUserQueryRs queryAppointmentBlockUser(GeoBlockUserQueryRq rq) {
        GeoAppointmentBlockUserQueryRs rs = new GeoAppointmentBlockUserQueryRs();
        GeoAppointmentBlockUserQueryViewForm viewForm = new GeoAppointmentBlockUserQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (StringUtils.isBlank(rq.getAppointmentMainId())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            List<GeoKgoAppointmentBlockUser> blockUserEntityList = blockUserRepository.findAllByAppointmentMainIdAndIsTriggerBlock(
                    rq.getAppointmentMainId(), GeoBooleanType.ENABLED.getCode());
            List<GeoKgoAppointmentBlockUserListModel> models = GeoKgoAppointmentBlockUserListModel.changeListToModel(blockUserEntityList);
            viewForm.setDataList(models);
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
    } //queryAppointmentBlockUser

    /**
     * 後台-線上預約臨櫃-編輯:依時間範圍取得該預約單資料
     */
    public GeoAppointmentInfoQueryRs getAppointmentInfoByTime(GeoAppointmentInfoByTimeQueryRq rq) {
        GeoAppointmentInfoQueryRs rs = new GeoAppointmentInfoQueryRs();
        GeoAppointmentInfoQueryViewForm viewForm = new GeoAppointmentInfoQueryViewForm();
        GeoKgoAppointmentMainQueryModel model = null;
        List<GeoKgoAppointmentDetailInsertModel> detailModelList = null;
        List<GeoKgoAppointmentContactUserInsertModel> contactUserModelList = null;
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (StringUtils.isBlank(rq.getAppointmentMainId())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            model = geoKgoAppointmentReposCustom.getAppointMainListById(GeoAppointmentMainStatusType.DELETE.getLabel(), rq.getAppointmentMainId());
            viewForm.setAppointmentMain(model);
            String dateStart = DateUtil.getFirstDateInWeek(rq.getYear(), rq.getMonth(), rq.getWeek());
            String dateEnd = DateUtil.getLastDateInWeek(rq.getYear(), rq.getMonth(), rq.getWeek());
            LOGGER.info("getAppointmentInfoByTime dateStart/dateEnd="+dateStart+"/"+dateEnd);

            if (model != null) {
                detailModelList = new ArrayList<>();
                contactUserModelList = new ArrayList<>();
                List<GeoKgoAppointmentDetail> detailList = geoKgoAppointmentReposCustom.getAppointmentInfoByTime(rq.getAppointmentMainId(), dateStart, dateEnd);
                if (detailList != null && detailList.size() > 0) {
                    for (int i = 0; i < WEEK_DAY; i++) {
                        String date = DateUtil.getEveryDayInWeek(rq.getYear(), rq.getMonth(), rq.getWeek(), i); //從當週的第一天開始獲取
                        boolean isNeedModel = true;
                        for (GeoKgoAppointmentDetail detail : detailList) {
                            String originalDate = DateUtil.timestampToString(detail.getAppointmentDetailDate(), "yyyy-MM-dd");
                            LOGGER.info("getAppointmentInfoByTime date=" + date + ",originalDate=" + originalDate);
                            if (originalDate.equals(date)) {
                                isNeedModel = false;
                                break;
                            } //if (originalDate.equals(date))
                        } // for (GeoKgoAppointmentDetail detail : detailList)
                        //LOGGER.info("getAppointmentInfoByTime isNeedModel=" + isNeedModel);
                        if (isNeedModel) {
                            GeoKgoAppointmentDetail detail = new GeoKgoAppointmentDetail();
                            detail.setAppointmentMainId("");
                            detail.setAppointmentDetailId("");
                            detail.setAppointmentDetailDate(DateUtil.strToTimestamp(date, "yyyy-MM-dd"));
                            LOGGER.info("getAppointmentInfoByTime true date="+DateUtil.strToTimestamp(date, "yyyy-MM-dd"));
                            detail.setEarliestDay(0);
                            detail.setEarliestTime("");
                            detail.setIsEnable(0);
                            detail.setLatestDay(0);
                            detail.setLatestTime("");
                            detail.setLocation("");
                            detailList.add(i, detail);
                        } //if (isNeedModel
                    } //for (int i = 0; i < WEEK_DAY; i++)
                } else {
                    detailList = new ArrayList<>();
                    for (int i = 0; i < WEEK_DAY; i++) {
                        String date = DateUtil.getEveryDayInWeek(rq.getYear(), rq.getMonth(), rq.getWeek(), i); //從當週的第一天開始獲取
                        LOGGER.info("getAppointmentInfoByTime false date ="+date);
                        GeoKgoAppointmentDetail detail = new GeoKgoAppointmentDetail();
                        detail.setAppointmentMainId("");
                        detail.setAppointmentDetailId("");
                        detail.setAppointmentDetailDate(DateUtil.strToTimestamp(date, "yyyy-MM-dd"));
                        LOGGER.info("getAppointmentInfoByTime false date="+DateUtil.strToTimestamp(date, "yyyy-MM-dd"));
                        detail.setEarliestDay(0);
                        detail.setEarliestTime("");
                        detail.setIsEnable(0);
                        detail.setLatestDay(0);
                        detail.setLatestTime("");
                        detail.setLocation("");
                        detailList.add(detail);
                    } //for (int i = 0; i < WEEK_DAY; i++)
                } //if (detailList != null && detailList.size() > 0)
                //LOGGER.info("getAppointmentInfoByTime detailList.size()=" + detailList.size());
                detailModelList = GeoKgoAppointmentDetailInsertModel.changeListToModel(detailList);
                for (GeoKgoAppointmentDetailInsertModel detail : detailModelList) {
                    List<GeoKgoAppointmentDetailTimeInsertModel> timeModelList = new ArrayList<>();
                    List<GeoKgoAppointmentDetailTime> timeList = detailTimeRepository.findAllByAppointmentDetailId(detail.getAppointmentDetailId());
                    if (timeList != null && timeList.size() > 0) {
                        timeModelList = GeoKgoAppointmentDetailTimeInsertModel.changeListToModel(timeList);
                        for (GeoKgoAppointmentDetailTimeInsertModel time : timeModelList) {
                            List<GeoKgoAppointmentDetailNumbersInsertModel> numbersModelList = new ArrayList<>();
                            List<GeoKgoAppointmentDetailNumbers> numbersList = detailNumbersRepository.findAllByAppointmentDetailTimeId(time.getAppointmentDetailTimeId());
                            if (geoKgoAppointmentRepository.existsGeoKgoAppointmentByAppointmentDetailTimeId(time.getAppointmentDetailTimeId())) {
                                time.setIsDeleteDetailTime(GeoBooleanType.DISABLED.getCode());
                            } else {
                                time.setIsDeleteDetailTime(GeoBooleanType.ENABLED.getCode());
                            } //if (geoKgoAppointmentRepository...
                            if (numbersList != null && numbersList.size() > 0) {
                                numbersModelList = GeoKgoAppointmentDetailNumbersInsertModel.changeListToModel(numbersList);
                            } //if (numbersList != null && numbersList.size() > 0)
                            time.setDetailNumbersList(numbersModelList);
                        } //for (GeoKgoAppointmentDetailTimeInsertModel time
                    } //if (timeList != null...
                    detail.setDetailTimeList(timeModelList);
                } //for (GeoKgoAppointmentDetailInsertModel...
                List<GeoKgoAppointmentContactUser> contactUserList = contactUserRepository.findAllByAppointmentMainId(rq.getAppointmentMainId());
                if (contactUserList != null && contactUserList.size() > 0) {
                    contactUserModelList = GeoKgoAppointmentContactUserInsertModel.changeListToModel(contactUserList);
                } // if (contactUserList != null && contactUserList.size() > 0)
            } //if (model != null)
            viewForm.setDetailList(detailModelList);
            viewForm.setContactUserList(contactUserModelList);
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
    } //getAppointmentInfoByTime

    /**
     * 後台-線上預約臨櫃-編輯：取得該預約對應表單
     */
    public GeoAppointmentFormQueryRs getAppointmentForm(GeoAppointmentFormQueryRq rq) {
        GeoAppointmentFormViewForm viewForm = new GeoAppointmentFormViewForm();
        GeoAppointmentFormQueryRs rs = new GeoAppointmentFormQueryRs();
        try {
            List<GeoAppointmentFormQueryDataModel> dataGridList = new LinkedList<GeoAppointmentFormQueryDataModel>();
            String appointmentMainId = rq.getAppointmentMainId();
            addBasicGroupColumnData(appointmentMainId);

            List<GeoAppointmentGroupQueryDataMaxVersionDto> dtoList = geoKgoAppointmentReposCustom.findMaxVersionGroupData(appointmentMainId, StringUtils.EMPTY);

            Integer version = ObjectUtils.isEmpty(dtoList) ? KgoUtil.DEFAULT_VERSION_NUMBER : dtoList.get(0).getVersion();

            if (ObjectUtils.isNotEmpty(dtoList)) {
                dtoList.forEach(l -> {
                    List<GeoKgoAppointmentColumn> geoKgoAppointmentColumnList = geoKgoAppointmentColumnRepository
                            .findByIdAppointmentMainIdAndGroupSeqOrderByOrderNumAsc(appointmentMainId, l.getGroupSeq());

                    List<GeoAppointmentFormQueryGroupColumnDataModel> geoAppointmentFormQueryGroupColumnDataModelList =
                            geoKgoAppointmentColumnList.stream().map(dl -> {
                                List<List<CasesetComplexColumnData>> complexDataList = null;
                                if (dl.getColumnType().equalsIgnoreCase(ColumnTypeEnum.M.getValue())) {
                                    Map<Integer, List<GeoKgoAppointmentColumnChild>> dataMap = geoKgoAppointmentColumnChildRepository
                                            .findByIdAppointmentMainIdAndIdVersionAndIdColumnIdOrderByIdOrderNumAsc(appointmentMainId, dl.getId().getVersion(), dl.getId().getColumnId())
                                            .stream().collect(Collectors.groupingBy(GeoKgoAppointmentColumnChild::getRow,
                                                    HashMap::new, Collectors.toCollection(LinkedList::new)));
                                    complexDataList = dataMap.keySet().stream().map(i -> {
                                        return dataMap.get(i).stream().map(cl -> {
                                            String vGroup = StringUtils.isBlank(cl.getVGroup()) ? StringUtils.EMPTY
                                                    : cl.getVGroup();
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
                                            complexData.setvGroup(vGroup);
                                            return complexData;
                                        }).collect(Collectors.toList());
                                    }).collect(Collectors.toList());
                                } // if (dl.getColumnType().equalsIgnoreCase

                                GeoAppointmentFormQueryGroupColumnDataModel dg = new GeoAppointmentFormQueryGroupColumnDataModel();
                                dg.setColumnId(dl.getId().getColumnId());
                                dg.setColumnName(dl.getColumnName());
                                dg.setColumnType(dl.getColumnType());
                                dg.setColumnValue(dl.getColumnValue());
                                dg.setColumnTypeName(ColumnTypeEnum.getColumnTypeEnum(dl.getColumnType()).getLabel());
                                IsMustKeyEnum aEnum = IsMustKeyEnum.getIsMustKeyEnum(dl.getMustKey());
                                dg.setIsMustKey(aEnum.getValue());
                                dg.setIsMustKeyStr(aEnum.getLabel());
                                /**GEO 20211019 add */
                                IsCHeckFrequencyEnum bEnum = IsCHeckFrequencyEnum.getIsCheckFrequencyEnum(dl.getIsCheckFrequency());
                                dg.setIsCheckFrequency(bEnum.getValue());
                                dg.setIsCheckFrequencyStr(bEnum.getLabel());
                                /** GEO 20211102 add 欄位勾選*/
                                IsFieldCheckEnum fieldCheckEnum = IsFieldCheckEnum.getIsFieldCheckEnum(dl.getIsFieldCheck());
                                dg.setIsFieldCheck(fieldCheckEnum.getValue());
                                dg.setIsFieldCheckStr(fieldCheckEnum.getLabel());

                                dg.setLength(dl.getLength());
                                dg.setMemo(dl.getMemo());
                                dg.setOrderNum(dl.getOrderNum());
                                if (FIL.getValue().equals(dl.getColumnType())) {//if ColumnType is Fil, set FileType 20201208 By Jay
                                    dg.setFileType(dl.getFileType());
                                } //if(FIL.getValue().equals(
                                dg.setComplex(complexDataList);
                                return dg;
                            }).collect(Collectors.toList());

                    GeoAppointmentFormQueryDataModel dataGrid = new GeoAppointmentFormQueryDataModel();
                    dataGrid.setColumnData(geoAppointmentFormQueryGroupColumnDataModelList);
                    dataGrid.setGroupSeq(l.getGroupSeq());
                    /** GEO 20211203 add 重複檢核 */
                    dataGrid.setCheckFrequencyPeriod(l.getCheckFrequencyPeriod());
                    dataGrid.setGroupName(l.getMemo());
                    dataGrid.setOrderNum(l.getOrderNum());
                    dataGridList.add(dataGrid);
                }); // dtoList.forEach(l ->
            } //if (ObjectUtils.isNotEmpty(dtoList))

            viewForm.setVersion(version);
            viewForm.setAppointmentId(appointmentMainId);
            viewForm.setGrid(dataGridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("getAppointmentForm error " + e.getMessage(), e);
        } //try catch
        return rs;
    } //getAppointmentForm

    /**
     * 後台-線上預約臨櫃-編輯：建立預設表單
     *
     * @param appointmentMainId
     */
    private void addBasicGroupColumnData(String appointmentMainId) {
        Integer groupSeq = findGroupSeq(appointmentMainId, KgoUtil.MYDATA_BASIC_GROUP_NAME);
        if (ObjectUtils.isEmpty(groupSeq)) {
            // 1.『基本設定』群組新增
            geoKgoAppointmentReposCustom.saveAppointmentGroupData(appointmentMainId, KgoUtil.DEFAULT_VERSION_NUMBER,
                    KgoUtil.MYDATA_BASIC_GROUP_NAME, KgoUtil.DEFAULT_ORDER_NUMBER, KgoUtil.DEFAULT_ORDER_NUMBER);
            groupSeq = findGroupSeq(appointmentMainId, KgoUtil.MYDATA_BASIC_GROUP_NAME);

            List<KgoCasesetTemplate> kgoCasesetTemplates = kgoCasesetTemplateRepository.findByIsDefault(TemplateIsDefaultEnum.ONE.getValue());

            if (!CollectionUtils.isEmpty(kgoCasesetTemplates)) {
                KgoCasesetTemplate kgoCasesetTemplate = kgoCasesetTemplates.get(0);
                List<KgoCasesetColumnTemplate> kgoCasesetColumnTemplates = kgoCasesetColumnTemplateRepository.
                        findByTemplateSeqAndSuspend(kgoCasesetTemplate.getSeq(), TemplateSuspendEnum.ZERO.getValue());
                List<Integer> seqs = kgoCasesetColumnTemplates.stream().map(KgoCasesetColumnTemplate::getSeq).collect(Collectors.toList());
                List<KgoCasesetColumnChildTemplate> kgoCasesetColumnChildTemplates = kgoCasesetColumnChildTemplateRepository.findByColumnSeqInAndSuspend(seqs, TemplateSuspendEnum.ZERO.getValue());
                Map<Integer, List<KgoCasesetColumnChildTemplate>> collect = kgoCasesetColumnChildTemplates.stream().collect(Collectors.groupingBy(KgoCasesetColumnChildTemplate::getColumnSeq));

                //獲取範本的預設欄位：身分證、姓名、電話、email
                for (KgoCasesetColumnTemplate kgoCasesetColumnTemplate : kgoCasesetColumnTemplates) {
                    GeoKgoAppointmentColumnPK appointmentColumnPK = new GeoKgoAppointmentColumnPK();
                    appointmentColumnPK.setAppointmentMainId(appointmentMainId);
                    appointmentColumnPK.setColumnId(kgoCasesetColumnTemplate.getColumnId());
                    appointmentColumnPK.setVersion(KgoUtil.DEFAULT_VERSION_NUMBER);
                    GeoKgoAppointmentColumn appointmentColumn = new GeoKgoAppointmentColumn();
                    appointmentColumn.setId(appointmentColumnPK);
                    appointmentColumn.setColumnName(kgoCasesetColumnTemplate.getColumnName());
                    appointmentColumn.setColumnType(kgoCasesetColumnTemplate.getColumnType());
                    IsMustKeyEnum isMustKeyEnum = IsMustKeyEnum.getIsMustKeyEnum(kgoCasesetColumnTemplate.getIsMustKey());

                    if (null != isMustKeyEnum) {
                        appointmentColumn.setMustKey(isMustKeyEnum.getBooleanValue());
                    } //if (null != isMustKeyEnum)

                    /**GEO 20211019 add */
                    IsCHeckFrequencyEnum isCHeckFrequencyEnum = IsCHeckFrequencyEnum.getIsCheckFrequencyEnum(kgoCasesetColumnTemplate.getIsCheckFrequency());
                    if (null != isCHeckFrequencyEnum) {
                        appointmentColumn.setIsCheckFrequency(Integer.parseInt(isCHeckFrequencyEnum.getValue()));
                    } //if (null != isCHeckFrequencyEnum)
                    /** GEO 20211102 add 欄位勾選*/
                    IsFieldCheckEnum isFieldCheckEnum = IsFieldCheckEnum.getIsFieldCheckEnum(kgoCasesetColumnTemplate.getIsFieldCheck());
                    if (null != isFieldCheckEnum) {
                        appointmentColumn.setIsFieldCheck(Integer.parseInt(isFieldCheckEnum.getValue()));
                    } //IsFieldCheckEnum isFieldCheckEnum
                    appointmentColumn.setGroupSeq(groupSeq);
                    appointmentColumn.setLength(kgoCasesetColumnTemplate.getLength());
                    appointmentColumn.setOrderNum(kgoCasesetColumnTemplate.getOrderNum());
                    appointmentColumn.setColumnValue(kgoCasesetColumnTemplate.getColumnValue());
                    appointmentColumn.setMemo(kgoCasesetColumnTemplate.getMemo());
                    appointmentColumn.setFileType(kgoCasesetColumnTemplate.getFileType());

                    List<KgoCasesetColumnChildTemplate> kgoCasesetColumnChildTemplatesList = collect.get(kgoCasesetColumnTemplate.getSeq());
                    if (!CollectionUtils.isEmpty(kgoCasesetColumnChildTemplatesList)) {
                        for (KgoCasesetColumnChildTemplate kgoCasesetColumnChildTemplate : kgoCasesetColumnChildTemplatesList) {
                            GeoKgoAppointmentColumnChildPK appointmentColumnChildPK = new GeoKgoAppointmentColumnChildPK();
                            appointmentColumnChildPK.setAppointmentMainId(appointmentMainId);
                            appointmentColumnChildPK.setcColumnId(kgoCasesetColumnChildTemplate.getCColumnId());
                            appointmentColumnChildPK.setVersion(KgoUtil.DEFAULT_VERSION_NUMBER);
                            appointmentColumnChildPK.setColumnId(kgoCasesetColumnChildTemplate.getColumnId());
                            GeoKgoAppointmentColumnChild geoKgoAppointmentColumnChild = new GeoKgoAppointmentColumnChild();
                            geoKgoAppointmentColumnChild.setId(appointmentColumnChildPK);
                            geoKgoAppointmentColumnChild.setBText(kgoCasesetColumnChildTemplate.getBText());
                            geoKgoAppointmentColumnChild.setColumnType(kgoCasesetColumnChildTemplate.getColumnType());
                            geoKgoAppointmentColumnChild.setColumnValue(kgoCasesetColumnChildTemplate.getColumnValue());
                            geoKgoAppointmentColumnChild.setFText(kgoCasesetColumnChildTemplate.getFText());
                            IsMustKeyEnum isMustKeyEnumInner = IsMustKeyEnum.getIsMustKeyEnum(kgoCasesetColumnChildTemplate.getIsMustKey());
                            if (null != isMustKeyEnumInner) {
                                geoKgoAppointmentColumnChild.setMustKey(isMustKeyEnumInner.getBooleanValue());
                            }
                            /**GEO 20211019 add */
                            IsCHeckFrequencyEnum isCHeckFrequencyEnumInner = IsCHeckFrequencyEnum.getIsCheckFrequencyEnum(kgoCasesetColumnChildTemplate.getIsCheckFrequency());
                            if (null != isCHeckFrequencyEnumInner) {
                                geoKgoAppointmentColumnChild.setIsCheckFrequency(Integer.parseInt(isCHeckFrequencyEnumInner.getValue()));
                            } //if (null != isCHeckFrequencyEnumInner)
                            /** GEO 20211102 add 欄位勾選*/
                            IsFieldCheckEnum isFieldCheckEnumInner = IsFieldCheckEnum.getIsFieldCheckEnum(kgoCasesetColumnChildTemplate.getIsFieldCheck());
                            if (null != isFieldCheckEnumInner) {
                                geoKgoAppointmentColumnChild.setIsFieldCheck(Integer.parseInt(isFieldCheckEnumInner.getValue()));
                            } //if (null != isFieldCheckEnumInner)
                            geoKgoAppointmentColumnChild.setLength(kgoCasesetColumnChildTemplate.getLength());
                            geoKgoAppointmentColumnChild.setMemo(kgoCasesetColumnChildTemplate.getMemo());
                            geoKgoAppointmentColumnChild.setPColumnId(kgoCasesetColumnChildTemplate.getPColumnId());
                            geoKgoAppointmentColumnChild.setRow(kgoCasesetColumnChildTemplate.getRow());
                            geoKgoAppointmentColumnChild.setVGroup(kgoCasesetColumnChildTemplate.getVGroup());

                            geoKgoAppointmentColumnChildRepository.save(geoKgoAppointmentColumnChild);
                        } // for (KgoCasesetColumnChildTemplate
                    } //if (!CollectionUtils.isEmpty
                    geoKgoAppointmentColumnRepository.save(appointmentColumn);
                } //for (KgoCasesetColumnTemplate
            } //if (!CollectionUtils.isEmpty(
        } // if (ObjectUtils.isEmpty(groupSe
    }  //private void addBasicGroupColumnData

    /**
     * 後台-線上預約臨櫃-找尋線上預約臨櫃表單群組資料最大版本號
     *
     * @param appointmentMainId
     * @param memo
     * @return
     */
    private Integer findGroupSeq(String appointmentMainId, String memo) {
        List<GeoAppointmentGroupQueryDataMaxVersionDto> dtoList = geoKgoAppointmentReposCustom.findMaxVersionGroupData(appointmentMainId, memo);
        return ObjectUtils.isEmpty(dtoList) ? null : dtoList.get(0).getGroupSeq();
    } //findGroupSeq

    /**
     * 後台-線上預約臨櫃-編輯：預約表單設定欄位初始化
     */
    public GeoAppointmentFormColumnHomeActionRS appointmentFormColumnHomeAction() {
        GeoAppointmentFormColumnHomeViewForm viewForm = new GeoAppointmentFormColumnHomeViewForm();
        GeoAppointmentFormColumnHomeActionRS rs = new GeoAppointmentFormColumnHomeActionRS();
        try {
            ComboBox columnTypeComboBox = commonServiceHelper.getComboBoxWithEnum(GeoAppointmentColumnTypeEnum.class);
            ComboBox fileTypeComboBox = commonServiceHelper.getCodeTypeComboBox(CodeTypeEnum.FILE_TYPE.getValue());
            ComboBox isMustKeyComboBox = commonServiceHelper.getComboBoxWithEnum(IsMustKeyEnum.class);
            /**GEO 20211019 add */
            ComboBox isCheckFrequencyComboBox = commonServiceHelper.getComboBoxWithEnum(IsCHeckFrequencyEnum.class);
            /** GEO 20211102 add 欄位勾選*/
            ComboBox isFieldCheckComboBox = commonServiceHelper.getComboBoxWithEnum(IsFieldCheckEnum.class);
            viewForm.setColumnTypeComboBox(columnTypeComboBox);
            viewForm.setFileTypeComboBox(fileTypeComboBox);
            viewForm.setIsMustKeyComboBox(isMustKeyComboBox);
            /**GEO 20211019 add */
            viewForm.setIsCheckFrequencyComboBox(isCheckFrequencyComboBox);
            /** GEO 20211102 add 欄位勾選*/
            viewForm.setIsFieldCheckComboBox(isFieldCheckComboBox);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("appointmentFormColumnHomeAction error " + e.getMessage(), e);
        } //try catch
        return rs;
    } //appointmentFormColumnHomeAction


    /**
     * 後台-線上預約臨櫃-編輯：表單儲存更版
     * 群組 欄位 整包一起依照下一個版號來儲存
     *
     * @param rq
     * @return
     */
    public synchronized GeoAppointmentFormColumnSaveActionRs saveAppointmentFormColumn(GeoAppointmentFormColumnSaveActionRq rq) {
        GeoAppointmentFormGroupColumnEditViewForm viewForm = new GeoAppointmentFormGroupColumnEditViewForm();
        GeoAppointmentFormColumnSaveActionRs rs = new GeoAppointmentFormColumnSaveActionRs();
        try {
            String appointmentMainId = rq.getAppointmentMainId();
            List<GeoAppointGroupColumnData> groupColumnDataList = rq.getGroupColumnDataList();

            int version = geoKgoAppointmentReposCustom.getNextVersionValue(appointmentMainId);

            groupColumnDataList.forEach(groupColumnData -> {
                /** GEO 20211203 add 重複檢核 */
                geoKgoAppointmentReposCustom.saveAppointmentGroupData(appointmentMainId, version, groupColumnData.getGroupName(), groupColumnData.getOrderNum(), groupColumnData.getIsShow());
                int groupSeq = findGroupSeq(appointmentMainId, groupColumnData.getGroupName());
                List<GeoAppointColumnData> list = groupColumnData.getColumnDataList();
                list.forEach(geoAppointColumnData -> {

                    String columnId = geoAppointColumnData.getColumnId();

                    /**儲存表單跟主檔關聯*/
                    GeoKgoAppointmentColumnPK geoKgoAppointmentColumnPK = new GeoKgoAppointmentColumnPK();
                    geoKgoAppointmentColumnPK.setAppointmentMainId(appointmentMainId);
                    geoKgoAppointmentColumnPK.setColumnId(columnId);
                    geoKgoAppointmentColumnPK.setVersion(version);

                    /**該預約表單 */
                    GeoKgoAppointmentColumn geoKgoAppointmentColumn = new GeoKgoAppointmentColumn();
                    geoKgoAppointmentColumn.setId(geoKgoAppointmentColumnPK);
                    geoKgoAppointmentColumn.setColumnName(geoAppointColumnData.getColumnName());
                    geoKgoAppointmentColumn.setColumnType(geoAppointColumnData.getColumnType());

                    /** GEO 20210817 changed for 1999 service **//** GEO 20211115 add for 民政局五種服務轉成B流程 **/
                    if (geoAppointColumnData.getColumnType().equals(ColumnTypeEnum.ADDR_1999.getValue()) ||
                            geoAppointColumnData.getColumnType().equals(ColumnTypeEnum.AREA_ADVICE.getValue()) ||
                            geoAppointColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_RESIDENT.getValue()) ||
                            geoAppointColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue())) {
                        List<Geo1999ItemsMainModel> mainList = null;
                        if (geoAppointColumnData.getColumnType().equals(ColumnTypeEnum.ADDR_1999.getValue())) {
                            //處理 1999 地址縣市(PS.後來改直接抓api)
                            //mainList = geoKcgCityExtService.sendGet1999AddrCityApi();
                        } else if (geoAppointColumnData.getColumnType().equals(ColumnTypeEnum.AREA_ADVICE.getValue())) {
                            //處理 1999 建議行政區
                            mainList = geoKcgCityExtService.sendGet1999AreaAdviceApi();
                        } else if (geoAppointColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_RESIDENT.getValue())) {
                            //處理 地區設定(戶籍地)
                            mainList = geoKcgCityExtService.sendGetAreaAdviceApi();
                        } else if (geoAppointColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue())) {
                            //處理 高雄區公所
                            mainList = new ArrayList<>();
                            GeoDistrictOfficeType[] districtOfficeList = GeoDistrictOfficeType.values();
                            for (GeoDistrictOfficeType districtOffice : districtOfficeList) {
                                Geo1999ItemsMainModel item = new Geo1999ItemsMainModel();
                                item.setItemId(String.valueOf(districtOffice.getCode()));
                                item.setItemName(districtOffice.getLabel());
                                mainList.add(item);
                            } //for (GeoDistrictOfficeType districtOffice
                        } //if (geoAppointColumnData.getColumnType().

                        if (mainList != null) {
                            if (!geoAppointColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue()))
                                geoKgoAppointmentColumn.setColumnType(ColumnTypeEnum.DRP.getValue()); //將欄位型態變更為下拉選單
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < mainList.size(); i++) {
                                if (i == 0) {
                                    sb.append(mainList.get(i).getItemId() + "-" + mainList.get(i).getItemName());
                                } else {
                                    sb.append("," + mainList.get(i).getItemId() + "-" + mainList.get(i).getItemName());
                                } // if (i == 0)
                            } // for (int i = 0; i < mainList.size(); i++)
                            String value = sb.toString();
                            geoKgoAppointmentColumn.setColumnValue(value);
                        } else {
                            geoKgoAppointmentColumn.setColumnValue(geoAppointColumnData.getColumnValue());
                        } //if (mainList!=null)
                    } else {
                        geoKgoAppointmentColumn.setColumnValue(geoAppointColumnData.getColumnValue());
                    } //if (casesetColumnData.getColumnType().equals..

                    geoKgoAppointmentColumn.setGroupSeq(groupSeq);
                    geoKgoAppointmentColumn.setMustKey(IsMustKeyEnum.getIsMustKeyEnum(geoAppointColumnData.getIsMustKey()).getBooleanValue());

                    /**GEO 20211109 add 重複檢核/欄位勾選 */
                    Integer isCheckFrequency = geoAppointColumnData.getIsCheckFrequency() == null ? 0 : geoAppointColumnData.getIsCheckFrequency();
                    geoKgoAppointmentColumn.setIsCheckFrequency(isCheckFrequency);
                    Integer isFieldCheck = geoKgoAppointmentColumn.getIsFieldCheck() == null ? 0 : geoKgoAppointmentColumn.getIsFieldCheck();
                    geoKgoAppointmentColumn.setIsFieldCheck(isFieldCheck);

                    geoKgoAppointmentColumn.setLength(geoAppointColumnData.getLength());
                    geoKgoAppointmentColumn.setMemo(geoAppointColumnData.getMemo());
                    geoKgoAppointmentColumn.setOrderNum(geoAppointColumnData.getOrderNum());

                    if (FIL.getValue().equals(geoAppointColumnData.getColumnType())) {
                        geoKgoAppointmentColumn.setFileType(geoAppointColumnData.getFileType());// When ColumnType is Fil, save FileType 20201208 By Jay
                    } //if(FIL.getValue().equals(geoA

                    geoKgoAppointmentColumnRepository.save(geoKgoAppointmentColumn);

                    List<List<CasesetComplexColumnData>> casesetComplexColumnDataList = geoAppointColumnData.getComplex();
                    casesetComplexColumnDataList.forEach(tempList -> {
                        tempList.forEach(appointmentComplexColumnData -> {

                            GeoKgoAppointmentColumnChildPK geoKgoAppointmentColumnChildPK = new GeoKgoAppointmentColumnChildPK();
                            geoKgoAppointmentColumnChildPK.setAppointmentMainId(appointmentMainId);
                            geoKgoAppointmentColumnChildPK.setOrderNum(appointmentComplexColumnData.getOrderNum());
                            geoKgoAppointmentColumnChildPK.setcColumnId(appointmentComplexColumnData.getcColumnId());
                            geoKgoAppointmentColumnChildPK.setVersion(version);
                            geoKgoAppointmentColumnChildPK.setColumnId(columnId);

                            GeoKgoAppointmentColumnChild geoKgoAppointmentColumnChild = new GeoKgoAppointmentColumnChild();
                            geoKgoAppointmentColumnChild.setId(geoKgoAppointmentColumnChildPK);
                            geoKgoAppointmentColumnChild.setBText(appointmentComplexColumnData.getbText());
                            geoKgoAppointmentColumnChild.setColumnType(appointmentComplexColumnData.getColumnType());
                            geoKgoAppointmentColumnChild.setColumnValue(appointmentComplexColumnData.getColumnValue());
                            geoKgoAppointmentColumnChild.setFText(appointmentComplexColumnData.getfText());
                            geoKgoAppointmentColumnChild.setMustKey(IsMustKeyEnum
                                    .getIsMustKeyEnum(appointmentComplexColumnData.getIsMustKey()).getBooleanValue());
                            /**GEO 20211109 add 重複檢核/欄位勾選 */
                            Integer isCheckFrequencyChild = appointmentComplexColumnData.getIsCheckFrequency() == null ? 0 : appointmentComplexColumnData.getIsCheckFrequency();
                            geoKgoAppointmentColumnChild.setIsCheckFrequency(isCheckFrequencyChild);
                            Integer isFieldCheckChild = appointmentComplexColumnData.getIsFieldCheck() == null ? 0 : appointmentComplexColumnData.getIsFieldCheck();
                            geoKgoAppointmentColumnChild.setIsFieldCheck(isFieldCheckChild);

                            geoKgoAppointmentColumnChild.setLength(appointmentComplexColumnData.getLength());
                            geoKgoAppointmentColumnChild.setMemo(appointmentComplexColumnData.getMemo());
                            geoKgoAppointmentColumnChild.getId().setOrderNum(appointmentComplexColumnData.getOrderNum());
                            geoKgoAppointmentColumnChild.setPColumnId(appointmentComplexColumnData.getpColumnId());
                            geoKgoAppointmentColumnChild.setRow(appointmentComplexColumnData.getRow());
                            geoKgoAppointmentColumnChild.setVGroup(appointmentComplexColumnData.getvGroup());

                            geoKgoAppointmentColumnChildRepository.save(geoKgoAppointmentColumnChild);
                        }); //tempList.forEach
                    }); //casesetComplexColumnDataList.forEach
                }); //list.forEach
            }); //groupColumnDataList.forEach
            viewForm.setVersion(version);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
            throw new KgoApiException("editAppointmentFormColumn error " + e.getMessage(), e);
        } //try catch
        return rs;
    } //editAppointmentFormColumn

    /**
     * 20220811 GEO add
     * 後台-線上預約臨櫃-編輯：身分驗證初始畫面
     */
    public InternetApplyIdentityVerifyHomeRs internetApplyIdentityVerifyHome(GeoAppointmentInfoQueryRq rq) {
        InternetApplyIdentityVerifyHomeViewForm viewForm = new InternetApplyIdentityVerifyHomeViewForm();
        InternetApplyIdentityVerifyHomeRs rs = new InternetApplyIdentityVerifyHomeRs();
        try {
            List<CheckBox> identityVerifyCheckBox = getVerifyCheckBox(rq.getAppointmentMainId());
            viewForm.setIdentityVerifyCheckBox(identityVerifyCheckBox);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("identityVerifyHome error " + e.getMessage(), e);
        }
        return rs;
    } //internetApplyIdentityVerifyHome

    /**
     * 20220811 GEO add
     * 身份驗證（舊功能）
     */
    public List<CheckBox> getVerifyCheckBox(String appointmentMainId) {
        GeoKgoAppointmentCheckRepository geoKgoAppointmentCheckRepository = SpringUtil.getDao(GeoKgoAppointmentCheckRepository.class);
        List<GeoKgoAppointmentCheck> geoKgoAppointmentCheckList = geoKgoAppointmentCheckRepository.findAllByIdAppointmentMainId(appointmentMainId);
        List<String> casesetChecks = geoKgoAppointmentCheckList.stream().map(check -> check == null ? "UNKNOWN_CHECK_TYPE" : check.getId().getCheckType()).collect(Collectors.toList());
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

    /**
     * 20220811 GEO add
     * 身份驗證（舊功能）
     */
    public CheckBox getIsServiceHtmlCheckBox(boolean isServiceHtml) {
        return internetApplyServiceHelper.getCheckBox(IS_SERVICE_HTML_LABEL, IS_SERVICE_HTML_VALUE, isServiceHtml);
    } //getIsServiceHtmlCheckBox
}
