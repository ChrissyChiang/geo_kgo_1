package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

/**
 *  是、否 enum, YesNoFlag enum.
 * */
public enum YesNoFlag {

	/** 是: Y.*/
	Y("Y", 1, "kcg.common.y"),
	
	/** 否: N*/
	N("N", 0, "kcg.common.n");
	

	private String flag;
	
	/** 0:否, 1:是 */
	private Integer bitVal;

	private String msgKey;

	private YesNoFlag(String flag, Integer bitVal, String msgKey) {
		this.flag = flag;
		this.bitVal = bitVal;
		this.msgKey = msgKey;
	}

	public static YesNoFlag getFlag(String flag) {
		for (YesNoFlag yesNoFlag : values()) {
			if (StringUtils.equals(yesNoFlag.getFlag(), flag)) {
				return yesNoFlag;
			}
		}
		return null;
	}
	
	public static YesNoFlag getFlag(Integer bitVal) {
		for (YesNoFlag yesNoFlag : values()) {
			if (yesNoFlag.getBitVal() == bitVal) {
				return yesNoFlag;
			}
		}
		return null;
	}

	public String getFlag() {
		return flag;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public Integer getBitVal() {
		return bitVal;
	}
	
}
