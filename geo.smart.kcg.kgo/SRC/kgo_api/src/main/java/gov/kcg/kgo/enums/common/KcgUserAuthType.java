package gov.kcg.kgo.enums.common;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 單一登入用戶認證類型 enum
 * 
 * AuthTypeName		  		Description
 * -------------------------------------
 * KCG_USER_BASIC_INFO 	→ 	用戶基本資訊,
 * KCG_MOICA_CARD_INFO 	→ 	自然人憑證登入資訊,
 * KCG_HCA_CARD_INFO 	→ 	健保卡登入資訊,
 * KCG_EGOV_SSO_INFO 	→ 	我的e 政府用戶基本資訊,
 * KCG_GOOGLE_SSO_INFO 	→ 	Google 用戶基本資訊, 
 * KCG_FACEBOOK_SSO_INFO→ 	Facebook 用戶基本資訊
 */
public enum KcgUserAuthType {

	/** 用戶基本資訊 */
	BASIC("KCG_USER_BASIC_INFO", "PUBLIC_APP_USER_SSO_TOKEN",
			Arrays.asList("APP_COMPANY_ID", "APP_USER_LOGIN_ID", "AUTH_FROM_ADDRESS", "AUTH_DATE", "AUTH_METHOD"),
			"用戶基本資訊"),
	/** 自然人憑證登入資訊 */
	MOICA("KCG_MOICA_CARD_INFO", "KCG_MOICA_USER_SSO_TOKEN",
			Arrays.asList("AUTH_FROM_ADDRESS", "AUTH_DATE", "MOICA_USER_TW_SSN", "MOICA_USER_NAME", "MOICA_CERT_SN"),
			"自然人憑證登入資訊"),
	/** 健保卡登入資訊 **/
	HCA("KCG_HCA_CARD_INFO", "KCG_HCA_USER_SSO_TOKEN",
			Arrays.asList("AUTH_FROM_ADDRESS", "AUTH_DATE", "HCA_USER_TW_SSN", "HCAMOICA_USER_NAME", "HCA_CARD_NUMBER"),
			"健保卡登入資訊"),
	/** 我的e 政府用戶基本資訊 **/
	EGOV("KCG_EGOV_SSO_INFO", "KCG_EGOV_USER_SSO_TOKEN",
			Arrays.asList("AUTH_FROM_ADDRESS", "AUTH_DATE", "EGOV_AUTH_METHOD", "EGOV_USER_ACCOUNT", "EGOV_USER_CN",
					"EGOV_USER_UID", "EGOV_USER_CERKEY_STR", "EGOV_USER_MAIL", "EGOV_USER_TYPE"),
			"我的e 政府用戶基本資訊"),
	/** Google 用戶基本資訊 **/
	GOOGEL(" KCG_GOOGLE_SSO_INFO", "KCG_GOOGLE_USER_SSO_TOKEN",
			Arrays.asList("AUTH_FROM_ADDRESS", "AUTH_DATE", "GOOGLE_USER_ACCOUNT", "GOOGLE_USER_NAME"),
			"Google 用戶基本資訊"),
	/** Facebook 用戶基本資訊 **/
	FACEBOOK("KCG_FACEBOOK_SSO_INFO", "KCG_FACEBOOK_USER_SSO_TOKEN",
			Arrays.asList("AUTH_FROM_ADDRESS", "AUTH_DATE", "FACEBOOK_USER_ACCOUNT", "FACEBOOK_USER_NAME"),
			"Facebook 用戶基本資訊 ");

	private String authTypeName;
	private String authTokenName;
	private List<String> respColumnList;
	private String typeMsgKey;

	private KcgUserAuthType(String authTypeName, String authTokenName, List<String> respColumnList, String typeMsgKey) {
		this.authTypeName = authTypeName;
		this.authTokenName = authTokenName;
		this.respColumnList = respColumnList;
		this.typeMsgKey = typeMsgKey;
	}

	public static KcgUserAuthType getKcgUserAuthType(String typeName) {
		for (KcgUserAuthType type : values()) {
			if (StringUtils.equalsIgnoreCase(type.getAuthTypeName(), typeName)) {
				return type;
			}
		}
		return null;
	}

	public String getAuthTypeName() {
		return authTypeName;
	}

	public void setAuthTypeName(String authTypeName) {
		this.authTypeName = authTypeName;
	}

	public String getAuthTokenName() {
		return authTokenName;
	}

	public void setAuthTokenName(String authTokenName) {
		this.authTokenName = authTokenName;
	}

	public List<String> getRespColumnList() {
		return respColumnList;
	}

	public void setRespColumnList(List<String> respColumnList) {
		this.respColumnList = respColumnList;
	}

	public String getTypeMsgKey() {
		return typeMsgKey;
	}

	public void setTypeMsgKey(String typeMsgKey) {
		this.typeMsgKey = typeMsgKey;
	}

}
