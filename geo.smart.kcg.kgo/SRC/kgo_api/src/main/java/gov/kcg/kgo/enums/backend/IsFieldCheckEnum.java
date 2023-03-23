package gov.kcg.kgo.enums.backend;

/**
 * GEO 20211102
 * 是否欄位勾選 enum
 */
public enum IsFieldCheckEnum {

	YES("1", "是", true), NO("0", "否", false);

	private String value;
	private String label;
	private Boolean booleanValue;

	private IsFieldCheckEnum(String value, String label, Boolean booleanValue) {
		this.value = value;
		this.label = label;
		this.booleanValue = booleanValue;
	}

	public static IsFieldCheckEnum getIsFieldCheckEnum(Integer value) {
		return value ==1 ? IsFieldCheckEnum.YES : IsFieldCheckEnum.NO;
	}

	public static IsFieldCheckEnum getIsFieldCheckEnum(String value) {
		for (IsFieldCheckEnum aEnum : values()) {
			if (aEnum.getValue().equalsIgnoreCase(value)) {
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

	public Boolean getBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

}
