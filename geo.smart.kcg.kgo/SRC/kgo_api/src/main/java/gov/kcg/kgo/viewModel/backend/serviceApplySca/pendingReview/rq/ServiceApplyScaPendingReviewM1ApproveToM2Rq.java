package gov.kcg.kgo.viewModel.backend.serviceApplySca.pendingReview.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-待審核匣-主管一同意到主管二 rq")
public class ServiceApplyScaPendingReviewM1ApproveToM2Rq extends ApiRequest {

    @ApiModelProperty(value = "案件階段編號")
    private String taskId;

    @ApiModelProperty(value = "主管二")
    private String manager;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
