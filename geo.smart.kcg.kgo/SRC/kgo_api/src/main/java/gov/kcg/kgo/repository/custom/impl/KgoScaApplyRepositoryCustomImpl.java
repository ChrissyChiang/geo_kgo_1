package gov.kcg.kgo.repository.custom.impl;

import gov.kcg.kgo.dto.ScaCaseViewQueryDto;
import gov.kcg.kgo.repository.custom.KgoScaApplyRepositoryCustom;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class KgoScaApplyRepositoryCustomImpl implements KgoScaApplyRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoScaApplyRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 查詢下一個 CaseId
	 * 
	 * @param caseIdPrefix
	 * @return
	 */
	@Override
	public String findNextCaseId(@Param("caseIdPrefix") String caseIdPrefix) {
		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append(
				"select CONCAT(:caseIdPrefix, RIGHT(REPLICATE('0', 5) + CAST( SUBSTRING( CASE WHEN ISNULL(MAX(CaseId),'')='' THEN '0' ELSE MAX(CaseId) END, 9, 14) + 1 as NVARCHAR), 5)) from KGO_SCA_APPLY where CaseId like CONCAT('%', :caseIdPrefix,'%')");

		/**
		 * Native Query
		 */
		return entityManager.createNativeQuery(sb.toString()).setParameter("caseIdPrefix", caseIdPrefix)
				.getSingleResult().toString();
	}

	/**
	 * 後台案件處理-案件檢視-服務案件新增(SCA)案件檢視
	 * 
	 * 取得 服務案件新增(SCA)案件 檢視資料
	 * 
	 * @param caseId
	 * @return
	 */
	@Override
	public ScaCaseViewQueryDto getScaCaseViewData(String caseId) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"select sa.CaseSetId CASESET_ID, sa.CaseId CASE_ID, o.OrganName APPLY_ORGAN, un.UnitName APPLY_UNIT, us1.Name APPLY_USER, sa.Phone PHONE, sa.Email EMAIL, sa.CaseSetName CASESET_NAME, sa.CaseType CASE_TYPE,\r\n"
						+ "sa.CaseFlowType CASE_FLOW_TYPE, sa.LinkType LINK_TYPE, sa.LinkUrl LINK_URL, o1.OrganName ORGAN, role.CodeName ROLE, service.CodeName SERVICE, o2.OrganName OWNER_ORGAN, cm.MANAGER_NAME, ar.Name APPLY_REVIEWER, ct.APPLY_TYPE, sa.processId PROCESS_ID \r\n"
						+ "from KGO_SCA_APPLY sa\r\n"
						+ "left join (Select STRING_AGG(Name,'、') MANAGER_NAME, CaseSetId from KGO_CASESET_MANAGER m Left join KGO_USER u on u. UserId=m. Manager group by CaseSetId) cm on sa.CaseSetId = cm.CaseSetId\r\n"
						+ "left join (select CodeId, CodeName from KGO_CODE where CodeType = 'Role') role on role.CodeId = sa.Role\r\n"
						+ "left join (select CodeId, CodeName from KGO_CODE where CodeType = 'Service') service on service.CodeId = sa.Service\r\n"
						+ "left join KGO_ORGAN o on sa.ApplyOrgan = o.OrganId\r\n"
						+ "left join KGO_ORGAN o1 on sa.Organ = o1.OrganId\r\n"
						+ "left join KGO_ORGAN o2 on sa.OwnerOrgan = o2.OrganId\r\n"
						+ "left join KGO_UNIT un on sa.ApplyUnit = un.UnitId\r\n"
						+ "left join KGO_USER us1 on sa.ApplyUser = us1.UserId\r\n" + "left join (\r\n"
						+ "	select ar.CaseId, u.Name\r\n" + "	from KGO_APPLY_REVIEWER ar\r\n"
						+ "	left join KGO_USER u on ar.Reviewer = u.UserId\r\n" + ")  ar on sa.CaseId = ar.CaseId\r\n"
						+ "left join ( select CaseSetId, STRING_AGG(ApplyType,',') APPLY_TYPE  from KGO_CASESET_TYPE group by CaseSetId ) ct on sa.CaseSetId = ct.CaseSetId\r\n"
						+ "where sa.caseId = :caseId");

		Query query = entityManager.createNativeQuery(sb.toString(), ScaCaseViewQueryDto.class);
		query.setParameter("caseId", caseId);

		ScaCaseViewQueryDto dto = null;
		List<ScaCaseViewQueryDto> resultList = query.getResultList();
		if (ObjectUtils.isNotEmpty(resultList) && resultList.size() > 0) {
			dto = resultList.get(0);
		}
		return dto;
	}

}
