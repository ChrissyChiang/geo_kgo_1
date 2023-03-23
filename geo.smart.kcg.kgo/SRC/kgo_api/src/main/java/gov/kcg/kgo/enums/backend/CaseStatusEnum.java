package gov.kcg.kgo.enums.backend;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 * Activiti task node
 * 
 *
 */
public enum CaseStatusEnum {

	/** 起流程 START*/
	START("START", "起流程"),
	/** 分文 DISPATCH*/
	DP("DP", "分文"),
	/** 承辦 CASE_HANDLING*/
	CH("CH", "承辦"),
	/** 補正 CORRECTION*/
	CO("CO", "補正"),
	/** 簽收 SIGN*/
	SIGN("SIGN", "簽收"),
	/** 取消簽收 CANCELCANCEL*/
	CSIGN("CSIGN", "取消簽收"),
	/** 結案 COMPLETE*/
	COMPLETE("COMPLETE", "結案"),
	
	
	/** 1  會簽 :1(會簽) */
	TASK_TYPE_1("1", "會簽"),
	
	/** 2 一般類型: D(分文)、A(陳核)、F(結案) **/
	TASK_TYPE_2("2","一般"),

	/** 退回上一關 */
	RETURN("RETURN", "退回上一關"),
	
	RESEND_FLOW("RESEND_FLOW", "修改重送流程"),
	
	// 未知
	UNKNOW("UNKNOW","UNKNOW");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;
	
	/**
	 * 取得節點名稱類型
	 */
	public static CaseStatusEnum getApplyTypeEnum(String formType) {
		for (CaseStatusEnum type : values()) {
			if (StringUtils.equals(type.getValue(), formType)) {
				return type;
			}
		}
		return UNKNOW;
	}

	private CaseStatusEnum(String value, String label) {
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
