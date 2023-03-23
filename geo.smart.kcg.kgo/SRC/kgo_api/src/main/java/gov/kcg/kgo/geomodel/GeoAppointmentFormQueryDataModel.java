package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台-線上預約臨櫃-編輯：取得該預約對應表單-群組所有欄位設定資料集合
 */
@ApiModel(description = "後台-線上預約臨櫃-編輯：取得該預約對應表單-群組所有欄位設定資料集合")
public class GeoAppointmentFormQueryDataModel {

	@ApiModelProperty(notes = "群組序號")
	private Integer groupSeq;

	@ApiModelProperty(notes = "群組名稱")
	private String groupName;

	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	@ApiModelProperty(notes = "重複檢核時間")
	private String CheckFrequencyPeriod;

	@ApiModelProperty(notes = "表單群組欄位資料")
	private List<GeoAppointmentFormQueryGroupColumnDataModel> columnData;

	public Integer getGroupSeq() {
		return groupSeq;
	}

	public void setGroupSeq(Integer groupSeq) {
		this.groupSeq = groupSeq;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public List<GeoAppointmentFormQueryGroupColumnDataModel> getColumnData() {
		return columnData;
	}

	public void setColumnData(List<GeoAppointmentFormQueryGroupColumnDataModel> columnData) {
		this.columnData = columnData;
	}

	public String getCheckFrequencyPeriod() {
		return CheckFrequencyPeriod;
	}

	public void setCheckFrequencyPeriod(String checkFrequencyPeriod) {
		CheckFrequencyPeriod = checkFrequencyPeriod;
	}
}
