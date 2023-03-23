package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.enums.common.BaseColumnEnum;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireAnswerDetail;
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCaseset;
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetDetail;
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetTopic;
import gov.kcg.kgo.geoenum.GeoQuestionnaireDetailType;
import gov.kcg.kgo.geomodel.*;
import gov.kcg.kgo.georepository.*;
import gov.kcg.kgo.georepository.custom.GeoKgoQuestionnaireReposCustom;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoQuestionnaireQueryCaseAnswerRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoQuestionnaireResultAnswerQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoQuestionnaireQueryAnswerListRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoQuestionnaireResultScoreQueryRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoQuestionnaireQueryAnswerViewForm;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoQuestionnaireResultQueryViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoQuestionnaireResultHomeRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoQuestionnaireResultHomeViewForm;
import gov.kcg.kgo.repository.KgoCaseMainRepository;
import gov.kcg.kgo.service.ExcelTempExportService;
import gov.kcg.kgo.service.bean.excel.GeoQuestionExportExcelVo;
import gov.kcg.kgo.service.impl.helper.CaseManagementServiceHelper;
import gov.kcg.kgo.service.impl.helper.OrganUnitManagementServiceHelper;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.MessageUtil;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * GEO 20211011 add
 * 問卷結果查詢 API Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GeoQuestionnaireResultService extends GeoBaseService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoQuestionnaireResultService.class);

    /**
     * commonServiceHelper
     **/
    private CaseManagementServiceHelper caseManagementServiceHelper = CaseManagementServiceHelper.getInstance();

    private OrganUnitManagementServiceHelper organUnitManagementServiceHelper = OrganUnitManagementServiceHelper
            .getInstance();

    @Autowired
    private GeoKgoQuestionnaireReposCustom geoKgoQuestionnaireReposCustom;

    @Autowired
    private GeoKgoQuestionnaireCasesetRepository geoKgoQuestionnaireCasesetRepository;

    @Autowired
    private GeoKgoQuestionnaireCasesetTopicRepository geoKgoQuestionnaireCasesetTopicRepository;

    @Autowired
    GeoKgoQuestionnaireCasesetDetailRepository geoKgoQuestionnaireCasesetDetailRepository;

    @Autowired
    GeoKgoQuestionnaireAnswerRepository geoKgoQuestionnaireAnswerRepository;

    @Autowired
    GeoKgoQuestionnaireAnswerDetailRepository geoKgoQuestionnaireAnswerDetailRepository;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private ExcelTempExportService excelTempExportService;

    @Autowired
    private KgoCaseMainRepository kgoCaseMainRepository;

    /**
     * 服務案件清單-初始畫面
     */
    public GeoQuestionnaireResultHomeRs questionnaireResultHome() {
        GeoQuestionnaireResultHomeViewForm viewForm = new GeoQuestionnaireResultHomeViewForm();
        GeoQuestionnaireResultHomeRs rs = new GeoQuestionnaireResultHomeRs();
        try {
            BackendLoginUserInfo loginUser = KgoUtil.getBackendLoginUser();
            /** 權責機關下拉式選單 **/
            ComboBox ownerOrganComboBox = organUnitManagementServiceHelper
                    .getOrganComboBoxWithUserRoleLimit(loginUser.getUserId());

            viewForm.setOrganComboBox(ownerOrganComboBox);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("questionnaireResultHome error " + e.getMessage(), e);
        } // try
        return rs;
    } //questionnaireResultHome

    /**
     * 後台-問卷結果查詢:服務問卷查詢
     */
    public gov.kcg.kgo.geoviewmodel.frontend.rs.GeoQuestionnaireResultQueryRs getQuestionnaireResultQueryList(gov.kcg.kgo.geoviewmodel.frontend.rq.GeoQuestionnaireResultQueryRq rq) {
        gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoQuestionnaireResultQueryViewForm viewForm = new gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoQuestionnaireResultQueryViewForm();
        gov.kcg.kgo.geoviewmodel.frontend.rs.GeoQuestionnaireResultQueryRs rs = new gov.kcg.kgo.geoviewmodel.frontend.rs.GeoQuestionnaireResultQueryRs();
        String dateStartStr = rq.getDateStart();
        String dateEndStr = rq.getDateEnd();
        Date dateStart = null;
        Date dateEnd = null;
        try {
            if (!(dateEndStr == null && dateStartStr == null)
                    && !(dateEndStr != null && dateStartStr != null)
                    && !(dateEndStr != null && dateEndStr.isEmpty())
                    && !(dateStartStr != null && dateStartStr.isEmpty())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            if (StringUtils.isNotBlank(dateStartStr) && StringUtils.isNotBlank(dateEndStr)) {
                dateStart = DateUtil.getStartOfDay(dateStartStr, DateUtil.PATTEN_YEAR_MONTH_DAY, DateUtil.PATTEN_FULL_TIME_MS);
                dateEnd = DateUtil.getEndOfDay(dateEndStr, DateUtil.PATTEN_YEAR_MONTH_DAY, DateUtil.PATTEN_FULL_TIME_MS);
            }
            List<GeoQuestionnaireResultQueryDataGridModel> gridList =
                    geoKgoQuestionnaireReposCustom.getQuestionnaireResultList(rq.getCaseSetName(), dateStart, dateEnd, rq.getOrganId());
            if (gridList != null) {
                for (GeoQuestionnaireResultQueryDataGridModel model : gridList) {
                    boolean isHaveScoreType = geoKgoQuestionnaireReposCustom.getQuestionnaireWithType(model.getCaseSetId(),
                            model.getQuestionVersion(), GeoQuestionnaireDetailType.CHOOSE_ONE_SCORE_TYPE.getValue());
                    model.setHadScore(isHaveScoreType);
                }
            }
            viewForm.setGrid(gridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in questionnaireResultQuery", e);
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH));
        } //try
        return rs;
    } //getQuestionnaireResultQueryList

    /**
     * 後台-問卷結果查詢:問卷結果(配分)
     */
    public GeoQuestionnaireResultScoreQueryRs getQuestionnaireResultScoreList(GeoQuestionnaireResultAnswerQueryRq rq) {
        GeoQuestionnaireResultQueryViewForm viewForm = new GeoQuestionnaireResultQueryViewForm();
        GeoQuestionnaireResultScoreQueryRs rs = new GeoQuestionnaireResultScoreQueryRs();
        try {
            if (rq.getCaseSetId() == null || StringUtils.isEmpty(rq.getCaseSetId())
                    || rq.getQuestionVersion() == null || rq.getQuestionVersion() <= 0) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            String caseSetId = rq.getCaseSetId();
            Integer version = rq.getQuestionVersion();
            GeoKgoQuestionnaireCaseset questionnaireCaseSet =
                    geoKgoQuestionnaireCasesetRepository.findByCaseSetIdAndQuestiinVersion(caseSetId, version);
            if (questionnaireCaseSet != null) {
                viewForm.setQuestionName(questionnaireCaseSet.getQuestionName());
                viewForm.setCaseSetId(questionnaireCaseSet.getCaseSetId());
                viewForm.setQuestionVersion(questionnaireCaseSet.getQuestiinVersion());
                viewForm.setDateStart(questionnaireCaseSet.getDateStart());
                viewForm.setDateEnd(questionnaireCaseSet.getDateEnd());
                viewForm.setTopicList(new ArrayList<>());
                List<GeoKgoQuestionnaireCasesetTopic> topicEntityList =
                        geoKgoQuestionnaireCasesetTopicRepository.findAllByCaseSetIdAndQuestiinVersion(caseSetId, version);
                if (topicEntityList != null && topicEntityList.size() > 0) {
                    List<GeoKgoQuestionnaireResultQueryTopicModel> topicList = GeoKgoQuestionnaireResultQueryTopicModel.changeListToModel(topicEntityList);
                    for (GeoKgoQuestionnaireResultQueryTopicModel model : topicList) {
                        List<GeoKgoQuestionnaireCasesetDetail> detailEntityList =
                                geoKgoQuestionnaireCasesetDetailRepository.findAllByTopicCasesetIdAndDetailType(model.getTopicCaseSetId(), GeoQuestionnaireDetailType.CHOOSE_ONE_SCORE_TYPE.getValue());
                        if (detailEntityList != null) {
                            List<GeoKgoQuestionnaireResultQueryDetailModel> detailList = GeoKgoQuestionnaireResultQueryDetailModel.changeListToModel(detailEntityList);
                            viewForm.getTopicList().add(model);
                            model.setDetailList(detailList);
                            for (GeoKgoQuestionnaireResultQueryDetailModel m : detailList) {
                                m.setDetailAnswerList(geoKgoQuestionnaireReposCustom.getQuestionnaireAnswerDetailTotalScoreList(m.getDetailCasesetId()));
                            }
                        } //if (detailEntityList
                    } //for (GeoKgoQuestionnaireResultQueryTopicModel...
                } //if (topicEntityList
            } //if (questionnaireCaseSet != null)
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in questionnaireResultQuery", e);
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH));
        } //try
        return rs;
    } //getQuestionnaireResultScoreList

    /**
     * 後台-檢視填寫:取得答案清單
     */
    public GeoQuestionnaireQueryAnswerListRs getQuestionnaireAnswerList(GeoQuestionnaireResultAnswerQueryRq rq) {
        GeoQuestionnaireQueryAnswerViewForm viewForm = new GeoQuestionnaireQueryAnswerViewForm();
        GeoQuestionnaireQueryAnswerListRs rs = new GeoQuestionnaireQueryAnswerListRs();
        try {
            if (rq.getCaseSetId() == null || StringUtils.isEmpty(rq.getCaseSetId())
                    || rq.getQuestionVersion() == null || rq.getQuestionVersion() <= 0) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            GeoBidCaseListQueryDataGridModel caseSetData = geoKgoQuestionnaireReposCustom.getCaseSetData(rq.getCaseSetId());
            if (caseSetData != null) {
                viewForm.setCaseSetId(caseSetData.getCaseSetId());
                viewForm.setCaseSetName(caseSetData.getCaseSetName());
                viewForm.setOrganId(caseSetData.getOrganId());
                viewForm.setOrganSeq(caseSetData.getOrganSeq());
                viewForm.setQuestionVersion(rq.getQuestionVersion());
                viewForm.setOrganName(caseSetData.getOrganName());
                viewForm.setAnswerList(new ArrayList<>());
                List<GeoKgoQuestionnaireAnswerListModel> answerList = geoKgoQuestionnaireReposCustom.getQuestionnaireAnswerList(rq.getCaseSetId(), rq.getQuestionVersion(), BaseColumnEnum.EMAIL.getColumnId());
                if (answerList != null) viewForm.setAnswerList(answerList);
            } //if (caseSetData != null)
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in questionnaireResultQuery", e);
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH));
        } //try
        return rs;
    } //getQuestionnaireResultScoreList

    /**
     * 後台-檢視填寫:檢視該問卷題目答案
     */
    public GeoQuestionnaireResultScoreQueryRs getQuestionnaireAnswer(GeoQuestionnaireQueryCaseAnswerRq rq) {
        GeoQuestionnaireResultQueryViewForm viewForm = new GeoQuestionnaireResultQueryViewForm();
        GeoQuestionnaireResultScoreQueryRs rs = new GeoQuestionnaireResultScoreQueryRs();
        try {
            if (rq.getCaseSetId() == null || StringUtils.isEmpty(rq.getCaseSetId())
                    || rq.getQuestionVersion() == null || rq.getQuestionVersion() <= 0) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            String caseSetId = rq.getCaseSetId();
            Integer version = rq.getQuestionVersion();
            GeoKgoQuestionnaireCaseset questionnaireCaseSet =
                    geoKgoQuestionnaireCasesetRepository.findByCaseSetIdAndQuestiinVersion(caseSetId, version);
            if (questionnaireCaseSet != null) {
                viewForm.setQuestionName(questionnaireCaseSet.getQuestionName());
                viewForm.setCaseSetId(questionnaireCaseSet.getCaseSetId());
                viewForm.setQuestionVersion(questionnaireCaseSet.getQuestiinVersion());
                viewForm.setDateStart(questionnaireCaseSet.getDateStart());
                viewForm.setDateEnd(questionnaireCaseSet.getDateEnd());
                viewForm.setTopicList(new ArrayList<>());
                List<GeoKgoQuestionnaireCasesetTopic> topicEntityList =
                        geoKgoQuestionnaireCasesetTopicRepository.findAllByCaseSetIdAndQuestiinVersion(caseSetId, version);
                if (topicEntityList != null && topicEntityList.size() > 0) {
                    List<GeoKgoQuestionnaireResultQueryTopicModel> topicList = GeoKgoQuestionnaireResultQueryTopicModel.changeListToModel(topicEntityList);
                    viewForm.setTopicList(topicList);
                    for (GeoKgoQuestionnaireResultQueryTopicModel model : topicList) {
                        List<GeoKgoQuestionnaireCasesetDetail> detailEntityList =
                                geoKgoQuestionnaireCasesetDetailRepository.findAllByTopicCasesetId(model.getTopicCaseSetId());
                        if (detailEntityList != null) {
                            List<GeoKgoQuestionnaireResultQueryDetailModel> detailList = GeoKgoQuestionnaireResultQueryDetailModel.changeListToModel(detailEntityList);
                            model.setDetailList(detailList);
                            for (GeoKgoQuestionnaireResultQueryDetailModel detailModel : detailList) {
                                GeoKgoQuestionnaireAnswerDetail answerDetail =
                                        geoKgoQuestionnaireAnswerDetailRepository.findByAnswerIdAndDetailCasesetId(rq.getAnswerId(), detailModel.getDetailCasesetId());
                                detailModel.setDetailAnswer(GeoKgoQuestionnaireResultQueryAnswerDetailModel.changeToModel(answerDetail));
                            }
                        } //if (detailEntityList
                    } //for (GeoKgoQuestionnaireResultQueryTopicModel...
                } //if (topicEntityList
            } //if (questionnaireCaseSet != null)
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in questionnaireResultQuery", e);
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH));
        } //try
        return rs;
    } //getQuestionnaireResultScoreList

    /**
     * GEO 20211030 add
     * 後台-問卷結果:匯出報表.
     *
     * @param rq the rq
     */
    public void exportExcelAction(GeoQuestionnaireResultAnswerQueryRq rq) {
        try {
            String caseSetId = rq.getCaseSetId();
            Integer version = rq.getQuestionVersion();

            if (StringUtils.isBlank(caseSetId) || ObjectUtils.isEmpty(version)) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            String fileName = messageUtil.getMessage("kgo.backend.question.excel.fileName");
            String sheetName = messageUtil.getMessage("kgo.backend.question.excel.sheet");
            Map<String, Object> data = new HashMap<>();
            data.put("NAME", messageUtil.getMessage("kgo.backend.question.excel.header.user.name"));
            data.put("PHONE", messageUtil.getMessage("kgo.backend.question.excel.header.phone"));
            data.put("EMAIL", messageUtil.getMessage("kgo.backend.question.excel.header.email"));
            data.put("CREATE_TIME", messageUtil.getMessage("kgo.backend.question.excel.header.creat_time"));
            data.put("NOTE", messageUtil.getMessage("kgo.backend.question.excel.header.note"));

            List<GeoKgoQuestionnaireCasesetTopic> topicList =
                    geoKgoQuestionnaireCasesetTopicRepository.findAllByCaseSetIdAndQuestiinVersion(caseSetId, version);//獲得對應的主問卷
            //LOGGER.info("GeoQuestionnaireResultService exportExcelAction 問卷所有子題目 topicList.size=" + topicList.size());
            LinkedHashMap<String, List<GeoKgoQuestionnaireCasesetDetail>> topicModel = getTopicAndDetailCorrespondList(topicList);//子題目與選項
            List<GeoKgoQuestionnaireAnswerListModel> answerList =
                    geoKgoQuestionnaireReposCustom.getQuestionnaireAnswerList(caseSetId, version, BaseColumnEnum.EMAIL.getColumnId());//作答者資料
            List<GeoQuestionExportExcelVo> geoQuestionExportExcelVos = getAnswerData(answerList);//放入excel內的作答者物件
            data.put("DATA_LIST", geoQuestionExportExcelVos);
            excelTempExportService.exportQuestionExcel(fileName, sheetName, data, topicModel, geoQuestionExportExcelVos);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("exportExcelAction error " + e.getMessage(), e);
        } //try catch
    } //exportExcelAction

    /**
     * GEO 20211030 add
     * *後台-問卷結果:產出問卷子題目與選項.
     */
    public LinkedHashMap<String, List<GeoKgoQuestionnaireCasesetDetail>> getTopicAndDetailCorrespondList(List<GeoKgoQuestionnaireCasesetTopic> topicList) {
        LinkedHashMap<String, List<GeoKgoQuestionnaireCasesetDetail>> topicModel = new LinkedHashMap<>();
        if (topicList != null && topicList.size() > 0) {
            for (GeoKgoQuestionnaireCasesetTopic topic : topicList) {
                List<GeoKgoQuestionnaireCasesetDetail> detailList = geoKgoQuestionnaireCasesetDetailRepository.findAllByTopicCasesetId(topic.getTopicCasesetId());
               // LOGGER.info("GeoQuestionnaireResultService exportExcelAction 該子題目對應下的選項數量 detailList.size()=" + detailList.size());
                for (GeoKgoQuestionnaireCasesetDetail detail : detailList) {
                    //LOGGER.info("GeoQuestionnaireResultService exportExcelAction 獲得子題目的選項標題 topicModel.size()=" + detail.getDetailName());
                    topicModel.put(topic.getTopicName(), detailList);
                } //for
            } //for
        } // if (topicList != null && topicList.size() > 0)
        return topicModel;
    } //getTopicAndDetailCorrespondList

    /**
     * GEO 20211030 add
     * *後台-問卷結果:作答者資料.
     */
    public List<GeoQuestionExportExcelVo> getAnswerData(List<GeoKgoQuestionnaireAnswerListModel> answerList) {
        List<GeoQuestionExportExcelVo> geoQuestionExportExcelVos = new ArrayList<>();
        if (answerList != null && answerList.size() > 0) {
            for (GeoKgoQuestionnaireAnswerListModel answer : answerList) {
                GeoQuestionExportExcelVo excelVoTopic = new GeoQuestionExportExcelVo();
                excelVoTopic.setApply_name(answer.getUserName());
                excelVoTopic.setApply_email(answer.getUserEmail());
                excelVoTopic.setApply_phone(answer.getUserMobilePhone());
                excelVoTopic.setApply_date(answer.getApplyDate());
                List<GeoKgoQuestionnaireAnswerDetail> answerDetails = geoKgoQuestionnaireAnswerDetailRepository.findByAnswerId(answer.getAnswerId());
                excelVoTopic.setAnswerList(answerDetails);
                geoQuestionExportExcelVos.add(excelVoTopic);
            } //for
        } // if (answerList != null && answerList.size() > 0)
        return geoQuestionExportExcelVos;
    } //getAnswerData
}


