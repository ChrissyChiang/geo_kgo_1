package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 動態流程 - 節點名稱 enum.
 */
public enum ActTaskNodeName {

	/** 節點名稱 - 主管1. */
	M1("M1", "kgo.act.tasknode.manager1.name"),
	
	/** 節點名稱 - 主管2 **/
	M2("M2", "kgo.act.tasknode.manager2.name");

	/** 參數設定 **/
	private String nodeCode;

	/** 參數設定名稱 I18nKey **/
	private String nodeNameI18nKey;

	private ActTaskNodeName(String nodeCode, String nodeNameI18nKey) {
		this.nodeCode = nodeCode;
		this.nodeNameI18nKey = nodeNameI18nKey;
	}

	/**
	  * 取得動態流程 - 節點名稱.
	 *
	 */
	public static ActTaskNodeName getActTaskNodeName(String nodeCode) {
		for (ActTaskNodeName aEnum : values()) {
			if (StringUtils.equals(aEnum.getNodeCode(), nodeCode)) {
				return aEnum;
			}
		}
		return null;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public String getNodeNameI18nKey() {
		return nodeNameI18nKey;
	}
}
