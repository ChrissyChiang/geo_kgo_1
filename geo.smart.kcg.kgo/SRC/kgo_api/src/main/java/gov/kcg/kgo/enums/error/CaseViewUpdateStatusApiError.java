package gov.kcg.kgo.enums.error;

/**
 * 後臺 api特定錯誤訊息.
 */
public enum CaseViewUpdateStatusApiError {

	/** 欄位驗證錯誤 */
	PROCESS_VALIDATE_ERROR("CaseViewUpdateStatus-0001", "kgo.backend.case.view.update.status.validate.error");

	/** 錯誤代碼. *編碼邏輯：controller名稱 + 四位數字(0001開始) */
	private String errorCode;

	/** 錯誤訊息. */
	private String errorMsgKey;

	private CaseViewUpdateStatusApiError(String errorCode, String errorMsgKey) {
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
