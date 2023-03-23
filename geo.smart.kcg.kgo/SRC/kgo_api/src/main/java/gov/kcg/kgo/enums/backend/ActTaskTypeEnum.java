package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 任務節點類型 - 1:會簽, 2:一般類型(子任務節點類型) enum.
 */
public enum ActTaskTypeEnum {

	/** GEO 20210831 add --> 會在 ACT_RU_TASK 的 NAME_ 欄位值的字首看到 **/

	/** 會簽 :1(會簽) */
	TASK_TYPE_1("1", new ActTaskSubTypeEnum[] {ActTaskSubTypeEnum.TYPE1}, "會簽"),
	
	/** 一般類型: D(分文)、A(陳核)、F(結案) **/
	TASK_TYPE_2("2", new ActTaskSubTypeEnum[] {ActTaskSubTypeEnum.D, ActTaskSubTypeEnum.A, ActTaskSubTypeEnum.F}, "一般類型");

	/**任務節點類型 **/
	private String type;
	
	/** 子任務節點類型 **/
	private ActTaskSubTypeEnum[] subTypes;
	
	/** 任務節點名稱 **/
	private String typeName;

	private ActTaskTypeEnum() {}
	
	private ActTaskTypeEnum(String type, ActTaskSubTypeEnum[] subTypes, String typeName) {
		this.type = type;
		this.subTypes = subTypes;
		this.typeName = typeName;
	}

	/**
	  * 取得流程類型- 會簽、 一般類型.
	 *
	 */
	public static ActTaskTypeEnum getActTaskType(String type) {
		for (ActTaskTypeEnum aEnum : values()) {
			if (StringUtils.equals(aEnum.getType(), type)) {
				return aEnum;
			}
		}
		return null;
	}

	public String getType() {
		return type;
	}
	
	public static ActTaskSubTypeEnum[] getSubTypes(ActTaskTypeEnum type) {
		ActTaskSubTypeEnum[] subTypesByType = null;
		if (ActTaskTypeEnum.TASK_TYPE_1.equals(type)) {
			subTypesByType = new ActTaskSubTypeEnum[] {ActTaskSubTypeEnum.TYPE1};
		}
		
		if (ActTaskTypeEnum.TASK_TYPE_2.equals(type)) {
			subTypesByType = new ActTaskSubTypeEnum[] {ActTaskSubTypeEnum.D, ActTaskSubTypeEnum.A, ActTaskSubTypeEnum.F};
		}
		return subTypesByType;
	}

	public ActTaskSubTypeEnum[] getSubTypes() {
		return subTypes;
	}

	public String getTypeName() {
		return typeName;
	}
}
