package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 系統類型 enum.
 */
public enum SysType {

	/** 系統類型: 前臺. */
	FRONT("F", "KCG_6410", "kgo.sys.type.front"),

	/** 系統類型: 後臺. */
	BACK("B", "KCG_4583", "kgo.sys.type.back");

	private String typeCode;
	/** 應用系統帶碼 */
	private String appId;
	private String typeMsgKey;

	private SysType(String typeCode, String appId, String typeMsgKey) {
		this.typeCode = typeCode;
		this.appId = appId;
		this.typeMsgKey = typeMsgKey;
	}

	public static SysType getType(String typeCode) {
		for (SysType type : values()) {
			if (StringUtils.equals(type.getTypeCode(), typeCode)) {
				return type;
			}
		}
		return null;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public String getTypeMsgKey() {
		return typeMsgKey;
	}

	public void setTypeMsgKey(String typeMsgKey) {
		this.typeMsgKey = typeMsgKey;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
