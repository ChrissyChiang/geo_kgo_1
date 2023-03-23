package gov.kcg.kgo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.dto.CaseManagementQueryDto;
import gov.kcg.kgo.dto.CasesetManagerQueryByCaseSetIdDto;
import gov.kcg.kgo.dto.TaskSetQueryByCaseSetIdDto;
import gov.kcg.kgo.enums.backend.*;
import gov.kcg.kgo.enums.common.*;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoCasesetCrossReview;
import gov.kcg.kgo.geoentity.GeoKgoCasesetCrossReviewPK;
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetTopic;
import gov.kcg.kgo.geoentity.OrganDiscountRatio;
import gov.kcg.kgo.geoenum.CaseSetCategoryEnum;
import gov.kcg.kgo.geomodel.CaseSetPayModel;
import gov.kcg.kgo.geomodel.GeoCaseSetRefundModel;
import gov.kcg.kgo.georepository.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoOrganDiscountComboBoxRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoDiscountComboxViewForm;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.service.CaseManagementService;
import gov.kcg.kgo.service.CityCoinAPIService;
import gov.kcg.kgo.service.CommonService;
import gov.kcg.kgo.service.impl.helper.*;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.MessageUtil;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.home.rq.CaseManagementCaseOrderHomeRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.home.rs.CaseManagementCaseOrderHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.home.rs.bean.CaseManagementCaseOrderDataGrid;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.home.rs.bean.CaseManagementCaseOrderHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.save.rq.CaseManagementCaseOrderSaveRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.save.rq.bean.CaseManagementCaseOrderSaveDataGrid;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.save.rs.CaseManagementCaseOrderSaveRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.save.rs.bean.CaseManagementCaseOrderSaveViewForm;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseSave.rq.CaseManagementCaseSaveRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseSave.rs.CaseManagementCaseSaveRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseSave.rs.bean.CaseManagementCaseSaveViewForm;
import gov.kcg.kgo.viewModel.backend.caseManagement.delete.rq.CaseManagementDeleteRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.delete.rs.CaseManagementDeleteRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.delete.rs.bean.CaseManagementDeleteViewForm;
import gov.kcg.kgo.viewModel.backend.caseManagement.editHome.rq.CaseManagementEditHomeRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.editHome.rs.CaseManagementEditHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.editHome.rs.bean.CaseManagementEditHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseManagement.editHome.rs.bean.CityCoinTask;
import gov.kcg.kgo.viewModel.backend.caseManagement.home.rs.CaseManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.home.rs.bean.CaseManagementHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rq.CaseManagerComboBoxQueryRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rs.CaseManagerComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rs.bean.CaseManagerComboBoxQueryViewForm;
import gov.kcg.kgo.viewModel.backend.caseManagement.organSelectQuery.rs.CaseManagementOrganSelectQueryRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.organSelectQuery.rs.bean.CaseManagementOrganSelectQueryViewForm;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rq.CaseManagementQueryRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.CaseManagementQueryRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.bean.CaseManagementQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.bean.CaseManagementQueryViewForm;
import gov.kcg.kgo.viewModel.backend.caseManagement.stateUpdate.rq.CaseManagementStatusUpdateRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.stateUpdate.rs.CaseManagementStatusUpdateRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.stateUpdate.rs.bean.CaseManagementStatusUpdateViewForm;
import gov.kcg.kgo.viewModel.compoent.SelectListItem;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

