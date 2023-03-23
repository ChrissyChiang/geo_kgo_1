package gov.kcg.kgo.viewModel.backend.internetApply.save.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 機關退費比率儲存View Form
 */
@ApiModel(description = "機關折扣比率儲存View Form")
public class OrganDiscountRatioSaveViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 儲存執行結果 **/
	@ApiModelProperty(value = "儲存執行結果")
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
