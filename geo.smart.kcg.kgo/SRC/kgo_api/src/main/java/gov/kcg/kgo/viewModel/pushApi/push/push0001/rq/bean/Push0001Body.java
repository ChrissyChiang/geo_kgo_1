package gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.bean;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class Push0001Body {

	/**
	 * 系統代碼
	 */
	@ApiModelProperty(value = "系統代碼")
	private String sysUID;
	/**
	 * 發送時間類型
	 */
	@ApiModelProperty(value = "發送時間類型")
	private String sendTimeType;
	/**
	 * 預計發送推播的日期(YYYYMMDD)
	 */
	@ApiModelProperty(value = "預計發送推播的日期(YYYYMMDD)")
	private String sendDate;
	/**
	 * 預計發送推播的時間(HHMM)
	 */
	@ApiModelProperty(value = "預計發送推播的時間(HHMM)")
	private String sendTime;
	/**
	 * 推播名稱
	 */
	@ApiModelProperty(value = "推播名稱")
	private String pushName;
	/**
	 * 推播說明
	 */
	@ApiModelProperty(value = "推播說明")
	private String pushDesc;
	/**
	 * 名單資料列
	 */
	@ApiModelProperty(value = "名單資料列")
	private List<KGData> dataList;

	/**
	 * init with prarmeter value
	 * 
	 * @param sysUID
	 * @param sendTimeType
	 * @param sendDate
	 * @param sendTime
	 * @param pushName
	 * @param pushDesc
	 * @param dataList
	 */
	public Push0001Body(String sysUID, String sendTimeType, String sendDate, String sendTime, String pushName,
			String pushDesc, List<KGData> dataList) {
		this.sysUID = sysUID;
		this.sendTimeType = sendTimeType;
		this.sendDate = sendDate;
		this.sendTime = sendTime;
		this.pushName = pushName;
		this.pushDesc = pushDesc;
		this.dataList = dataList;
	}

	public String getSysUID() {
		return sysUID;
	}

	public void setSysUID(String sysUID) {
		this.sysUID = sysUID;
	}

	public String getSendTimeType() {
		return sendTimeType;
	}

	public void setSendTimeType(String sendTimeType) {
		this.sendTimeType = sendTimeType;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getPushName() {
		return pushName;
	}

	public void setPushName(String pushName) {
		this.pushName = pushName;
	}

	public String getPushDesc() {
		return pushDesc;
	}

	public void setPushDesc(String pushDesc) {
		this.pushDesc = pushDesc;
	}

	public List<KGData> getDataList() {
		return dataList;
	}

	public void setDataList(List<KGData> dataList) {
		this.dataList = dataList;
	}

}
