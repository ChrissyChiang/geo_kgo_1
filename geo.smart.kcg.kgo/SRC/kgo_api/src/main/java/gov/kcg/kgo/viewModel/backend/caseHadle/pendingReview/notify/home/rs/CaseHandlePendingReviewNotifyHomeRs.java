package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.home.rs;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-待審核匣-訊息通知-初始畫面 rs")
public class CaseHandlePendingReviewNotifyHomeRs extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件編號")
	private String caseId;
	
	@ApiModelProperty(value = "案件狀態")
	private String caseStatus;
	
	@ApiModelProperty(value = "案件處理人")
	private String caseOfficer;
	
	@ApiModelProperty(value = "案件內容")
	private String caseComment;	
	
	@ApiModelProperty(value = "案件處理時間")
	private String handleTime;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getCaseOfficer() {
		return caseOfficer;
	}

	public void setCaseOfficer(String caseOfficer) {
		this.caseOfficer = caseOfficer;
	}

	public String getCaseComment() {
		return caseComment;
	}

	public void setCaseComment(String caseComment) {
		this.caseComment = caseComment;
	}

	public String getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}

}
