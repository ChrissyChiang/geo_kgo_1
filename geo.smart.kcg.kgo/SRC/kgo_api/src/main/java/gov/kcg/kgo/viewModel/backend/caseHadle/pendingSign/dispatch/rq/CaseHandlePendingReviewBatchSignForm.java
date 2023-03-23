package gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.dispatch.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-待簽收匣-批次簽收-資料")
public class CaseHandlePendingReviewBatchSignForm extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "案件階段編號")
    private String taskId;

    @ApiModelProperty(value = "案件編號")
    private String caseId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}
