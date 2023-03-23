package gov.kcg.kgo.viewModel.backend.internetApply.description.save.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-網路申請說明-儲存 View Form
 */
@ApiModel(description = "網路申辦-網路申請說明-儲存 View Form")
public class InternetApplyDescriptionSaveViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 執行結果訊息 **/
	@ApiModelProperty(value = "執行結果訊息")
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
