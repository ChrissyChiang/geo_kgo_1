package gov.kcg.kgo.viewModel.backend.serviceApplyUra.pendingReview.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "後台案件處理-待審核匣-機關管理者同意結束 rq")
public class ServiceApplyUraPendingReviewOMApproveEndRq extends ApiRequest {
    @ApiModelProperty(value = "案件階段編號")
    private String taskId;


    @ApiModelProperty(value = "審查結果")
    private String result;

    /** GEO 2021115 add 簽核意見*/
    @ApiModelProperty(value = "簽核意見")
    private String taskComment;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTaskComment() {
        return taskComment;
    }

    public void setTaskComment(String taskComment) {
        this.taskComment = taskComment;
    }
}
