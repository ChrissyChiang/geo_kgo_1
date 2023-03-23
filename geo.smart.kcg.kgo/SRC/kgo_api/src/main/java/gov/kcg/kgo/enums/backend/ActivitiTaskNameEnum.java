package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * Activiti task node
 * 
 *
 */
public enum ActivitiTaskNameEnum {

	/** 分文群組 DISPATCH_GROUP*/
	DG("DG", "分文群組"),
	/** 承辦群組 CASE_HANDLING_GROUP*/
	CHG("CHG", "承辦群組"),
	/** 承辦人員 CASE_OFFICER*/
	CF("CF", "承辦人員"),
	/** 民眾補正 CORRECTION*/
	CO("CO", "民眾補正"),
	/** 主管一 MANAGER1*/
	M1("M1", "主管一"),
	/** 主管二 MANAGER2*/
	M2("M2", "主管二"),
	/** 機關管理者 UNIT MANAGER*/
	UNIT_M("UNIT_M", "機關管理者"),
	
	// 未知
	UNKNOW("UNKNOW","UNKNOW");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;
	
	/**
	 * 取得節點名稱類型
	 */
	public static ActivitiTaskNameEnum getApplyTypeEnum(String formType) {
		for (ActivitiTaskNameEnum type : values()) {
			if (StringUtils.equals(type.getValue(), formType)) {
				return type;
			}
		}
		return UNKNOW;
	}

	private ActivitiTaskNameEnum(String value, String label) {
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
