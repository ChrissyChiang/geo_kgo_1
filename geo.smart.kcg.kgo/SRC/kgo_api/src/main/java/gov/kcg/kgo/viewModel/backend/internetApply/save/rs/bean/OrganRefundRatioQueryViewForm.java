package gov.kcg.kgo.viewModel.backend.internetApply.save.rs.bean;

import java.util.List;

import gov.kcg.kgo.model.OrganRefundRatio;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 機關退費比率查詢View Form
 */
@ApiModel(description = "機關退費比率查詢View Form")
public class OrganRefundRatioQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "儲存執行結果")
	private String msg;
	
	@ApiModelProperty(value = "機關退費比率查詢")
	private List<OrganRefundRatio> grid;	

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<OrganRefundRatio> getGrid() {
		return grid;
	}

	public void setGrid(List<OrganRefundRatio> grid) {
		this.grid = grid;
	}


}
