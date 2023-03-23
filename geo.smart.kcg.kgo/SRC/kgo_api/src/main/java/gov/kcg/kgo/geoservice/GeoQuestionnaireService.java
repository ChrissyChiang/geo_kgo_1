package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.*;
import gov.kcg.kgo.geoenum.GeoBooleanType;
import gov.kcg.kgo.geomodel.*;
import gov.kcg.kgo.georepository.*;
import gov.kcg.kgo.georepository.custom.GeoKgoQuestionnaireReposCustom;
import gov.kcg.kgo.geoutil.GeoStringUtil;
import gov.kcg.kgo.geoviewmodel.backend.rq.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.*;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoQuestionnaireCaseSetQueryRq;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoQuestionnaireSaveAnswerRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoQuestionnaireCaseSetQueryRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoQuestionnaireSaveAnswerRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoQuestionnaireCaseSetQueryViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoQuestionnaireSaveAnswerViewForm;
import gov.kcg.kgo.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * GEO 20210828 add
 * <p>
 * 問卷 API Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GeoQuestionnaireService extends GeoBaseService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoQuestionnaireService.class);

    @Autowired
    GeoKgoQuestionnaireReposCustom geoKgoQuestionnaireReposCustom;

    @Autowired
    GeoKgoQuestionnaireMainRepository geoKgoQuestionnaireMainRepository;

    @Autowired
    GeoKgoQuestionnaireTopicRepository geoKgoQuestionnaireTopicRepository;

    @Autowired
    GeoKgoQuestionnaireTopicDetailRepository geoKgoQuestionnaireTopicDetailRepository;

    @Autowired
    GeoKgoQuestionnaireMainTopicRepository geoKgoQuestionnaireMainTopicRepository;

    @Autowired
    GeoKgoQuestionnaireCasesetRepository geoKgoQuestionnaireCasesetRepository;

    @Autowired
    GeoKgoQuestionnaireCasesetTopicRepository geoKgoQuestionnaireCasesetTopicRepository;

    @Autowired
    GeoKgoQuestionnaireCasesetDetailRepository geoKgoQuestionnaireCasesetDetailRepository;

    @Autowired
    GeoKgoQuestionnaireAnswerRepository geoKgoQuestionnaireAnswerRepository;

    @Autowired
    GeoKgoQuestionnaireAnswerDetailRepository geoKgoQuestionnaireAnswerDetailRepository;

    /**
     * 取得該項服務問卷
     *
     * @param rq the rq
     */
    public GeoQuestionnaireCaseSetQueryRs getQuestionnaireByCaseSet(GeoQuestionnaireCaseSetQueryRq rq) {
        GeoQuestionnaireCaseSetQueryRs rs = new GeoQuestionnaireCaseSetQueryRs();
        GeoQuestionnaireCaseSetQueryViewForm viewForm = new GeoQuestionnaireCaseSetQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (rq.getCaseSetId() == null || rq.getCaseSetId().isEmpty()) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            Integer questionnaireCaseSetMaxVersion = geoKgoQuestionnaireReposCustom.findMaxQuestionSetVersionByCaseSetId(rq.getCaseSetId());
            GeoKgoQuestionnaireCaseSetModel questionnaireCaseSetModel = geoKgoQuestionnaireReposCustom.findMaxVersionQuestionCaseSet(rq.getCaseSetId(), questionnaireCaseSetMaxVersion);
            if (questionnaireCaseSetModel == null) {
                return rs;
            } else {
                viewForm.setQuestionnaireMain(questionnaireCaseSetModel);
                List<GeoKgoQuestionnaireCasesetTopic> caseSetTopicList =
                        geoKgoQuestionnaireCasesetTopicRepository.findAllByCaseSetIdAndQuestiinVersion(questionnaireCaseSetModel.getCaseSetId(), questionnaireCaseSetMaxVersion);
                List<GeoKgoQuestionnaireCaseSetTopicModel> topicModelList= new ArrayList<>();
                if (caseSetTopicList != null && caseSetTopicList.size() > 0) {
                    topicModelList = GeoKgoQuestionnaireCaseSetTopicModel.changeListToModel(caseSetTopicList);
                    for (GeoKgoQuestionnaireCaseSetTopicModel casesetTopic : topicModelList) {
                        List<GeoKgoQuestionnaireCasesetDetail> caseSetDetailList =
                                geoKgoQuestionnaireCasesetDetailRepository.findAllByTopicCasesetId(casesetTopic.getTopicCasesetId());
                        List<GeoKgoQuestionnaireCaseSetDetailModel> caseSetDetailModelList = new ArrayList<>();
                        if (caseSetDetailList != null) {
                            caseSetDetailModelList = GeoKgoQuestionnaireCaseSetDetailModel.changeListToModel(caseSetDetailList);
                        }
                        casesetTopic.setDetailList(caseSetDetailModelList);
                    }
                    viewForm.setTopicList(topicModelList);
                } //if (caseSetTopicList != null && caseSetTopicList.size() > 0)
            } //if (questionnaireCaseSetModel == null)
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //getQuestionnaireByCaseSet

    /**
     * 前台案件申請結束後，取得該項服務問卷
     *
     * @param rq the rq
     */
    public GeoQuestionnaireCaseSetQueryRs getQuestionnaireByCaseSetAndIsEnable(GeoQuestionnaireCaseSetQueryRq rq) {
        GeoQuestionnaireCaseSetQueryRs rs = new GeoQuestionnaireCaseSetQueryRs();
        GeoQuestionnaireCaseSetQueryViewForm viewForm = new GeoQuestionnaireCaseSetQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (rq.getCaseSetId() == null || rq.getCaseSetId().isEmpty()) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            Integer questionnaireCaseSetMaxVersion = geoKgoQuestionnaireReposCustom.findMaxQuestionSetVersionByCaseSetId(rq.getCaseSetId());
            GeoKgoQuestionnaireCaseSetModel model = geoKgoQuestionnaireReposCustom.findMaxVersionQuestionCaseSet(rq.getCaseSetId(), questionnaireCaseSetMaxVersion);
            if (model != null && model.getIsEnable() == GeoBooleanType.ENABLED.getCode() &&
                    model.getDateStart() != null && !model.getDateStart().isEmpty() &&
                    model.getDateEnd() != null && !model.getDateEnd().isEmpty()) {
//                LOGGER.info("GeoQuestionnaireService getQuestionnaireByCaseSet model :" + model.getIsEnable() + model.getDateStart() + model.getDateEnd());
                String formatStr = DateUtil.PATTEN_YEAR_MONTH_DAY;
                Timestamp dateStart = DateUtil.getStartOfDay(model.getDateStart(), formatStr, formatStr);
                Timestamp dateEnd = DateUtil.getEndOfDay(model.getDateEnd(), formatStr, formatStr);
                Timestamp now = DateUtil.getCurrentTimestamp();
//                LOGGER.info("GeoQuestionnaireService getQuestionnaireByCaseSet dateStart/dateEnd :"+dateStart+"/"+dateEnd);
                if ((dateStart.equals(now) || dateStart.before(now)) && (dateEnd.after(now))) {
                    viewForm.setQuestionnaireMain(model);
                    List<GeoKgoQuestionnaireCasesetTopic> caseSetTopicList =
                            geoKgoQuestionnaireCasesetTopicRepository.findAllByCaseSetIdAndQuestiinVersion(model.getCaseSetId(), questionnaireCaseSetMaxVersion);
                    List<GeoKgoQuestionnaireCaseSetTopicModel> topicModelList = new ArrayList<>();
                    if (caseSetTopicList != null && caseSetTopicList.size() > 0) {
                        topicModelList = GeoKgoQuestionnaireCaseSetTopicModel.changeListToModel(caseSetTopicList);
                        for (GeoKgoQuestionnaireCaseSetTopicModel casesetTopic : topicModelList) {
                            List<GeoKgoQuestionnaireCasesetDetail> caseSetDetailList =
                                    geoKgoQuestionnaireCasesetDetailRepository.findAllByTopicCasesetId(casesetTopic.getTopicCasesetId());
                            List<GeoKgoQuestionnaireCaseSetDetailModel> caseSetDetailModelList = new ArrayList<>();
                            if (caseSetDetailList != null) {
                                caseSetDetailModelList = GeoKgoQuestionnaireCaseSetDetailModel.changeListToModel(caseSetDetailList);
                            }
                            casesetTopic.setDetailList(caseSetDetailModelList);
                        } //for (GeoKgoQuestionnaireCaseSetTopicModel...
                        viewForm.setTopicList(topicModelList);
                    } //if (caseSetTopicList != null ...
                } //if ((dateStart.equals(now)...
            } //if (model != null...
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //getQuestionnaireByCaseSetAndIsEnable


    /**
     * 編輯該服務問卷-主內容、題組、子題目
     *
     * @param rq       the rq
     * @param userInfo the userInfo
     */
    public GeoQuestionnaireCaseSetQueryRs editQuestionnaireCaseSet(GeoQuestionnaireCaseSetEditRq rq, BackendLoginUserInfo userInfo) {
        LOGGER.info("GeoQuestionnaireService editQuestionnaireCaseSet rq :" + rq);
        GeoQuestionnaireCaseSetQueryRs rs = new GeoQuestionnaireCaseSetQueryRs();
        GeoQuestionnaireCaseSetQueryViewForm viewForm = new GeoQuestionnaireCaseSetQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            GeoKgoQuestionnaireCaseset entity = null;
            Timestamp now = new Timestamp(System.currentTimeMillis());
            String startDate = rq.getDateStart();
            String endDate = rq.getDateEnd();
            if (rq.getIsEnable() == GeoBooleanType.ENABLED.getCode() &&
                    (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) ||
                    (rq.getCaseSetId() == null)) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            Integer questionnaireCaseSetMaxVersion = geoKgoQuestionnaireReposCustom.findMaxQuestionSetVersionByCaseSetId(rq.getCaseSetId());
            if (questionnaireCaseSetMaxVersion == null) questionnaireCaseSetMaxVersion = 0;
            entity = new GeoKgoQuestionnaireCaseset();
            questionnaireCaseSetMaxVersion += 1;
            entity.setQuestionName(rq.getQuestionName());
            entity.setQuestionDesc(rq.getQuestionDesc());
            entity.setCaseSetId(rq.getCaseSetId());
            entity.setIsEnable(rq.getIsEnable());
            entity.setDateStart(rq.getDateStart());
            entity.setDateEnd(rq.getDateEnd());
            entity.setIsDefault(GeoBooleanType.DISABLED.getCode());
            entity.setQuestiinVersion(questionnaireCaseSetMaxVersion);
            entity.setEditOrgan(userInfo.getOrgan());
            entity.setEditUser(userInfo.getUserId());
            entity.setEditTime(now);

            GeoKgoQuestionnaireCaseSetModel model = null;
            entity = geoKgoQuestionnaireCasesetRepository.save(entity);
            List<GeoKgoQuestionnaireCaseSetTopicModel> topicModelList = null;
            if (rq.getTopicList() != null) {
//                LOGGER.info("GeoQuestionnaireService editQuestionnaireCaseSet rq.getTopicList().size() :" + rq.getTopicList().size());
                topicModelList = new ArrayList<>();
                for (GeoKgoQuestionnaireCaseSetTopicModel topicModel : rq.getTopicList()) {
                    GeoKgoQuestionnaireCaseSetTopicModel tempTopicModel = editQuestionnaireCaseSetTopic(topicModel, questionnaireCaseSetMaxVersion, rq.getCaseSetId());
                    topicModelList.add(tempTopicModel);
                    List<GeoKgoQuestionnaireCaseSetDetailModel> detailModelList = new ArrayList<>();
                    if (topicModel.getDetailList() != null) {
                        for (GeoKgoQuestionnaireCaseSetDetailModel detailModel : topicModel.getDetailList()) {
                            GeoKgoQuestionnaireCaseSetDetailModel tempDetailModel = editOneQuestionnaireCaseSetDetail(detailModel, tempTopicModel.getTopicCasesetId());
                            detailModelList.add(tempDetailModel);
                        }
                    } //if (topicModel.getDetailList() != null)
                    tempTopicModel.setDetailList(detailModelList);
                } //for (GeoKgoQuestionnaireCaseSetTopicModel topicModel
            } //if (rq.getTopicList() != null)
            viewForm.setTopicList(topicModelList);
            model = GeoKgoQuestionnaireCaseSetModel.changeToModel(entity);
            viewForm.setQuestionnaireMain(model);
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
    } //editQuestionnaireCaseSet

    /**
     * 編輯該服務問卷-題組
     */
    private GeoKgoQuestionnaireCaseSetTopicModel editQuestionnaireCaseSetTopic(GeoKgoQuestionnaireCaseSetTopicModel inputModel, Integer version, String caseSetId) {
        if (inputModel.getTopicName() == null || version == null || caseSetId == null) {
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
        }

        GeoKgoQuestionnaireCasesetTopic entity = null;
        entity = new GeoKgoQuestionnaireCasesetTopic();
        entity.setTopicCasesetId(geoKgoQuestionnaireReposCustom.getNextTableId(
                GeoStringUtil.QUESTION_CASESET_TOPIC_ID_PREFIX_CHAR, "GEO_KGO_QUESTIONNAIRE_CASESET_TOPIC", "topic_caseset_id"));
        entity.setTopicName(inputModel.getTopicName());
        entity.setTopicDesc(inputModel.getTopicDesc());
        entity.setQuestiinVersion(version);
        entity.setTopicSort(inputModel.getTopicSort());
        entity.setCaseSetId(caseSetId);

        GeoKgoQuestionnaireCaseSetTopicModel model = null;
        entity = geoKgoQuestionnaireCasesetTopicRepository.save(entity);
        model = GeoKgoQuestionnaireCaseSetTopicModel.changeToModel(entity);
        return model;
    } //editQuestionnaireCaseSetTopic

    /**
     * 編輯該服務問卷-子題目
     */
    private GeoKgoQuestionnaireCaseSetDetailModel editOneQuestionnaireCaseSetDetail(GeoKgoQuestionnaireCaseSetDetailModel inputModel, String topicId) {
        if (inputModel.getDetailName() == null || topicId == null) {
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
        }
        GeoKgoQuestionnaireCasesetDetail entity = null;
        if (inputModel.getDetailName() != null && !inputModel.getDetailName().isEmpty()) {
            entity = new GeoKgoQuestionnaireCasesetDetail();
            entity.setDetailCasesetId(geoKgoQuestionnaireReposCustom.getNextTableId(
                    GeoStringUtil.QUESTION_CASESET_DETAIL_ID_PREFIX_CHAR, "GEO_KGO_QUESTIONNAIRE_CASESET_DETAIL", "detail_caseset_id"));
            entity.setDetailName(inputModel.getDetailName());
            if (inputModel.getDetailSort() != null) entity.setDetailSort(inputModel.getDetailSort());
            if (inputModel.getDetailType() != null) entity.setDetailType(inputModel.getDetailType());
            if (inputModel.getDetailChoose() != null) entity.setDetailChoose(inputModel.getDetailChoose());
            if (inputModel.getIsMust() != null) entity.setIsMust(inputModel.getIsMust());
            entity.setTopicCasesetId(topicId);
        } //if (inputModel.getDetailName()!=null && !inputModel.getDetailName().isEmpty())
        GeoKgoQuestionnaireCaseSetDetailModel model = null;
        entity = geoKgoQuestionnaireCasesetDetailRepository.save(entity);
        model = GeoKgoQuestionnaireCaseSetDetailModel.changeToModel(entity);
        return model;
    } //saveOneQuestionnaireTopicDetail

    /**
     * 取得問卷列表
     *
     * @param rq the rq
     */
    public GeoQuestionnaireListRs getQuestionnaireList(GeoQuestionnaireListRq rq) {
        GeoQuestionnaireListRs rs = new GeoQuestionnaireListRs();
        GeoQuestionnaireListViewForm viewForm = new GeoQuestionnaireListViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            List<GeoKgoQuestionnaireMainModel> modelList =
                    geoKgoQuestionnaireReposCustom.getQuestionnaireList(rq.getOrganId(), rq.getStartTime(), rq.getEndTime(), rq.getKeyWord());
            viewForm.setQuestionnaireList(modelList);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //getQuestionnaireList

    /**
     * 編輯問卷主內容
     *
     * @param rq       the rq
     * @param userInfo the userInfo
     */
    public GeoQuestionnaireQueryRs editQuestionnaire(GeoQuestionnaireEditRq rq, BackendLoginUserInfo userInfo) {
        GeoQuestionnaireQueryRs rs = new GeoQuestionnaireQueryRs();
        GeoQuestionnaireQueryViewForm viewForm = new GeoQuestionnaireQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            GeoKgoQuestionnaireMain entity = null;
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (rq.getQuestionId() != null && !rq.getQuestionId().isEmpty()) {
                //編輯
                entity = geoKgoQuestionnaireMainRepository.findByQuestionId(rq.getQuestionId());
                if (entity != null) {
                    if (rq.getQuestionName() != null) entity.setQuestionName(rq.getQuestionName());
                    if (rq.getQuestionDesc() != null) entity.setQuestionDesc(rq.getQuestionDesc());
                    entity.setEditOrgan(userInfo.getOrgan());
                    entity.setEditUser(userInfo.getUserId());
                    entity.setEditTime(now);
                } //if (entity!=null)
            } else {
                //新增
                if (rq.getQuestionName() != null) {
                    entity = new GeoKgoQuestionnaireMain();
                    entity.setQuestionId(geoKgoQuestionnaireReposCustom.getNextTableId(
                            GeoStringUtil.QUESTION_MAIN_ID_PREFIX_CHAR, "GEO_KGO_QUESTIONNAIRE_MAIN", "question_id"));
                    entity.setQuestionName(rq.getQuestionName());
                    if (rq.getQuestionDesc() != null) entity.setQuestionDesc(rq.getQuestionDesc());
                    entity.setIsEnable(GeoBooleanType.ENABLED.getCode());
                    entity.setIsDefault(GeoBooleanType.DISABLED.getCode());
                    entity.setEditOrgan(userInfo.getOrgan());
                    entity.setEditUser(userInfo.getUserId());
                    entity.setEditTime(now);
                } //if (rq.getQuestionName()!=null)
            } //if (rq.getQuestionId()!=null)
            GeoKgoQuestionnaireMainModel model = null;
            if (entity != null) {
                entity = geoKgoQuestionnaireMainRepository.save(entity);
                model = GeoKgoQuestionnaireMainModel.changeToModel(entity);
            }
            viewForm.setQuestionnaireMain(model);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //editQuestionnaire

    /**
     * 刪除問卷主內容
     *
     * @param rq       the rq
     * @param userInfo the userInfo
     */
    public GeoQuestionnaireQueryRs deleteQuestionnaire(GeoQuestionnaireQueryRq rq, BackendLoginUserInfo userInfo) {
        GeoQuestionnaireQueryRs rs = new GeoQuestionnaireQueryRs();
        GeoQuestionnaireQueryViewForm viewForm = new GeoQuestionnaireQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            GeoKgoQuestionnaireMain entity = null;
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (rq.getQuestionId() != null && !rq.getQuestionId().isEmpty()) {
                entity = geoKgoQuestionnaireMainRepository.findByQuestionId(rq.getQuestionId());
                if (entity != null) {
                    entity.setIsEnable(GeoBooleanType.DISABLED.getCode());
                    entity.setEditOrgan(userInfo.getOrgan());
                    entity.setEditUser(userInfo.getUserId());
                    entity.setEditTime(now);
                    geoKgoQuestionnaireMainRepository.save(entity);
                } //if (entity!=null)
            } //if (rq.getQuestionId()!=null)
            viewForm.setQuestionnaireMain(null);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //deleteQuestionnaire

    /**
     * 取得問卷題組列表(含題目)
     *
     * @param rq the rq
     */
    public GeoQuestionnaireTopicListRs getQuestionnaireTopicList(GeoQuestionnaireQueryRq rq) {
        GeoQuestionnaireTopicListRs rs = new GeoQuestionnaireTopicListRs();
        GeoQuestionnaireTopicListViewForm viewForm = new GeoQuestionnaireTopicListViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            List<GeoKgoQuestionnaireTopicModel> modelList =
                    geoKgoQuestionnaireReposCustom.getQuestionnaireTopicList(rq.getQuestionId());
            if (modelList != null && modelList.size() > 0) {
                for (int i = 0; i < modelList.size(); i++) {
                    List<GeoKgoQuestionnaireTopicDetailModel> detailList =
                            geoKgoQuestionnaireReposCustom.getQuestionnaireDetailList(modelList.get(i).getTopicId());
                    modelList.get(i).setDetailList(detailList);
                } //for (int i=0; i<modelList.size(); i++)
            } //if (modelList!=null && modelList.size()>0)
            viewForm.setTopicList(modelList);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //getQuestionnaireTopicList

    /**
     * 新增編輯問卷題組主內容
     *
     * @param rq the rq
     */
    public GeoQuestionnaireTopicQueryRs editQuestionnaireTopic(GeoQuestionnaireTopicEditRq rq) {
        GeoQuestionnaireTopicQueryRs rs = new GeoQuestionnaireTopicQueryRs();
        GeoQuestionnaireTopicQueryViewForm viewForm = new GeoQuestionnaireTopicQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            List<GeoKgoQuestionnaireTopicModel> modelList = rq.getModelList();
            for (int i = 0; i < modelList.size(); i++) {
                GeoKgoQuestionnaireTopicModel model = saveOneQuestionnaireTopic(modelList.get(i));
                if (modelList.get(i).getTopicId() == null || modelList.get(i).getTopicId().isEmpty())
                    modelList.get(i).setTopicId(model.getTopicId());
                List<GeoKgoQuestionnaireTopicDetailModel> detailList = modelList.get(i).getDetailList();
                if (detailList != null && detailList.size() > 0) {
                    for (int j = 0; j < detailList.size(); j++) {
                        saveOneQuestionnaireTopicDetail(detailList.get(j), model.getTopicId(), model.getIsEnable());
                    }
                } //if (detailList!=null && detailList.size()>0)
            } //for (int i=0; i<modelList.size(); i++)

            geoKgoQuestionnaireMainTopicRepository.deleteByQuestionId(modelList.get(0).getQuestionId());
            for (int i = 0; i < modelList.size(); i++) {
                //題組和問卷的關聯建立
                if (modelList.get(i).getTopicId() != null && !modelList.get(i).getTopicId().isEmpty() &&
                        modelList.get(i).getQuestionId() != null && !modelList.get(i).getQuestionId().isEmpty()) {
                    GeoKgoQuestionnaireMainTopic mainTopicEntity = new GeoKgoQuestionnaireMainTopic();
                    mainTopicEntity.setTopicId(modelList.get(i).getTopicId());
                    mainTopicEntity.setQuestionId(modelList.get(i).getQuestionId());
                    mainTopicEntity.setTopicSort(modelList.get(i).getTopicSort());
                    geoKgoQuestionnaireMainTopicRepository.save(mainTopicEntity);
                } //if (modelList.get(i).getTopicId()!=null..
            } //for (int i=0; i<modelList.size(); i++)

            viewForm.setQuestionnaireTopic(null);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //editQuestionnaireTopic

    /**
     * 取得全部題組列表(不含題目)
     */
    public GeoQuestionnaireTopicListRs getAllTopicList() {
        GeoQuestionnaireTopicListRs rs = new GeoQuestionnaireTopicListRs();
        GeoQuestionnaireTopicListViewForm viewForm = new GeoQuestionnaireTopicListViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            List<GeoKgoQuestionnaireTopicModel> modelList =
                    geoKgoQuestionnaireReposCustom.getQuestionnaireTopicList(null);
            viewForm.setTopicList(modelList);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(null, rs, error);
        } //try
        return rs;
    } //getAllTopicList

    /**
     * 取得題組的子題目列表
     *
     * @param rq the rq
     */
    public GeoQuestionnaireDetailListRs getTopicDetailList(GeoQuestionnaireTopicQueryRq rq) {
        GeoQuestionnaireDetailListRs rs = new GeoQuestionnaireDetailListRs();
        GeoQuestionnaireDetailListViewForm viewForm = new GeoQuestionnaireDetailListViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (rq.getTopicId() != null && !rq.getTopicId().isEmpty()) {
                List<GeoKgoQuestionnaireTopicDetailModel> detailList =
                        geoKgoQuestionnaireReposCustom.getQuestionnaireDetailList(rq.getTopicId());
                viewForm.setDetailList(detailList);
            }
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //getQuestionnaireTopicList

    private GeoKgoQuestionnaireTopicModel saveOneQuestionnaireTopic(GeoKgoQuestionnaireTopicModel inputModel) {
        GeoKgoQuestionnaireTopic entity = null;
        if (inputModel.getTopicId() != null && !inputModel.getTopicId().isEmpty()) {
            //編輯
            entity = geoKgoQuestionnaireTopicRepository.findByTopicId(inputModel.getTopicId());
            if (entity != null) {
                if (inputModel.getTopicName() != null) entity.setTopicName(inputModel.getTopicName());
                if (inputModel.getTopicDesc() != null) entity.setTopicDesc(inputModel.getTopicDesc());
                if (inputModel.getIsEnable() != null) entity.setIsEnable(inputModel.getIsEnable()); //刪除只會在此變更狀態
            } //if (entity!=null)
        } else {
            //新增
            if (inputModel.getTopicName() != null && !inputModel.getTopicName().isEmpty()) {
                entity = new GeoKgoQuestionnaireTopic();
                entity.setTopicId(geoKgoQuestionnaireReposCustom.getNextTableId(
                        GeoStringUtil.QUESTION_TOPIC_ID_PREFIX_CHAR, "GEO_KGO_QUESTIONNAIRE_TOPIC", "topic_id"));
                entity.setTopicName(inputModel.getTopicName());
                if (inputModel.getTopicDesc() != null) entity.setTopicDesc(inputModel.getTopicDesc());
                entity.setIsEnable(GeoBooleanType.ENABLED.getCode());
            } //if (inputModel.getTopicName()!=null && !inputModel.getTopicName().isEmpty())
        } //if (inputModel.getTopicId()!=null && !inputModel.getTopicId().isEmpty())
        GeoKgoQuestionnaireTopicModel model = null;
        if (entity != null) {
            entity = geoKgoQuestionnaireTopicRepository.save(entity);
            model = GeoKgoQuestionnaireTopicModel.changeToModel(entity);
        }
        return model;
    } //saveOneQuestionnaireTopic

    private GeoKgoQuestionnaireTopicDetailModel saveOneQuestionnaireTopicDetail(GeoKgoQuestionnaireTopicDetailModel inputModel, String topicId, int isTopicEnable) {
        GeoKgoQuestionnaireTopicDetail entity = null;
        if (inputModel.getDetailId() != null && !inputModel.getDetailId().isEmpty()) {
            if (inputModel.getIsEnable() != 0 && isTopicEnable == GeoBooleanType.ENABLED.getCode()) {
                //編輯
                entity = geoKgoQuestionnaireTopicDetailRepository.findByDetailId(inputModel.getDetailId());
                if (entity != null) {
                    if (inputModel.getDetailName() != null) entity.setDetailName(inputModel.getDetailName());
                    if (inputModel.getDetailSort() != null) entity.setDetailSort(inputModel.getDetailSort());
                    if (inputModel.getDetailType() != null) entity.setDetailType(inputModel.getDetailType());
                    if (inputModel.getDetailChoose() != null) entity.setDetailChoose(inputModel.getDetailChoose());
                    if (inputModel.getIsMust() != null) entity.setIsMust(inputModel.getIsMust());
                } //if (entity!=null)
            }
            if (inputModel.getIsEnable() != null && inputModel.getIsEnable() == GeoBooleanType.DISABLED.getCode()) {
                entity = geoKgoQuestionnaireTopicDetailRepository.findByDetailId(inputModel.getDetailId());
                if (entity != null) geoKgoQuestionnaireTopicDetailRepository.delete(entity);
            }
        } else {
            //新增
            if (inputModel.getDetailName() != null && !inputModel.getDetailName().isEmpty()) {
                entity = new GeoKgoQuestionnaireTopicDetail();
                entity.setDetailId(geoKgoQuestionnaireReposCustom.getNextTableId(
                        GeoStringUtil.QUESTION_TOPIC_DETAIL_ID_PREFIX_CHAR, "GEO_KGO_QUESTIONNAIRE_TOPIC_DETAIL", "detail_id"));
                entity.setTopicId(topicId);
                entity.setDetailName(inputModel.getDetailName());
                if (inputModel.getDetailSort() != null) entity.setDetailSort(inputModel.getDetailSort());
                if (inputModel.getDetailType() != null) entity.setDetailType(inputModel.getDetailType());
                if (inputModel.getDetailChoose() != null) entity.setDetailChoose(inputModel.getDetailChoose());
                if (inputModel.getIsMust() != null) entity.setIsMust(inputModel.getIsMust());
            } //if (inputModel.getDetailName()!=null && !inputModel.getDetailName().isEmpty())
        } //if (inputModel.getDetailId()!=null && !inputModel.getDetailId().isEmpty())
        GeoKgoQuestionnaireTopicDetailModel model = null;
        if (entity != null) {
            if (inputModel.getIsEnable() != 0 && isTopicEnable == GeoBooleanType.ENABLED.getCode()) {
                entity = geoKgoQuestionnaireTopicDetailRepository.save(entity);
            } else {
                geoKgoQuestionnaireTopicDetailRepository.deleteByDetailId(inputModel.getDetailId()); //刪除資料
            } //if (inputModel.getIsEnable()!=0 && isTopicEnable==GeoBooleanType.ENABLED.getCode())

            model = GeoKgoQuestionnaireTopicDetailModel.changeToModel(entity);
        } //if (entity!=null)
        return model;
    } //saveOneQuestionnaireTopicDetail

    /**
     * 新增服務問卷作答:主檔、內容
     *
     * @param rq the rq
     */
    public GeoQuestionnaireSaveAnswerRs insertQuestionnaireAnswer(GeoQuestionnaireSaveAnswerRq rq) {
        GeoQuestionnaireSaveAnswerRs rs = new GeoQuestionnaireSaveAnswerRs();
        GeoQuestionnaireSaveAnswerViewForm viewForm = new GeoQuestionnaireSaveAnswerViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (rq.getCaseSetId() == null || rq.getCaseId()==null || rq.getDetailList() == null) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            Timestamp now = new Timestamp(System.currentTimeMillis());
            GeoKgoQuestionnaireAnswer entity = new GeoKgoQuestionnaireAnswer();
            entity.setAnswerId(geoKgoQuestionnaireReposCustom.getNextTableId(
                    GeoStringUtil.QUESTION_ANSWER_ID_PREFIX_CHAR, "GEO_KGO_QUESTIONNAIRE_ANSWER", "answer_id"));
            entity.setCaseId(rq.getCaseId());
            entity.setCaseSetId(rq.getCaseSetId());
            entity.setQuestiinVersion(rq.getQuestionVersion());
            if (rq.getApplyUserId() != null )entity.setApplyUserId(rq.getApplyUserId());
            entity.setApplyName(rq.getApplyName());
            entity.setApplyDate(now);
            entity = geoKgoQuestionnaireAnswerRepository.save(entity);
            if (rq.getDetailList() != null && rq.getDetailList().size() > 0) {
                for (GeoKgoQuestionnaireAnswerDetailModel detailModel : rq.getDetailList()) {
                    GeoKgoQuestionnaireAnswerDetail detailEntity = new GeoKgoQuestionnaireAnswerDetail();
                    detailEntity.setDetailId(geoKgoQuestionnaireReposCustom.getNextTableId(
                            GeoStringUtil.QUESTION_ANSWER_DETAIL_ID_PREFIX_CHAR , "GEO_KGO_QUESTIONNAIRE_ANSWER_DETAIL", "detail_id"));
                    detailEntity.setDetailCasesetId(detailModel.getDetailCasesetId());
                    detailEntity.setAnswerId(entity.getAnswerId());
                    detailEntity.setAnswerValue(detailModel.getAnswerValue());
                    if (detailModel.getAnswerScore() != null) detailEntity.setAnswerScore(detailModel.getAnswerScore());
                    detailEntity = geoKgoQuestionnaireAnswerDetailRepository.save(detailEntity);
                } //for (GeoKgoQuestionnaireAnswerDetailModel
            } //if (rq.getDetailList() != null && rq.getDetailList().size() > 0)
            viewForm.setMsg(SuccessMsg.SAVE.getMsg());
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE), e);
        } finally {
            setResultMessage(rq, rs, error); // 設置 成功/失敗訊息.
        } //try
        return rs;
    } //editQuestionnaireCaseSet


}
