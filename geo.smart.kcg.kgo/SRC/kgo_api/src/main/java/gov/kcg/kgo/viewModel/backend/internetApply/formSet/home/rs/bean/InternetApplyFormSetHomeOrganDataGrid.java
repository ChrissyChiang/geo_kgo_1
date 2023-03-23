package gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211109 add 機關審核表單
 * 網路申辦-表單設定-初始畫面-機關審核表單群組所有欄位設定資料集合
 */
@ApiModel(description = "網路申辦-表單設定-初始畫面-機關審核表單群組所有欄位設定資料集合")
public class InternetApplyFormSetHomeOrganDataGrid {

	/** 群組序號 */
	@ApiModelProperty(notes = "群組序號")
	private Integer groupSeq;

	/** 群組名稱 */
	@ApiModelProperty(notes = "群組名稱")
	private String groupName;

	/** 顯示順序 */
	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	@ApiModelProperty(notes = "是否顯示")
	private Integer isShow;

	/** 表單群組欄位資料 */
	@ApiModelProperty(notes = "表單群組欄位資料")
	private List<InternetApplyFormSetOrganGroupColumnDataGrid> columnData;

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

	public List<InternetApplyFormSetOrganGroupColumnDataGrid> getColumnData() {
		return columnData;
	}

	public void setColumnData(List<InternetApplyFormSetOrganGroupColumnDataGrid> columnData) {
		this.columnData = columnData;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
}
