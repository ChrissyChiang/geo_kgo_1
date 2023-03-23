package gov.kcg.kgo.viewModel.backend.organUnitManagement.query.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分類維護功能-主畫面搜尋 View Form
 */
@ApiModel(description = "機關科室管理-機關科室查詢 View Form")
public class OrganUnitManagementQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 機關科室搜尋結果清單 **/
	@ApiModelProperty(value = "機關科室搜尋結果清單")
	private List<OrganUnitManagementQueryDataGrid> grid;

	public List<OrganUnitManagementQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<OrganUnitManagementQueryDataGrid> grid) {
		this.grid = grid;
	}

}
