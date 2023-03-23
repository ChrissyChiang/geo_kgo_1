package gov.kcg.kgo.enums.backend;

/**
 * 分類管理檔-類別
 * 
 * M = 主類別, D = 次類別
 *
 */
public enum KgoGroupLevelTypeEnum {

	/** 主類別 **/
	M("M", "主類別"),
	/** 次類別 **/
	D("D", "次類別");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private KgoGroupLevelTypeEnum(String value, String label) {
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
