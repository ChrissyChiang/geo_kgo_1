package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-案件檢視-服務申辦(SA)案件檢視-初始畫面 rq")
public class CaseHandleCaseViewSaCaseHomeRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件編號")
	private String caseId;
	
	
	@ApiModelProperty(value = "節點編號")
	private String taskId;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
}
