package gov.kcg.kgo.enums.error;

/**
 * common api特定錯誤訊息.
 */
public enum KgoCommonApiError {
	
	/** 使用者未登入-i18n. */
	USER_NOT_LOGIN("COMMON-0001", "kcg.common.user.not.login"),
	
	/** 登入失敗 */
	LOGIN_ERROR("COMMON-0002", "kgo.common.login.error"),
	
	/** 閒置時間過長，您已被登出系統 -i18n. */
	USER_SESSION_TIMEOUT("COMMON-0003", "kcg.common.user.session.timeout"),
	
	/** 儲存操作紀錄錯誤 -i18n. */
	USER_LOG_ERROR("COMMON-0004", "kcg.common.userlog.error"),
	
	/** email未填寄件人 -i18n. */
	EMAIL_SEND_TO_ERROR("COMMON-0005", "kcg.common.email.send.to.error"),

	/** 未取得授權-i18n. */
	UNAUTHORIZED("COMMON-0401", "kcg.common.unauthorized"),
	
	/** 檔案大小超出限制 */
	OVER_SIZE_LIMIT("COMMON-9902", "kcg.common.over.size.limit"),
	
	/** Server檔案存放路徑錯誤 */
	WRONG_TARGET_PATH("COMMON-9901", "kcg.common.wrong.target.path"),

	/** 上傳失敗 */
	FAIL_TO_UPLOAD("COMMON-9980", "kcg.common.fail.to.upload"),

	/** 下載失敗 */
	FAIL_TO_DOWNLOAD("COMMON-9981", "kcg.common.fail.to.download"),
	
	/** 查詢失敗 */
	FAIL_TO_SEARCH("COMMON-9991", "kcg.common.fail.to.search"),
	
	/** 系統錯誤 */
	COMMON_SYSTEM_ERROR("COMMON-9998", "kcg.common.system.error"),
	
	/** 未知的錯誤 I18n 多語系. */
	COMMON_UNKNOWN_EXCEPTION("COMMON-9999", "kcg.common.unknow.exception"),

	LOGIN_TYPE_NOT_REAL_NAME("COMMON-0402", "kcg.common.user.not.login.real.name");


	/** 錯誤代碼. *編碼邏輯：controller名稱 + 四位數字(0001開始) */
	private String errorCode;

	/** 錯誤訊息. */
	private String errorMsgKey;

	private KgoCommonApiError(String errorCode, String errorMsgKey) {
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
