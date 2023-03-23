package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.geomodel.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * GEO 20210828 add
 * 取得問卷相關組合資料
 */

@Repository
public class GeoKgoQuestionnaireReposCustom extends GeoBaseReposCustom {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoKgoQuestionnaireReposCustom.class);


    /**
     * 取得問卷清單
     *
     * @param organId
     * @param startTime
     * @param endTime
     * @param keyWord
     */
    public List<GeoKgoQuestionnaireMainModel> getQuestionnaireList(String organId, String startTime, String endTime, String keyWord) {
        String sqlStr = "SELECT qm.question_id, qm.question_name, qm.question_desc, ko.OrganName, qm.edit_time, " +
                "qm.is_default ";
        String fromStr = "FROM GEO_KGO_QUESTIONNAIRE_MAIN qm, KGO_ORGAN ko ";
        String whereStr = "WHERE qm.is_enable=1 and qm.edit_organ=ko.OrganId ";
        String orderStr = "order by qm.edit_time ;";
        if (organId != null && !organId.isEmpty()) whereStr = whereStr + "and qm.edit_organ=:organId ";
        if (startTime != null && !startTime.isEmpty())
            whereStr = whereStr + "and qm.edit_time>=convert(datetime, :startTime) ";
        if (endTime != null && !endTime.isEmpty())
            whereStr = whereStr + "and qm.edit_time<=convert(datetime, :endTime) ";
        if (keyWord != null && !keyWord.isEmpty())
            whereStr = whereStr + "and qm.question_name like CONCAT('%',:keyWord,'%') ";

        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr + orderStr);
        //LOGGER.info("GeoKgoQuestionnaireReposCustom getQuestionnaireList sqlStr: "+sqlStr+fromStr+whereStr+orderStr);
        if (organId != null && !organId.isEmpty()) sqlQuery.setParameter("organId", organId);
        if (startTime != null && !startTime.isEmpty()) sqlQuery.setParameter("startTime", startTime);
        if (endTime != null && !endTime.isEmpty()) sqlQuery.setParameter("endTime", endTime);
        if (keyWord != null && !keyWord.isEmpty()) sqlQuery.setParameter("keyWord", keyWord);

        List listData = sqlQuery.getResultList();
        List<GeoKgoQuestionnaireMainModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoKgoQuestionnaireMainModel dto = new GeoKgoQuestionnaireMainModel();
            Object[] recordArray = (Object[]) listData.get(i);
            dto.setQuestionId((String) recordArray[0]);
            dto.setQuestionName((String) recordArray[1]);
            if (recordArray[2] != null) dto.setQuestionDesc((String) recordArray[2]);
            dto.setOrganName((String) recordArray[3]);
            dto.setEditTime((Timestamp) recordArray[4]);
            dto.setIsDefault((int) recordArray[5]);
            datas.add(dto);
        }
        //LOGGER.info("GeoKgoQuestionnaireReposCustom getQuestionnaireList list size: "+list.size());

        return datas;
    } //getQuestionnaireList

    /**
     * 取得問卷題組列表
     *
     * @param questionId
     */
    public List<GeoKgoQuestionnaireTopicModel> getQuestionnaireTopicList(String questionId) {
        String sqlStr = "SELECT qmt.question_id, qmt.topic_id, qt.topic_name, qt.topic_desc, qmt.topic_sort,qt.is_enable ";
        String fromStr = "FROM GEO_KGO_QUESTIONNAIRE_MAIN_TOPIC qmt left join GEO_KGO_QUESTIONNAIRE_TOPIC qt on qmt.topic_id = qt.topic_id ";
        String whereStr = "WHERE qt.is_enable=1 ";

        if (questionId != null && !questionId.isEmpty()) {
            //依問卷id取得
            whereStr = whereStr + "and qmt.topic_id=qt.topic_id and qmt.question_id=:questionId order by qmt.topic_sort ;";
        } else {
            whereStr = whereStr + ";";
        } //if (questionId != null && !questionId.isEmpty())

        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr);
        //LOGGER.info("GeoKgoQuestionnaireReposCustom getQuestionnaireTopicList sqlStr: "+sqlStr+fromStr+whereStr);
        if (questionId != null && !questionId.isEmpty()) sqlQuery.setParameter("questionId", questionId);

        List listData = sqlQuery.getResultList();
        List<GeoKgoQuestionnaireTopicModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoKgoQuestionnaireTopicModel model = new GeoKgoQuestionnaireTopicModel();
            Object[] recordArray = (Object[]) listData.get(i);
            model.setQuestionId((String) recordArray[0]);
            model.setTopicId((String) recordArray[1]);
            model.setTopicName((String) recordArray[2]);
            if (recordArray[3] != null) model.setTopicDesc((String) recordArray[3]);
            if (recordArray[4] != null) model.setTopicSort((int) recordArray[4]);
            model.setIsEnable((Integer) recordArray[5]);
            datas.add(model);
        } //for (int i = 0; i < listData.size(); i++)
        //LOGGER.info("GeoKgoQuestionnaireReposCustom getQuestionnaireTopicList list size: "+list.size());
        return datas;
    } //getQuestionnaireTopicList

    /**
     * 取得問卷子題目列表
     *
     * @param topicId
     */
    public List<GeoKgoQuestionnaireTopicDetailModel> getQuestionnaireDetailList(String topicId) {
        String sqlStr = "SELECT qtd.detail_id, qtd.detail_name, qtd.detail_type, qtd.detail_sort, qtd.detail_choose, " +
                "qtd.is_must ";
        String fromStr = "FROM GEO_KGO_QUESTIONNAIRE_TOPIC_DETAIL qtd ";
        String whereStr = "WHERE qtd.topic_id=:topicId order by qtd.detail_sort ;";

        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr);
        //LOGGER.info("GeoKgoQuestionnaireReposCustom getQuestionnaireDetailList sqlStr: "+sqlStr+fromStr+whereStr);
        sqlQuery.setParameter("topicId", topicId);

        List listData = sqlQuery.getResultList();
        List<GeoKgoQuestionnaireTopicDetailModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoKgoQuestionnaireTopicDetailModel model = new GeoKgoQuestionnaireTopicDetailModel();
            Object[] recordArray = (Object[]) listData.get(i);
            model.setDetailId((String) recordArray[0]);
            if (recordArray[1] != null) model.setDetailName((String) recordArray[1]);
            if (recordArray[2] != null) model.setDetailType((int) recordArray[2]);
            if (recordArray[3] != null) model.setDetailSort((int) recordArray[3]);
            if (recordArray[4] != null) model.setDetailChoose((String) recordArray[4]);
            if (recordArray[5] != null) model.setIsMust((int) recordArray[5]);
            datas.add(model);
        }
        //LOGGER.info("GeoKgoQuestionnaireReposCustom getQuestionnaireDetailList list size: "+list.size());
        return datas;
    } //getQuestionnaireDetailList

    /**
     * 找尋該案件ID底下版本最大號的問卷資料
     *
     * @param caseSetId
     * @return
     */
    public GeoKgoQuestionnaireCaseSetModel findMaxVersionQuestionCaseSet(String caseSetId, Integer maxVersion) {
        GeoKgoQuestionnaireCaseSetModel model = null;
        if (maxVersion != null && !ObjectUtils.isEmpty(maxVersion)) {
            String sqlStr = "SELECT qc.case_set_id, qc.question_name, qc.question_desc, " +
                    "qc.date_start, qc.date_end, qc.is_enable, qc.edit_organ, qc.edit_user, qc.questiin_version ";
            String fromStr = "FROM GEO_KGO_QUESTIONNAIRE_CASESET qc ";
            String whereStr = "WHERE qc.case_set_id = :caseSetId and qc.questiin_version = :version ";

            Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr);
            sqlQuery.setParameter("caseSetId", caseSetId);
            sqlQuery.setParameter("version", maxVersion);
            List listData = sqlQuery.getResultList();
            if (listData != null && listData.size() > 0) {
                model = new GeoKgoQuestionnaireCaseSetModel();
                Object[] recordArray = (Object[]) listData.get(0);
                if (recordArray[0] != null) model.setCaseSetId((String) recordArray[0]);
                if (recordArray[1] != null) model.setQuestionName((String) recordArray[1]);
                if (recordArray[2] != null) model.setQuestionDesc((String) recordArray[2]);
                if (recordArray[3] != null) model.setDateStart((String) recordArray[3]);
                if (recordArray[4] != null) model.setDateEnd((String) recordArray[4]);
                if (recordArray[5] != null) model.setIsEnable((Integer) recordArray[5]);
                if (recordArray[6] != null) model.setEditOrgan((String) recordArray[6]);
                if (recordArray[7] != null) model.setEditUser((String) recordArray[7]);
                if (recordArray[8] != null) model.setQuestiinVersion((Integer) recordArray[8]);
            }
        }//if (ObjectUtils.isEmpty(maxVersion))
        return model;
    } //findMaxVersionQuestionCaseSet

    /**
     * 找該案件對應最大的問卷 version，null 表示沒資料
     *
     * @param caseSetId
     */
    public Integer findMaxQuestionSetVersionByCaseSetId(String caseSetId) {
        String sqlStr = "select MAX([questiin_version]) MAX_VERSION  ";
        String fromStr = "FROM GEO_KGO_QUESTIONNAIRE_CASESET qc ";
        String whereStr = "WHERE qc.case_set_id = :caseSetId";

        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr);
        sqlQuery.setParameter("caseSetId", caseSetId);
        Integer maxVersion = null;
        try {
            maxVersion = (Integer) sqlQuery.getSingleResult();
        } catch (Exception e) {
            LOGGER.error("findMaxVersionByCasesetId error:" + e.getMessage());
        }

        return maxVersion;
    } //findMaxQuestionSetVersionByCaseSetId

    /**
     * 取得服務問卷查詢列表
     */
    public List<GeoQuestionnaireResultQueryDataGridModel> getQuestionnaireResultList(String caseSetName, Date dateStart, Date dateEnd, String ownerOrgan) {
        String witSelectStr = "WITH CaseSetData (caseSetId, caseSetName, OwnerOrgan, Organ) AS ( " +
                "SELECT cs.CaseSetId, cs.CaseSetName, cs.OwnerOrgan, cs.Organ " +
                "FROM KGO_CASESET cs " +
                "WHERE cs.CaseSetName like CONCAT('%', :caseSetName,'%') ";
        String sqlStr = "SELECT qcs.case_set_id ,cs.caseSetName,gl.OrganId, cs.Organ, gl.OrganName,qcs.question_name,qcs.questiin_version,qcs.date_start, qcs.date_end ";
        String fromStr = "FROM CaseSetData cs, GEO_KGO_QUESTIONNAIRE_CASESET qcs, KGO_ORGAN gl ";
        String whereStr = "WHERE cs.caseSetId = qcs.case_set_id and gl.OrganId = cs.OwnerOrgan  and qcs.is_enable = 1 ";
        String orderStr = "GROUP BY qcs.case_set_id , cs.caseSetName, gl.OrganId, cs.Organ, gl.OrganName, qcs.question_name, qcs.questiin_version,qcs.date_start,qcs.date_end ";
        if (StringUtils.isNotBlank(ownerOrgan)) witSelectStr += "and cs.OwnerOrgan = :ownerOrgan) ";
        else witSelectStr += ") ";
        if (dateStart != null && dateEnd != null) {
            whereStr += "and Convert(datetime,qcs.date_end) <= Convert(datetime, :dateEnd ) and Convert(datetime,qcs.date_start) >= Convert(datetime, :dateStart) ";
        }

        Query sqlQuery = getEntityManager().createNativeQuery(witSelectStr + sqlStr + fromStr + whereStr + orderStr);
        sqlQuery.setParameter("caseSetName", caseSetName);
        if (StringUtils.isNotBlank(ownerOrgan)) sqlQuery.setParameter("ownerOrgan", ownerOrgan);
        if (dateStart != null && dateEnd != null) {
            sqlQuery.setParameter("dateEnd", dateEnd);
            sqlQuery.setParameter("dateStart", dateStart);
        }
        List listData = sqlQuery.getResultList();
        List<GeoQuestionnaireResultQueryDataGridModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoQuestionnaireResultQueryDataGridModel model = new GeoQuestionnaireResultQueryDataGridModel();
            Object[] recordArray = (Object[]) listData.get(i);
            if (recordArray[0] != null) model.setCaseSetId((String) recordArray[0]);
            if (recordArray[1] != null) model.setCaseSetName((String) recordArray[1]);
            if (recordArray[2] != null) model.setOrganId((String) recordArray[2]);
            if (recordArray[3] != null) model.setSeqId((String) recordArray[3]);
            if (recordArray[4] != null) model.setOrganName((String) recordArray[4]);
            if (recordArray[5] != null) model.setQuestionName((String) recordArray[5]);
            if (recordArray[6] != null) model.setQuestionVersion((int) recordArray[6]);
            if (recordArray[7] != null) model.setDateStart((String) recordArray[7]);
            if (recordArray[8] != null) model.setDateEnd((String) recordArray[8]);
            datas.add(model);
        }
        LOGGER.info("GeoKgoQuestionnaireReposCustom getQuestionnaireResultList datas size: " + datas.size());
        return datas;
    } //getQuestionnaireResultList

    /**
     * 問卷內是否含有配分題型
     */
    public boolean getQuestionnaireWithType(String caseSetId, Integer caseSetVersion, int type) {
        String sqlStr = "SELECT qct.case_set_id, qct.questiin_version ";
        String fromStr = "FROM GEO_KGO_QUESTIONNAIRE_CASESET_TOPIC qct, GEO_KGO_QUESTIONNAIRE_CASESET_DETAIL qcd ";
        String whereStr = "WHERE qcd.detail_type = :type and qct.topic_caseset_id = qcd.topic_caseset_id and qct.case_set_id = :caseSetId and qct.questiin_version = :caseSetVersion ";
        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr);
        sqlQuery.setParameter("caseSetId", caseSetId);
        sqlQuery.setParameter("caseSetVersion", caseSetVersion);
        sqlQuery.setParameter("type", type);

        List listData = sqlQuery.getResultList();
        if (listData != null && listData.size() > 0) {
            return true;
        }
        return false;
    } //getQuestionnaireResultList

    /**
     * 取得問卷結果(配分)
     */
    public List<GeoKgoQuestionnaireResultQueryAnswerDetailModel> getQuestionnaireAnswerDetailTotalScoreList(String detailCcaseSetId) {
        String sqlStr = "SELECT qad.detail_caseset_id, qad.answer_value, qad.answer_score, COUNT(qad.answer_score) ";
        String fromStr = "FROM GEO_KGO_QUESTIONNAIRE_ANSWER_DETAIL qad ";
        String whereStr = "WHERE qad.detail_caseset_id = :detailCcaseSetId " +
                "GROUP BY qad.detail_caseset_id, qad.answer_value, qad.answer_score ";
        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr);
        sqlQuery.setParameter("detailCcaseSetId", detailCcaseSetId);

        List listData = sqlQuery.getResultList();
        List<GeoKgoQuestionnaireResultQueryAnswerDetailModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoKgoQuestionnaireResultQueryAnswerDetailModel model = new GeoKgoQuestionnaireResultQueryAnswerDetailModel();
            Object[] recordArray = (Object[]) listData.get(i);
            if (recordArray[0] != null) model.setDetailCasesetId((String) recordArray[0]);
            if (recordArray[1] != null) model.setAnswerValue((String) recordArray[1]);
            if (recordArray[2] != null) model.setAnswerScore((Integer) recordArray[2]);
            if (recordArray[3] != null) model.setFrequencyCount((Integer) recordArray[3]);
            datas.add(model);
        }
