package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.geoentity.GeoKgoAgent;
import gov.kcg.kgo.geomodel.GeoKgoUserInfoModel;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GEO 20211030 add
 * 後台-案件稽核管理 取得稽核案件相關資料
 */
@Repository
public class GeoCaseInspectReposCustom extends GeoBaseReposCustom {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseInspectReposCustom.class);

    @PersistenceContext
    public EntityManager entityManager;

    /** GEO 20211224 add 搜尋條件增加：服務名稱 caseSetName */
    public List getRandomCaseList(String caseFlowType,String caseSetName, Timestamp applyDateStart, Timestamp applyDateEnd,Integer percentage) {
        return getRandomCaseList(caseFlowType, applyDateStart, applyDateEnd, null, null, caseSetName, percentage);
    }

    /**
     * 後台-案件稽核管理:案件查詢
     */
    public List getRandomCaseList(String caseFlowType, Timestamp applyDateStart, Timestamp applyDateEnd, String ownerOrganId, String caseSetId, String caseSetName,Integer percentage) {
        String sqlStr = "SELECT Top (:percentage) " +
                "cm.CaseId, cm.ApplyDate, cm.ApplyUserName, cs.CaseSetName, cm.DeadlineDate, cm.Status, u.Name ";
        String fromStr = "From KGO_CASE_MAIN cm " +
                "left join KGO_CASESET cs on cm.CaseSetId = cs.CaseSetId " +
                "left join KGO_USER u on cm.CaseOfficer = u.UserId ";
        String whereStr = "WHERE 1=1 ";
        String orderStr = "ORDER BY NEWID(); ";
        if (StringUtils.isNotBlank(caseFlowType)) whereStr += "AND cs.CaseFlowType = :caseFlowType ";
        if (ObjectUtils.isNotEmpty(applyDateStart) && ObjectUtils.isNotEmpty(applyDateEnd)) whereStr +=
                "AND convert(date, cm.ApplyDate) between (:applyDateStart) and (:applyDateEnd)";
        if (StringUtils.isNotBlank(ownerOrganId)) whereStr += "AND cs.OwnerOrgan = :ownerOrganId ";
        if (StringUtils.isNotBlank(caseSetId)) whereStr += "AND cm.CaseSetId = :caseSetId ";
        if (StringUtils.isNotBlank(caseSetName)) whereStr += "AND cs.CaseSetName like CONCAT('%', :caseSetName,'%') ";

        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr + orderStr);
        LOGGER.info("getRandomCaseList sqlQuery :" + sqlStr + fromStr + whereStr + orderStr);
        sqlQuery.setParameter("percentage", percentage);
        if (StringUtils.isNotBlank(caseFlowType)) sqlQuery.setParameter("caseFlowType", caseFlowType);
        if (ObjectUtils.isNotEmpty(applyDateStart)) sqlQuery.setParameter("applyDateStart", applyDateStart);
        if (ObjectUtils.isNotEmpty(applyDateEnd)) sqlQuery.setParameter("applyDateEnd", applyDateEnd);
        if (StringUtils.isNotBlank(ownerOrganId)) sqlQuery.setParameter("ownerOrganId", ownerOrganId);
        if (StringUtils.isNotBlank(caseSetId)) sqlQuery.setParameter("caseSetId", caseSetId);
        if (StringUtils.isNotBlank(caseSetName)) sqlQuery.setParameter("caseSetName", caseSetName);
        return sqlQuery.getResultList();
    } //getRandomCaseList

//    public Integer getRandomCaseCount(String caseFlowType, Timestamp applyDateStart, Timestamp applyDateEnd) {
//        return getRandomCaseCount(caseFlowType, applyDateStart, applyDateEnd, null, null, null);
//    }

    /**
     * 後台-案件稽核管理:案件查詢，總案件數
     */
/**
    public Integer getRandomCaseCount(String caseFlowType, Timestamp applyDateStart, Timestamp applyDateEnd, String ownerOrganId, String caseSetId, String caseSetName) {
        Integer count = null;
        String sqlStr = "SELECT COUNT(cm.CaseId) ";
        String fromStr = "From KGO_CASE_MAIN cm " +
                "left join KGO_CASESET c on cm.CaseSetId = c.CaseSetId " +
                "left join KGO_USER u on cm.CaseOfficer = u.UserId ";
        String whereStr = "WHERE 1=1 ";
        if (StringUtils.isNotBlank(caseFlowType)) whereStr += "AND c.CaseFlowType = :caseFlowType ";
        if (ObjectUtils.isNotEmpty(applyDateStart)) whereStr += "AND cm.ApplyDate >= Convert(datetime, :applyDateStart) ";
        if (ObjectUtils.isNotEmpty(applyDateEnd)) whereStr += "AND cm.ApplyDate <= Convert(datetime, :applyDateEnd) ";
        if (StringUtils.isNotBlank(ownerOrganId)) whereStr += "AND c.OwnerOrgan = :ownerOrganId ";
        if (StringUtils.isNotBlank(caseSetId)) whereStr += "AND cm.CaseSetId = :caseSetId ";
        if (StringUtils.isNotBlank(caseSetName)) whereStr += "AND c.CaseSetName like CONCAT('%', :caseSetName,'%') ";

        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr);
        LOGGER.info("getRandomCaseCount sqlQuery :" + sqlStr + fromStr + whereStr);
        if (StringUtils.isNotBlank(caseFlowType)) sqlQuery.setParameter("caseFlowType", caseFlowType);
        if (ObjectUtils.isNotEmpty(applyDateStart)) sqlQuery.setParameter("applyDateStart", applyDateStart);
        if (ObjectUtils.isNotEmpty(applyDateEnd)) sqlQuery.setParameter("applyDateEnd", applyDateEnd);
        if (StringUtils.isNotBlank(ownerOrganId)) sqlQuery.setParameter("ownerOrganId", ownerOrganId);
        if (StringUtils.isNotBlank(caseSetId)) sqlQuery.setParameter("caseSetId", caseSetId);
        if (StringUtils.isNotBlank(caseSetName)) sqlQuery.setParameter("caseSetName", caseSetName);
        List listData = sqlQuery.getResultList();
        if (listData != null && listData.size() > 0) {
            count = (Integer) listData.get(0);
        }
        return count;
    } //getRandomCaseList

 */
}
