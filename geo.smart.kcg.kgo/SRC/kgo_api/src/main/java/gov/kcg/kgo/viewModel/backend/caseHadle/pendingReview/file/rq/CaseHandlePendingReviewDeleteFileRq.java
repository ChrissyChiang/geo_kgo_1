package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.file.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-待審核匣-刪除附件 rq")
public class CaseHandlePendingReviewDeleteFileRq extends ApiRequest {

    @ApiModelProperty(value = "案件編號")
    private String caseId;

    @ApiModelProperty(value = "附件序號")
    private String seq;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

}
