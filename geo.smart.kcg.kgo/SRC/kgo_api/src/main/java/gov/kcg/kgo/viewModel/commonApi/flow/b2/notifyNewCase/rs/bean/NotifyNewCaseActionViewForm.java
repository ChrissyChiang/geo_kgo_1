package gov.kcg.kgo.viewModel.commonApi.flow.b2.notifyNewCase.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;

/**
 * 通知既有案件服務有新進案件，B-2流程_1 Form
 */
@ApiModel(description = "通知既有案件服務有新進案件，B-2流程_1 ViewForm")
public class NotifyNewCaseActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 案件編號. */
	private String caseId;
	
	/** 接收結果（成功/失敗） */
	private String result;


	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
