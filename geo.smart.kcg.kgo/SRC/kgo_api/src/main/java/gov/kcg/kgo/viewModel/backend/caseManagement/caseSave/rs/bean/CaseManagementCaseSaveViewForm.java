package gov.kcg.kgo.viewModel.backend.caseManagement.caseSave.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務案件清單-案件維護-儲存 View Form
 */
@ApiModel(description = "服務案件清單-案件維護-儲存 View Form")
public class CaseManagementCaseSaveViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 服務案件編號 **/
	@ApiModelProperty(value = "服務案件編號")
	private String caseSetId;

	/** 執行結果訊息 **/
	@ApiModelProperty(value = "執行結果訊息")
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

}
