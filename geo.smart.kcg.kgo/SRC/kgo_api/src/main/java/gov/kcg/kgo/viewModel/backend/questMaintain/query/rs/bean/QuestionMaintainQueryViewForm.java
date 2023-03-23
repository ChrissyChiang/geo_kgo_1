package gov.kcg.kgo.viewModel.backend.questMaintain.query.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 常見問題維護-初始畫面 View Form
 */
@ApiModel(description = "常見問題維護-初始畫面 View Form")
public class QuestionMaintainQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 系統後台公告清單 **/
	@ApiModelProperty(value = "系統後台公告清單")
	private List<QuestionMaintainQueryDataGrid> grid;

	public List<QuestionMaintainQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<QuestionMaintainQueryDataGrid> grid) {
		this.grid = grid;
	}

}
