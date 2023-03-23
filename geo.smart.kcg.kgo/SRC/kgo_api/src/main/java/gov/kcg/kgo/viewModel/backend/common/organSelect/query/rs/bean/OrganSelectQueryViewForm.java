package gov.kcg.kgo.viewModel.backend.common.organSelect.query.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 共用-機關列表Popup-查詢 View Form
 */
@ApiModel(description = "共用-機關列表Popup-查詢 View Form")
public class OrganSelectQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 機關清單 **/
	@ApiModelProperty(value = "機關清單")
	private List<OrganSelectQueryDataGrid> grid;

	public List<OrganSelectQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<OrganSelectQueryDataGrid> grid) {
		this.grid = grid;
	}

}
