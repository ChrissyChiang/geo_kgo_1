package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author TPIuser
 *
 */
public enum ApplyCaseStatusEnum {

	R("R", "機關管理者審核"), D("D", "主管2審核"), W("W", "待處理"), P("P", "處理中(機關管理者)"), F("F", "結案"), J("J", "結案不通過");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private ApplyCaseStatusEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}
	
	/**
	 * 取得對應狀態類別.
	 *
	 */
	public static ApplyCaseStatusEnum getApplyCaseStatusEnum(String value) {
		for (ApplyCaseStatusEnum aEnum : values()) {
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
