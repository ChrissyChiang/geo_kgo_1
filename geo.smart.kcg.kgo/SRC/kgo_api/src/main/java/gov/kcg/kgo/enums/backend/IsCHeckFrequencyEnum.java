package gov.kcg.kgo.enums.backend;

/**
 * GEO 20211019 add
 * 是否重複檢核 enum
 */
public enum IsCHeckFrequencyEnum {

	YES("1", "是", true), NO("0", "否", false);

	private String value;
	private String label;
	private Boolean booleanValue;

	private IsCHeckFrequencyEnum(String value, String label, Boolean booleanValue) {
		this.value = value;
		this.label = label;
		this.booleanValue = booleanValue;
	}

	public static IsCHeckFrequencyEnum getIsCheckFrequencyEnum(Integer value) {
		return value ==1 ? IsCHeckFrequencyEnum.YES : IsCHeckFrequencyEnum.NO;
	}

	public static IsCHeckFrequencyEnum getIsCheckFrequencyEnum(String value) {
		for (IsCHeckFrequencyEnum aEnum : values()) {
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
