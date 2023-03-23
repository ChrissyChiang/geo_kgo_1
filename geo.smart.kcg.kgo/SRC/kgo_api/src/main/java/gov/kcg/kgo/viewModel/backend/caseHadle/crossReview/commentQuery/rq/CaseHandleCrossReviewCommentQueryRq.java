package gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentQuery.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211115 add for 跨機關檢視
 * 後台案件處理-跨機關檢視-取得備註 rq
 */
@ApiModel(description = "後台案件處理-跨機關檢視-取得備註 rq rq")
public class CaseHandleCrossReviewCommentQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "案件編號", required = true)
    private String caseId;

    @ApiModelProperty(value = "填寫備註者id（取得自己的備註不填｜系統管理者，機關服務管理者取得他人備註時，必填）")
    private String userId;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
