package gov.kcg.kgo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.dto.ActivityMaintainQueryDto;
import gov.kcg.kgo.repository.custom.KgoActivitysetRepositoryCustom;

public class KgoActivitysetRepositoryCustomImpl implements KgoActivitysetRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoActivitysetRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 表單下載-表單查詢
	 * 
	 * @return
	 */
	@Override
	public List<ActivityMaintainQueryDto> findCustomData(String activityName, String publishTimeStart,
			String publishTimeEnd) {

		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append("Select ActivitySeq, ActivityName, State");
		sb.append(" From KGO_ACTIVITYSET k");
		sb.append(" Where 1=1");
		if (StringUtils.isNotBlank(activityName)) {
			sb.append("  And ActivityName like CONCAT('%', :activityName,'%') ");
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			sb.append("  And PublishTime >= :publishTimeStart");
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			sb.append("  And PublishTime <= :publishTimeEnd");
		}
		sb.append(" Order by publishTime desc");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), ActivityMaintainQueryDto.class);
		if (StringUtils.isNotBlank(activityName)) {
			query.setParameter("activityName", activityName);
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			query.setParameter("publishTimeStart", publishTimeStart);
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			query.setParameter("publishTimeEnd", publishTimeEnd);
		}
		
		return query.getResultList();
	}

}
