package gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.bean;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class PushMsgData {

	/**
	 * 推播項目代碼
	 */
	@ApiModelProperty(value = "推播項目代碼")
	private String msgItem;
	/**
	 * 發送內容類型
	 */
	@ApiModelProperty(value = "發送內容類型")
	private String msgConType;
	/**
	 * 訊息範本代碼
	 */
	@ApiModelProperty(value = "訊息範本代碼")
	private String msgTemp;
	/**
	 * 訊息範本的參數值
	 */
	@ApiModelProperty(value = "訊息範本的參數值")
	private List<String> tempValue;
	/**
	 * 發送主旨
	 */
	@ApiModelProperty(value = "發送主旨")
	private String msgTitle;
	/**
	 * 推播文字
	 */
	@ApiModelProperty(value = "推播文字")
	private String msgBody;
	/**
	 * 推播詳細內容
	 */
	@ApiModelProperty(value = "推播詳細內容")
	private String clickDetail;

	public String getMsgItem() {
		return msgItem;
	}

	public void setMsgItem(String msgItem) {
		this.msgItem = msgItem;
	}

	public String getMsgConType() {
		return msgConType;
	}

	public void setMsgConType(String msgConType) {
		this.msgConType = msgConType;
	}

	public String getMsgTemp() {
		return msgTemp;
	}

	public void setMsgTemp(String msgTemp) {
		this.msgTemp = msgTemp;
	}

	public List<String> getTempValue() {
		return tempValue;
	}

	public void setTempValue(List<String> tempValue) {
		this.tempValue = tempValue;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getClickDetail() {
		return clickDetail;
	}

	public void setClickDetail(String clickDetail) {
		this.clickDetail = clickDetail;
	}

}
