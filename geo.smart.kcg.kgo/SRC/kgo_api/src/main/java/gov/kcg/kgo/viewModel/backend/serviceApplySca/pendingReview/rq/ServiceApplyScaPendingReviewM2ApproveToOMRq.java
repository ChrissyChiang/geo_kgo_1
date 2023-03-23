package gov.kcg.kgo.viewModel.backend.serviceApplySca.pendingReview.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-待審核匣-主管二同意到機關管理者 rq")
public class ServiceApplyScaPendingReviewM2ApproveToOMRq extends ApiRequest {
    @ApiModelProperty(value = "案件階段編號")
    private String taskId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
