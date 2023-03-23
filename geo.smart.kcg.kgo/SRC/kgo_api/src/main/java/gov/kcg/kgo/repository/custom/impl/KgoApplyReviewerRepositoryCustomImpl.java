package gov.kcg.kgo.repository.custom.impl;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.repository.custom.KgoApplyReviewerRepositoryCustom;
import gov.kcg.kgo.repository.custom.KgoUraApplyRepositoryCustom;

public class KgoApplyReviewerRepositoryCustomImpl implements KgoApplyReviewerRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoApplyReviewerRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 儲存資料
	 * 
	 * @param caseId
	 * @param caseType
	 * @param reviewer
	 * @param createUser
	 * @param createTime
	 * @param updateUser
	 * @param updateTime
	 * @return
	 */
	@Override
	public int saveData(String caseId, String caseType, String reviewer, String createUser, Timestamp createTime,
			String updateUser, Timestamp updateTime) {
		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append(
				"INSERT INTO KGO_APPLY_REVIEWER (CaseId, CaseType, Reviewer, IsReview, CreateUser, CreateTime, UpdateUser, UpdateTime) ");
		sb.append("VALUES (:caseId, :caseType, :reviewer, 0, :createUser, :createTime, :updateUser, :updateTime)");

		/**
		 * Native Query
		 */
		return entityManager.createNativeQuery(sb.toString()).setParameter("caseId", caseId)
				.setParameter("caseType", caseType).setParameter("reviewer", reviewer)
				.setParameter("createUser", createUser).setParameter("createTime", createTime)
				.setParameter("updateUser", updateUser).setParameter("updateTime", updateTime).executeUpdate();
	}

}
