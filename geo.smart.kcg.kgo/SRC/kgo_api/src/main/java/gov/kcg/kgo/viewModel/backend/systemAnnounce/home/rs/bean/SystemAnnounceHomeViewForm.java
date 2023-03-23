package gov.kcg.kgo.viewModel.backend.systemAnnounce.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系統公告-初始畫面 View Form
 */
@ApiModel(description = "系統公告-初始畫面 View Form")
public class SystemAnnounceHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 系統後台公告清單 **/
	@ApiModelProperty(value = "系統後台公告清單")
	private List<SystemAnnounceHomeDataGrid> grid;

	public List<SystemAnnounceHomeDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<SystemAnnounceHomeDataGrid> grid) {
		this.grid = grid;
	}

}
