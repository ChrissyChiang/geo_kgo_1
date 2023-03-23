package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.dto.CasesetOrganGroupQueryDataMaxVersionDto;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * GEO 20211109 add 機關審核表單
 * 網路申辦-表單設定- 機關審核表單
 */
@Repository
public class GeoKgoCasesetOrganGroupReposCustom extends GeoBaseReposCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoKgoCasesetOrganGroupReposCustom.class);

    @PersistenceContext
    public EntityManager entityManager;

    public Integer saveData(String caseSetId, int version, String memo, int orderNum, int caseFormVersion, int isShow) {
        String sqlStr = "insert into GEO_KGO_CASESET_GROUP_ORGAN (CaseSetId, [Version], Memo, OrderNum, [CaseFormVersion], isShow ) " +
                "values (:caseSetId, :version, :memo, :orderNum, :caseFormVersion, :isShow) ";
        return entityManager.createNativeQuery(sqlStr).setParameter("caseSetId", caseSetId)
                .setParameter("version", version).setParameter("memo", memo).setParameter("orderNum", orderNum)
                .setParameter("caseFormVersion", caseFormVersion).setParameter("isShow", isShow)
                .executeUpdate();
    } //saveData

    /**
     * 找尋該案件ID底下所有版本最大號的群組資料
     *
     * @param caseSetId
     * @return
     */
    public List<CasesetOrganGroupQueryDataMaxVersionDto> findMaxVersionGroupData(String caseSetId, String memo) {
        Integer maxCaseFormVersion = findMaxCaseFormVersionByCasesetId(caseSetId);
        if (ObjectUtils.isEmpty(maxCaseFormVersion)) return null;

        Integer maxVersion = findMaxVersionByCasesetId(caseSetId, maxCaseFormVersion);
        if (ObjectUtils.isEmpty(maxCaseFormVersion)) return null;

        String selectStr = "select GroupSeq GROUP_SEQ, Memo MEMO, Version VERSION, OrderNum ORDER_NUM, IsShow, CaseFormVersion CASE_FORM_VERSION ";
        String fromStr = "from GEO_KGO_CASESET_GROUP_ORGAN ";
        String whereStr = "where Version = :version And CaseFormVersion = :maxCaseFormVersion ";
        String orderStr = "order by GroupSeq ";
        if (StringUtils.isNotBlank(caseSetId)) whereStr += "and CaseSetId = :caseSetId ";
        if (StringUtils.isNotBlank(memo)) whereStr += "and Memo = :memo ";

        Query query = entityManager.createNativeQuery(selectStr + fromStr + whereStr + orderStr, CasesetOrganGroupQueryDataMaxVersionDto.class);
        query.setParameter("version", maxVersion);
        query.setParameter("maxCaseFormVersion", maxCaseFormVersion);
        if (StringUtils.isNotBlank(caseSetId)) query.setParameter("caseSetId", caseSetId);
        if (StringUtils.isNotBlank(memo)) query.setParameter("memo", memo);

        return query.getResultList();
    } //findMaxVersionGroupData

    /**
     * 找尋該案件群組資料
     *
     * @param caseSetId
     * @return
     */
    public List<CasesetOrganGroupQueryDataMaxVersionDto> findGroupData(String caseSetId, String memo, Integer caseFormVersion) {
        Integer maxVersion = findMaxVersionByCasesetId(caseSetId, caseFormVersion);
        if (ObjectUtils.isEmpty(caseFormVersion)) return null;

        String selectStr = "select GroupSeq GROUP_SEQ, Memo MEMO, Version VERSION, OrderNum ORDER_NUM, IsShow, CaseFormVersion CASE_FORM_VERSION ";
        String fromStr = "from GEO_KGO_CASESET_GROUP_ORGAN ";
        String whereStr = "where Version = :version And CaseFormVersion = :maxCaseFormVersion ";
        String orderStr = "order by GroupSeq ";
        if (StringUtils.isNotBlank(caseSetId)) whereStr += "and CaseSetId = :caseSetId ";
        if (StringUtils.isNotBlank(memo)) whereStr += "and Memo = :memo ";

        Query query = entityManager.createNativeQuery(selectStr + fromStr + whereStr + orderStr, CasesetOrganGroupQueryDataMaxVersionDto.class);
        query.setParameter("version", maxVersion);
        query.setParameter("maxCaseFormVersion", caseFormVersion);
        if (StringUtils.isNotBlank(caseSetId)) query.setParameter("caseSetId", caseSetId);
        if (StringUtils.isNotBlank(memo)) query.setParameter("memo", memo);

        return query.getResultList();
    } //findGroupData

    public Integer findMaxVersionByCasesetId(String caseSetId, Integer maxCaseFormVersion) {
        String sqlStr = "select MAX([Version]) MAX_VERSION from GEO_KGO_CASESET_GROUP_ORGAN where CaseSetId = :caseSetId AND CaseFormVersion = :maxCaseFormVersion";
        Query query = entityManager.createNativeQuery(sqlStr);
        query.setParameter("caseSetId", caseSetId);
        query.setParameter("maxCaseFormVersion", maxCaseFormVersion);
        Integer maxVerison = null;
        try {
            maxVerison = (Integer) query.getSingleResult();
        } catch (Exception e) {
            LOGGER.error("GeoKgoCasesetOrganGroupReposCustom findMaxVersionByCasesetId error:" + e.getMessage());
        }
        return maxVerison;
    } //findMaxVersionByCasesetId

    public Integer findMaxCaseFormVersionByCasesetId(String caseSetId) {
        String sqlStr = "select MAX([Version]) MAX_VERSION from KGO_CASESET_GROUP where CaseSetId = :caseSetId ";
        Query query = entityManager.createNativeQuery(sqlStr);
        query.setParameter("caseSetId", caseSetId);
        Integer maxVerison = null;
        try {
            maxVerison = (Integer) query.getSingleResult();
        } catch (Exception e) {
            LOGGER.error("GeoKgoCasesetOrganGroupReposCustom findMaxCaseFormVersionByCasesetId error:" + e.getMessage());
        }
        return maxVerison;
    } //findMaxCaseFormVersionByCasesetId
}
