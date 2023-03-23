package gov.kcg.kgo.viewModel.backend.caseManagement.delete.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務案件清單-案件刪除 View Form
 */
@ApiModel(description = "服務案件清單-案件刪除 View Form")
public class CaseManagementDeleteViewForm extends BaseViewForm {

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
