package gov.kcg.kgo.enums.backend;

/**
 * KGO_CASESET_COLUMN.MyDataType 代碼資料對應集合
 * 
 * @author TPIuser
 *
 */
public enum CasesetColumnMyDataTypeEnum {

	/** JSON **/
	JSON("JSON", "JSON"),
	/** PDF **/
	PDF("PDF", "PDF");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private CasesetColumnMyDataTypeEnum(String value, String label) {
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
