package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 任務發布對象集合
 * 
 * F-前台;B-後台
 *
 */
public enum ReleaseToEnum {

	/** 前台 */
	FRONT("F", "前台"),
	/** 後台 */
	BACK("B", "後台");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private ReleaseToEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}
	
	/**
	 * 取得對應狀態類別.
	 *
	 */
	public static ReleaseToEnum getReleaseToEnum(String value) {
		for (ReleaseToEnum aEnum : values()) {
			if (StringUtils.equalsIgnoreCase(aEnum.getValue(), value)) {
				return aEnum;
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
