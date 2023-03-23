package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;

/**
 * 設定前台身份驗證 來源： MEG-MYeGOV CDC-自然人憑證 SM-社群帳號驗證 NAN-免驗證
 * 
 * 
 */
public enum CheckTypeEnum {
	/** 市府員工 **/
	BASIC("BASIC", "市府員工", LoginAuthTokenType.BASIC),
	/** 自然人憑證 **/
	MOICA("MOICA", "自然人憑證", LoginAuthTokenType.MOICA),
	/** 健保卡 **/
	HCA("HCA", "健保卡", LoginAuthTokenType.HCA),
	/** 我的e 政府 **/
	EGOV("EGOV", "我的e 政府", LoginAuthTokenType.EGOV),
	/** Google登入 **/
	GOOGLE("GOOGLE", "Google登入", LoginAuthTokenType.GOOGLE),
	/** Facebook登入 **/
	FACEBOOK("FACEBOOK", "Facebook登入", LoginAuthTokenType.FACEBOOK),
	/** LINE登入 **/
	LINE("LINE", "LINE登入", LoginAuthTokenType.LINE),
	/** TW_FIDO登入 **/
	TW_FIDO("TW_FIDO", "TW_FIDO登入", LoginAuthTokenType.TW_FIDO),
	/** 免驗證 **/
	NAN("NAN", "免驗證", null);

	private String value;
	private String label;
	private LoginAuthTokenType loginAuthType;

	public static CheckTypeEnum getEnum(String value) {
		for (CheckTypeEnum type : values()) {
			if (StringUtils.equalsIgnoreCase(type.value, value)) {
				return type;
			}
		}
		return null;
	}

	private CheckTypeEnum(String value, String label, LoginAuthTokenType loginAuthType) {
		this.value = value;
		this.label = label;
		this.loginAuthType = loginAuthType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public LoginAuthTokenType getLoginAuthType() {
		return loginAuthType;
	}

	public void setLoginAuthType(LoginAuthTokenType loginAuthType) {
		this.loginAuthType = loginAuthType;
	}

}
