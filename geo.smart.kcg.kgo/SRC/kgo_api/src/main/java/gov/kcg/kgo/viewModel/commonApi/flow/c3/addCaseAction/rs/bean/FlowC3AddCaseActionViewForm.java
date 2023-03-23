package gov.kcg.kgo.viewModel.commonApi.flow.c3.addCaseAction.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 提供平台案件處理區呼叫使用，C-3流程 新增案件 Form
 */
@ApiModel(description = "提供平台案件處理區呼叫使用，C-3流程 新增案件 ViewForm")
public class FlowC3AddCaseActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "案件編號")
	private String caseId;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	
	
}
