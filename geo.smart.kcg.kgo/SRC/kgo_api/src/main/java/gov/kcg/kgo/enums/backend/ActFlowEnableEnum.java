package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 動態流程 - 啟用、停用 enum.
 */
public enum ActFlowEnableEnum {

	/** 啟用 */
	Y("Y", "kgo.act.flow.enable"),
	
	/** 停用 **/
	N("N", "kgo.act.flow.disable");

	/** 參數設定 **/
	private String code;

	/** 參數設定名稱 I18nKey **/
	private String i18nKey;

	private ActFlowEnableEnum(String code, String i18nKey) {
		this.code = code;
		this.code = code;
	}

	/**
	  * 取得動態流程 - 啟用、停用.
	 *
	 */
	public static ActFlowEnableEnum getActFlowEnableEnum(String code) {
		for (ActFlowEnableEnum aEnum : values()) {
			if (StringUtils.equals(aEnum.getCode(), code)) {
				return aEnum;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getI18nKey() {
		return i18nKey;
	}
}
