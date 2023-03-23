package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoAgent;
import gov.kcg.kgo.geoentity.GeoKgoAppointmentBlockUser;
import gov.kcg.kgo.geoenum.GeoBooleanType;
import gov.kcg.kgo.geomodel.GeoKgoAgentListModel;
import gov.kcg.kgo.geomodel.GeoKgoAgentModel;
import gov.kcg.kgo.geomodel.GeoKgoUserInfoModel;
import gov.kcg.kgo.georepository.GeoKgoAgentRepository;
import gov.kcg.kgo.georepository.custom.GeoAgentReposCustom;
import gov.kcg.kgo.geoutil.GeoStringUtil;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAgentDeleteRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAgentInsertRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAgentQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAgentUserInfoQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.*;
import gov.kcg.kgo.service.impl.helper.OrganUnitManagementServiceHelper;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GEO 20211026 add
 * 後台-設定代理人 API Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GeoAgentService extends GeoBaseService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoAgentService.class);

    private OrganUnitManagementServiceHelper organUnitManagementServiceHelper = OrganUnitManagementServiceHelper
            .getInstance();

    @Autowired
    private GeoAgentReposCustom geoAgentReposCustom;

    @Autowired
    private GeoKgoAgentRepository geoKgoAgentRepository;

    /**
     * 設定代理人-初始畫面
     */
    public GeoAgentHomeRs agentSettingHome() {
        GeoAgentHomeViewForm viewForm = new GeoAgentHomeViewForm();
        GeoAgentHomeRs rs = new GeoAgentHomeRs();

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
    } //agentSettingHome

    /**
     * 後台-設定代理人:取得該登入人員資料
     *
     * @param rq
     * @return
     */
    public GeoAgentUserInfoQueryRs getUserInfo(GeoAgentUserInfoQueryRq rq) {
        GeoAgentUserInfoQueryRs rs = new GeoAgentUserInfoQueryRs();
        GeoAgentUserInfoQueryViewForm viewForm = new GeoAgentUserInfoQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            String userId = rq.getUserId();
            if (StringUtils.isBlank(userId)) userId = KgoUtil.getBackendLoginUser().getUserId();
            GeoKgoUserInfoModel model = geoAgentReposCustom.getUserInfoById(userId);
            if (model == null) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH));
            }
            viewForm.setUserInfo(model);
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
    } //getUserInfo

    /**
     * 後台-設定代理人:新增代理人
     *
     * @param rq
     * @return
     */
    public GeoAgentInsertRs insertAgent(GeoAgentInsertRq rq) {
        GeoAgentInsertRs rs = new GeoAgentInsertRs();
        GeoAgentInsertViewForm viewForm = new GeoAgentInsertViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (StringUtils.isBlank(rq.getPrincipalUserId()) || StringUtils.isBlank(rq.getAgentUserId())
                    || StringUtils.isBlank(rq.getStartTime()) || StringUtils.isBlank(rq.getEndTime())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            BackendLoginUserInfo userInfo = KgoUtil.getBackendLoginUser();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            GeoKgoAgent entity = new GeoKgoAgent();
            entity.setAgentId(geoAgentReposCustom.getNextTableId(
                    GeoStringUtil.AGENT_PREFIX_CHAR, "GEO_KGO_AGENT", "agent_id"));
            entity.setAgentUserId(rq.getAgentUserId());
            entity.setPrincipalUserId(rq.getPrincipalUserId());
            entity.setIsDelete(GeoBooleanType.DISABLED.getCode());
            entity.setIsSigned(GeoBooleanType.DISABLED.getCode());
            entity.setStartTime(DateUtil.strToTimestamp(rq.getStartTime(), DateUtil.PATTEN_YEAR_MONTH_DAY));
            Timestamp endTime = DateUtil.getEndOfDay(rq.getEndTime(), DateUtil.PATTEN_YEAR_MONTH_DAY, DateUtil.PATTEN_FULL_TIME_NO_HYPHEN);
            entity.setEndTime(endTime);
            entity.setEditOrgan(userInfo.getOrgan());
            entity.setEditUser(userInfo.getUserId());
            entity.setEditTime(now);
            entity = geoKgoAgentRepository.save(entity);
            GeoKgoAgentModel model = GeoKgoAgentModel.changeToModel(entity);
            viewForm.setAgent(model);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //insertAgent

    /**
     * 後台-設定代理人:查詢代理人清單
     *
     * @param rq
     * @return
     */
    public GeoAgentQueryRs getAgentList(GeoAgentQueryRq rq) {
        GeoAgentQueryRs rs = new GeoAgentQueryRs();
        GeoAgentQueryViewForm viewForm = new GeoAgentQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            List listData = geoAgentReposCustom.getAgentList(rq.getOrganId(), rq.getUnitId(),
                    rq.getPrincipalName(), GeoBooleanType.DISABLED.getCode());
            List<GeoKgoAgentListModel> datas = Collections.synchronizedList(new ArrayList<>());
            for (int i = 0; i < listData.size(); i++) {
                GeoKgoAgentListModel model = new GeoKgoAgentListModel();
                Object[] recordArray = (Object[]) listData.get(i);
                model.setAgentId((String) recordArray[0]);
                model.setPrincipalUserId((String) recordArray[1]);
                model.setPrincipalUserName((String) recordArray[2]);
                model.setOrganId((String) recordArray[3]);
                model.setUnitId((String) recordArray[4]);
                model.setOrganName((String) recordArray[5]);
                model.setUnitName(((String) recordArray[6]));
                model.setAgentUserId(((String) recordArray[7]));
                model.setAgentUserName(((String) recordArray[8]));
                model.setStartTime(DateUtil.timestampToString(((Timestamp) recordArray[9]), DateUtil.PATTEN_YEAR_MONTH_DAY));
                model.setEndTime(DateUtil.timestampToString(((Timestamp) recordArray[10]), DateUtil.PATTEN_YEAR_MONTH_DAY));
                model.setIsTimeout(((Timestamp) recordArray[9]), ((Timestamp) recordArray[10]));
                model.setIsSigned(((Integer) recordArray[11]));
                datas.add(model);
            } //for (int i = 0; i < listData.size(); i++)
            viewForm.setDataList(datas);
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
    } //getAgentList

    /**
     * 後台-設定代理人:查詢被代理人清單
     * @return
     */
    public List<GeoKgoAgent> getPrincipalList(String agentUserId, Timestamp now, Integer isDelete) {
        List<GeoKgoAgent> listData = geoAgentReposCustom.getPrincipalList(agentUserId, now, isDelete);
        LOGGER.info("getPrincipalList listData.size: " + listData.size());
        return listData;
    } //getAgentList

    /**
     * 後台-設定代理人:刪除代理人
     * @return
     */
    public GeoAgentDeleteRs deleteAgent(GeoAgentDeleteRq rq) {
        GeoAgentDeleteRs rs = new GeoAgentDeleteRs();
        GeoAgentDeleteViewForm viewForm = new GeoAgentDeleteViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            BackendLoginUserInfo userInfo = KgoUtil.getBackendLoginUser();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            GeoKgoAgent entity = geoKgoAgentRepository.findByAgentId(rq.getAgentId());
            if (entity == null || !entity.getAgentId().equals(rq.getAgentId()))  {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_DELETE));
            }
            entity.setIsDelete(GeoBooleanType.ENABLED.getCode());
            entity.setEditOrgan(userInfo.getOrgan());
            entity.setEditUser(userInfo.getUserId());
            entity.setEditTime(now);
            geoKgoAgentRepository.save(entity);
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
    } //deleteAgent
}
