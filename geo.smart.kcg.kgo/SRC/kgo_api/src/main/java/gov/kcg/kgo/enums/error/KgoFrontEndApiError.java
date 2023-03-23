package gov.kcg.kgo.enums.error;

/**
 * 前臺 api特定錯誤訊息.
 */
public enum KgoFrontEndApiError {

    /**
     * 未取得授權-i18n.
     */
    UNAUTHORIZED("KGOFE-0401", "kgo.front.end.unauthorized"),

    /**
     * 一 (年月週) 內不可重複申請
     */
    NONE_QUOTA_APPLY("KGOFE-9955", "kgo.front.end.caseset.apply.quota.none"),

    /**
     * 請求MyData Model 2 資料有誤
     */
    RQ_NOT_CORRECT("KGOFE-9954", "kgo.front.end.rq.not.correct"),

    /** GEO 20211019 add */
    /**
     * 一 (年月週) 內不可重複申請
     */
    REPEAT_APPLY("KGOFE-9956", "kgo.front.end.caseset.repeat.appiy"),

    /**
     * 驗證碼過期
     */
    VALIDATE_CODE_EXPIRED("KGOFE-9957", "kgo.front.end.validate.code.expired"),

    /**
     * 驗證碼錯誤
     */
    VALIDATE_CODE_ERROR("KGOFE-9958", "kgo.front.end.validate.code.error"),

    /**
     * 一年內不可重複申請
     */
    REPEAT_APPIY_ONE_YEAR("KGOFE-9959", "kgo.front.end.caseset.repeat.appiy.one.year"),

    /**
     * 此服務已下架‧，請選擇其他服務
     */
    SERVICE_IS_NOT_PROVIDED("KGOFE-9960", "kgo.front.end.service.is.not.provided"),

    /**
     * MyData 取檔等待
     */
    MYDATA_WAIT_DELAY_SECONDS("KGOFE-9961", "kgo.front.end.caseset.column.mydata.delaySeconds"),

    /**
     * 取得檔案中失敗
     */
    MYDATA_DONLOAD_FAILUER("KGOFE-9962", "kgo.front.end.caseset.column.mydata.donload.failure"),

    /**
     * 送出失敗
     */
    FAIL_TO_SEND("KGOFE-9969", "kgo.front.end.fail.to.send"),

    /**
     * 無資料儲存
     */
    NO_DATA_SAVE("KGOFE-9970", "kgo.front.end.cathayNo.no.data.save"),

    /**
     * 檔案加密失敗
     */
    DATA_ENCRYPTION_FAILED("KGOFE-9971", "kgo.front.end.data.encryption.failed"),

    /**
     * 請求資料有誤
     */
    RQ_NOT_COMPLETED("KGOFE-9972", "kgo.front.end.rq.not.completed"),

    /**
     * 無該筆資料
     */
    DATA_NOT_EXIST("KGOFE-9973", "kgo.front.end.data.not.exist"),

    /**
     * 輸入欄位不可為空:{欄位名稱}
     */
    INPUT_IS_EMPTY("KGOFE-9974", "kgo.front.end.input.is.empty"),

    /**
     * 資料庫資料錯誤
     */
    DB_OPERATE_ERROR("KGOFE-9975", "kgo.front.end.db.operate.error"),

    /**
     * 退回失敗
     */
    FAIL_TO_SENDBACK("KGOFE-9976", "kgo.front.end.fail.to.sendback"),

    /**
     * 發信失敗
     */
    FAIL_SEND_MAIL("KGOFE-9977", "kgo.front.end.fail.to.send.mail"),

    /**
     * 儲存操作紀錄錯誤
     */
    SYS_LOG_SAVE_ERROR("KGOFE-9978", "kgo.front.end.sys.log.save.error"),

    /**
     * 回饋活動使用中，不可編輯
     */
    UNEDITABLE("KGOFE-9979", "kgo.front.end.cathayNo.uneditable"),

    /**
     * 集團員編不存在
     */
    CATHAYNO_NOT_EXIST("KGOFE-9980", "kgo.front.end.cathayNo.not.exist"),

