package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 動態流程 - N:一般流程, T:暫存流程 enum.
 */
public enum ActFlowTypeEnum {

	/** 一般流程 */
	N("N"),
	
	/** 暫存流程 **/
	T("T");

	/** 流程類型 **/
	private String type;


	private ActFlowTypeEnum(String type) {
		this.type = type;
	}

	/**
	  * 取得流程類型- 一般流程、暫存.
	 *
	 */
	public static ActFlowTypeEnum getActFlowEnableEnum(String type) {
		for (ActFlowTypeEnum aEnum : values()) {
			if (StringUtils.equals(aEnum.getType(), type)) {
				return aEnum;
			}
		}
		return null;
	}

	public String getType() {
		return type;
	}
}
