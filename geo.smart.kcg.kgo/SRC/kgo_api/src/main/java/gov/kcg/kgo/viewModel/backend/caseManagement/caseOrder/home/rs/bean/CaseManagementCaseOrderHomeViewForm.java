package gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務案件清單-案件排序更改 View Form
 */
@ApiModel(description = "服務案件清單-案件排序更改 View Form")
public class CaseManagementCaseOrderHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 案件搜尋結果清單 **/
	@ApiModelProperty(value = "列出符合條件的案件資料")
	private List<CaseManagementCaseOrderDataGrid> grid;

	public List<CaseManagementCaseOrderDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<CaseManagementCaseOrderDataGrid> grid) {
		this.grid = grid;
	}

}
