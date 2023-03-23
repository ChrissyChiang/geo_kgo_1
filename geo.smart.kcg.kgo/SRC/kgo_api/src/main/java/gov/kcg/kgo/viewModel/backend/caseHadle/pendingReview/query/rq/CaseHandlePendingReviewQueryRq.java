package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.query.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-待審核匣-案件查詢 rq")
public class CaseHandlePendingReviewQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件編號")
	private String caseId;

	@ApiModelProperty(value = "申請人姓名")
	private String applicant;

	@ApiModelProperty(value = "申請時間起迄日")
	private String[] applyDate;

	@ApiModelProperty(value = "服務案件名稱")
	private String caseName;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String[] getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String[] applyDate) {
		this.applyDate = applyDate;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

}
