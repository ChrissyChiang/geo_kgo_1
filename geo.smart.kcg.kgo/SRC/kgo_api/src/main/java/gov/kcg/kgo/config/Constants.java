package gov.kcg.kgo.config;

/**
 * 常數.
 */
public class Constants {
	/** 登入類型: 前台、後台 SESSION KEY **/
	public final static String SYS_TYPE = "SYS_TYPE";

	/** 登入使用者session key. */
	public final static String LOGIN_USER_SESSION_KEY = "loginUserInfo";

	/** 成功代碼: 0000. */
	public final static String SUCCESS_CODE = "0000";
	
    /** token rq 表頭 "Authorization". */
    public static final String TOKEN_HERDER = "Authorization";
    
    /** token類型. */
    public static final String TOKEN_TYPE = "bearer";

	/** 未知錯誤訊息代碼. */
	public final static String UNKNOWN_EXCEPTION_CODE = "9999";

	/** 政府單登入口基本網址 **/
	public final static String SSO_LOGIN_BASE_URL = "https://accounts.kcg.gov.tw/";

	/** KCG API 基本網址 **/
	public final static String KCG_API_BASE_URL = "https://accounts-api.kcg.gov.tw/";

	/** KCG 介接服務TOKEN基本網址 **/
	public final static String KCG_SERVICE_TOKEN_BASE_URL = "/service/Token/";

	/** KCG 介接服務GET基本網址 **/
	public final static String KCG_SERVICE_GET_BASE_URL = "/service/get/";
	
	/** KCG 介接服務GET基本網址 **/
	public final static String CITY_COIN_API_BASE_PATH = "/api/Mission/";

	/** 使用者單登認證後回傳的資訊中記載詳細內容的欄位名稱 **/
	public final static String KCG_USER_AUTH_RESPONCE_CONTENT_COLUMN_NAME = "PUBLISH_INFO_CONTENT";

	/** ComboBox 顯示值的欄位名稱 **/
	public final static String ENUM_COMBOBOX_LABEL_FIELD = "label";
	
	/** ComboBox 內容值的欄位名稱 **/
	public final static String ENUM_COMBOBOX_VALUE_FIELD = "value";

}
