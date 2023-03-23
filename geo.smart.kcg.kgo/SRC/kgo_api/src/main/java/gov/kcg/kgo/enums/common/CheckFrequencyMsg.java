package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

/**
 * GEO 20211202 add
 * 執行重複檢核訊息集合
 */
public enum CheckFrequencyMsg {

	PASS("重複檢核通過"), NOT_PASS("您已申請過該項服務，請確認是否要再申請一次，謝謝");

	private String msg;

	private CheckFrequencyMsg(String msg) {
		this.msg = msg;
	}

	public static CheckFrequencyMsg getMsg(String msg) {
		for (CheckFrequencyMsg successMsg : values()) {
			if (StringUtils.equals(successMsg.getMsg(), msg)) {
				return successMsg;
			}
		}
		return NOT_PASS;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
