package gov.kcg.kgo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.enums.backend.PublishStateEnum;
import gov.kcg.kgo.model.KgoCommonQuestion;
import gov.kcg.kgo.repository.custom.KgoCommonQuestionRepositoryCustom;

public class KgoCommonQuestionRepositoryCustomImpl implements KgoCommonQuestionRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoGroupLevelRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	@Override
	public List<KgoCommonQuestion> findByQuestionAndPublishTimeOrderBySeq(String question, String publishDateStart,
			String publishDateEnd) {
		StringBuilder sb = new StringBuilder();
		sb.append("Select *  ");
		sb.append("From KGO_COMMON_QUESTION  ");
		sb.append("Where 1=1  ");
		if (StringUtils.isNotBlank(question)) {
			sb.append(" And Question like CONCAT('%', :question,'%') ");
		}
		if (StringUtils.isNotBlank(publishDateStart)) {
			sb.append("And convert(varchar, PublishDate, 112) >= :publishDateStart ");
		}
		if (StringUtils.isNotBlank(publishDateEnd)) {
			sb.append("And convert(varchar, PublishDate, 112) <= :publishDateEnd ");
		}
		sb.append(" Order by PublishDate desc");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), KgoCommonQuestion.class);
		if (StringUtils.isNotBlank(question)) {
			query.setParameter("question", question);
		}
		if (StringUtils.isNotBlank(publishDateStart)) {
			query.setParameter("publishDateStart", publishDateStart);
		}
		if (StringUtils.isNotBlank(publishDateEnd)) {
			query.setParameter("publishDateEnd", publishDateEnd);
		}

		return query.getResultList();
	}

}
