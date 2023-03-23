package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待審核匣-初始畫面 View Form
 */
@ApiModel(description = "後台案件處理-待審核匣-初始畫面 View Form")
public class CaseHandlePendingReviewHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 待審核匣-案件集合 **/
	@ApiModelProperty(value = "待審核匣-案件集合")
	private List<CaseHandleCaseQueryDataGrid> grid;

	public List<CaseHandleCaseQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<CaseHandleCaseQueryDataGrid> grid) {
		this.grid = grid;
	}

}
