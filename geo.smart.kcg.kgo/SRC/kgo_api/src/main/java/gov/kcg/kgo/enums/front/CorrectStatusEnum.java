package gov.kcg.kgo.enums.front;

import org.apache.commons.lang3.StringUtils;

/**
 * 補正狀態
 * 
 *
 */
public enum CorrectStatusEnum {

	/** 未通知 UNNOTIFY*/
	UNNOTIFY("UNNOTIFY", "1-未通知"),
	/** 已通知 NOTIFIED*/
	NOTIFIED("NOTIFIED", "2-已通知"),
	/** 未補正 CORRECTED*/
	CORRECTED(" CORRECTED", "3-已補正"),
	
	// 未知
	UNKNOW("UNKNOW","UNKNOW");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;
	
	/**
	 * 取得補正狀態
	 */
	public static CorrectStatusEnum getCorrectStatusEnum(String formType) {
		for (CorrectStatusEnum type : values()) {
			if (StringUtils.equals(type.getValue(), formType)) {
				return type;
			}
		}
		return UNKNOW;
	}

	private CorrectStatusEnum(String value, String label) {
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
