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
public enum LoginAuthType {

	// 市府員工 用戶基本資訊 authType 有兩種
	// authType
	// pass_idpass_auth 一般帳密登入
	// pass_moica_auth 自然人憑證(綁定)登入
	/** 市府員工 用戶基本資訊 */
	BASIC_ID("BASIC_ID","pass_idpass_auth", "市府員工-一般帳號"),
	
	/** 市府員工 用戶基本資訊 */
	BASIC_MOICA("BASIC_MOICA", "pass_moica_auth", "市府員工-自然人憑證(綁定)"),
	
//	/** 市府員工 用戶基本資訊 */
//	BASIC("BASIC","pass_moica_auth", "市府員工-一般帳號"),

	

	/** 一般民眾 自然人憑證登入資訊 */
	MOICA("MOICA","pass_moica_auth","自然人憑證"),

	/** 一般民眾 健保卡登入資訊 **/
	HCA("HCA", "pass_hca_auth", "健保卡"),

	/** 一般民眾 我的e政府用戶基本資訊 **/
	EGOV("EGOV", "pass_egov_auth","我的e政府"),

	/** 一般民眾 Google 用戶基本資訊 **/
	GOOGLE("GOOGLE", "pass_google_auth","GOOGLE"),

	/** 一般民眾 Facebook 用戶基本資訊 **/
	FACEBOOK("FACEBOOK" ,"pass_facebook_auth", "FACEBOOK"),

	/** LINE用戶基本資訊. **/
	LINE("LINE" ,"pass_line_auth", "市府員工登入LINE"),

	/** TW FidO 用戶基本資訊. **/
	TW_FIDO("TW_FIDO" ,"pass_fido_auth", "TW-FidO");

	private String typeName;
	
	/** SSO_TOKEN type. */
	private String authType;

	/** 登入類型 */
	private String authName;
	

	private LoginAuthType(String typeName, String authType, String authName) {
		this.typeName = typeName;
		this.authType = authType;
		this.authName = authName;
	}
	
	public static LoginAuthType getLoginAuthTypeByTypeName(String typeName) {
		for (LoginAuthType type : values()) {
			if (StringUtils.equalsIgnoreCase(type.getTypeName(), typeName)) {
				return type;
			}
		}
		return null;
	}

	public static LoginAuthType getLoginAuthType(String authType) {
		for (LoginAuthType type : values()) {
			if (StringUtils.equalsIgnoreCase(type.getAuthType(), authType)) {
				return type;
			}
		}
		return null;
	}
	

	public String getTypeName() {
		return typeName;
	}

	public String getAuthType() {
		return authType;
	}

	public String getAuthName() {
		return authName;
	}
}
