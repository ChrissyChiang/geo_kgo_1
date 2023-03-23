package gov.kcg.kgo.enums.backend;

/**
 * 適用於只有固定選項(機關/單位)的 ComboBox上
 * 
 * @author TPIuser
 *
 */
public enum OrganUnitEnum {

	ORGAN("organ", "機關"), UNIT("unit", "單位");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private OrganUnitEnum(String value, String label) {
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
