package gov.kcg.kgo.geoviewmodel.backend.rq.data;

import gov.kcg.kgo.geoviewmodel.backend.rq.data.GeoAppointColumnData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 *後台-線上預約臨櫃-編輯：表單儲存更版
 */
@ApiModel(description = "後台-線上預約臨櫃-編輯：表單儲存更版")
public class GeoAppointGroupColumnData {

	@ApiModelProperty(notes = "區塊說明")
	private String groupName;

	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	@ApiModelProperty(notes = "是否顯示")
	private Integer isShow;

	@ApiModelProperty(notes = "欄位設定集合")
	private List<GeoAppointColumnData> columnDataList;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<GeoAppointColumnData> getColumnDataList() {
		return columnDataList;
	}

	public void setColumnDataList(List<GeoAppointColumnData> columnDataList) {
		this.columnDataList = columnDataList;
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
}
