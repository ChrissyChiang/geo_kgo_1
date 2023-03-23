package gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.save.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務申請-權限開通申請-儲存 View Form
 */
@ApiModel(description = "服務申請-權限開通申請-儲存 View Form")
public class ServiceApplyPermissionActiveSaveViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "執行結果訊息")
	private String msg;

	@ApiModelProperty(value = "案件編號")
	private String caseId;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

}
