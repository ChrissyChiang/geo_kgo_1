package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 受理機關下拉式選單設定集合
 * 
 * @author TPIuser
 *
 */
public enum AcceptSetEnum {

	/** 機關分文 */
	UNIT("UNIT", "機關分文"),
	/** 承辦人 */
	OFFICER("OFFICER", "承辦人"),
	/** 區機關 */
	AREA("AREA", "區機關");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private AcceptSetEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public static AcceptSetEnum getEnum(String value) {
		for (AcceptSetEnum cEnum : values()) {
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
