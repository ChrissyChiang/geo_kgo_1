package gov.kcg.kgo.enums.common.sso;

/**
 * 單一登入 查詢交換資訊 enum
 * 
 * AuthTypeName Description -------------------------------------
 * PUBLIC_APP_USER_SSO_TOKEN → 用戶基本資訊 , KCG_MOICA_USER_SSO_TOKEN → 自然人憑證登入資訊,
 * KCG_HCA_USER_SSO_TOKEN → 健保卡登入資訊, KCG_EGOV_USER_SSO_TOKEN → 我的e 政府用戶基本資訊,
 * KCG_GOOGLE_USER_SSO_TOKEN → Google 用戶基本資訊, KCG_FACEBOOK_USER_SSO_TOKEN→
 * Facebook 用戶基本資訊
 */
public enum QueryInfoType {

	/** 用戶基本資訊 */
	BASIC(LoginAuthTokenType.BASIC, "KCG_ONE_ACCOUNT", "KCG_USER_BASIC_INFO"),

	/** 自然人憑證登入資訊 */
	MOICA(LoginAuthTokenType.MOICA, "KCG_ONE_ACCOUNT", "KCG_MOICA_CARD_INFO"),

	/** 健保卡登入資訊 **/
	HCA(LoginAuthTokenType.HCA, "KCG_ONE_ACCOUNT", "KCG_HCA_CARD_INFO"),

	/** 我的e 政府用戶基本資訊 **/
	EGOV(LoginAuthTokenType.EGOV, "KCG_ONE_ACCOUNT", "KCG_EGOV_SSO_INFO"),

	/** Google 用戶基本資訊 **/
	GOOGEL(LoginAuthTokenType.GOOGLE, "KCG_ONE_ACCOUNT", "KCG_GOOGLE_SSO_INFO"),

	/** Facebook 用戶基本資訊 **/
	FACEBOOK(LoginAuthTokenType.FACEBOOK, "KCG_ONE_ACCOUNT", "KCG_FACEBOOK_SSO_INFO"),

	/** LINE用戶基本資訊 **/
	LINE(LoginAuthTokenType.LINE, "KCG_ONE_ACCOUNT", "KCG_LINE_SSO_INFO"),

	/** TW FidO用戶基本資訊 **/
	TW_FIDO(LoginAuthTokenType.TW_FIDO, "KCG_ONE_ACCOUNT", "KCG_TWFIDO_SSO_INFO");

	/** 單一登入用戶認證類型. */
	private LoginAuthTokenType loginAuthTokenType;

	/** 資訊格式代碼一 */
	String publishInfoKey1;

	/** 資訊格式代碼二 */
	String publishInfoKey2;

	private QueryInfoType(LoginAuthTokenType loginAuthTokenType, String publishInfoKey1, String publishInfoKey2) {
		this.loginAuthTokenType = loginAuthTokenType;
		this.publishInfoKey1 = publishInfoKey1;
		this.publishInfoKey2 = publishInfoKey2;
	}

	/**
	 * 查詢交換資訊類型.
	 *
	 * @param loginAuthTokenType the login auth token type
	 * @return the query info type
	 */
	public static QueryInfoType getQueryInfoType(LoginAuthTokenType loginAuthTokenType) {
		for (QueryInfoType type : values()) {
			if (type.getLoginAuthTokenType().equals(loginAuthTokenType)) {
				return type;
			}
		}
		return null;
	}

	public LoginAuthTokenType getLoginAuthTokenType() {
		return loginAuthTokenType;
	}

	public String getPublishInfoKey1() {
		return publishInfoKey1;
	}

	public String getPublishInfoKey2() {
		return publishInfoKey2;
	}
}
