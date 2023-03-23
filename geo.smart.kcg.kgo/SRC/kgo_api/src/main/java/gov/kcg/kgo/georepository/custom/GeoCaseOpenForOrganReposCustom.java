package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.dto.BidCaseListQueryDto;
import gov.kcg.kgo.dto.CaseManagementQueryDto;
import gov.kcg.kgo.enums.backend.PublishStateEnum;
import gov.kcg.kgo.enums.common.MainTypeEnum;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rq.CaseManagementQueryRq;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * GEO 20211019 add
 * 後台-府內線上服務 取得 KGO_CASE_SET相關組合資料
 */
@Repository
public class GeoCaseOpenForOrganReposCustom extends GeoBaseReposCustom {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseOpenForOrganReposCustom.class);

    @PersistenceContext
    public EntityManager entityManager;

    /**
     * @param organSeq    機關分類代碼 from KGO_GROUP_LEVEL.seq
     * @param ownerOrgan  權責機關代碼
     * @param caseSetId   案件種類ID
     * @param caseSetName 案件名稱
     * @param userId      管理者ID
     * @return
     */
    public List<CaseManagementQueryDto> findAllCase(Integer organSeq, String ownerOrgan, String caseSetId, String caseSetName, String userId) {

        StringBuilder sb = new StringBuilder();

        /**
         * Generate custom query String
         */
        sb.append("with MANAGER_ALL as ( ");
        sb.append("Select k.caseSetId CASESET_ID, k.manager MANAGER, ku.Name NAME, ROW_NUMBER() OVER (PARTITION BY k.caseSetId ORDER BY k.Manager ASC) ROWNUM ");
        sb.append("From KGO_CASESET_MANAGER k Left Join KGO_USER ku On ku.UserId = k.Manager ");
        sb.append("), ");
        sb.append("SIZE_OF_MANAGER_BY_CASE_ID as ( ");
        sb.append("Select CASESET_ID, MAX(rownum) as SIZE ");
        sb.append("From MANAGER_ALL  ");
        sb.append("Group By CASESET_ID ");
        sb.append("), ");
        sb.append("FINAL_MANAGER_DATA as ( ");
        sb.append("Select m.CASESET_ID, m.MANAGER, Case When g.SIZE > 1 then m.NAME + '...' else m.NAME End As NAME ");
        sb.append("From SIZE_OF_MANAGER_BY_CASE_ID g, MANAGER_ALL m ");
        sb.append("where g.SIZE = m.ROWNUM and g.CASESET_ID = m.CASESET_ID ");
        sb.append(") ");
        sb.append(
                "Select c.Sort SORT, c.ServiceTo SERVICE_TO, c.CaseSetId CASESET_ID, c.CaseSetName CASESET_NAME, c.Organ ORGAN_ID, gl.Name ORGAN_NAME, c.OwnerOrgan OWNER_ORGAN_ID, o2.OrganName OWNER_ORGAN_NAME, f.NAME as MANAGER_NAME , c.Status [STATUS], c.CaseType CASE_TYPE, c.FlowId FLOW_Id ");
        sb.append("From KGO_CASESET c ");
        sb.append("Left Join KGO_GROUP_LEVEL gl On c.Organ = gl.Seq ");
        sb.append("Left Join KGO_ORGAN o2 On c.OwnerOrgan = o2.OrganId ");
        sb.append("Left Join FINAL_MANAGER_DATA f on f.CASESET_ID = c.CaseSetId ");

        if (StringUtils.isNotBlank(userId)) {
            sb.append(", MANAGER_ALL m ");
        }
        sb.append("where 1=1 ");
        sb.append("and c.IsOpenForOrgan = 1 ");
        if (!ObjectUtils.isEmpty(organSeq)) {
            sb.append("and c.Organ = :organSeq ");
        }
        if (StringUtils.isNotBlank(ownerOrgan)) {
            sb.append("and c.OwnerOrgan = :ownerOrgan ");
        }
        if (StringUtils.isNotBlank(caseSetId)) {
            sb.append("and c.CaseSetId = :caseSetId ");
        }
        if (StringUtils.isNotBlank(caseSetName)) {
            sb.append("and c.CaseSetName like CONCAT('%', :caseSetName,'%') ");
        }
        if (StringUtils.isNotBlank(userId)) {
            sb.append("and m.CASESET_ID = c.CaseSetId ");
            sb.append("and m.MANAGER = :userId ");
        }
        sb.append("ORDER BY  CASE " + "when c.Status='On' then 1 " + "when c.Status='Off' then 2 " + "ELSE 3 END ,c.Sort ");
//		sb.append("ORDER BY c.UpdateTime desc");

        /**
         * Native Query
         */
        Query query = entityManager.createNativeQuery(sb.toString(), CaseManagementQueryDto.class);
        if (!ObjectUtils.isEmpty(organSeq)) {
            query.setParameter("organSeq", organSeq);
        }
        if (StringUtils.isNotBlank(ownerOrgan)) {
            query.setParameter("ownerOrgan", ownerOrgan);
        }
        if (StringUtils.isNotBlank(caseSetId)) {
            query.setParameter("caseSetId", caseSetId);
        }
        if (StringUtils.isNotBlank(caseSetName)) {
            query.setParameter("caseSetName", caseSetName);
        }
        if (StringUtils.isNotBlank(userId)) {
            query.setParameter("userId", userId);
        }

        return query.getResultList();
    }

    public List<BidCaseListQueryDto> getBidCaseListData(CaseManagementQueryRq rq) {

        StringBuilder sb = new StringBuilder();

        /**
         * Generate custom query String
         */

        sb.append("with MANAGER_ALL as ( ");
        sb.append("Select k.caseSetId CASESET_ID, k.manager MANAGER, ku.Name NAME, ROW_NUMBER() OVER (PARTITION BY k.caseSetId ORDER BY k.Manager ASC) ROWNUM ");
        sb.append("From KGO_CASESET_MANAGER k Left Join KGO_USER ku On ku.UserId = k.Manager ");
        sb.append("), ");
        sb.append("SIZE_OF_MANAGER_BY_CASE_ID as ( ");
        sb.append("Select CASESET_ID, MAX(rownum) as SIZE ");
        sb.append("From MANAGER_ALL  ");
        sb.append("Group By CASESET_ID ");
        sb.append("), ");
        sb.append("FINAL_MANAGER_DATA as ( ");
        sb.append("Select m.CASESET_ID, m.MANAGER, Case When g.SIZE > 1 then m.NAME + '...' else m.NAME End As NAME ");
        sb.append("From SIZE_OF_MANAGER_BY_CASE_ID g, MANAGER_ALL m ");
        sb.append("where g.SIZE = m.ROWNUM and g.CASESET_ID = m.CASESET_ID ");
        sb.append(") ");
        sb.append("select ROW_NUMBER() over( order by c.Sort) SORT,  STRING_AGG(ct.ApplyType, ',') APPLY_TYPE, c.CaseSetId CASESET_ID, c.CaseFlowType CASEFLOW_TYPE, c.CaseSetName CASESET_NAME\r\n");
        sb.append("From KGO_CASESET c ");
        sb.append("Left Join FINAL_MANAGER_DATA f on f.CASESET_ID = c.CaseSetId ");
        sb.append("left join KGO_CASESET_TYPE ct on c.CaseSetId = ct.CaseSetId \r\n");
        if (StringUtils.isNotBlank(rq.getManager())) {
            sb.append(", MANAGER_ALL m ");
        }

        sb.append("where 1=1 \r\n");
        sb.append("and c.IsOpenForOrgan = 1 \r\n");
        sb.append("and c.Status = :status \r\n");
        if (!ObjectUtils.isEmpty(rq.getOrganId())) {
            sb.append("and c.Organ = :organSeq ");
        }
        if (StringUtils.isNotBlank(rq.getOwnerOrganId())) {
            sb.append("and c.OwnerOrgan = :ownerOrgan ");
        }
        if (StringUtils.isNotBlank(rq.getCaseSetId())) {
            sb.append("and c.CaseSetId = :caseSetId ");
        }
        if (StringUtils.isNotBlank(rq.getCaseSetName())) {
            sb.append("and c.CaseSetName like CONCAT('%', :caseSetName,'%') ");
        }
        if (StringUtils.isNotBlank(rq.getManager())) {
            sb.append("and m.CASESET_ID = c.CaseSetId ");
            sb.append("and m.MANAGER = :userId ");
        }
        sb.append("Group by c.CaseSetId, c.CaseFlowType, c.CaseSetName,c.Sort\r\n");
        // sb.append("Order By c.CaseSetId desc");
        sb.append("Order By SORT");
        /**
         * Native Query
         */
        Query query = entityManager.createNativeQuery(sb.toString(), BidCaseListQueryDto.class);
        query.setParameter("status", PublishStateEnum.ON.getValue());
        if (!ObjectUtils.isEmpty(rq.getOrganId())) {
            query.setParameter("organSeq", rq.getOrganId());
        }
        if (StringUtils.isNotBlank(rq.getOwnerOrganId())) {
            query.setParameter("ownerOrgan", rq.getOwnerOrganId());
        }
        if (StringUtils.isNotBlank(rq.getCaseSetId())) {
            query.setParameter("caseSetId", rq.getCaseSetId());
        }
        if (StringUtils.isNotBlank(rq.getCaseSetName())) {
            query.setParameter("caseSetName", rq.getCaseSetName());
        }
        if (StringUtils.isNotBlank(rq.getManager())) {
            query.setParameter("userId", rq.getManager());
        }

        return query.getResultList();
    }

}
