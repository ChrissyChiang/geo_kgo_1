package gov.kcg.kgo.viewModel.backend.paramSet.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;

/**
 * 參數設定 - 畫面初始 ViewForm
 */
@ApiModel(description = "參數設定 - 畫面初始  ViewForm")
public class ParamSetHomeActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	List<ParamSetGrid> grid;

	public List<ParamSetGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<ParamSetGrid> grid) {
		this.grid = grid;
	}
}
