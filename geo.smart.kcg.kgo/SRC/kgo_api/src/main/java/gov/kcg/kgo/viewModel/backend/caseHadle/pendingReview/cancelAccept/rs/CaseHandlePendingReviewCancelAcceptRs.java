package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.cancelAccept.rs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待審核匣-取消簽收 rs
 */
@ApiModel(description = "後台案件處理-待審核匣-取消簽收 rs")
public class CaseHandlePendingReviewCancelAcceptRs {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "caseId")
	private String caseId;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
}
