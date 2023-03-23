package gov.kcg.kgo.geomodel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 	 後台-線上預約臨櫃-編輯：取得該預約對應表單-取得該預約對應表單群組資料查詢集合
 */
@ApiModel(description = "後台-線上預約臨櫃-編輯-取得該預約對應表單群組資料查詢集合")
@Entity
public class GeoAppointmentGroupQueryDataMaxVersionDto {

	@Id
	@ApiModelProperty(notes = "群組序號")
	@Column(name = "GROUP_SEQ")
	private Integer groupSeq;

	@ApiModelProperty(notes = "區塊說明")
	@Column(name = "MEMO")
	private String memo;

	@ApiModelProperty(notes = "版號")
	@Column(name = "VERSION")
	private Integer version;

	@ApiModelProperty(notes = "顯示順序")
	@Column(name = "ORDER_NUM")
	private Integer orderNum;

	@ApiModelProperty(notes = "是否顯示")
	@Column(name = "IS_SHOW")
	private Integer isShow;

	@ApiModelProperty(notes = "重複檢核時間")
	@Column(name = "CHECK_FREQUENCY_PERIOD")
	private String checkFrequencyPeriod;

	public Integer getGroupSeq() {
		return groupSeq;
	}

	public void setGroupSeq(Integer groupSeq) {
		this.groupSeq = groupSeq;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getCheckFrequencyPeriod() {
		return checkFrequencyPeriod;
	}

	public void setCheckFrequencyPeriod(String checkFrequencyPeriod) {
		this.checkFrequencyPeriod = checkFrequencyPeriod;
	}
}
