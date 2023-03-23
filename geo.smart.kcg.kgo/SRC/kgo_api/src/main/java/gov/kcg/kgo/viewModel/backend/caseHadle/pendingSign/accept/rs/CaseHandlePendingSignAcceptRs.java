package gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.accept.rs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待簽收匣-案件簽收 rs
 */
@ApiModel(description = "後台案件處理-待簽收匣-案件簽收 rs")
public class CaseHandlePendingSignAcceptRs {

	@ApiModelProperty(value = "待簽收匣-簽收-ProcessId")
	private String processId;

	@ApiModelProperty(value = "待簽收匣-簽收-CaseId")
	private String caseId;

	@ApiModelProperty(value = "待簽收匣-簽收-taskId")
	private String taskId;

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

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
