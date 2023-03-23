package gov.kcg.kgo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.repository.custom.KgoUraApplyRepositoryCustom;

public class KgoUraApplyRepositoryCustomImpl implements KgoUraApplyRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoUraApplyRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 查詢下一個 CaseId
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
				"select CONCAT(:caseIdPrefix, RIGHT(REPLICATE('0', 5) + CAST( SUBSTRING( CASE WHEN ISNULL(MAX(CaseId),'')='' THEN '0' ELSE MAX(CaseId) END, 9, 14) + 1 as NVARCHAR), 5)) from KGO_URA_APPLY where CaseId like CONCAT('%', :caseIdPrefix,'%')");

		/**
		 * Native Query
		 */
		return entityManager.createNativeQuery(sb.toString()).setParameter("caseIdPrefix", caseIdPrefix)
				.getSingleResult().toString();
	}

}
