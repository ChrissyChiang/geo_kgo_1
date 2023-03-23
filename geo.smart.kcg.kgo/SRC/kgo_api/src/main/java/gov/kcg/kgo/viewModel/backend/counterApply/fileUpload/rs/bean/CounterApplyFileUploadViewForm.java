package gov.kcg.kgo.viewModel.backend.counterApply.fileUpload.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 臨櫃申請-附件上傳 View Form
 */
@ApiModel(description = "臨櫃申請-附件上傳 View Form")
public class CounterApplyFileUploadViewForm extends BaseViewForm {

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