//        LOGGER.info("GeoKgoQuestionnaireReposCustom getQuestionnaireResultList datas size: "+datas.size());
        return datas;
    } //getQuestionnaireResultList

    public GeoBidCaseListQueryDataGridModel getCaseSetData(String caseSetId) {
        String sqlStr = "SELECT cs.CaseSetId, cs.CaseSetName, cs.OwnerOrgan, cs.Organ, gl.OrganName ";
        String fromStr = "FROM KGO_CASESET cs, KGO_ORGAN gl ";
        String whereStr = "WHERE cs.CaseSetId = :caseSetId and gl.OrganId = cs.OwnerOrgan ";
        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr);
        if (caseSetId != null) sqlQuery.setParameter("caseSetId", caseSetId);
//        LOGGER.info("GeoKgoQuestionnaireReposCustom getCaseSetData sqlQuery: " + sqlStr + fromStr + whereStr);
        List listData = sqlQuery.getResultList();
        GeoBidCaseListQueryDataGridModel data = null;
        if (listData != null && listData.size() > 0) {
            data = new GeoBidCaseListQueryDataGridModel();
            Object[] recordArray = (Object[]) listData.get(0);
            data.setCaseSetId((String) recordArray[0]);
            data.setCaseSetName((String) recordArray[1]);
            data.setOrganId((String) recordArray[2]);
            data.setOrganSeq((String) recordArray[3]);
            data.setOrganName((String) recordArray[4]);
        }
        return data;
    } //getCaseSetData

    /**
     * 後台-檢視填寫:取得答案清單組合資料
     */
    public List<GeoKgoQuestionnaireAnswerListModel> getQuestionnaireAnswerList(String caseSetId, Integer version, String columnId) {
        String sqlStr = "SELECT cm.ApplyUserName, cm.CellPhone, cd.ColumnValue,qa.apply_date,cm.CaseId, qa.answer_id ";
        String fromStr = "FROM KGO_CASE_MAIN cm, GEO_KGO_QUESTIONNAIRE_ANSWER qa, KGO_CASE_DETAIL cd ";
        String whereStr = "WHERE qa.case_set_id = :caseSetId and qa.questiin_version = :version " +
                "and qa.case_id = cm.CaseId and qa.case_id = cd.CaseId and cd.ColumnId = :columnId ORDER BY qa.apply_date ASC ";
        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr);
        sqlQuery.setParameter("caseSetId", caseSetId);
        sqlQuery.setParameter("version", version);
        sqlQuery.setParameter("columnId", columnId);
        List listData = sqlQuery.getResultList();
        List<GeoKgoQuestionnaireAnswerListModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoKgoQuestionnaireAnswerListModel model = new GeoKgoQuestionnaireAnswerListModel();
            Object[] recordArray = (Object[]) listData.get(i);
            if (recordArray[0] != null) model.setUserName((String) recordArray[0]);
            if (recordArray[1] != null) model.setUserMobilePhone((String) recordArray[1]);
            if (recordArray[2] != null) model.setUserEmail((String) recordArray[2]);
            if (recordArray[3] != null) model.setApplyDate((Timestamp) recordArray[3]);
            if (recordArray[4] != null) model.setCaseId((String) recordArray[4]);
            if (recordArray[5] != null) model.setAnswerId((String) recordArray[5]);
            datas.add(model);
        }
//        LOGGER.info("GeoKgoQuestionnaireReposCustom getQuestionnaireAnswerList datas size: "+datas.size());
        return datas;
    } //getQuestionnaireAnswerList
}
