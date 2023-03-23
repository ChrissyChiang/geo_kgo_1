package gov.kcg.kgo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.model.KgoCommonWord;
import gov.kcg.kgo.repository.custom.KgoCommonWordRepositoryCustom;

public class KgoCommonWordRepositoryCustomImpl implements KgoCommonWordRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoCommonQuestionRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	@Override
	public List<KgoCommonWord> findSeqAndTitleByTitleLikeOrderByUpdateTimeDesc(String title) {
		StringBuilder sb = new StringBuilder();
		sb.append("Select *  ");
		sb.append("From KGO_COMMON_WORD  ");
		sb.append("Where 1=1 ");
		if (StringUtils.isNotBlank(title)) {
			sb.append(" And Title like CONCAT('%', :title,'%') ");
		}
		sb.append(" Order by UpdateTime desc");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), KgoCommonWord.class);
		if (StringUtils.isNotBlank(title)) {
			query.setParameter("title", title);
		}

		return query.getResultList();
	}

}
