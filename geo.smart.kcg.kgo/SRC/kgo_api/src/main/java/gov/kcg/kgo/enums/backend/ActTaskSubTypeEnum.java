package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 會簽任務節點: 1(會簽)
 * 一般任務節點:子類別 D(分文)、A(陳核)、F(結案) enum.
 */
public enum ActTaskSubTypeEnum {
	/** 會簽任務節點 */
	/** 會簽 */
	TYPE1("1", "會簽", ActTaskTypeEnum.TASK_TYPE_1),
	
	
	/** 一般任務節點 */
	/** D(分文) */
	D("D", "分文", ActTaskTypeEnum.TASK_TYPE_2),
	
	/** A(陳核) */
	A("A", "陳核", ActTaskTypeEnum.TASK_TYPE_2),
	
	/** F(結案) */
	F("F", "結案", ActTaskTypeEnum.TASK_TYPE_2);

	/** 一般類型 子類別類型 **/
	private String subType;
	
	/** 子類別類型名稱  */
	private String subTypeName;
	
	/** 任務節點類型 - 1:會簽, 2:一般類型(子任務節點類型) */
	private ActTaskTypeEnum taskType;


	private ActTaskSubTypeEnum(String subType, String subTypeName, ActTaskTypeEnum taskType) {
		this.subType = subType;
		this.subTypeName = subTypeName;
		this.taskType = taskType;
	}

	/**
	  * 取得 一般類型: D(分文)、A(陳核)、F(結案)
	 *
	 */
	public static ActTaskSubTypeEnum getActTaskSubType(String subType) {
		for (ActTaskSubTypeEnum aEnum : values()) {
			if (StringUtils.equals(aEnum.getSubType(), subType)) {
				return aEnum;
			}
		}
		return null;
	}

	public String getSubType() {
		return subType;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public ActTaskTypeEnum getTaskType() {
		return taskType;
	}
}
