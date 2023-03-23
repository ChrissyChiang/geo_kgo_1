package gov.kcg.kgo.enums.error;

/**
 * CoinCity API 特定錯誤訊息.
 */
public enum CoinCityApiError {

	/** 未取得授權-i18n. */
	UNAUTHORIZED("COINCITY-0401", "coin.city.unauthorized"),

	/** 送出失敗 */
	FAIL_TO_SEND("COINCITY-9982", "coin.city.fail.to.send"),
	
	/** 未知的錯誤 I18n 多語系. */
	UNKNOWN_EXCEPTION("COINCITY-9999", "coin.city.unknow.exception");

	/** 錯誤代碼. *編碼邏輯：controller名稱 + 四位數字(0001開始) */
	private String errorCode;

	/** 錯誤訊息. */
	private String errorMsgKey;

	private CoinCityApiError(String errorCode, String errorMsgKey) {
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
