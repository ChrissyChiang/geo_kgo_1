package gov.kcg.kgo.viewModel.backend.caseHadle.reviewed.query.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-已審核匣-案件查詢 View Form
 */
@ApiModel(description = "後台案件處理-已審核匣-案件查詢 View Form")
public class CaseHandleReviewedQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 已審核匣-案件清單 **/
	@ApiModelProperty(value = "已審核匣-案件清單")
	private List<CaseHandleCaseQueryDataGrid> grid;

	public List<CaseHandleCaseQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<CaseHandleCaseQueryDataGrid> grid) {
		this.grid = grid;
	}

}
