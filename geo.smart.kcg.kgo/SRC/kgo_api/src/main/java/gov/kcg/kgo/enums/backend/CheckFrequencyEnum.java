package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 重複檢核時間
 *
 */
public enum CheckFrequencyEnum {

	YEAR("Year", "一年"),
	MONTH("Month", "一個月"),
	WEEK("Week", "一週"),
	NO("", "");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	public static CheckFrequencyEnum getCheckFrequencyEnum(String periodType) {
		for (CheckFrequencyEnum type : values()) {
			if (StringUtils.equals(type.getValue(), periodType)) {
				return type;
			}
		}
		return null;
	}

	private CheckFrequencyEnum(String value, String label) {
		this.value = value;
		this.label = label;
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
