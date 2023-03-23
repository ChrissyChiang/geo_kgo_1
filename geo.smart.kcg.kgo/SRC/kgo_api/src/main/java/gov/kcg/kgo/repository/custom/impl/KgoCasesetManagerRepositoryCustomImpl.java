package gov.kcg.kgo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.dto.CasesetManagerQueryByCaseSetIdDto;
import gov.kcg.kgo.repository.custom.KgoCasesetManagerRepositoryCustom;

public class KgoCasesetManagerRepositoryCustomImpl implements KgoCasesetManagerRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoCasesetManagerRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 服務案件清單-案件維護-初始畫面-案件管理者資料查詢
	 * 
	 * @param caseSetId
	 * @return
	 */
	@Override
	public CasesetManagerQueryByCaseSetIdDto getManagerInfoByCasesetId(String caseSetId) {
		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		/** GEO 20211202 modify */
		sb.append("Select o.OrganId ORGAN_ID, STRING_AGG(CAST(u.Name AS nvarchar(MAX)),'、') as NAME, STRING_AGG(CAST(u.UserId AS nvarchar(MAX)),',') as USER_ID\r\n");
		sb.append("From KGO_USER u\r\n");
		sb.append("Left join KGO_ORGAN o on o. OrganId= u.Organ\r\n");
		sb.append("Left join KGO_UNIT ut on ut.UnitId=u.Unit,\r\n" + "KGO_CASESET_MANAGER cm\r\n");
		sb.append("Where u.UserId=cm.Manager and cm.CaseSetId = :caseSetId\r\n");
		sb.append("group by o.OrganId\r\n");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), CasesetManagerQueryByCaseSetIdDto.class);
		query.setParameter("caseSetId", caseSetId);

		List<CasesetManagerQueryByCaseSetIdDto> dtoList = query.getResultList();
		CasesetManagerQueryByCaseSetIdDto dto = ObjectUtils.isEmpty(dtoList) ? null : dtoList.get(0);

		return dto;
	}

}
