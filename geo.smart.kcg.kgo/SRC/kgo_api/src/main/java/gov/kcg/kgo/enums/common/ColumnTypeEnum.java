package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 案件設定欄位型態(KGO_CASESET_COLUMN.ColumnType)集合
 * 
 * @author TPIuser
 *
 */
public enum ColumnTypeEnum {

	/** 身分證 */
	TEXT_ID("TextId", "身分證"),
	/** 統一編號 */
	TEXT_TAX_ID("TextTaxId", "統一編號"),
	/** 手機 */
	TEXT_PHONE("TextPhone", "手機"),
	/** 身分證 */
	TEXT_TEL("TextTel", "電話"),
	/** 身分證 */
	TEXT_EMAIL("TextEmail", "電子郵件"),
	/** 地址 */
	ADDRESS("Address", "地址"),
	/** 地址 */
	ADDRESSTEXT("AddressTextBox", "地址(分處)"),
	/** 單行文字 */
	TEXT("TextBox", "單行文字"),
	/** 單選下拉 */
	DRP("Drp", "單選下拉"),
	/** 身分證 */
	TEXT_ARES("TextArea", "多行文字"),
	/** 數字 */
	NUM("Num", "數字"),
	/** 單選 */
	RADIO("Radio", "單選"),
	/** 日期 */
	DATE("Date", "日期"),
	/** 日期起迄 */
	DATE_SE("DateSE", "日期起迄"),
	/** 時間 */
	TIME("Time", "時間"),
	/** 時間起訖 */
	TIME_SE("TimeSE", "時間起訖"),
	/** 附件 */
	FIL("Fil", "附件"),
	/** 附件照片 */
	PIC("Pic", "附件照片"),
	/** 段小段 */
	LAND_NUM("LandNum", "段小段"),
	/** 複合欄位-單選 */
	S_RADIO("SRadio", "複合欄位-單選"),
	/** 複合欄位-勾選框 */
	S_CHECKBOX("SCheckbox", "複合欄位-勾選框"),
	/** 勾選框 */
	CHECKBOX("Checkbox", "勾選框"),
	/** 複合欄位 */
	M("M", "複合欄位"),
	/** 文字 */
	TEXT_LABEL("TextLabel", "文字"),
	/** 機關檢核 */
	ORG_CHECK("OrgCheck", "機關檢核"),

	// modify: 2020.12.02 移除選項
	/** 整合串接(身分證) */
	// INTGR_API_ID("IntgrApiId", "整合串接(身分證)");


	/** GEO 20210817 add for 1999 service **/
	/** 1999派工 **/
	KD_1999("kd1999", "1999派工主子項"),

	/** 1999陳情 **/
	NEW_1999("new1999", "1999陳情主子項"),

	/** 建議事項行政區 **/
	AREA_ADVICE("areaAdvice", "1999建議事項行政區"),

	/** 建議地點 **/
	ADDR_ADVICE("addrAdvice", "1999建議地點"),

	/** 1999地址 **/
	ADDR_1999("addr1999", "1999地址"),

	/** GEO 20211115 add for 民政局五種服務轉成B流程 **/
	AGENT("Agent","取件人"),

	/** GEO 20211115 add for 民政局五種服務轉成B流程 **/
	KAOHSIUNG_RESIDENT("Resident","地區設定"),

	/** GEO 20211115 add for 民政局五種服務轉成B流程 **/
	KAOHSIUNG_DISTRICT_OFFICE("DistrictOffice","區公所");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private ColumnTypeEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	/**
	 * 取得案件設定欄位型態.
	 *
	 */
	public static ColumnTypeEnum getColumnTypeEnum(String value) {
		for (ColumnTypeEnum cEnum : values()) {
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
