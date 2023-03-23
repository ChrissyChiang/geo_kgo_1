package gov.kcg.kgo.viewModel.backend.serviceApply.query.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務申請-申請案件查詢 View Form
 */
@ApiModel(description = "服務申請-申請案件查詢 View Form")
public class ServiceApplyQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 申請案件查詢結果清單 **/
	@ApiModelProperty(value = "申請案件查詢結果清單")
	private List<ServiceApplyQueryDataGrid> grid;

	public List<ServiceApplyQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<ServiceApplyQueryDataGrid> grid) {
		this.grid = grid;
	}

}
