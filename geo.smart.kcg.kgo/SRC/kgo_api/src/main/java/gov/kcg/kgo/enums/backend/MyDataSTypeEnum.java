package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * for Mydata管理集
 * 
 * @author TPIuser
 *
 */
public enum MyDataSTypeEnum {

	/** 高市府 */
	INSIDE("I", "高市府"),
	/** 國發會 */
	OUTSIDE("O", "國發會");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private MyDataSTypeEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public static MyDataSTypeEnum getMyDataSTypeEnum(String value) {
		for (MyDataSTypeEnum aEnum : values()) {
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
