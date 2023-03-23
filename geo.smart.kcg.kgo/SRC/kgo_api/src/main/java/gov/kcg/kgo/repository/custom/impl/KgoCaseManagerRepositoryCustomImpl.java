package gov.kcg.kgo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.dto.CaseManagerNameQueryDto;
import gov.kcg.kgo.repository.custom.KgoCaseManagerRepositoryCustom;

public class KgoCaseManagerRepositoryCustomImpl implements KgoCaseManagerRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoCaseManagerRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 服務案件清單-管理者搜尋
	 * 
	 * @return
	 */
	@Override
	public List<CaseManagerNameQueryDto> findCaseManagerByOrganId(String organId) {

		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append("select u.UserId MANAGER, u.Name NAME from KGO_USER u ");
		sb.append("inner join KGO_USER_ROLE r on u.UserId=r.UserId and  r.RoleId='CASE_M' ");

		if (StringUtils.isNotBlank(organId)){
			sb.append("where u.Organ = :organId");
		}

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), CaseManagerNameQueryDto.class);
		if (StringUtils.isNotBlank(organId)){
			query.setParameter("organId", organId);
		}

		return query.getResultList();
	}

}
