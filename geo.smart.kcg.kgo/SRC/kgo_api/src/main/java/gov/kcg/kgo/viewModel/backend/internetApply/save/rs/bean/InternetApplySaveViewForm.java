package gov.kcg.kgo.viewModel.backend.internetApply.save.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-案件儲存View Form
 */
@ApiModel(description = "網路申辦-案件儲存View Form")
public class InternetApplySaveViewForm extends BaseViewForm {

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
