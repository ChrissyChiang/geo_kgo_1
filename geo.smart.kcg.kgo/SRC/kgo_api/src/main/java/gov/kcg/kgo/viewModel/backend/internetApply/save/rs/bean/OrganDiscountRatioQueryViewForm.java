package gov.kcg.kgo.viewModel.backend.internetApply.save.rs.bean;

import java.util.List;

import gov.kcg.kgo.geomodel.OrganDiscountRatioModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 機關優惠折扣查詢View Form
 */
@ApiModel(description = "機關優惠折扣查詢View Form")
public class OrganDiscountRatioQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "儲存執行結果")
	private String msg;
	
	@ApiModelProperty(value = "機關優惠折扣查詢")
	private List<OrganDiscountRatioModel> grid;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<OrganDiscountRatioModel> getGrid() {
		return grid;
	}

	public void setGrid(List<OrganDiscountRatioModel> grid) {
		this.grid = grid;
	}


}
