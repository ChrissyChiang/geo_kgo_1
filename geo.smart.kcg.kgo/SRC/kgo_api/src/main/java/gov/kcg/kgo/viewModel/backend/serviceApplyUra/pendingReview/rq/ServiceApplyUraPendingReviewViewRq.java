package gov.kcg.kgo.viewModel.backend.serviceApplyUra.pendingReview.rq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "後台案件處理-待審核匣-ura-檢視 rq")
public class ServiceApplyUraPendingReviewViewRq {

    @ApiModelProperty(value = "案件編號")
    private String caseId;

    @ApiModelProperty(value = "案件階段編號")
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
