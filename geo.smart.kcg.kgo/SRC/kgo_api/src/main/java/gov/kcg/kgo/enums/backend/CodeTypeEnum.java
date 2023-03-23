package gov.kcg.kgo.enums.backend;

/**
 * KGO_CODE. codeType 集合
 * 
 * @author TPIuser
 *
 */
public enum CodeTypeEnum {

	FLOW("Flow", "作業流程"), ROLE("Role", "角色"), SERVICE("Service", "服務"), MY_DTA_TYPE("MyDataType", "MyDATA類別"),
	DOC_TYPE("DocType", "書表維護分類"), QUESTION_TYPE("QuestionType", "常見問題類別"), F_FUNCTION("FFunction", "前台功能"),
	B_FUNCTION("BFunction", "後台功能"),FILE_TYPE("FileType", "檔案類型");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private CodeTypeEnum(String value, String label) {
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
