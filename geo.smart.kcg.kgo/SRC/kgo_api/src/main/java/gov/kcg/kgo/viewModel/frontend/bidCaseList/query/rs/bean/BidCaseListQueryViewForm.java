package gov.kcg.kgo.viewModel.frontend.bidCaseList.query.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rs.bean.BidCaseListQueryDataGrid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 申辦案件清單-申辦案件資料查詢 View Form
 */
@ApiModel(description = "申辦案件清單-申辦案件資料查詢 View Form")
public class BidCaseListQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 申辦案件資料查詢集合 **/
	@ApiModelProperty(value = "申辦案件資料查詢集合")
	private List<BidCaseListQueryDataGrid> grid;

	public List<BidCaseListQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<BidCaseListQueryDataGrid> grid) {
		this.grid = grid;
	}

}
