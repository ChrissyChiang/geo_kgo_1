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
 * GEO 20211026 add
 * 後台-設定代理人 取得GEO_KGO_AGENT相關組合資料
 */
@Repository
public class GeoAgentReposCustom extends GeoBaseReposCustom {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoAgentReposCustom.class);

    @PersistenceContext
    public EntityManager entityManager;

    /**
     * 後台-設定代理人:取得該登入人員資料
     */
    public GeoKgoUserInfoModel getUserInfoById(String userId) {
        GeoKgoUserInfoModel model = null;
        String sqlStr = "Select u.UserId, u.Name, u.Organ, u.Unit, o.OrganName, uni.UnitName, u.Email, u.Tel ";
        String fromStr = "From KGO_USER u " +
                "left join KGO_ORGAN o on u.Organ = o.OrganId " +
                "left join KGO_UNIT uni on u.Unit = uni.UnitId and u.Organ = uni.OrganId ";
        String whereStr = "WHERE 1=1 ";
        if (StringUtils.isNotBlank(userId)) whereStr += "and u.UserId = :userId ";

        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr);
        if (StringUtils.isNotBlank(userId)) sqlQuery.setParameter("userId", userId);
        List listData = sqlQuery.getResultList();
        List<GeoKgoUserInfoModel> datas = Collections.synchronizedList(new ArrayList<>());
        if (listData != null && listData.size() > 0) {
            model = new GeoKgoUserInfoModel();
            Object[] recordArray = (Object[]) listData.get(0);
            model.setUserId((String) recordArray[0]);
            model.setName((String) recordArray[1]);
            model.setOrganId((String) recordArray[2]);
            model.setUnitId((String) recordArray[3]);
            model.setOrganName((String) recordArray[4]);
            model.setUnitName((String) recordArray[5]);
            model.setEmail(((String) recordArray[6]));
            String phone = ((String) recordArray[7]);
            model.setPhone(phone == null ? StringUtils.EMPTY : phone);
        }
        return model;
    } //getUserInfoById

    /**
     * 後台-設定代理人:查詢代理人清單
     */
    public List getAgentList(String organId, String unitId, String principalName, Integer isDelete) {
        String withSelectStr = "WITH PrincipalData(principalId, principalName, organName, unitName, organId, unitId) AS ( " +
                "Select u.UserId, u.Name, o.OrganName, uni.UnitName, u.Organ, u.Unit " +
                "From GEO_KGO_AGENT a " +
                "left join KGO_USER u on a.principal_user_id = u.UserId " +
                " left join KGO_ORGAN o on u.Organ = o.OrganId " +
                " left join KGO_UNIT uni on u.Unit = uni.UnitId and u.Organ = uni.OrganId " +
                "GROUP BY u.UserId, u.Name, o.OrganName, uni.UnitName, u.Organ, u.Unit ) ";
        String sqlStr = "Select a.agent_id, a.principal_user_id, p.principalName, p.organId, p.unitId, " +
                "p.organName, p.unitName, a.agent_user_id, u.Name, a.start_time, a.end_time, a.is_signed ";
        String fromStr = "From GEO_KGO_AGENT a " +
                "left join KGO_USER u on a.agent_user_id = u.UserId " +
                "left join PrincipalData p on a.principal_user_id = p.principalId  ";
        String whereStr = "WHERE 1=1 ";
        if (StringUtils.isNotBlank(organId)) whereStr += "and p.organId = :organId ";
        if (StringUtils.isNotBlank(unitId)) whereStr += "and p.unitId = :unitId ";
        if (StringUtils.isNotBlank(principalName))
            whereStr += "and p.principalName like CONCAT('%', :principalName,'%') ";
        if (ObjectUtils.isNotEmpty(isDelete)) whereStr += "and a.is_delete = :isDelete ";

        Query sqlQuery = getEntityManager().createNativeQuery(withSelectStr + sqlStr + fromStr + whereStr);
        if (StringUtils.isNotBlank(organId)) sqlQuery.setParameter("organId", organId);
        if (StringUtils.isNotBlank(unitId)) sqlQuery.setParameter("unitId", unitId);
        if (StringUtils.isNotBlank(principalName)) sqlQuery.setParameter("principalName", principalName);
        if (ObjectUtils.isNotEmpty(isDelete)) sqlQuery.setParameter("isDelete", isDelete);
        return sqlQuery.getResultList();
    } //getAgentList

    /**
     * 後台-設定代理人:查詢代理人清單
     */
    public List<GeoKgoAgent> getPrincipalList(String agentUserId, Timestamp now, Integer isDelete) {
        String sqlStr = "SELECT * ";
        String fromStr = "From GEO_KGO_AGENT a ";
        String whereStr = "WHERE 1=1 ";
        if (StringUtils.isNotBlank(agentUserId)) whereStr += "AND agent_user_id = :agentUserId ";
        if (ObjectUtils.isNotEmpty(now)) whereStr += "AND a.start_time <= Convert(datetime, :now) AND a.end_time >= Convert(datetime, :now) ";
        if (ObjectUtils.isNotEmpty(isDelete)) whereStr += "and a.is_delete = :isDelete ";

        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr, GeoKgoAgent.class);
        if (StringUtils.isNotBlank(agentUserId)) sqlQuery.setParameter("agentUserId", agentUserId);
        if (ObjectUtils.isNotEmpty(now)) sqlQuery.setParameter("now", now);
        if (ObjectUtils.isNotEmpty(isDelete)) sqlQuery.setParameter("isDelete", isDelete);
        return sqlQuery.getResultList();
    } //getPrincipalList
}
