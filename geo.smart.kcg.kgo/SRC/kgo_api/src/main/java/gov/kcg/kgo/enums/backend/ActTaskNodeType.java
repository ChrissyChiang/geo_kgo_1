package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 動態流程 - 節點類型 enum.
 *
 */
public enum ActTaskNodeType {
	
	/** 普通. */
	COM("COM", "kgo.act.tasknode.type.com"),
	
	/** 會簽 **/
	COU("COU", "kgo.act.tasknode.type.cou");

	/** 節點名稱 **/
	private String nodeType;

	/** 節點類型 I18n **/
	private String nodeTypeNameI18n;

	private ActTaskNodeType(String nodeType, String nodeTypeNameI18n) {
		this.nodeType = nodeType;
		this.nodeTypeNameI18n = nodeTypeNameI18n;
	}

	/**
	 * 取得動態流程 - 節點類型.
	 *
	 */
	public static ActTaskNodeType getActTaskNodeType(String value) {
		for (ActTaskNodeType aEnum : values()) {
			if (StringUtils.equals(aEnum.getNodeType(), value)) {
				return aEnum;
			}
		}
		return null;
	}

	public String getNodeType() {
		return nodeType;
	}

	public String getNodeTypeNameI18n() {
		return nodeTypeNameI18n;
	}
}
