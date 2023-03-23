package gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rs.bean;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務申請-權限開通申請-額外標題欄位集合
 */
@ApiModel(description = "服務申請-權限開通申請-額外標題欄位集合")
public class ServiceApplyCasesetGroupDataGrid {

	/** 群組標題 */
	@ApiModelProperty(notes = "群組標題")
	private String groupName;

	/** 群組代碼 */
	@ApiModelProperty(notes = "群組代碼")
	private Integer groupSeq;

	/** 欄位型態 */
	@ApiModelProperty(notes = "欄位資料集合")
	private List<ServiceApplyCasesetColumnDataGrid> columnDataSet;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getGroupSeq() {
		return groupSeq;
	}

	public void setGroupSeq(Integer groupSeq) {
		this.groupSeq = groupSeq;
	}

	public List<ServiceApplyCasesetColumnDataGrid> getColumnDataSet() {
		return columnDataSet;
	}

	public void setColumnDataSet(List<ServiceApplyCasesetColumnDataGrid> columnDataSet) {
		this.columnDataSet = columnDataSet;
	}

}
