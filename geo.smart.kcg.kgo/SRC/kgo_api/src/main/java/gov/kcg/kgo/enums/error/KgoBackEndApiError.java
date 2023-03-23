package gov.kcg.kgo.enums.error;

/**
 * 後臺 api特定錯誤訊息.
 */
public enum KgoBackEndApiError {
	
	/** 系統別錯誤. */
	SYSTEM_TYPE_ERROR("KGOBE-0001", "kgo.back.end.system.type.error"),
	
	/** 登入錯誤 - 更新市府員工資料失敗 . */
	UPDATE_KGO_USER_ERROR("KGOBE-0002", "kgo.back.end.update.kgouser.error"),
	
	/** 圖片轉換錯誤. */
	IMAGE_TRANS_ERROR("KGOBE-0003", "kgo.back.end.image.trans.error"),
	
	/** 查無此流程. */
	CAN_NOT_FIND_FLOW_ERROR("KGOBE-0004", "kgo.back.end.cannot.find.flow.error"),
	
	/** 案件編號為空. */
	CASE_ID_ERROR("KGOBE-0005", "kgo.back.end.caseid.error"),
	
	/** 查無此案件. */
	CAN_NOT_FIND_CASE("KGOBE-0006", "kgo.back.end.cannot.find.case"),
	
	/** 此流程已有服務綁定. */
	FLOW_IS_USED("KGOBE-0007", "kgo.back.end.flow.is.used"),
	
	/** 此案件服務已從承辦被改成 機關分文 或 區機關. */
	/** 此案件服務已從機關分文或區機關被改成 承辦. */
	CASESET_ACCECPT_SETTING_HAS_BEEN_CHANGE("KGOBE-0008", "kgo.back.end.caseset.accecpt.setting.hasbeen.change"),

	/** 此服務已下架‧，請選擇其他服務 */
	SERVICE_IS_NOT_PROVIDED("KGOBE-9960", "kgo.front.end.service.is.not.provided"),

	/** 無操作權限 I18n 多語系. TODO 待角色確認 再實作 */
	PERMISSION_DENIED("KGOBE-9961", "kgo.front.end.permission.denied"),

	/** 非府內線上服務 */
	SERVICE_IS_NOT_PROVIDED_FOR_ORGAN("KGOBE-9962", "kgo.back.end.service.is.not.provided.for.organ"),

	/** 未取得授權-i18n. */
	UNAUTHORIZED("KGOBE-0401", "kgo.back.end.unauthorized"),

	/** 參數錯誤 */
	WRONG_PARAMETER("KGOBE-0501", "kgo.back.end.wrong.parameter"),

	/** 查詢欄位不可為空:{欄位名稱} */
	SEARCH_IS_EMPTY("KGOBE-9971", "kgo.back.end.search.is.empty"),

	/** 無該筆資料 */
	DATA_NOT_EXIST("KGOBE-9973", "kgo.front.end.data.not.exist"),

	/** 上傳失敗 */
	FAIL_TO_UPLOAD("KGOBE-9980", "kgo.back.end.fail.to.upload"),

	/** 下載失敗 */
	FAIL_TO_DOWNLOAD("KGOBE-9981", "kgo.back.end.fail.to.download"),

	/** 送出失敗 */
	FAIL_TO_SEND("KGOBE-9982", "kgo.back.end.fail.to.send"),

	/** 暫存失敗 */
	FAIL_TO_TEMP_SAVE("KGOBE-9983", "kgo.back.end.fail.to.temp.save"),

	/** 資料格式錯誤 */
	DATA_FORMAT_ERROR("KGOBE-9987", "kgo.front.end.data.format.error"),

	/** Server檔案存放路徑錯誤 */
	WRONG_TARGET_PATH("KGOBE-9901", "kgo.back.end.wrong.target.path"),

	/** 檔案大小超出限制 */
	OVER_SIZE_LIMIT("KGOBE-9902", "kgo.back.end.over.size.limit"),

	/** 查詢失敗 */
	FAIL_TO_SEARCH("KGOBE-9991", "kgo.back.end.fail.to.search"),

	/** 新增失敗 */
	FAIL_TO_ADD("KGOBE-9992", "kgo.back.end.fail.to.add"),

	/** 編輯失敗 */
	FAIL_TO_EDIT("KGOBE-9993", "kgo.back.end.fail.to.edit"),

	/** 儲存失敗 I18n 多語系. */
	FAIL_TO_SAVE("KGOBE-9994", "kgo.back.end.fail.to.save"),

	/** 刪除失敗 I18n 多語系. */
	FAIL_TO_DELETE("KGOBE-9995", "kgo.back.end.fail.to.delete"),

	/** email失敗 */
	FAIL_TO_EMAIL("KGOBE-9996", "kgo.back.end.fail.to.email"),

	/** 推播失敗 */
	FAIL_TO_NOTIFY("KGOBE-9997", "kgo.back.end.fail.to.notify"),

	/** 取號失敗 */
	FAIL_TO_GET_NUMBERS("KGOBE-9998", "kgo.backend.appointment.order.numbers.fail"),

	/** 預約新增失敗 重複預約*/
	DATA_IS_REPEATED("KGOBE-9988", "kgo.backend.appointment.order.add.fail.data.is.repeated"),

	/** 預約新增失敗 已超過最晚可預約時段*/
	HAS_ENDED("KGOBE-9989", "kgo.backend.appointment.order.add.fail.data.has.ended"),

	/** 預約新增失敗 尚未開放預約時段*/
	NOT_OPEN("KGOBE-9986", "kgo.backend.appointment.order.add.fail.data.not.open"),

	/** 預約時間重複或衝突 */
	RENTAL_TIME_ERROR("KGOBE-9987","kgo.back.end.service.rental.time.error"),

	/** 尚未新增服務案件，無法取得上一周預約時間 */
	RENTAL_WEEKQUERY_ERROR("KGOBE-9988","kgo.back.end.service.rental.time.week.error"),

	/** 請確認退費期限及退費日期正確 */
	RENTAL_REFUND_DAYS_ERROR("KGOBE-9989","kgo.back.end.service.rental.refunddays.error"),

	/** 案件已有民眾預約，無法變更*/
	SITE_EDIT_ERROR("KGOBE-9990","kgo.back.end.service.rental.site.edit.error"),

	/** 服務案件之身分驗證設定為免驗證，無法使用線上繳費功能。*/
	PAY_SETTING_CHECKTYPE_ERROR("KGOBE-9991","kgo.back.end.service.rental.paysetting..error"),

	/** 設定預約人數與號碼牌設定不符。*/
	APPOINTMENT_QOUTA_SETTING_ERROR("KGOBE-0201", "kgo.back.end.appointment.quota.error"),

	/** 號碼牌已接受預約，無法變更。*/
	APPOINTMENT_NUMBERS_EXSIST_ERROR("KGOBE-0202","kgo.back.end.appointment.numbers.error"),

	/** 未知的錯誤 I18n 多語系. */
	UNKNOWN_EXCEPTION("KGOBE-9999", "kgo.back.end.unknow.exception");

	/** 錯誤代碼. *編碼邏輯：controller名稱 + 四位數字(0001開始) */
	private String errorCode;

	/** 錯誤訊息. */
	private String errorMsgKey;

	private KgoBackEndApiError(String errorCode, String errorMsgKey) {
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
