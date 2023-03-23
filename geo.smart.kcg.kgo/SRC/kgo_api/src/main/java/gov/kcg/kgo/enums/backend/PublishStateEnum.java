package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 案件狀態發佈集合
 * 
 * On = 上架，Off = 下架，Accept=受理中
 *
 */
public enum PublishStateEnum {

	/** 上架 **/
	ON("On", "上架"),
	/** 下架 **/
	OFF("Off", "下架"),
	/** 受理中 **/
	ACCEPT("Accept", "受理中");

	/** 上下架代碼 */
	private String value;

	/** 上下架實體值 */
	private String label;

	private PublishStateEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	/**
	 * 取得對應狀態類別.
	 *
	 */
	public static PublishStateEnum getPublishStateEnum(String value) {
		for (PublishStateEnum aEnum : values()) {
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
