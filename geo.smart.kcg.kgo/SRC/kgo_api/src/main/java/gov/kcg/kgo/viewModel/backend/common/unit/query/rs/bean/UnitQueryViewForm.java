package gov.kcg.kgo.viewModel.backend.common.unit.query.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 共用-機關科室查詢 View Form
 */
@ApiModel(description = "共用-機關科室查詢 View Form")
public class UnitQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "科室名稱清單")
	private List<UnitQueryDataGrid> grid;

	public List<UnitQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<UnitQueryDataGrid> grid) {
		this.grid = grid;
	}

}
