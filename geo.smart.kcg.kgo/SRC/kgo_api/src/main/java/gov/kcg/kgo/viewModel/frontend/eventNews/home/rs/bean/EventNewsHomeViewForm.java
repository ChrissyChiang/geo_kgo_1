package gov.kcg.kgo.viewModel.frontend.eventNews.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 活動消息-初始畫面 View Form
 */
@ApiModel(description = "活動消息-初始畫面 View Form")
public class EventNewsHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 已上架活動清單 **/
	@ApiModelProperty(value = "已上架活動清單")
	private List<EventNewsHomeDataGrid> grid;

	public List<EventNewsHomeDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<EventNewsHomeDataGrid> grid) {
		this.grid = grid;
	}

}