@Transactional(rollbackFor = Exception.class)
@Service("CaseManagementService")
public class CaseManagementServiceImpl extends KgoBackEndServiceImpl implements CaseManagementService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CaseManagementServiceImpl.class);

    private static final String SPLIT_SYMBOLE = ",";

    private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();

    private OrganUnitManagementServiceHelper organUnitManagementServiceHelper = OrganUnitManagementServiceHelper
            .getInstance();

    private AccountManagementServiceHelper accountManagementServicehelper = AccountManagementServiceHelper.getInstance();

    /**
     * commonServiceHelper
     **/
    private CaseManagementServiceHelper caseManagementServiceHelper = CaseManagementServiceHelper.getInstance();

    @Autowired
    private KgoCasesetRepository kgoCasesetRepository;

    @Autowired
    private KgoCasesetManagerRepository kgoCasesetManagerRepository;

    @Autowired
    private KgoCasesetTypeRepository kgoCasesetTypeRepository;

    @Autowired
    private KgoTaskSetRepository kgoTaskSetRepository;

    @Autowired
    private KgoCasesetCheckRepository kgoCasesetCheckRepository;

    @Autowired
    private KgoCasesetUnitRepository kgoCasesetUnitRepository;

    @Autowired
    private KgoCasesetOfficerRepository kgoCasesetOfficerRepository;

    @Autowired
    private KgoCasesetMemoRepository kgoCasesetMemoRepository;

    @Autowired
    private KgoCasesetGroupRepository kgoCasesetGroupRepository;

    @Autowired
    private KgoCasesetColumnRepository kgoCasesetColumnRepository;

    @Autowired
    private KgoCasesetMydataRepository kgoCasesetMydataRepository;

    @Autowired
    private KgoOrganRepository kgoOrganRepository;

    @Autowired
    private KgoCasesetTaskRepository kgoCasesetTaskRepository;

    @Autowired
    private KgoCasesetFormRepository kgoCasesetFormRepository;

    @Autowired
    private KgoCasesetAreaRepository kgoCasesetAreaRepository;

    @Autowired
    private KgoCasesetColumnChildRepository kgoCasesetColumnChildRepository;

    @Autowired
    private KgoTaskAchieveRepository kgoTaskAchieveRepository;

    @Autowired
    private KgoCaseMainRepository kgoCaseMainRepository;

    @Autowired
    CityCoinAPIService cityCoinAPIService;

    @Autowired
    CommonService commonService;

    @Autowired
    private TpiFlowRepository tpiFlowRepository;

    @Autowired
    private KgoCasesetGroupLevelRepository kgoCasesetGroupLevelRepository;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private KgoUserRepository kgoUserRepository;

    @Autowired
    private GeoKgoCasesetCrossReviewRepository geoKgoCasesetCrossReviewRepository;

    @Autowired
    private KgoCasesetRefundRatioRepository kgoCasesetRefundRatioRepository;

    @Autowired
    private GeoOrganDiscountRatioRepository geoOrganDiscountRatioRepository;
    /**
     * 服務案件清單-初始畫面
     */
    @Override
    public CaseManagementHomeRs caseManagementHome() {
        CaseManagementHomeViewForm viewForm = new CaseManagementHomeViewForm();
        CaseManagementHomeRs rs = new CaseManagementHomeRs();

        try {
            BackendLoginUserInfo loginUser = KgoUtil.getBackendLoginUser();

            /** 機關下拉式選單 */
            // 調整為依登入者角色，若為系統管理者則取得所有機關清單，其餘角色則取得所屬及底下機關
            ComboBox organComboBox = null;
            if (loginUser.getRoles().stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase)) {
                organComboBox = caseManagementServiceHelper.getGroupLevelOrganComboBox(StringUtils.EMPTY);
            } else {
                organComboBox = caseManagementServiceHelper.getCasesetOrganByLoginUserOrgan(loginUser.getOrgan(),
                        StringUtils.EMPTY);
            }

            /** 權責機關下拉式選單 **/
            ComboBox ownerOrganComboBox = organUnitManagementServiceHelper
                    .getOrganComboBoxWithUserRoleLimit(loginUser.getUserId());

            /** 管理者下拉式選單 **/
            ComboBox caseManagerComboBox = new ComboBox();// 因管理者清單需依權責機關, 初始先給空下拉選單 20201211 By Jay

            /** 案件資料 **/
            // Don't query all caseset in home, frontend will call query to get caseset
            // data, 20201207 By Jay
            List<CaseManagementQueryDataGrid> dataGridList = new ArrayList<>();

            viewForm.setOrganComboBox(organComboBox);
            viewForm.setOwnerOrganComboBox(ownerOrganComboBox);
            viewForm.setCaseManagerComboBox(caseManagerComboBox);
            viewForm.setGrid(dataGridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseManagementHome error " + e.getMessage(), e);
        }

        return rs;
    }

    /**
     * 服務案件管理-服務管理者下拉式選單查詢
     *
     * @param rq
     * @return
     */
    public CaseManagerComboBoxQueryRs caseManagerComboBoxQuery(CaseManagerComboBoxQueryRq rq) {
        CaseManagerComboBoxQueryRs rs = new CaseManagerComboBoxQueryRs();
        try {
            CaseManagerComboBoxQueryViewForm queryViewForm = new CaseManagerComboBoxQueryViewForm();
            ComboBox caseManagerBox = caseManagementServiceHelper.getCaseManagerBox(rq.getOrganId());

            queryViewForm.setCaseManagerComboBox(caseManagerBox);
            rs.setData(queryViewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in caseManagerComboBoxQuery,", e);
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH));
        }
        return rs;
    }

    @Autowired
    private ActReDeploymentRepository actReDeploymentRepository;

    /**
     * 服務案件清單-案件查詢
     */
    @Override
    public CaseManagementQueryRs caseManagementQuery(CaseManagementQueryRq rq) {
        CaseManagementQueryViewForm viewForm = new CaseManagementQueryViewForm();
        CaseManagementQueryRs rs = new CaseManagementQueryRs();
        try {

            try {
                BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
                List<String> roles = backendLoginUser.getRoles();
                if (!CollectionUtils.isEmpty(roles)) {
                    if (roles.contains(KgoRoleEnum.CASE_M.getValue()) && !roles.contains(KgoRoleEnum.UNIT_M.getValue())
                            && !roles.stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase)) {
                        rq.setManager(backendLoginUser.getUserId());
                    }
                }
            } catch (Exception e) {
                LOGGER.error("caseManagementQuery", e);
            }

            // 案件資料
            List<CaseManagementQueryDto> dtoList = kgoCasesetRepository.findAllCase(rq.getOrganId(),
                    rq.getOwnerOrganId(), rq.getCaseSetId(), rq.getCaseSetName(), rq.getManager());

            // When caseset with data in CaseMain, Not allowed user to delete caseset data,
            // add by Jay 20201207
            List<String> caseSetIds = dtoList.stream().map(CaseManagementQueryDto::getCaseSetId).collect(toList());
            List<KgoCaseMain> caseMainList = kgoCaseMainRepository.findByCaseSetIdIn(caseSetIds);
            Map<String, ArrayList<KgoCaseMain>> KgoCaseMainGroupByCaseSetId = caseMainList.stream()
                    .collect(Collectors.groupingBy(KgoCaseMain::getCaseSetId, toCollection(ArrayList::new)));

            List<CaseManagementQueryDataGrid> dataGridList = transferQueryDataToDataGrid(dtoList,
                    KgoCaseMainGroupByCaseSetId);
            ProcessDefinition pf = repositoryService.createProcessDefinitionQuery().processDefinitionKey("CaseApply")
                    .latestVersion().singleResult();
            ProcessDefinition auhtPf = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey("AuthApply").latestVersion().singleResult();
            String caseImage = getProcessImgStream(pf.getId());
            String authImage = getProcessImgStream(auhtPf.getId());
            viewForm.setAuthImage(authImage);
            viewForm.setCaseImage(caseImage);
            viewForm.setGrid(dataGridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in caseManagementQuery", e);
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH));
        }

        return rs;
    }

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    private static final String DEFAULT_FONT = "新細明體";

    public String getProcessImgStream(String flowDefId) {
        LOGGER.info("getProcessImgStream start");

        LOGGER.info("flowDefId:{}", flowDefId);

        // 獲取流程圖
        BpmnModel bpmnModel = repositoryService.getBpmnModel(flowDefId);

        // 這個類在5.22.0往上的版本中才有
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();

        // 繪制bpmnModel代表的流程的流程圖
        InputStream inputStream = diagramGenerator.generateDiagram(bpmnModel, new ArrayList<>(), new ArrayList<>(),
                DEFAULT_FONT, DEFAULT_FONT, DEFAULT_FONT);

        LOGGER.info("getProcessImgStream complete");
        String encoded = null;
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            encoded = Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encoded;
    }

    /**
     * 服務案件清單-案件狀態更改 (上架/下架/立即受理)
     */
    @Override
    public CaseManagementStatusUpdateRs statusUpdate(CaseManagementStatusUpdateRq rq) {
        CaseManagementStatusUpdateViewForm viewForm = new CaseManagementStatusUpdateViewForm();
        CaseManagementStatusUpdateRs rs = new CaseManagementStatusUpdateRs();
        try {
            String loginUserId = KgoUtil.getLoginUserId(); // TODO:待修正從 session 取得

            List<String> caseSetIdList = rq.getCaseSetIdList();
            String status = rq.getStatus();
            if (ObjectUtils.isEmpty(caseSetIdList) || StringUtils.isBlank(status)) {
                throw new Exception("All parameter value cannot be null.");
            }
            int successCnt = kgoCasesetRepository.updateStatus(caseSetIdList, status, loginUserId,
                    DateUtil.getCurrentTimestamp());
            if (successCnt != caseSetIdList.size()) {
                throw new Exception(
                        "one or more case update failed. caseSetIdList = " + caseSetIdList + ", status = " + status);
            }
            viewForm.setMsg(PublishStateEnum.getPublishStateEnum(status).getLabel() + SuccessMsg.UPDATE.getMsg());
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
            throw new KgoApiException("statusUpdate error " + e.getMessage(), e);
        }

        return rs;
    }

    @Autowired
    GeoKgoCasesetGroupOrganRepository geoKgoCasesetGroupOrganRepository;

    @Autowired
    GeoKgoCasesetColumnOrganRepository geoKgoCasesetColumnOrganRepository;

    @Autowired
    GeoKgoCasesetColumnChildOrganRepository geoKgoCasesetColumnChildOrganRepository;

    @Autowired
    GeoKgoCasesetAssociateRepository geoKgoCasesetAssociateRepository;

    @Autowired
    GeoKgoQuestionnaireAnswerRepository geoKgoQuestionnaireAnswerRepository;

    @Autowired
    GeoKgoQuestionnaireCasesetRepository geoKgoQuestionnaireCasesetRepository;

    @Autowired
    GeoKgoQuestionnaireCasesetTopicRepository geoKgoQuestionnaireCasesetTopicRepository;

    @Autowired
    GeoKgoQuestionnaireCasesetDetailRepository geoKgoQuestionnaireCasesetDetailRepository;

    /**
     * 服務案件清單-案件刪除
     * <p>
     * 將其他相關的 table data 一併刪除
     */
    @Override
    @Transactional
    public CaseManagementDeleteRs caseManagementDelete(CaseManagementDeleteRq rq) {
        CaseManagementDeleteViewForm viewForm = new CaseManagementDeleteViewForm();
        CaseManagementDeleteRs rs = new CaseManagementDeleteRs();
        KgoApiException error = null;
        OperationApiMemo memo = null;

        try {
            // 後台、刪除、SA
            memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_D, BackendFunctionCodeEnum.SA);

            String caseSetId = rq.getCaseSetId();
            // if CASE_MAIN has data, not allow to delete caseset!!
            List<KgoCaseMain> caseMains = kgoCaseMainRepository.findByCaseSetId(caseSetId);
            if (!caseMains.isEmpty()) {
                LOGGER.warn("CaseSetId:[{}] already have CaseMain Data, NOT ALLOWED TO DELETE!!", caseSetId);
                rs.setError(new ErrorResult(KgoBackEndApiError.FAIL_TO_DELETE));
                return rs;
            }

            // 調整刪除順序以及Table 20201204 by Jay
            // delete from KGO_CASESET_TASK where CaseSetId =(刪除CaseSetId);
            kgoCasesetTaskRepository.deleteByIdCaseSetId(caseSetId);

            // delete from KGO_TASK_ACHIEVE where CaseSetId =(刪除CaseSetId);
            kgoTaskAchieveRepository.deleteByIdCaseSetId(caseSetId);

            // 服務啟動清單刪除, delete from KGO_CASESET_TYPE where CaseSetId =(刪除CaseSetId);
            kgoCasesetTypeRepository.deleteByIdCaseSetId(caseSetId);

            // 案件管理員刪除, delete from KGO_CASESET_MANAGER where CaseSetId =(刪除CaseSetId);
            kgoCasesetManagerRepository.deleteByIdCaseSetId(caseSetId);

            // 案件MyData設定刪除, delete from KGO_CASESET_MYDATA where CaseSetId =(刪除CaseSetId);
            kgoCasesetMydataRepository.deleteByIdCaseSetId(caseSetId);

            // 書表維護, delete from KGO_CASESET_FORM where CaseSetId =(刪除CaseSetId);
            int delCasesetFormCount = kgoCasesetFormRepository.deleteByCaseSetId(caseSetId);
            // if 書表維護 has data, then delete files
            if (delCasesetFormCount > 0) {
                String formUploadFilsPath = commonService.getFormDownloadUploadBasePath(caseSetId);
                File formUploadFils = new File(formUploadFilsPath);
                if (formUploadFils.exists())
                    FileUtils.deleteDirectory(formUploadFils);
            }

            // 臨櫃申請說明, 網路申辦說明 刪除, delete from KGO_CASESET_MEMO where CaseSetId
            // =(刪除CaseSetId);
            int delCasesetMemoCount = kgoCasesetMemoRepository.deleteByCaseSetId(caseSetId);
            // Delete Upload File, if KGO_CASESET_MEMO has data
            if (delCasesetMemoCount > 0) {
                // 線上
                String applyTypeEUploadPath = commonService.getAttachmentUploadBasePath(caseSetId,
                        ApplyTypeEnum.E.getValue());
                File applyTypeEFiles = new File(applyTypeEUploadPath);
                if (applyTypeEFiles.exists()) {
                    FileUtils.deleteDirectory(applyTypeEFiles);
                }
                // 臨櫃
                String applyTypeCUploadPath = commonService.getAttachmentUploadBasePath(caseSetId,
                        ApplyTypeEnum.C.getValue());
                File applyTypeCFiles = new File(applyTypeCUploadPath);
                if (applyTypeCFiles.exists()) {
                    FileUtils.deleteDirectory(applyTypeCFiles);
                }
            }

            // 案件身份驗證設定刪除, delete from KGO_CASESET_CHECK where CaseSetId =(刪除CaseSetId);
            kgoCasesetCheckRepository.deleteByIdCaseSetId(caseSetId);

            // delete from KGO_CASESET_AREA where CaseSetId =(刪除CaseSetId);
            kgoCasesetAreaRepository.deleteByIdCaseSetId(caseSetId);

            // 案件受理承辦人設定刪除, delete from KGO_CASESET_OFFICER where CaseSetId =(刪除CaseSetId);
            kgoCasesetOfficerRepository.deleteByCaseSetId(caseSetId);

            // 案件業管受理機關設定刪除, delete from KGO_CASESET_UNIT where CaseSetId =(刪除CaseSetId);
            kgoCasesetUnitRepository.deleteByCaseSetId(caseSetId);

            // 案件設定群組刪除, delete from KGO_CASESET_GROUP where CaseSetId =(刪除CaseSetId);
            kgoCasesetGroupRepository.deleteByIdCaseSetId(caseSetId);

            // 案件設定欄位刪除, delete from KGO_CASESET_COLUMN where CaseSetId =(刪除CaseSetId);
            kgoCasesetColumnRepository.deleteByIdCaseSetId(caseSetId);

            // delete from KGO_CASESET_COLUMN_CHILD where CaseSetId =(刪除CaseSetId);
            kgoCasesetColumnChildRepository.deleteByIdCaseSetId(caseSetId);

            // 跨機關檢視 案件與機關對應 刪除
            geoKgoCasesetCrossReviewRepository.deleteAllByIdCaseSetId(caseSetId);

            // 跨機關檢視 案件設定群組刪除
            geoKgoCasesetGroupOrganRepository.deleteByIdCaseSetId(caseSetId);

            // 跨機關檢視 案件設定群組刪除
            geoKgoCasesetGroupOrganRepository.deleteByIdCaseSetId(caseSetId);

            // 跨機關檢視 案件設定欄位刪除
            geoKgoCasesetColumnOrganRepository.deleteByIdCaseSetId(caseSetId);

            // 跨機關檢視 案件設定欄位刪除(子欄位)
            geoKgoCasesetColumnChildOrganRepository.deleteByIdCaseSetId(caseSetId);

            //關聯服務 此服務為主 關聯其他
            geoKgoCasesetAssociateRepository.deleteById_CasesetId(caseSetId);

            //關聯服務 其他主服務關聯到此服務
            geoKgoCasesetAssociateRepository.deleteById_AssociateCasesetId(caseSetId);

            LOGGER.info("caseSetId=" + caseSetId);

            List<GeoKgoQuestionnaireCasesetTopic> casesetTopics = geoKgoQuestionnaireCasesetTopicRepository.findAllByCaseSetId(caseSetId);

            if (casesetTopics != null && casesetTopics.size() > 0) {
                for (GeoKgoQuestionnaireCasesetTopic topic : casesetTopics) {
                    //服務與問卷關聯 子題
                    geoKgoQuestionnaireCasesetDetailRepository.deleteByTopicCasesetId(topic.getTopicCasesetId());
                } //for (GeoKgoQuestionnaireCasesetTopic topic
                //服務與問卷關聯 主題
                geoKgoQuestionnaireCasesetTopicRepository.deleteByCaseSetId(caseSetId);
                //服務與問卷關聯
                geoKgoQuestionnaireCasesetRepository.deleteByCaseSetId(caseSetId);
            } //if (casesetTopics != null && casesetTopics.size()>0)

            // 主檔資料刪除, delete from KGO_CASESET where CaseSetId =(刪除CaseSetId);
            kgoCasesetRepository.deleteById(caseSetId);

            viewForm.setMsg(SuccessMsg.DELETE.getMsg());
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_DELETE.getErrorMsgKey());
            error = new KgoApiException("caseManagementDelete error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn("案件編號", rq.getCaseSetId()));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }

        return rs;
    }

    /**
     * 服務案件清單-案件排序-初始畫面
     */
    @Override
    public CaseManagementCaseOrderHomeRs caseManagementCaseOrderHome(CaseManagementCaseOrderHomeRq rq) {
        CaseManagementCaseOrderHomeViewForm viewForm = new CaseManagementCaseOrderHomeViewForm();
        CaseManagementCaseOrderHomeRs rs = new CaseManagementCaseOrderHomeRs();
        try {
            Integer organId = rq.getOrganId();
            List<CaseManagementQueryDto> dtoList = kgoCasesetRepository.findAllCase(organId);

            // transform data from CaseManagementQueryDto to CaseManagementQueryDataGrid
            List<CaseManagementCaseOrderDataGrid> dataGridList = dtoList.stream().map(l -> {
                CaseManagementCaseOrderDataGrid grid = new CaseManagementCaseOrderDataGrid();
                grid.setOrder(l.getSort());
                grid.setCaseSetId(l.getCaseSetId());
                grid.setCaseSetName(l.getCaseSetName());
                grid.setManagerName(l.getManagerName());
                grid.setOrganId(l.getOrganId());
                grid.setOrganName(l.getOrganName());
                return grid;
            }).collect(Collectors.toList());

            viewForm.setGrid(dataGridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseManagementCaseOrderHome error " + e.getMessage(), e);
        }

        return rs;
    }

    /**
     * 服務案件清單-案件排序-修改
     */
    @Override
    public CaseManagementCaseOrderSaveRs caseManagementCaseOrderSave(CaseManagementCaseOrderSaveRq rq) {
        CaseManagementCaseOrderSaveViewForm viewForm = new CaseManagementCaseOrderSaveViewForm();
        CaseManagementCaseOrderSaveRs rs = new CaseManagementCaseOrderSaveRs();
        try {
            String loginUserId = KgoUtil.getLoginUserId();

            List<CaseManagementCaseOrderSaveDataGrid> pqDataGridList = rq.getGrid();
            List<KgoCaseset> kgoCasesetList = pqDataGridList.stream().map(l -> {
                KgoCaseset kgoCaseSet = kgoCasesetRepository.getOne(l.getCaseSetId());
                kgoCaseSet.setSort(l.getOrder());
                kgoCaseSet.setUpdateUser(loginUserId);
                kgoCaseSet.setUpdateTime(DateUtil.getCurrentTimestamp());
                return kgoCaseSet;
            }).collect(Collectors.toList());
            kgoCasesetRepository.saveAll(kgoCasesetList);
            viewForm.setMsg(SuccessMsg.UPDATE.getMsg());
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
            throw new KgoApiException("caseManagementCaseOrderSave error " + e.getMessage(), e);
        }

        return rs;
    }

    /**
     * 服務案件清單-案件維護-初始畫面
     */
    @Override
    public CaseManagementEditHomeRs caseManagementEditHome(CaseManagementEditHomeRq rq) {
        CaseManagementEditHomeViewForm viewForm = new CaseManagementEditHomeViewForm();
        CaseManagementEditHomeRs rs = new CaseManagementEditHomeRs();

        BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
        try {
            String caseSetId = rq.getCaseSetId();
            String caseSetName = StringUtils.EMPTY;
            String linkUrl = StringUtils.EMPTY;
            String managerName = StringUtils.EMPTY;
            String managerId = StringUtils.EMPTY;
            String ownerOrgan = StringUtils.EMPTY;
            List<CityCoinTask> taskList = null;
            Boolean cityCoin = false;

            /**GEO 20211109 add 府內線上服務 機關審核表單 跨機關檢視*/
            Integer isOpenForOrgan = null;
            Integer isOpenOrganForm = null;
            Boolean isAvailableCrossReview = null;

            /**GEO 20221025 是否繳費欄位 */
            String category = null;
            Boolean activeFlow = null;
            CaseSetPayModel paySetting = new CaseSetPayModel();


            BackendLoginUserInfo beUser = KgoUtil.getBackendLoginUser();
            ComboBox serviceToComboBox = commonServiceHelper.getComboBoxWithEnum(ServiceToEnum.class);

            /** === 2020.12.06 caseTypeComboBox 增加動態流程 flow ==== */
//			ComboBox caseTypeComboBox = caseManagementServiceHelper.getCodeTypeComboBox(CodeTypeEnum.FLOW.getValue());

            // modify: 2020.12.24 調整: 預設一般流程 SA actFlowType =  (一般) + 動態流程
            ComboBox caseTypeComboBox = new ComboBox();
            caseTypeComboBox.add(CaseTypeEnum.SA.getLabel(), CaseTypeEnum.SA.getValue(), ActFlowTypeEnum.N.getType());

            // 查找啟用的流程
            boolean isDefault = false;
            List<TpiFlow> tpiFlowList = tpiFlowRepository.findByFlowEnableAndOrganIdAndIsDefault(ActFlowEnableEnum.Y.getCode(),
                    beUser.getOrgan(), isDefault);
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(tpiFlowList)) {
                tpiFlowList.stream()
                        .sorted(Comparator.comparing(TpiFlow::getCreateTime,
                                Comparator.nullsLast(Comparator.reverseOrder())))
                        .forEach(f -> caseTypeComboBox.add(f.getFlowName(), f.getFlowId(), f.getFlowType()));
            }
            /** === caseTypeComboBox 增加動態流程 flow ==== */

            ComboBox caseFlowTypeComboBox = commonServiceHelper.getComboBoxWithEnum(CaseFlowTypeEnum.class);
            // 新增時，機關分類下拉選單改為系統管理者外，其餘角色只能看到所屬機關
            ComboBox organComboBox = null;
            if (backendLoginUser.getRoles().stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase)) {
                organComboBox = caseManagementServiceHelper.getGroupLevelOrganComboBox(StringUtils.EMPTY);
            } else {
                organComboBox = caseManagementServiceHelper.getOrganByLoginUserOrgan(backendLoginUser.getOrgan(),
                        StringUtils.EMPTY);
            }
            ComboBox linkTypeComboBox = commonServiceHelper.getComboBoxWithEnum(LinkTypeEnum.class);
            ComboBox roleComboBox = caseManagementServiceHelper.getGroupLevelRoleComboBox(StringUtils.EMPTY);
            if (StringUtils.isNotEmpty(rq.getCaseSetId())) {
                List<KgoCasesetGroupLevel> role = kgoCasesetGroupLevelRepository
                        .findByCaseSetIdAndMainType(rq.getCaseSetId(), "Role");
                for (SelectListItem option : roleComboBox.getOptions()) {
                    KgoCasesetGroupLevel kgoCasesetGroupLevel = role.stream()
                            .filter(item -> item.getGroupLevelSeq().equals(option.getValue())).findAny().orElse(null);
                    if (null != kgoCasesetGroupLevel) {
                        option.setSelected(true);
                    }
                }
            }
            ComboBox serviceComboBox = caseManagementServiceHelper.getGroupLevelServiceComboBox(StringUtils.EMPTY);
            if (StringUtils.isNotEmpty(rq.getCaseSetId())) {
                List<KgoCasesetGroupLevel> service = kgoCasesetGroupLevelRepository
                        .findByCaseSetIdAndMainType(rq.getCaseSetId(), "Service");
                for (SelectListItem option : serviceComboBox.getOptions()) {
                    KgoCasesetGroupLevel kgoCasesetGroupLevel = service.stream()
                            .filter(item -> item.getGroupLevelSeq().equals(option.getValue())).findAny().orElse(null);
                    if (null != kgoCasesetGroupLevel) {
                        option.setSelected(true);
                    }
                }
            }
            /**GEO 20211115 add 跨機關檢視*/
            ComboBox crossReviewComboBox = organUnitManagementServiceHelper.getOrganComboBox();
            if (StringUtils.isNotEmpty(rq.getCaseSetId())) {
                List<GeoKgoCasesetCrossReview> casesetCrossReviews = geoKgoCasesetCrossReviewRepository.findByIdCaseSetId(rq.getCaseSetId());
                for (SelectListItem option : crossReviewComboBox.getOptions()) {
                    GeoKgoCasesetCrossReview review = casesetCrossReviews.stream()
                            .filter(item -> item.getId().getOrganId().equals(option.getValue())).findAny().orElse(null);
                    if (null != review) {
                        option.setSelected(true);
                    }
                }
            }

            // 新增時，機關下拉選單改為系統管理者外，其餘角色只能看到所屬機關，並預設選取登入者機關
            ComboBox ownerOrganComboBox = organUnitManagementServiceHelper
                    .getOrganComboBoxByUserRole(backendLoginUser.getUserId(), backendLoginUser.getOrgan());
            List<CheckBox> serviceCheckBox = caseManagementServiceHelper.getApplyTypeCheckBox();

            if (StringUtils.isNotBlank(caseSetId)) {
                // 取得 KgoCaseset 資料
                KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(caseSetId);

                // 取得 案件管理者相關資料 (userId, userName, organUnitName)
                CasesetManagerQueryByCaseSetIdDto dto = kgoCasesetManagerRepository
                        .getManagerInfoByCasesetId(caseSetId);

                // 取得服務啟用清單
                List<KgoCasesetType> kgoCasesetTypeList = kgoCasesetTypeRepository.findByIdCaseSetId(caseSetId);
                kgoCasesetTypeList.forEach(l -> {
                    serviceCheckBox.forEach(l2 -> {
                        if (StringUtils.equalsIgnoreCase(l2.getValue(), l.getId().getApplyType())) {
                            l2.setSelected(true);
                        }
                    });
                });

                serviceToComboBox.setSelectedVal(kgoCaseset.getServiceTo());

                /** 動態流程 塞flowId */
                String caseTypeComboVal = kgoCaseset.getCaseType();
                // 不等於 SA、SCA、URA 塞flowId (SCA、URA 已停用)
                CaseTypeEnum caseTypeEnum = CaseTypeEnum.getEnum(kgoCaseset.getCaseType());
                if (!CaseTypeEnum.SA.equals(caseTypeEnum) && !CaseTypeEnum.SCA.equals(caseTypeEnum) && !CaseTypeEnum.URA.equals(caseTypeEnum)) {
                    caseTypeComboVal = kgoCaseset.getFlowId();
                    caseTypeComboBox.setSelectedVal(caseTypeComboVal);
                } else {
                    // modify 2020.12.24 非動態流程塞 caseTypeEnum值
                    caseTypeComboBox.setSelectedVal(caseTypeEnum.getValue());
                }
                /** 動態流程 塞flowId */

                caseSetName = kgoCaseset.getCaseSetName();
                cityCoin = kgoCaseset.getCityCoin();
                caseFlowTypeComboBox.setSelectedVal(kgoCaseset.getCaseFlowType());
                linkUrl = kgoCaseset.getLinkUrl();
                linkTypeComboBox.setSelectedVal(kgoCaseset.getLinkType());
                organComboBox.setSelectedVal(kgoCaseset.getOrgan());
                roleComboBox.setSelectedVal(kgoCaseset.getRole());
                serviceComboBox.setSelectedVal(kgoCaseset.getService());
                if (ObjectUtils.isNotEmpty(dto)) {
                    ownerOrgan = dto.getOrganId();
                    managerId = dto.getUserId();
                    if (StringUtils.isNotEmpty(managerId)) {
                        String messageSplit = messageUtil.getMessage("kcg.common.english.split.character");
                        managerId = Arrays.asList(managerId.split(messageSplit)).stream().distinct()
                                .collect(Collectors.joining(messageSplit));
                    }
                    managerName = dto.getName();
                    if (StringUtils.isNotEmpty(managerName)) {
                        String messageSplit = messageUtil.getMessage("kcg.common.chinese.split.character");
                        managerName = Arrays.asList(managerName.split(messageSplit)).stream().distinct()
                                .collect(Collectors.joining(messageSplit));
                    }
                }
                taskList = getCityCoinState(caseSetId);
                ownerOrganComboBox = organUnitManagementServiceHelper.getOneOrganComboBox(ownerOrgan);

                /**GEO 20211109 add 府內線上服務 機關審核表單 跨機關檢視*/
                isOpenForOrgan = kgoCaseset.getIsOpenForOrgan();
                isOpenOrganForm = kgoCaseset.getIsOpenOrganForm() == null ? 0 : kgoCaseset.getIsOpenOrganForm();
                isAvailableCrossReview = kgoCaseset.getAvailableCrossReview() != null && kgoCaseset.getAvailableCrossReview();

                category = kgoCaseset.getCasesetCategory();
                activeFlow = kgoCaseset.getActiveFlow();
                //繳費內容
                paySetting.setNeedPay(kgoCaseset.getNeedPay());
                paySetting.setPayDeadline((kgoCaseset.getPayDeadline()));
                paySetting.setRefundDeadline(kgoCaseset.getRefundDeadline());
                List<KgoCasesetRefundRatio> ratioList = kgoCasesetRefundRatioRepository.findByCasesetId(kgoCaseset.getCaseSetId());
                List<GeoCaseSetRefundModel> radioModel = ratioList.stream().map(entity -> {
                    GeoCaseSetRefundModel model = new GeoCaseSetRefundModel();
                    model.setRefundRatio(entity.getRefundRatio());
                    model.setEndDays(entity.getEndDays());
                    model.setFromDays(entity.getFromDays());
                    return model;
                }).collect(toList());
                paySetting.setRefundList(radioModel);
            }
            // 取得機關名稱
            KgoOrgan kgoOrgan = kgoOrganRepository.findByOrganId(ownerOrganComboBox.getSelectedVal());
            List<Map<String, String>> categoryMap = Arrays.stream(CaseSetCategoryEnum.values()).map(e -> {
                Map<String, String> map = new HashMap<>();
                map.put("label", e.getLabel());
                map.put("value", e.getValue());
                return map;
            }).collect(Collectors.toList());
            viewForm.setCaseSetId(caseSetId); // 服務案件編號
            viewForm.setServiceToComboBox(serviceToComboBox); // 案件服務對象
            viewForm.setCaseTypeComboBox(caseTypeComboBox); // 作業流程
            viewForm.setCaseSetName(caseSetName); // 案件名稱
            viewForm.setCityCoin(cityCoin); // 城市幣啟用狀態
            viewForm.setCaseFlowTypeComboBox(caseFlowTypeComboBox); // 作業流程分類
            viewForm.setLinkTypeComboBox(linkTypeComboBox); // 站外連結方式
            viewForm.setLinkUrl(linkUrl); // 站外連結網址
            viewForm.setOrganComboBox(organComboBox); // 機關分類
            viewForm.setRoleComboBox(roleComboBox); // 角色分類
            viewForm.setServiceComboBox(serviceComboBox); // 服務分類
            viewForm.setServerCheckList(serviceCheckBox); // 服務啟用
            viewForm.setOwnerOrganComboBox(ownerOrganComboBox); // 權責機關
            viewForm.setOwnerOrganLabel(kgoOrgan.getOrganName()); // 權責機關名稱
            viewForm.setOwnerOrganValue(ownerOrganComboBox.getSelectedVal()); // 權責機關代碼
            viewForm.setManagerId(managerId); // 管理者代碼字串
            viewForm.setManagerName(managerName); // 管理者名稱字串
            viewForm.setTaskList(taskList); // 城市幣任務
            /**GEO 20211109 add 府內線上服務 機關審核表單 跨機關檢視*/
            viewForm.setIsOpenForOrgan(isOpenForOrgan); //是否開啟府內線上服務
            viewForm.setIsOpenOrganForm(isOpenOrganForm);//是否開啟機關審核表單
            viewForm.setAvailableCrossReview(isAvailableCrossReview); //是否開啟跨機關檢視
            viewForm.setCrossReviewComboBox(crossReviewComboBox); // 跨機關檢視
            //加入服務類別清單及是否審核
            viewForm.setCategoryType(categoryMap);
            viewForm.setCaseSetCategory(category);
            viewForm.setActiveFlow(activeFlow);
            viewForm.setPaySetting(paySetting);

            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseManagementEditHome error " + e.getMessage(), e);
        }

        return rs;
    }

    /**
     * 服務案件清單-案件維護-儲存
     */
    @Override
    public CaseManagementCaseSaveRs caseManagementCaseSave(CaseManagementCaseSaveRq rq) {
        LOGGER.info("CaseManagementServiceImpl caseManagementCaseSave rq: " + rq);
        CaseManagementCaseSaveViewForm viewForm = new CaseManagementCaseSaveViewForm();
        CaseManagementCaseSaveRs rs = new CaseManagementCaseSaveRs();
        KgoApiException error = null;
        OperationApiMemo memo = null;

        try {

            // 後台、新增或儲存、案件維護
            SysLogExeType sysLogExeType = StringUtils.isBlank(rq.getCaseSetId()) ? SysLogExeType.TYPE_A
                    : SysLogExeType.TYPE_U;
            memo = super.genOperationMemo(SystemTypeEnum.B, sysLogExeType, BackendFunctionCodeEnum.CaseM);
            String loginUserId = KgoUtil.getLoginUserId(); // TODO:修改成從 session 取得

            String msg = StringUtils.isBlank(rq.getCaseSetId()) ? SuccessMsg.INSERT.getMsg()
                    : SuccessMsg.UPDATE.getMsg();
            String caseSetId = StringUtils.isBlank(rq.getCaseSetId()) ? KgoUtil.getNextCaseSetId() : rq.getCaseSetId();
            String serviceTo = rq.getServiceTo();
            String caseSetName = rq.getCaseSetName();
            String caseType = rq.getCaseType();
            String caseFlowType = rq.getCaseFlowType();
            String linkType = rq.getLinkType();
            String linkUrl = rq.getLinkUrl();
            String role = rq.getRole();
            String service = rq.getService();
            String organ = rq.getOrgan();
            String ownerOrgan = rq.getOwnerOrgan();
            String crossReview = rq.getCrossReview();
            Boolean activeFlow = rq.getActiveFlow();
            Boolean cityCoin = rq.getCityCoin();
            String caseSetCategory = rq.getCaseSetCategory();
            CaseSetPayModel paySetting = rq.getPaySetting();
            /**GEO 20211109 add 府內線上服務 機關審核表單 跨機關檢視*/
            Integer isOpenForOrgan = rq.getIsOpenForOrgan() == null ? 0 : rq.getIsOpenForOrgan();
            Integer isOpenOrganForm = rq.getIsOpenOrganForm();
            Boolean isAvailableCrossReview = rq.getAvailableCrossReview();
            LOGGER.info("CaseManagementServiceImpl caseManagementCaseSave caseType: " + caseType);

            String nextOrder = kgoCasesetRepository.findNextOrderByOrganId(organ);
            List<String> managerIdList = Arrays.asList(rq.getManagerId().split(SPLIT_SYMBOLE));
            List<String> caseSetTypeList = Arrays.asList(rq.getCaseSetType().split(SPLIT_SYMBOLE));

            boolean isExist = kgoCasesetRepository.existsById(caseSetId);
            /**
             * KGO_CASESET 資料儲存
             */
            KgoCaseset kgoCaseset = isExist ? kgoCasesetRepository.getOne(caseSetId) : new KgoCaseset();
            kgoCaseset.setCaseSetId(caseSetId);
            kgoCaseset.setServiceTo(serviceTo);
            kgoCaseset.setCaseSetName(caseSetName);

            if (cityCoin != null)
                kgoCaseset.setCityCoin(cityCoin);

            kgoCaseset.setCaseType(caseType);
            /** === 2020.12.06 新增動態流程 flowId ===  */
            // TODO 之後動態改傳 flowId (非SA、SCA、URA 代表是動態流程存入flowId)

            // caseType = flowId (UUID)值
            CaseTypeEnum caseTypeEnum = CaseTypeEnum.getEnum(caseType);
            if (caseTypeEnum == null) {
                kgoCaseset.setFlowId(caseType);
                // 2020.12.15 新增 動態流程 type
                kgoCaseset.setCaseType(CaseTypeEnum.D.getValue());
            }

            // 更改為原流程 flowId 清空
            if (CaseTypeEnum.SA.equals(caseTypeEnum) || CaseTypeEnum.URA.equals(caseTypeEnum) || CaseTypeEnum.SCA.equals(caseTypeEnum)) {
                kgoCaseset.setFlowId("");
            }
            /** === 2020.12.06 新增動態流程 flowId ===   */

            kgoCaseset.setCaseFlowType(caseFlowType);
            kgoCaseset.setLinkType(linkType);
            kgoCaseset.setLinkUrl(linkUrl);
//			kgoCaseset.setRole(role);
//			kgoCaseset.setService(service);

            //是否審查
            kgoCaseset.setActiveFlow(activeFlow);
            //案件類型, 一般服務: common , 場地預約: site , 活動預約: activity , 退費服務: refund
            //檢核如果已經有民眾申請預約則不提供更改預約設定
            List<KgoCaseMain> caseMainList = kgoCaseMainRepository.findByCaseSetId(caseSetId);
            CaseSetCategoryEnum categoryType = CaseSetCategoryEnum.getApplyTypeEnum(caseSetCategory);
            if(caseMainList.size() == 0){
                kgoCaseset.setCasesetCategory(caseSetCategory);
                //場地或活動預約輸入繳費設定
                if (CaseSetCategoryEnum.SITE.equals(categoryType) || CaseSetCategoryEnum.ACTIVITY.equals(categoryType)) {
                    validatePaySetting(paySetting);
                    //免驗證服務案件不可變更為線上繳費功能
                    if(isExist && paySetting.getNeedPay() ){
                        List<KgoCasesetCheck> kgoCasesetCheckList = kgoCasesetCheckRepository.findAllByIdCaseSetId(caseSetId);
                        boolean invalidCheckType = kgoCasesetCheckList.stream().map(check -> check == null ? "UNKNOWN_CHECK_TYPE" : check.getId().getCheckType())
                                                   .anyMatch(type->CheckTypeEnum.NAN.getValue().equals(type));
                        if(invalidCheckType) throw  new KgoApiException(new ErrorResult(KgoBackEndApiError.PAY_SETTING_CHECKTYPE_ERROR));
                    }

                    kgoCaseset.setNeedPay(paySetting.getNeedPay());
                    if(paySetting.getNeedPay()){
                        kgoCaseset.setPayDeadline(paySetting.getPayDeadline());
                        kgoCaseset.setRefundDeadline(paySetting.getRefundDeadline());
                        List<GeoCaseSetRefundModel> refundlist = paySetting.getRefundList();
                        if(refundlist.size() > 0 ) kgoCasesetRefundRatioRepository.deleteAllByCasesetId(caseSetId);
                        List<KgoCasesetRefundRatio> refundEntityList = refundlist.stream().map(refund -> {
                            KgoCasesetRefundRatio entity = new KgoCasesetRefundRatio();
                            entity.setCasesetId(caseSetId);
                            entity.setRefundRatio(refund.getRefundRatio());
                            entity.setEndDays(refund.getEndDays());
                            entity.setFromDays(refund.getFromDays());
                            return entity;
                        }).collect(Collectors.toList());
                        if (refundEntityList.size() > 0) kgoCasesetRefundRatioRepository.saveAll(refundEntityList);
                    }else{
                        kgoCaseset.setPayDeadline(paySetting.getPayDeadline());
                        kgoCaseset.setRefundDeadline(paySetting.getRefundDeadline());
                        kgoCasesetRefundRatioRepository.deleteAllByCasesetId(caseSetId);
                    }

                }
            }else if (caseMainList.size() > 0 && CaseSetCategoryEnum.SITE.equals(categoryType) || CaseSetCategoryEnum.ACTIVITY.equals(categoryType)){
                msg += "\r\n 服務已開放民眾預約，服務及預約相關設定不提供修改。";
            }




            kgoCasesetGroupLevelRepository.deleteByCaseSetIdAndMainType(caseSetId, "Role");
            if (StringUtils.isNotEmpty(role)) {
                List<String> roleList = Arrays.asList(role.split(","));
                if (!CollectionUtils.isEmpty(roleList)) {
                    List<KgoCasesetGroupLevel> objects = new ArrayList<>();
                    for (String roleItem : roleList) {
                        KgoCasesetGroupLevel kgoCasesetGroupLevel = new KgoCasesetGroupLevel();
                        kgoCasesetGroupLevel.setCaseSetId(caseSetId);
                        kgoCasesetGroupLevel.setGroupLevelSeq(roleItem);
                        kgoCasesetGroupLevel.setMainType("Role");
                        objects.add(kgoCasesetGroupLevel);
                    }
                    kgoCasesetGroupLevelRepository.saveAllBatch(objects);
                }
            }
            kgoCasesetGroupLevelRepository.deleteByCaseSetIdAndMainType(caseSetId, "Service");
            if (StringUtils.isNotEmpty(service)) {
                List<String> serviceList = Arrays.asList(service.split(","));
                if (!CollectionUtils.isEmpty(serviceList)) {
                    List<KgoCasesetGroupLevel> objects = new ArrayList<>();
                    for (String roleItem : serviceList) {
                        KgoCasesetGroupLevel kgoCasesetGroupLevel = new KgoCasesetGroupLevel();
                        kgoCasesetGroupLevel.setCaseSetId(caseSetId);
                        kgoCasesetGroupLevel.setGroupLevelSeq(roleItem);
                        kgoCasesetGroupLevel.setMainType("Service");
                        objects.add(kgoCasesetGroupLevel);
                    }
                    kgoCasesetGroupLevelRepository.saveAllBatch(objects);
                }
            }
            /**GEO 20211115 add 跨機關檢視*/
            geoKgoCasesetCrossReviewRepository.deleteAllByIdCaseSetId(caseSetId);
            if (StringUtils.isNotEmpty(crossReview) && isAvailableCrossReview) {
                List<String> crossReviewList = Arrays.asList(crossReview.split(","));
                if (!CollectionUtils.isEmpty(crossReviewList)) {
                    List<GeoKgoCasesetCrossReview> objects = new ArrayList<>();
                    for (String reviewItem : crossReviewList) {
                        GeoKgoCasesetCrossReview geoKgoCasesetCrossReview = new GeoKgoCasesetCrossReview();
                        GeoKgoCasesetCrossReviewPK pk = new GeoKgoCasesetCrossReviewPK();
                        pk.setCaseSetId(caseSetId);
                        pk.setOrganId(reviewItem);
                        geoKgoCasesetCrossReview.setId(pk);
                        objects.add(geoKgoCasesetCrossReview);
                    }
                    geoKgoCasesetCrossReviewRepository.saveAll(objects);
                }
            } //if (StringUtils.isNotEmpty(crossReview))
            kgoCaseset.setOrgan(organ);
            kgoCaseset.setOwnerOrgan(ownerOrgan);
            kgoCaseset.setMail(StringUtils.EMPTY);
            kgoCaseset.setSort(StringUtils.isBlank(nextOrder) ? 1 : Integer.valueOf(nextOrder));
            kgoCaseset.setUpdateTime(DateUtil.getCurrentTimestamp());
            kgoCaseset.setUpdateUser(loginUserId);
            /**GEO 20211109 add 府內線上服務 機關審核表單 跨機關檢視*/
            kgoCaseset.setIsOpenForOrgan(isOpenForOrgan);
            kgoCaseset.setIsOpenOrganForm(isOpenOrganForm);
            kgoCaseset.setAvailableCrossReview(isAvailableCrossReview);

            if (!isExist && !caseType.equalsIgnoreCase(CaseTypeEnum.SCA.getValue())) {
                /**
                 * 不須存入：1. 作業流程為服務案件新增流程 (SCA) 2.案件成維護狀態
                 *
                 * 其餘則存Off
                 */
                kgoCaseset.setStatus(PublishStateEnum.OFF.getValue());
            }
            if (StringUtils.isBlank(rq.getCaseSetId())) {
                kgoCaseset.setCreateTime(DateUtil.getCurrentTimestamp());
                kgoCaseset.setCreateUser(loginUserId);
            }
            kgoCasesetRepository.save(kgoCaseset);

            /**
             * KGO_CASESET_TYPE 資料儲存
             */
            kgoCasesetTypeRepository.deleteByIdCaseSetId(caseSetId);
            List<KgoCasesetType> kgoCasesetTypeList = caseSetTypeList.stream().map(s -> {
                KgoCasesetType kgoCasesetType = new KgoCasesetType();
                KgoCasesetTypePK KgoCasesetTypePK = new KgoCasesetTypePK();
                KgoCasesetTypePK.setApplyType(s);
                KgoCasesetTypePK.setCaseSetId(caseSetId);
                kgoCasesetType.setId(KgoCasesetTypePK);
                return kgoCasesetType;
            }).collect(Collectors.toList());
            kgoCasesetTypeRepository.saveAll(kgoCasesetTypeList);

            /**
             * KGO_CASESET_MANAGER 資料儲存
             */
            kgoCasesetManagerRepository.deleteByIdCaseSetId(caseSetId);
            List<KgoCasesetManager> kgoCasesetManagerList = managerIdList.stream().map(s -> {
                KgoCasesetManager kgoCasesetManager = new KgoCasesetManager();
                KgoCasesetManagerPK id = new KgoCasesetManagerPK();
                id.setCaseSetId(caseSetId);
                id.setManager(s);
                kgoCasesetManager.setId(id);
                return kgoCasesetManager;
            }).collect(Collectors.toList());
            kgoCasesetManagerRepository.saveAll(kgoCasesetManagerList);

            viewForm.setCaseSetId(caseSetId);
            viewForm.setMsg(msg);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
            error = new KgoApiException("caseManagementCaseSave error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn("案件編號", rs.getData() != null ?
                    rs.getData().getCaseSetId() : "None"));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }

        return rs;
    }

    /**
     * 取得城市幣任務狀態清單
     * <p>
     * TODO:待實際測試..
     *
     * @param caseSetId
     * @return
     * @throws JsonMappingException
     * @throws JsonProcessingException
     * @throws ParseException
     */
    private List<CityCoinTask> getCityCoinState(String caseSetId) {
        List<CityCoinTask> taskList = new LinkedList<CityCoinTask>();
        List<TaskSetQueryByCaseSetIdDto> dtoList = kgoTaskSetRepository.findTaskByCaseSetId(caseSetId);

        taskList = dtoList.stream().map(dto -> {
            boolean enable = dto.getIsPublish();

//			/** call coin city search api **/
//			SearchRs searchRs = cityCoinAPIService.search(NumberUtils.toInt(dto.getTaskSeq()));
//			try {
//				Date startTime = DateUtil.strToDate(searchRs.getResult().getStartTime(),
//						DateUtil.PATTEN_FULL_TIME_MS_T);
//				Date endTime = DateUtil.strToDate(searchRs.getResult().getEndTime(), DateUtil.PATTEN_FULL_TIME_MS_T);
//				Date currentDate = new Date();
//				enable = startTime.before(currentDate) && endTime.after(currentDate) && dto.getIsPublish() ? true
//						: false;
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}

            if (enable) {

                String[] activityDate = StringUtils.defaultString(dto.getActivityDate(), "").split("~");
                if (activityDate.length > 1) {
                    try {
                        Date startTime = DateUtil.strToDate(activityDate[0], DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH);
                        Date endTime = DateUtil.strToDate(activityDate[1], DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH);
                        Date currentDate = new Date();
                        enable = startTime.before(currentDate) && endTime.after(currentDate) ? true : false;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            /** set result **/
            CityCoinTask task = new CityCoinTask();
            // task.setTitle(searchRs.getResult().getTitle());
            task.setTitle(dto.getActivityName());
            task.setEnable(enable);
            return task;
        }).collect(Collectors.toList());

        return taskList;
    }

    /**
     * transform data from CaseManagementQueryDto to CaseManagementQueryDataGrid
     *
     * @param dtoList
     * @return
     */
    private List<CaseManagementQueryDataGrid> transferQueryDataToDataGrid(List<CaseManagementQueryDto> dtoList,
                                                                          Map<String, ArrayList<KgoCaseMain>> kgoCaseMain) {
        Map<String, TpiFlow> map = null;

        List<String> flowIds = dtoList.stream().filter(d -> StringUtils.isNotBlank(d.getFlowId()))
                .map(CaseManagementQueryDto::getFlowId).collect(toList());
        List<TpiFlow> tpiFlows = tpiFlowRepository.findAllById(flowIds);
        map = tpiFlows.stream().collect(Collectors.toMap(c -> c.getFlowId(), c -> c));
        Map<String, TpiFlow> finalMap = map;
        return dtoList.stream().map(l -> {
            CaseManagementQueryDataGrid grid = new CaseManagementQueryDataGrid();
            grid.setCaseSetId(l.getCaseSetId());
            grid.setCaseSetName(l.getCaseSetName());
            grid.setCaseType(l.getCaseType());
            grid.setManagerName(l.getManagerName());
            grid.setServiceTo(l.getServiceTo());
            grid.setStatus(l.getStatus());
            grid.setOrganId(l.getOrganId());
            grid.setOrganName(l.getOrganName());
            grid.setOwnerOrganId(l.getOwnerOrganId());
            grid.setOwnerOrganName(l.getOwnerOrganName());
            grid.setAllowDelete(
                    kgoCaseMain.get(l.getCaseSetId()) == null ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
            if (!CollectionUtils.isEmpty(finalMap) && StringUtils.isNotEmpty(l.getFlowId())) {
                // flowId 取taskName
                TpiFlow tpiFlow = finalMap.get(l.getFlowId());
                if (null != tpiFlow) {
                    grid.setTaskName(tpiFlow.getFlowName());
                    grid.setFlowId(tpiFlow.getFlowId());
                }
            }
            return grid;
        }).collect(Collectors.toList());
    }

    /**
     * GEO 20211115 add for 跨機關檢視
     * 服務案件清單-取得所有機關選單
     *
     * @return
     */
    @Override
    public CaseManagementOrganSelectQueryRs getOrganSelectComboBox() {
        CaseManagementOrganSelectQueryRs rs = new CaseManagementOrganSelectQueryRs();
        CaseManagementOrganSelectQueryViewForm viewForm = new CaseManagementOrganSelectQueryViewForm();
        try {
            viewForm.setOrganComboBox(organUnitManagementServiceHelper.getOrganComboBox());
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("getOrganSelectComboBox error " + e.getMessage(), e);
        }
        return rs;
    } //getOrganSelectComboBox


    public GeoOrganDiscountComboBoxRs getDiscountComboBox(CaseManagementEditHomeRq rq ){
        LOGGER.info("CaseManagementService getDiscountComboBox start ...");
        GeoOrganDiscountComboBoxRs rs = new GeoOrganDiscountComboBoxRs();
        GeoDiscountComboxViewForm viewForm = new GeoDiscountComboxViewForm();
        try {
            String caseSetId = rq.getCaseSetId();
            String organId = KgoUtil.getBackendLoginUser().getOrgan();
            if (caseSetId != null) {
                KgoCaseset caseset = kgoCasesetRepository.getById(caseSetId);
                if (caseset != null) organId = caseset.getOwnerOrgan();
            }
            List<OrganDiscountRatio> discountList = geoOrganDiscountRatioRepository.findAllByOrganIdOrderByDiscountRatio(organId);
            ComboBox combox = KgoServiceHelper.getInstance().getComboBox(discountList, "discountRatio", "discountRatio", null, ComboBoxStatusEnum.ALL.getCode(), false);
            viewForm.setDiscountComboBox(combox);
            rs.setData(viewForm);
            rs.setMsg(SuccessMsg.UNKNOW.getMsg());
        }catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("getDiscountComboBox error " + e.getMessage(), e);
        }
        return rs;
    }


    private  void validatePaySetting(CaseSetPayModel paySetting) throws KgoApiException{
        boolean needpay = paySetting.getNeedPay();
        if(needpay){
            LOGGER.info("validPaySetting start ...");
            boolean invalid = false;
            Integer payDeadLine = paySetting.getPayDeadline();
            Integer refundDeadLind = paySetting.getRefundDeadline();
            LOGGER.info("payDeadLine :" + payDeadLine + " refundDeadLind :" + refundDeadLind);
            List<GeoCaseSetRefundModel> refundList = paySetting.getRefundList();
            if( payDeadLine < refundDeadLind){
                invalid = true;
            }
            List<int[]> usedPairs  = new ArrayList<>();
            for(GeoCaseSetRefundModel model : refundList ){
                LOGGER.info("valid refundDays pair :"+model.getFromDays() + " - " + model.getEndDays());
                if(model.getFromDays() > model.getEndDays() ) invalid = true;
                if(model.getFromDays() < refundDeadLind ) invalid = true;
                for(int[] pair : usedPairs ){
                    if( pair[1] > model.getFromDays() ) invalid = true;
                    int[] newPair = new int[]{model.getFromDays(),model.getEndDays()};
                    usedPairs.add(newPair);
                };
            }

            if( invalid ) throw new KgoApiException(new ErrorResult(KgoBackEndApiError.RENTAL_REFUND_DAYS_ERROR));
        }
    }

}
