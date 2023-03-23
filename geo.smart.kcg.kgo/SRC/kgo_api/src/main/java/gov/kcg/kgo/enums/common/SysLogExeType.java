package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 使用者異動紀錄類型.
 */
public enum SysLogExeType {

	/** 新增-i18n. */
	TYPE_A("A", "gov.kcg.kgo.sys.log.exe.type.a"),

	/** 修改-i18n. */
	TYPE_U("U", "gov.kcg.kgo.sys.log.exe.type.u"),

	/** 刪除-i18n. */
	TYPE_D("D", "gov.kcg.kgo.sys.log.exe.type.d"),

	/** 下載-i18n. */
	TYPE_L("L", "gov.kcg.kgo.sys.log.exe.type.l");

	/** log代碼. */
	private String code;

	/** log代碼對應i18n訊息. */
	private String codeMsgKey;

	private SysLogExeType(String code, String codeMsgKey) {
		this.code = code;
		this.codeMsgKey = codeMsgKey;
	}

	/**
	 * 取得對應異動紀錄類型.
	 *
	 * @param errorCode the error code
	 * @return the error
	 */
	public static SysLogExeType getSysLogExeType(String code) {
		for (SysLogExeType type : values()) {
			if (StringUtils.equals(type.getCode(), code)) {
				return type;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeMsgKey() {
		return codeMsgKey;
	}
}
