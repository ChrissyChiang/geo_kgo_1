package gov.kcg.kgo.geoenum;

import org.apache.commons.lang3.StringUtils;

/**
 * 臨櫃預約表單設定欄位型態(GEO_KGO_APPOINTMENT_COLUMN.ColumnType.ColumnType)集合
 * 
 * @author TPIuser
 *
 */
public enum GeoAppointmentColumnTypeEnum {

	TEXT_ID("TextId", "身分證"),
	TEXT_TAX_ID("TextTaxId", "統一編號"),
	TEXT_PHONE("TextPhone", "手機"),
	TEXT_TEL("TextTel", "電話"),
	TEXT_EMAIL("TextEmail", "電子郵件"),
	ADDRESS("Address", "地址"),
	ADDRESSTEXT("AddressTextBox", "地址(分處)"),
	TEXT("TextBox", "單行文字"),
	DRP("Drp", "單選下拉"),
	TEXT_ARES("TextArea", "多行文字"),
	NUM("Num", "數字"),
	RADIO("Radio", "單選"),
	DATE("Date", "日期"),
	DATE_SE("DateSE", "日期起迄"),
	TIME("Time", "時間"),
	TIME_SE("TimeSE", "時間起訖"),
	FIL("Fil", "附件"),
	PIC("Pic", "附件照片"),
	LAND_NUM("LandNum", "段小段"),
	S_RADIO("SRadio", "複合欄位-單選"),
	S_CHECKBOX("SCheckbox", "複合欄位-勾選框"),
	CHECKBOX("Checkbox", "勾選框"),
	M("M", "複合欄位"),
	TEXT_LABEL("TextLabel", "文字"),
	ORG_CHECK("OrgCheck", "機關檢核");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private GeoAppointmentColumnTypeEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	/**
	 * 取得案件設定欄位型態.
	 *
	 */
	public static GeoAppointmentColumnTypeEnum getColumnTypeEnum(String value) {
		for (GeoAppointmentColumnTypeEnum cEnum : values()) {
			if (StringUtils.equalsIgnoreCase(cEnum.getValue(), value)) {
				return cEnum;
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

}
