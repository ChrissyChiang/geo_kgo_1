package gov.kcg.kgo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.dto.KeywordCountDto;
import gov.kcg.kgo.repository.custom.KgoKeywordsRepositoryCustom;

public class KgoKeywordsRepositoryCustomImpl implements KgoKeywordsRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoKeywordsRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 關鍵字統計
	 * 
	 * @return
	 */
	@Override
	public List<KeywordCountDto> countKeyword() {

		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append(
				"Select ROW_NUMBER() OVER( ORDER BY Keyword ) as SEQ, Keyword as KEYWORD, Count(Keyword) as [STATISTICS] from KGO_KEYWORDS group by Keyword order by Count(Keyword) desc");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), KeywordCountDto.class);

		return query.getResultList();
	}

}
