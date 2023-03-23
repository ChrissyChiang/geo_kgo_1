package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 軌跡紀錄 - 身份驗證類型
 * 
 * @author TPIuser
 *
 */
public enum KgoLogLoginTypeEnum {

	/** 自然人憑證. */
	MOICA("MOICA", "kgo.log.login.type.moica"),

	/** 健保卡. */
	HCA("HCA", "kgo.log.login.type.hca"),
	
	/** FB. */
	FACEBOOK("FACEBOOK", "kgo.log.login.type.facebook"),
	
	/** GOOGLE. */
	GOOGLE("GOOGLE", "kgo.log.login.type.google"),
	
	/** E政府. */
	EGOV("EGOV", "kgo.log.login.type.egov"),
	
	/** 無. */
	UNKNOW("UNKNOW", "kgo.log.login.type.unknow");

	/** 登入類別 */
	private String loginType;
	
	/** 登入類別 對應i18n 文字 */
	private String msgKey;


	private KgoLogLoginTypeEnum(String loginType, String msgKey) {
		this.loginType = loginType;
		this.msgKey = msgKey;
	}

	/**
	 * 取得對應身份驗證類型
	 *
	 */
	public static KgoLogLoginTypeEnum getLoginType(String loginType) {
		for (KgoLogLoginTypeEnum e : values()) {
			if (StringUtils.equals(e.getLoginType(), loginType)) {
				return e;
			}
		}
		return null;
	}

	public String getLoginType() {
		return loginType;
	}

	public String getMsgKey() {
		return msgKey;
	}
}
