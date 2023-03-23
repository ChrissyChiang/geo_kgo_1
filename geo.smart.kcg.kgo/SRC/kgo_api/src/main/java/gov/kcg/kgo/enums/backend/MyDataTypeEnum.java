package gov.kcg.kgo.enums.backend;

/**
 * KGO_CASESET_MYDATA.MyDataType 代碼資料對應集合
 * 
 * @author TPIuser
 *
 */
public enum MyDataTypeEnum {

	/** 本府社政 **/
	SP("SP", "本府社政"),
	/** 交通 **/
	TF("TF", "交通"),
	/** 戶役政 **/
	HS("HS", "戶役政"),
	/** 財稅 **/
	FT("FT", "財稅"),
	/** 本府社政 **/
	LP("LP", "勞保");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private MyDataTypeEnum(String value, String label) {
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
