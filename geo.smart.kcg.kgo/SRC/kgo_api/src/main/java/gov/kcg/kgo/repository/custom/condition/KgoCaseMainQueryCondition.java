package gov.kcg.kgo.repository.custom.condition;

import java.sql.Timestamp;

/**
 * KgoCaseMain 動態查詢條件 物件.
 */
public class KgoCaseMainQueryCondition {
	
	/** 案件編號. */
	private String caseId;

	/** 申請者. */
	private String applyUser;

	/** 手機. */
	private String cellPhone;
	
    /** (createDate)查詢起日 yyyy/MM/dd */
	private Timestamp startDate;

	/** (createDate)查詢迄日 yyyy/MM/dd */
	private Timestamp endDate;
	
	/** 查詢機關代碼" */
	private String organId;


	/**
	 * Gets the case id.
	 *
	 * @return the case id
	 */
	public String getCaseId() {
		return caseId;
	}

	/**
	 * Sets the case id.
	 *
	 * @param caseId the new case id
	 */
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	/**
	 * Gets the apply user.
	 *
	 * @return the apply user
	 */
	public String getApplyUser() {
		return applyUser;
	}

	/**
	 * Sets the apply user.
	 *
	 * @param applyUser the new apply user
	 */
	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	/**
	 * Gets the cell phone.
	 *
	 * @return the cell phone
	 */
	public String getCellPhone() {
		return cellPhone;
	}

	/**
	 * Sets the cell phone.
	 *
	 * @param cellPhone the new cell phone
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
}