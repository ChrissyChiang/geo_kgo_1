package gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentEdit.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211115 add for 跨機關檢視
 * 後台案件處理-跨機關檢視-新增/編輯備註 rq
 */
@ApiModel(description = "後台案件處理-跨機關檢視-新增/編輯備註 rq")
public class CaseHandleCrossReviewCommentEditRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "備註id，新增不填，編輯必填")
    private String crossReviewCommentId;

    @ApiModelProperty(notes = "服務id", required = true)
    private String caseId;

    @ApiModelProperty(notes = "機關id", required = true)
    private String organId;

    @ApiModelProperty(notes = "科室id", required = true)
    private String unitId;

    @ApiModelProperty(notes = "填寫人員id", required = true)
    private String userId;

    @ApiModelProperty(notes = "備註內容", required = true)
    private String comment;

    public String getCrossReviewCommentId() {
        return crossReviewCommentId;
    }

    public void setCrossReviewCommentId(String crossReviewCommentId) {
        this.crossReviewCommentId = crossReviewCommentId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
