package gov.kcg.kgo.enums.error;

/**
 * 後臺 api特定錯誤訊息.
 */
public enum ActivitiApiError {
	
	/** Activiti啟動失敗 */
	PROCESS_START_ERROR("Activiti-0001", "activiti.process.start.error"),

	/** 未知的錯誤 I18n 多語系. */
	UNKNOWN_EXCEPTION("Activiti-9999", "activiti.unknow.exception");

	/** 錯誤代碼. *編碼邏輯：controller名稱 + 四位數字(0001開始) */
	private String errorCode;

	/** 錯誤訊息. */
	private String errorMsgKey;

	private ActivitiApiError(String errorCode, String errorMsgKey) {
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
