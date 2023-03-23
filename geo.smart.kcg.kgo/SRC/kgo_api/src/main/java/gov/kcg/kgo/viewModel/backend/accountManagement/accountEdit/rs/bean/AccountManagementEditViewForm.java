package gov.kcg.kgo.viewModel.backend.accountManagement.accountEdit.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 帳號權限管理-帳號維護(新增/修改) View Form
 */
@ApiModel(description = "帳號權限管理-帳號維護(新增/修改) View Form")
public class AccountManagementEditViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "執行結果訊息")
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
