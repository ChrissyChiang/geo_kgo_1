package gov.kcg.kgo.enums.error;

/**
 * 後臺 api特定錯誤訊息.
 */
public enum CaseProcessSearchApiError {

	/** 欄位驗證錯誤 */
	PROCESS_VALIDATE_ERROR("CaseProcessSearch-0001", "kgo.frontend.case.process.search.validate.error"),
	
	/** 超過案件暫存區保存期限 */
	APPLY_DATE_OVERTIME_ERROR("CaseProcessSearch-0002", "kgo.frontend.case.process.search.validate.applydate.overtime.error");

	/** 錯誤代碼. *編碼邏輯：controller名稱 + 四位數字(0001開始) */
	private String errorCode;

	/** 錯誤訊息. */
	private String errorMsgKey;

	private CaseProcessSearchApiError(String errorCode, String errorMsgKey) {
		this.errorCode = errorCode;
		this.errorMsgKey = errorMsgKey;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsgKey() {
		return errorMsgKey;
	}

	public void setErrorMsgKey(String errorMsgKey) {
		this.errorMsgKey = errorMsgKey;
	}

}
