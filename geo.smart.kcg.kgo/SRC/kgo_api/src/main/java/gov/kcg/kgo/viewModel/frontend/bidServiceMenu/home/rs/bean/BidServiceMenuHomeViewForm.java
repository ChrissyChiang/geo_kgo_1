package gov.kcg.kgo.viewModel.frontend.bidServiceMenu.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.dto.BidServiceMenuQueryDto;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 申辦服務選單-初始畫面 View Form
 */
@ApiModel(description = "申辦服務選單-初始畫面 View Form")
public class BidServiceMenuHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 申辦案件數資料集合 **/
	@ApiModelProperty(value = "申辦案件數資料集合")
	private List<BidServiceMenuQueryDto> grid;

	public List<BidServiceMenuQueryDto> getGrid() {
		return grid;
	}

	public void setGrid(List<BidServiceMenuQueryDto> grid) {
		this.grid = grid;
	}

}
