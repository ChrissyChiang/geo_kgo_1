package gov.kcg.kgo.enums.backend;

/**
 * 案件服務對象
 * 
 * I-對內;O-對外
 *
 */
public enum ServiceToEnum {

	/** 對內 */
	INSIDE("I", "對內"),
	/** 對外 */
	OUTSIDE("O", "對外");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private ServiceToEnum(String value, String label) {
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
