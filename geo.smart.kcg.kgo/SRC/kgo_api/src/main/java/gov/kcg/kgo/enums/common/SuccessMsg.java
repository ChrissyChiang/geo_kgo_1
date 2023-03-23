package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 執行成功訊息集合
 */
public enum SuccessMsg {

	INSERT("新增成功"), UPDATE("更新成功"), DELETE("刪除成功"), UPLOAD("上傳成功"), SAVE("儲存成功"), ON("上架成功"), OFF("下架成功"), UNKNOW("成功");

	private String msg;

	private SuccessMsg(String msg) {
		this.msg = msg;
	}

	public static SuccessMsg getMsg(String msg) {
		for (SuccessMsg successMsg : values()) {
			if (StringUtils.equals(successMsg.getMsg(), msg)) {
				return successMsg;
			}
		}
		return UNKNOW;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
