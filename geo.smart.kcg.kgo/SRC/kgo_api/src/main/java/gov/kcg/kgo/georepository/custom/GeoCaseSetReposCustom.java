package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.dto.CaseManagementQueryDto;
import gov.kcg.kgo.geomodel.dto.GeoExCaseQueryDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.Query;
import java.util.List;

@Repository
public class GeoCaseSetReposCustom extends GeoBaseReposCustom{

    /**
     * 查詢服務管理
     * rq: organSeq:機關分類代碼  caseSetName: 服務名稱
     */
    public List<GeoExCaseQueryDto> findAllCase(Integer organSeq, String caseSetName ) {

        StringBuilder sb = new StringBuilder();

        sb.append("Select c.Sort SORT, c.CaseSetId CASESET_ID, c.CaseSetName CASESET_NAME, c.Organ ORGAN_ID, gl.Name ORGAN_NAME, c.OwnerOrgan OWNER_ORGAN_ID, o2.OrganName OWNER_ORGAN_NAME, c.Status [STATUS], c.CaseType CASE_TYPE ");
        sb.append("From KGO_CASESET c ");
        sb.append("Left Join KGO_GROUP_LEVEL gl On c.Organ = gl.Seq ");
        sb.append("Left Join KGO_ORGAN o2 On c.OwnerOrgan = o2.OrganId ");
        sb.append("where c.caseSet_category = 'excase' ");

        if (!ObjectUtils.isEmpty(organSeq)) {
            sb.append("and c.Organ = :organSeq ");
        }
        if (StringUtils.isNotBlank(caseSetName)) {
            sb.append("and c.CaseSetName like CONCAT('%', :caseSetName,'%') ");
        }
        sb.append("ORDER BY  CASE " + "when c.Status='On' then 1 " + "when c.Status='Off' then 2 " + "ELSE 3 END ,c.Sort ");

        Query query = getEntityManager().createNativeQuery(sb.toString(), GeoExCaseQueryDto.class);
        if (!ObjectUtils.isEmpty(organSeq)) {
            query.setParameter("organSeq", organSeq);
        }

        if (StringUtils.isNotBlank(caseSetName)) {
            query.setParameter("caseSetName", caseSetName);
        }
        return query.getResultList();
    }
}
