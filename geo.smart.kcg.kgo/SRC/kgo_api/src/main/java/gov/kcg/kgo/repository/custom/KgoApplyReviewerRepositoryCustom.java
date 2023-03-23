package gov.kcg.kgo.repository.custom;

import java.sql.Timestamp;

public interface KgoApplyReviewerRepositoryCustom {

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
	int saveData(String caseId, String caseType, String reviewer, String createUser, Timestamp createTime,
			String updateUser, Timestamp updateTime);

}
