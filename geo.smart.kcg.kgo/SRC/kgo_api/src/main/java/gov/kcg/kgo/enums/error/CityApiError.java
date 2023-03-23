package gov.kcg.kgo.enums.error;

/**
 * 城市資料平台API 特定錯誤訊息.
 */
public enum CityApiError {
	/** 案件編號錯誤 */
	CASE_ID_ERROR("CITY-0001", "kcg.city.case.id.error"),
	
	/** 案件服務編號錯誤 */
	CASE_SET_ID_ERROR("CITY-0002", "kcg.city.case.set.id.error"),
	
	/** 查無案件 */
	CAN_NOT_FIND_CASE("CITY-0003", "kcg.city.cantnotfindcase"),
	
	/** Status= O(其他)時，狀態說明「狀態說明」欄位為必填 .  */
	REQUIRED_STATUS_DESC("CITY-0004", "kcg.city.required.status.desc"),
	
	/** 書表維護類別錯誤 */
	CASE_SET_FORM_TYPE_ERROR("CITY-0005", "kcg.city.case.set.form.type.error"),
	
	/** 查詢條件不可為空 */
	REQUIRED_QUERY_CONDITION("CITY-0006", "kcg.city.required.query.condition"),
	
	/** 日期格式錯誤 */
	TIME_FORMAT_ERROR("CITY-0007", "kcg.city.time.format.error"),
	
	/** 案件狀態不可為空  */
	REQUIRED_STATUS("CITY-0008", "kcg.city.required.status"),
	
	/** 請輸入查詢機關代碼  */
	REQUIRED_ORGANID("CITY-0009", "kcg.city.required.organid"),
	
	/** {} 欄位未填寫Email  */
	COL_REQUIRED("CITY-0010", "kcg.city.col.required"),
	
	/** CITY-9998: 非自然人憑證登入 */
	NOT_MOICA_LOGIN("CITY-9998", "kcg.city.moica.login.error"),
	
	/** 未知的錯誤 I18n 多語系. */
	UNKNOWN_EXCEPTION("CITY-9999", "kcg.common.unknow.exception");

	/** 錯誤代碼. *編碼邏輯：controller名稱 + 四位數字(0001開始) */
	private String errorCode;

	/** 錯誤訊息. */
	private String errorMsgKey;

	private CityApiError(String errorCode, String errorMsgKey) {
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
