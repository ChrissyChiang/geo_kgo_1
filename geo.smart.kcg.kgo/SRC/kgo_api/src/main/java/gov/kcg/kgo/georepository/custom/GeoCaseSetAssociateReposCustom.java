package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.dto.CaseManagementQueryDto;
import gov.kcg.kgo.dto.GeoCaseByOrganModel;
import gov.kcg.kgo.geomodel.GeoKgoCasesetAssociateModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GEO 20211019 add
 * 後台-案件關聯服務 取得 KGO_CASESET_ASSOCIATE相關組合資料
 */
@Repository
public class GeoCaseSetAssociateReposCustom extends GeoBaseReposCustom {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseSetAssociateReposCustom.class);

    @PersistenceContext
    public EntityManager entityManager;

    /**
     * 服務案件清單-案件查詢
     *
     * @param organSeq    機關分類代碼 from KGO_GROUP_LEVEL.seq
     * @param ownerOrgan  權責機關代碼
     * @param caseSetId   案件種類ID
     * @param caseSetName 案件名稱
     * @param userId      管理者ID
     * @return
     */
    public List<CaseManagementQueryDto> findAllCaseSet(Integer organSeq, String ownerOrgan, String caseSetId, String caseSetName, String userId, String thisCaseSetId, Integer isOpen) {
        String withSelectStr = "with MANAGER_ALL as ( " +
                "Select k.caseSetId CASESET_ID, k.manager MANAGER, ku.Name NAME, ROW_NUMBER() OVER (PARTITION BY k.caseSetId ORDER BY k.Manager ASC) ROWNUM " +
                "From KGO_CASESET_MANAGER k Left Join KGO_USER ku On ku.UserId = k.Manager ), " +
                "SIZE_OF_MANAGER_BY_CASE_ID as ( " +
                "Select CASESET_ID, MAX(rownum) as SIZE " +
                "From MANAGER_ALL " +
                "Group By CASESET_ID ), " +
                "FINAL_MANAGER_DATA as ( " +
                "Select m.CASESET_ID, m.MANAGER, Case When g.SIZE > 1 then m.NAME + '...' else m.NAME End As NAME " +
                "From SIZE_OF_MANAGER_BY_CASE_ID g, MANAGER_ALL m " +
                "where g.SIZE = m.ROWNUM and g.CASESET_ID = m.CASESET_ID) ";

        String selectStr = "Select c.Sort SORT, c.ServiceTo SERVICE_TO, c.CaseSetId CASESET_ID, c.CaseSetName CASESET_NAME, " +
                "c.Organ ORGAN_ID, gl.Name ORGAN_NAME, c.OwnerOrgan OWNER_ORGAN_ID, o2.OrganName OWNER_ORGAN_NAME, " +
                "f.NAME as MANAGER_NAME , c.Status [STATUS], c.CaseType CASE_TYPE, c.FlowId FLOW_Id ";

        String fromStr = "From KGO_CASESET c " +
                "Left Join KGO_GROUP_LEVEL gl On c.Organ = gl.Seq " +
                "Left Join KGO_ORGAN o2 On c.OwnerOrgan = o2.OrganId " +
                "Left Join FINAL_MANAGER_DATA f on f.CASESET_ID = c.CaseSetId ";
        if (StringUtils.isNotBlank(userId)) fromStr += ", MANAGER_ALL m ";

        String whereStr = "where c.CaseSetId != :thisCaseSetId ";
        if (!ObjectUtils.isEmpty(isOpen)) whereStr += "and c.IsOpenForOrgan = :isOpen ";
        if (!ObjectUtils.isEmpty(organSeq)) whereStr += "and c.Organ = :organSeq ";
        if (StringUtils.isNotBlank(ownerOrgan)) whereStr += "and c.OwnerOrgan = :ownerOrgan ";
        if (StringUtils.isNotBlank(caseSetId)) whereStr += "and c.CaseSetId = :caseSetId ";
        if (StringUtils.isNotBlank(caseSetName)) whereStr += "and c.CaseSetName like CONCAT('%', :caseSetName,'%') ";
        if (StringUtils.isNotBlank(userId)) whereStr += "and m.CASESET_ID = c.CaseSetId and m.MANAGER = :userId ";

        String orderStr = "ORDER BY  CASE " + "when c.Status='On' then 1 " + "when c.Status='Off' then 2 " + "ELSE 3 END ,c.Sort ";
        Query query = entityManager.createNativeQuery(withSelectStr + selectStr + fromStr + whereStr + orderStr, CaseManagementQueryDto.class);
        query.setParameter("thisCaseSetId", thisCaseSetId);
        if (!ObjectUtils.isEmpty(isOpen)) query.setParameter("isOpen", isOpen);
        if (!ObjectUtils.isEmpty(organSeq)) query.setParameter("organSeq", organSeq);
        if (StringUtils.isNotBlank(ownerOrgan)) query.setParameter("ownerOrgan", ownerOrgan);
        if (StringUtils.isNotBlank(caseSetId)) query.setParameter("caseSetId", caseSetId);
        if (StringUtils.isNotBlank(caseSetName)) query.setParameter("caseSetName", caseSetName);
        if (StringUtils.isNotBlank(userId)) query.setParameter("userId", userId);

        return query.getResultList();
    } //findAllCaseSet

    /**
     * 服務案件清單-案件查詢
     *
     * @param caseSetId   案件種類ID
     * @return
     */
    public List<CaseManagementQueryDto> findAllAssociateCaseSetByCaseSetId(String caseSetId) {
        String withSelectStr = "with MANAGER_ALL as ( " +
                "Select k.caseSetId CASESET_ID, k.manager MANAGER, ku.Name NAME, ROW_NUMBER() OVER (PARTITION BY k.caseSetId ORDER BY k.Manager ASC) ROWNUM " +
                "From KGO_CASESET_MANAGER k Left Join KGO_USER ku On ku.UserId = k.Manager ), " +
                "SIZE_OF_MANAGER_BY_CASE_ID as ( " +
                "Select CASESET_ID, MAX(rownum) as SIZE " +
                "From MANAGER_ALL " +
                "Group By CASESET_ID ), " +
                "FINAL_MANAGER_DATA as ( " +
                "Select m.CASESET_ID, m.MANAGER, Case When g.SIZE > 1 then m.NAME + '...' else m.NAME End As NAME " +
                "From SIZE_OF_MANAGER_BY_CASE_ID g, MANAGER_ALL m " +
                "where g.SIZE = m.ROWNUM and g.CASESET_ID = m.CASESET_ID) ";

        String selectStr = "Select c.Sort SORT, c.ServiceTo SERVICE_TO, c.CaseSetId CASESET_ID, c.CaseSetName CASESET_NAME, " +
                "c.Organ ORGAN_ID, gl.Name ORGAN_NAME, c.OwnerOrgan OWNER_ORGAN_ID, o2.OrganName OWNER_ORGAN_NAME, " +
                "f.NAME as MANAGER_NAME , c.Status [STATUS], c.CaseType CASE_TYPE, c.FlowId FLOW_Id ";

        String fromStr = "From KGO_CASESET c " +
                "Left Join KGO_GROUP_LEVEL gl On c.Organ = gl.Seq " +
                "Left Join KGO_ORGAN o2 On c.OwnerOrgan = o2.OrganId " +
                "Left Join FINAL_MANAGER_DATA f on f.CASESET_ID = c.CaseSetId " +
                "Left Join  GEO_KGO_CASESET_ASSOCIATE ca on c.CaseSetId = ca.associate_caseset_id ";

        String whereStr = "where ca.caseset_id = :caseSetId ";
        String orderStr = "ORDER BY  CASE " + "when c.Status='On' then 1 " + "when c.Status='Off' then 2 " + "ELSE 3 END ,c.Sort ";
        Query query = entityManager.createNativeQuery(withSelectStr + selectStr + fromStr + whereStr + orderStr, CaseManagementQueryDto.class);
        query.setParameter("caseSetId", caseSetId);
        return query.getResultList();
    } //findAllCaseSet

    /**
     * 查詢關聯服務 By PK
     */
    public List<GeoKgoCasesetAssociateModel> findAllCaseAssociateByPK(String caseSetId, String associateCaseSetId) {
        return findAllCaseAssociate(caseSetId, associateCaseSetId);
    }

    /**
     * 查詢關聯服務
     */
    private List<GeoKgoCasesetAssociateModel> findAllCaseAssociate(String caseSetId, String associateCaseSetId) {
        String selectStr = "select ca.caseset_id, ca.associate_caseset_id ";
        String fromStr = "from GEO_KGO_CASESET_ASSOCIATE ca ";
        String whereStr = "where 1=1 ";
        if (StringUtils.isNotBlank(caseSetId)) whereStr += "and ca.caseset_id = :caseSetId ";
        if (StringUtils.isNotBlank(associateCaseSetId)) whereStr += "and ca.associate_caseset_id = :associateCaseSetId ";

        Query sqlQuery = entityManager.createNativeQuery(selectStr + fromStr + whereStr);
        if (StringUtils.isNotBlank(caseSetId)) sqlQuery.setParameter("caseSetId", caseSetId);
        if (StringUtils.isNotBlank(associateCaseSetId)) sqlQuery.setParameter("associateCaseSetId", associateCaseSetId);
        List listData = sqlQuery.getResultList();
        List<GeoKgoCasesetAssociateModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoKgoCasesetAssociateModel model = new GeoKgoCasesetAssociateModel();
            Object[] recordArray = (Object[]) listData.get(i);
            model.setCasesetId((String) recordArray[0]);
            model.setAssociateCasesetId((String) recordArray[1]);
            datas.add(model);
        }
        return datas;
    } //findAllCaseAssociate

    /**
     * 確認該服務 是否開啟府內線上服務
     */
    public Integer checkIsOpenForOrgan(String caseSetId) {
        Integer isOpenForOrgan = null;
        String selectStr = "select c.IsOpenForOrgan , c.CaseSetId ";
        String fromStr = "from KGO_CASESET c where c.CasesetId = :caseSetId ";
        Query sqlQuery = entityManager.createNativeQuery(selectStr + fromStr);
        sqlQuery.setParameter("caseSetId", caseSetId);
        List listData = sqlQuery.getResultList();
        if (listData != null && listData.size() > 0) {
            Object[] recordArray = (Object[]) listData.get(0);
            isOpenForOrgan = (Integer) recordArray[0];
        }
        LOGGER.info("checkIsOpenForOrgan isOpenForOrgan/ listData.size: " + caseSetId + "/" + isOpenForOrgan + "/" + listData.size());
        return isOpenForOrgan;
    } //checkIsOpenForOrgan

    public List<GeoCaseByOrganModel> getCaseDetailColumnByCaseSetId(String caseSetId, Integer version) {
        LOGGER.info("getCaseDetailColumnByCaseSetId caseSetId="+caseSetId);
        LOGGER.info("getCaseDetailColumnByCaseSetId version="+version);
        List<GeoCaseByOrganModel> geoCaseByOrganModels = new ArrayList<>();
        StringBuilder sql  = new StringBuilder();
        sql.append("SElECT distinct kcdm.ColumnName, kdl.CaseId, kdl.ColumnId, kdl.ColumnValue, "
                + " kdl.Version,kcm.CaseSetId "
                + " from KGO_CASE_DETAIL kdl "
                + " left join KGO_CASE_MAIN kcm on kcm.CaseId = kdl.CaseId "
                + " left join KGO_CASESET_COLUMN kcdm on kcdm.CaseSetId = kcm.CaseSetId "
                + " and kcdm.Version = kdl.Version "
                + " and kcdm.ColumnId = kdl.ColumnId "
                + " where kcdm.CaseSetId = :caseSetId ");
        if (version !=0 ) sql.append(" and kcdm.Version=:version ");

        /**
         * Native Query
         */
        Query query = entityManager.createNativeQuery(sql.toString());
        LOGGER.info("sql.toString()="+sql.toString());
        query.setParameter("caseSetId", caseSetId);
        if (version != 0) query.setParameter("version", version);
        List listData = query.getResultList();
        if (listData!=null && listData.size()>0){
            for (int i=0;i<listData.size();i++){
                Object[] recordArray = (Object[]) listData.get(i);
                GeoCaseByOrganModel model = new GeoCaseByOrganModel();
                model.setColumnName((String)recordArray[0]);
                model.setCaseId((String)recordArray[1]);
                model.setColumnId((String)recordArray[2]);
                model.setColumnValue((String)recordArray[3]);
                model.setVersion((Integer) recordArray[4]);
                model.setCaseSetId((String) recordArray[5]);
                geoCaseByOrganModels.add(model);
            } //for (int i=0;i<listData.size();i++)
        } //if (listData!=null && listData.size()>0)
        return geoCaseByOrganModels;
    } //getCaseDetailColumnByCaseSetId
}
