package gov.kcg.kgo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.dto.CasesetGroupQueryDataMaxVersionDto;
import gov.kcg.kgo.repository.custom.KgoCasesetGroupRepositoryCustom;

public class KgoCasesetGroupRepositoryCustomImpl implements KgoCasesetGroupRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoCasesetGroupRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 新增資料
	 * 
	 * @param caseSetId
	 * @param version
	 * @param memo
	 * @param orderNum
	 * @return
	 */
	@Override
	public Integer saveData(String caseSetId, int version, String memo, int orderNum, String checkFrequencyPeriod) {

		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 * GEO 20211203 change 重複檢核
		 */
		sb.append("insert into KGO_CASESET_GROUP ");
		sb.append("(CaseSetId, [Version], Memo, OrderNum, CheckFrequencyPeriod ) ");
		sb.append("values ");
		sb.append("(:caseSetId, :version, :memo, :orderNum, :checkFrequencyPeriod)");

		/**
		 * Native Query
		 */
		return entityManager.createNativeQuery(sb.toString()).setParameter("caseSetId", caseSetId)
				.setParameter("version", version).setParameter("memo", memo).setParameter("orderNum", orderNum)
				.setParameter("checkFrequencyPeriod", checkFrequencyPeriod)
				.executeUpdate();
	}

	/**
	 * 找尋該案件ID底下所有版本最大號的群組資料
	 * 
	 * @param caseSetId
	 * @return
	 */
	@Override
	public List<CasesetGroupQueryDataMaxVersionDto> findMaxVersionGroupData(String caseSetId, String memo) {
		StringBuilder sb = new StringBuilder();

		Integer maxVersion = findMaxVersionByCasesetId(caseSetId);
		if (ObjectUtils.isEmpty(maxVersion)) {
			return null;
		} else {
			/**
			 * Generate custom query String
			 */
			sb.append("select GroupSeq GROUP_SEQ, Memo MEMO, Version VERSION, OrderNum ORDER_NUM, CheckFrequencyPeriod CHECK_FREQUENCY_PERIOD ");
			sb.append("from KGO_CASESET_GROUP ");
			sb.append("where Version = :version ");
			if (StringUtils.isNotBlank(caseSetId)) {
				sb.append("and CaseSetId = :caseSetId ");
			}
			if (StringUtils.isNotBlank(memo)) {
				sb.append("and Memo = :memo ");
			}
			sb.append("order by GroupSeq");

			/**
			 * Native Query
			 */
			Query query = entityManager.createNativeQuery(sb.toString(), CasesetGroupQueryDataMaxVersionDto.class);
			query.setParameter("version", maxVersion);
			if (StringUtils.isNotBlank(caseSetId)) {
				query.setParameter("caseSetId", caseSetId);
			}
			if (StringUtils.isNotBlank(memo)) {
				query.setParameter("memo", memo);
			}

			return query.getResultList();
		}
	}

	/**
	 * 找該案件最大 version，null 表示沒資料
	 * 
	 * @param caseSetId
	 * @return
	 */
	@Override
	public Integer findMaxVersionByCasesetId(String caseSetId) {
		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append("select MAX([Version]) MAX_VERSION from KGO_CASESET_GROUP where CaseSetId = :caseSetId");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("caseSetId", caseSetId);
		Integer maxVerison = null;
		try {
			maxVerison = (Integer) query.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("findMaxVersionByCasesetId error:" + e.getMessage());
		}

		return maxVerison;
	}
}
