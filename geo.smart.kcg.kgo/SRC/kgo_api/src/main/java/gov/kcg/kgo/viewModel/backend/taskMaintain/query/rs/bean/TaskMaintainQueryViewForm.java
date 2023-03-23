package gov.kcg.kgo.viewModel.backend.taskMaintain.query.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任務及公告管理-任務以及公告查詢 View Form
 */
@ApiModel(description = "任務及公告管理-任務以及公告查詢 View Form")
public class TaskMaintainQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 任務搜尋結果清單 **/
	@ApiModelProperty(value = "任務搜尋結果清單")
	private List<TaskMaintainQueryDataGrid> grid;

	public List<TaskMaintainQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<TaskMaintainQueryDataGrid> grid) {
		this.grid = grid;
	}

}
