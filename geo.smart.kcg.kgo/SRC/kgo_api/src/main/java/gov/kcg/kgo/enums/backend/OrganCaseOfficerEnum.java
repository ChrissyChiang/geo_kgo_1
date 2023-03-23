package gov.kcg.kgo.enums.backend;

/**
 * 適用於只有固定選項(機關/單位)的 ComboBox上
 * 
 * @author TPIuser
 *
 */
public enum OrganCaseOfficerEnum {

	ORGAN("organ", "機關"), CASEOFFICER("caseofficer", "承辦");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private OrganCaseOfficerEnum(String value, String label) {
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
