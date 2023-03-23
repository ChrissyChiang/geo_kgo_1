package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

/**
 * Excel 格式.
 */
public enum ExcelType {
	
    /** Excel格式 xlsx, contentType. */
	XLSX(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
	
	/** Excel格式 xls, contentType. */
	XLS(".xls", "application/vnd.ms-excel"),
	
	/** UNKNOWN */
	UNKNOWN("UNKNOWN", "");
	
	/** excel 格式. */
	private String excelType;
	
	/** excel contentType. */
	private String contentType;
    
	private ExcelType(String excelType, String contentType) {
		this.excelType = excelType;
		this.contentType = contentType;
	}
	
	/**
	 * 取得對應Excel格式.
	 *
	 * @param errorCode the error code
	 * @return the error
	 */
	public static ExcelType getExcelType(String str) {
		for (ExcelType type : values()) {
			if (StringUtils.equals(type.getExcelType(), str)) {
				return type;
			}
		}
		return UNKNOWN;
	}

	public String getExcelType() {
		return excelType;
	}

	public String getContentType() {
		return contentType;
	}
}
