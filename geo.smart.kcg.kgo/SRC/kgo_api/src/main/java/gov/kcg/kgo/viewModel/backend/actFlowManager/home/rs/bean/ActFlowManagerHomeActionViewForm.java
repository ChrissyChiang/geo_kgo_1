package gov.kcg.kgo.viewModel.backend.actFlowManager.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;

/**
 * 後台 -動態流程管理 - 畫面初始 ViewForm
 */
@ApiModel(description = "動態流程管理 - 畫面初始 - 畫面初始  ViewForm")
public class ActFlowManagerHomeActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	private List<ActFlowManagerHomeActionDataGrid> grid;

	public List<ActFlowManagerHomeActionDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<ActFlowManagerHomeActionDataGrid> grid) {
		this.grid = grid;
	}
}
