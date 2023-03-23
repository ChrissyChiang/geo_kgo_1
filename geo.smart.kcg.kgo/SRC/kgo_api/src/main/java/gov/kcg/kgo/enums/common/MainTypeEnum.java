package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 主類別集合
 * 
 * @author TPIuser
 *
 */
public enum MainTypeEnum {

	/**
	 * 機關
	 */
	ORGAN("Organ", "機關"),
	/**
	 * 角色
	 */
	ROLE("Role", "角色"),
	/**
	 * 服務
	 */
	SERVICE("Service", "服務");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private MainTypeEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	/**
	 * 取得對應狀態類別.
	 *
	 */
	public static MainTypeEnum getMainTypeEnum(String value) {
		for (MainTypeEnum aEnum : values()) {
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
