package gov.kcg.kgo.viewModel.backend.caseHadle.reviewed.query.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "後台案件處理-已審核匣-案件查詢 rq")
public class CaseHandleReviewedQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件編號")
	private String caseId;

	@ApiModelProperty(value = "申請人姓名")
	private String applicant;

	@ApiModelProperty(value = "申請時間起迄日")
	private List<String> applyDate;

	@ApiModelProperty(value = "服務案件名稱")
	private String caseName;
	
	@ApiModelProperty(value = "結案狀態")
	private String completeStatus;

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

	public List<String> getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(List<String> applyDate) {
		this.applyDate = applyDate;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCompleteStatus() {
		return completeStatus;
	}

	public void setCompleteStatus(String completeStatus) {
		this.completeStatus = completeStatus;
	}

}
