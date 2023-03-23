package gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.query.rs.bean;

import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台案件處理-案件異動-案件查詢 View Form
 */
@ApiModel(description = "後台案件處理-案件異動-案件查詢 View Form")
public class CaseHandleCaseUpdateQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 案件異動-案件清單 **/
	@ApiModelProperty(value = "案件異動-案件清單")
	private List<CaseHandleCaseQueryDataGrid> grid;

	public List<CaseHandleCaseQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<CaseHandleCaseQueryDataGrid> grid) {
		this.grid = grid;
	}

}
