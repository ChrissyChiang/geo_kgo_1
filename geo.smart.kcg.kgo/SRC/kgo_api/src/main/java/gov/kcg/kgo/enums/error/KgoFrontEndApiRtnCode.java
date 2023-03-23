package gov.kcg.kgo.enums.error;

/**
 * 推播 api 特定錯誤訊息.
 */
public enum KgoFrontEndApiRtnCode {

	/**
	 * 成功
	 */
	M0000("00", "00", "Successful"),
	/**
	 * 使用者不存在
	 */
	M0001("00", "01", "User not found"),
	/**
	 * 裝置不存在
	 */
	M0101("01", "01", "Device not found"),
	/**
	 * 裝置目前被他人綁定，要重新綁定嗎?
	 */
	M0102("01", "02", "Device has been binded by other person"),
	/**
	 * 系統錯誤
	 */
	M9901("99", "01", "System error"),
	/**
	 * 輸入參數有誤
	 */
	M9902("99", "02", "Parameter error"),
	/**
	 * API 不存在
	 */
	M9903("99", "03", "API not exists"),
	/**
	 * 未知錯誤
	 */
	UNKNOW("99", "99", "Unknow API error");

	/**
	 * Groupe ID 2 digits
	 */
	private String groupID;
	/**
	 * Sequence 2 digits
	 */
	private String sequence;
	/**
	 * 錯誤代碼. 編碼邏輯：Rtn_Code = <Groupe ID 2 digits> + <Sequence 2 digits>
	 */
	private String rtnCode;

	/** 錯誤訊息. */
	private String description;

	private KgoFrontEndApiRtnCode(String groupID, String sequence, String description) {
		this.groupID = groupID;
		this.sequence = sequence;
		this.description = description;
		this.rtnCode = "M" + groupID + sequence;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
