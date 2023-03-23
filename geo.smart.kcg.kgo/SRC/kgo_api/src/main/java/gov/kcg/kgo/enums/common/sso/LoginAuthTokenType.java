package gov.kcg.kgo.enums.common.sso;

import org.apache.commons.lang3.StringUtils;

/**
 * 單一登入用戶登入認證類型 enum
 * 
 * AuthTypeName Description -------------------------------------
 * PUBLIC_APP_USER_SSO_TOKEN → 市府員工 用戶基本資訊 TOKEN, KCG_MOICA_USER_SSO_TOKEN →
 * 一般民眾 自然人憑證登入資訊 TOKEN, KCG_HCA_USER_SSO_TOKEN → 一般民眾 健保卡登入資訊 TOKEN,
 * KCG_EGOV_USER_SSO_TOKEN → 一般民眾 我的e 政府用戶基本資訊 TOKEN, KCG_GOOGLE_USER_SSO_TOKEN
 * → 一般民眾 Google 用戶基本資訊 TOKEN, KCG_FACEBOOK_USER_SSO_TOKEN→ 一般民眾 Facebook 用戶基本資訊
 * TOKEN
 */
public enum LoginAuthTokenType {

	// 市府員工 用戶基本資訊 authType 有兩種
	// authType
	// pass_idpass_auth 一般帳密登入
	// pass_moica_auth 自然人憑證(綁定)登入
	/** 市府員工 用戶基本資訊 */
	BASIC("PUBLIC_APP_USER_SSO_TOKEN", new LoginAuthType[] {LoginAuthType.BASIC_ID, LoginAuthType.BASIC_MOICA}, "BASIC", "市府員工"),

	/** 一般民眾 自然人憑證登入資訊 */
	MOICA("KCG_MOICA_USER_SSO_TOKEN", new LoginAuthType[] { LoginAuthType.MOICA }, "MOICA", "自然人憑證"),

	/** 一般民眾 健保卡登入資訊 **/
	HCA("KCG_HCA_USER_SSO_TOKEN", new LoginAuthType[] { LoginAuthType.HCA }, "HCA", "健保卡"),

	/** 一般民眾 我的e政府用戶基本資訊 **/
	EGOV("KCG_EGOV_USER_SSO_TOKEN", new LoginAuthType[] { LoginAuthType.EGOV }, "EGOV", "我的e政府"),

	/** 一般民眾 Google 用戶基本資訊 **/
	GOOGLE("KCG_GOOGLE_USER_SSO_TOKEN", new LoginAuthType[] { LoginAuthType.GOOGLE }, "GOOGLE", "GOOGLE"),

	/** 一般民眾 Facebook 用戶基本資訊 **/
	FACEBOOK("KCG_FACEBOOK_USER_SSO_TOKEN", new LoginAuthType[] { LoginAuthType.FACEBOOK }, "FACEBOOK", "FACEBOOK"),

	/** LINE用戶基本資訊. */
	LINE("KCG_LINE_USER_SSO_TOKEN", new LoginAuthType[] { LoginAuthType.LINE }, "LINE", "市府員工登入LINE"),

	/** TW FidO用戶基本資訊. */
	TW_FIDO("KCG_TWFIDO_USER_SSO_TOKEN", new LoginAuthType[] { LoginAuthType.TW_FIDO }, "TW_FIDO", "TW-FidO");
	
	/** SSO_TOKEN type. */
	private String authTokenName;

	/** 驗證方式 */
	private LoginAuthType[] authType;
	
	/** 登入類型 */
	private String type;
	
	/** 登入類型名稱 */
	private String typeName;

	private LoginAuthTokenType(String authTokenName, LoginAuthType[] authType, String type, String typeName) {
		this.authTokenName = authTokenName;
		this.authType = authType;
		this.type = type;
		this.typeName = typeName;
	}

	public static LoginAuthTokenType getLoginAuthTokenType(String authTokenName) {
		for (LoginAuthTokenType type : values()) {
			if (StringUtils.equalsIgnoreCase(type.getAuthTokenName(), authTokenName)) {
				return type;
			}
		}
		return null;
	}
	
	/**
	 * get auth by type.
	 *
	 * @param type the type
	 * @return the login auth type
	 */
	public static LoginAuthTokenType getLoginAuthType(String type) {
		for (LoginAuthTokenType typeE : values()) {
			if (StringUtils.equalsIgnoreCase(typeE.getType(), type)) {
				return typeE;
			}
		}
		return null;
	}

	public String getAuthTokenName() {
		return authTokenName;
	}

	public LoginAuthType[] getAuthType() {
		return authType;
	}

	public String getType() {
		return type;
	}

	public String getTypeName() {
		return typeName;
	}
	
}
