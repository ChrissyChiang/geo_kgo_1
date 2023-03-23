package gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211109 add 機關審核表單
 * 網路申辦-表單設定-機關審核表單欄位群組維護-進版儲存-群組欄位資料
 */
@ApiModel(description = "網路申辦-表單設定-機關審核表單欄位群組維護-進版儲存-群組欄位資料")
public class OrganGroupColumnData {

	@ApiModelProperty(notes = "區塊說明")
	private String groupName;

	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	@ApiModelProperty(notes = "欄位設定集合")
	private List<CasesetOrganColumnData> columnData;

	@ApiModelProperty(notes = "是否顯示")
	private Integer isShow;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<CasesetOrganColumnData> getColumnData() {
		return columnData;
	}

	public void setColumnData(List<CasesetOrganColumnData> columnData) {
		this.columnData = columnData;
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