    /**
     * 查詢欄位不可為空:{欄位名稱}
     */
    SEARCH_IS_EMPTY("KGOFE-9981", "kgo.front.end.search.is.empty"),

    /**
     * 下載失敗
     */
    FAIL_TO_DOWNLOAD("KGOFE-9982", "kgo.front.end.fail.to.download"),

    /**
     * 暫存失敗
     */
    FAIL_TO_TEMP_SAVE("KGOFE-9983", "kgo.front.end.fail.to.temp.save"),

    /**
     * [回饋年度]不可為空，請填入！
     */
    YEAR_IS_EMPTY("KGOFE-9984", "kgo.front.end.year.is.empty"),

    /**
     * 匯入空資料
     */
    IMPORT_EMPTY_DATA("KGOFE-9985", "kgo.front.end.import.empty.data"),

    /**
     * 進(退)關失敗
     */
    FAIL_TO_CHECKPOINT("KGOFE-9986", "kgo.front.end.fail.to.pass"),

    /**
     * 資料格式錯誤
     */
    DATA_FORMAT_ERROR("KGOFE-9987", "kgo.front.end.data.format.error"),

    /**
     * 檔案格式錯誤
     */
    FILE_TYPE_ERROR("KGOFE-9988", "kgo.front.end.file.type.error"),

    /**
     * 檔案大小超出限制
     */
    OVER_SIZE_LIMIT("KGOFE-9989", "kgo.front.end.over.size.limit"),

    /**
     * 匯入失敗
     */
    FAIL_TO_IMPORT("KGOFE-9990", "kgo.front.end.fail.to.import"),

    /**
     * 查詢失敗
     */
    FAIL_TO_SEARCH("KGOFE-9991", "kgo.front.end.fail.to.search"),

    /**
     * 新增失敗
     */
    FAIL_TO_ADD("KGOFE-9992", "kgo.front.end.fail.to.add"),

    /**
     * 編輯失敗
     */
    FAIL_TO_EDIT("KGOFE-9993", "kgo.front.end.fail.to.edit"),

    /**
     * 儲存失敗 I18n 多語系.
     */
    FAIL_TO_SAVE("KGOFE-9994", "kgo.front.end.fail.to.save"),

    /**
     * 刪除失敗 I18n 多語系.
     */
    FAIL_TO_DELETE("KGOFE-9995", "kgo.front.end.fail.to.delete"),

    /**
     * 使用者未登入 I18n 多語系.
     */
    USER_NOT_LOGIN("KGOFE-9996", "kgo.front.end.user.not.login"),

    /**
     * 使用者無模組權限 I18n 多語系.TODO 待角色確認 再實作
     */
    USER_NOT_ROLE_AUTH("KGOFE-9997", "kgo.front.end.user.not.role.auth"),

    /**
     * 無操作權限 I18n 多語系. TODO 待角色確認 再實作
     */
    PERMISSION_DENIED("KGOFE-9998", "kgo.front.end.permission.denied"),

    /**
     * 未知的錯誤 I18n 多語系.
     */
    UNKNOWN_EXCEPTION("KGOFE-9999", "kgo.front.end.unknow.exception"),

    /** 前台案件申請 - 退費申請 - 查無預約案件*/
    REFUND_NO_CASE("KGOCF-0001","kgo.front.end.validate.casemain.apply"),
    /** 前台案件申請 - 退費申請 - 尚未繳費無法申請*/
    REFUND_PAYERROR("KGOCF-0002","kgo.front.end.validate.casemain.payerror"),
    /** 前台案件申請 - 退費申請 - 已超過退費期限*/
    REFUND_OVER_TIME("KGOCF-0003","kgo.front.end.validate.casemain.overtime");


    /**
     * 錯誤代碼. *編碼邏輯：controller名稱 + 四位數字(0001開始)
     */
    private String errorCode;

    /**
     * 錯誤訊息.
     */
    private String errorMsgKey;

    private KgoFrontEndApiError(String errorCode, String errorMsgKey) {
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
