package gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 申辦案件清單-初始畫面 View Form
 */
@ApiModel(description = "申辦案件清單-初始畫面 View Form")
public class BidCaseListHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 被選類別標題 **/
	@ApiModelProperty(value = "被選類別標題")
	private String titleName;

	/** 申辦案件資料集合 **/
	@ApiModelProperty(value = "申辦案件資料集合")
	private List<BidCaseListQueryDataGrid> dataGrid;

	/** 申辦案件頁籤顯示集合 **/
	@ApiModelProperty(value = "申辦案件頁籤顯示集合")
	private List<BidCaseListTypeDataGrid> typeGrid;

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public List<BidCaseListQueryDataGrid> getDataGrid() {
		return dataGrid;
	}

	public void setDataGrid(List<BidCaseListQueryDataGrid> dataGrid) {
		this.dataGrid = dataGrid;
	}

	public List<BidCaseListTypeDataGrid> getTypeGrid() {
		return typeGrid;
	}

	public void setTypeGrid(List<BidCaseListTypeDataGrid> typeGrid) {
		this.typeGrid = typeGrid;
	}

}
