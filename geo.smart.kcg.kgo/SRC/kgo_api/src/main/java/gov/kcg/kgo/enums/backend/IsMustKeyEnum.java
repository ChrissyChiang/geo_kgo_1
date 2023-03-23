package gov.kcg.kgo.enums.backend;

public enum IsMustKeyEnum {

	YES("1", "是", true), NO("0", "否", false);

	private String value;
	private String label;
	private Boolean booleanValue;

	private IsMustKeyEnum(String value, String label, Boolean booleanValue) {
		this.value = value;
		this.label = label;
		this.booleanValue = booleanValue;
	}

	public static IsMustKeyEnum getIsMustKeyEnum(Boolean value) {
		return value ? IsMustKeyEnum.YES : IsMustKeyEnum.NO;
	}

	public static IsMustKeyEnum getIsMustKeyEnum(String value) {
		for (IsMustKeyEnum aEnum : values()) {
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
