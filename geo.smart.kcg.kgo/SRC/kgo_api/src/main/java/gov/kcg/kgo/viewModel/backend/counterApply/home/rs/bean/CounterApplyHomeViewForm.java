package gov.kcg.kgo.viewModel.backend.counterApply.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 臨櫃申請-初始畫面View Form
 */
@ApiModel(description = "臨櫃申請-初始畫面View Form")
public class CounterApplyHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 臨櫃申請清單 **/
	@ApiModelProperty(value = "臨櫃申請清單")
	private List<CounterApplyHomeDataGrid> grid;

	public List<CounterApplyHomeDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<CounterApplyHomeDataGrid> grid) {
		this.grid = grid;
	}

}
