package gov.kcg.kgo.viewModel.backend.internetApply.description.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-網路申請說明-初始畫面View Form
 */
@ApiModel(description = "網路申辦-網路申請說明-初始畫面View Form")
public class InternetApplyDescriptionHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 說明申請清單 **/
	@ApiModelProperty(value = "說明申請清單")
	private List<InternetApplyDescriptionHomeDataGrid> grid;

	public List<InternetApplyDescriptionHomeDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<InternetApplyDescriptionHomeDataGrid> grid) {
		this.grid = grid;
	}

}
