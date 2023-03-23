package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author TPIuser
 *
 */
public enum CaseMainStateEnum {

	W("1", "未通知"), P("2", "已通知"), F("3", "已補正");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private CaseMainStateEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	/**
	 * 取得對應狀態類別.
	 *
	 */
	public static CaseMainStateEnum getCaseMainStateEnum(String value) {
		for (CaseMainStateEnum aEnum : values()) {
			if (StringUtils.equalsIgnoreCase(aEnum.getValue(), value)) {
				return aEnum;
			}
		}
		return null;
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

}
