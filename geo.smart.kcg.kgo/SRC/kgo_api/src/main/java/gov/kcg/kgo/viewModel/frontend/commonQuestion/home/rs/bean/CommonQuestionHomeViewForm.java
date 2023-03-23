package gov.kcg.kgo.viewModel.frontend.commonQuestion.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 常見問題-初始畫面 View Form
 */
@ApiModel(description = "常見問題-初始畫面 View Form")
public class CommonQuestionHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 已上架常見問題清單 **/
	@ApiModelProperty(value = "已上架常見問題清單")
	private List<CommonQuestionQueryDataGrid> grid;

	public List<CommonQuestionQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<CommonQuestionQueryDataGrid> grid) {
		this.grid = grid;
	}

}
