package gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.save.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.save.rq.bean.CaseManagementCaseOrderSaveDataGrid;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "服務案件清單-案件排序-修改 rq")
public class CaseManagementCaseOrderSaveRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "順序修改資料清單")
	private List<CaseManagementCaseOrderSaveDataGrid> grid;

	public List<CaseManagementCaseOrderSaveDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<CaseManagementCaseOrderSaveDataGrid> grid) {
		this.grid = grid;
	}

}
