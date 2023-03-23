package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 輸入站外連結方式 來源：SELF-本頁開啟;OPEN-另開視窗
 * 
 */
public enum LinkTypeEnum {

	/** 本頁開啟 **/
	SELF("SELF", "本頁開啟"),
	/** 另開視窗 **/
	OPEN("OPEN", "另開視窗");

	private String value;
	private String label;

	public static LinkTypeEnum getLinkTypeEnum(String value) {
		for (LinkTypeEnum aEnum : values()) {
			if (StringUtils.equalsIgnoreCase(aEnum.getValue(), value)) {
				return aEnum;
			}
		}
		return null;
	}

	private LinkTypeEnum(String value, String label) {
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
