package gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.dispatch.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待簽收匣-案件查詢 View Form
 */
@ApiModel(description = "後台案件處理-待簽收匣-分案 View Form")
public class CaseHandlePendingSignDispatchViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 待簽核匣-分案 **/
	@ApiModelProperty(value = "待簽收匣-分案")
	private List<CaseHandleCaseQueryDataGrid> grid;

	public List<CaseHandleCaseQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<CaseHandleCaseQueryDataGrid> grid) {
		this.grid = grid;
	}

}
