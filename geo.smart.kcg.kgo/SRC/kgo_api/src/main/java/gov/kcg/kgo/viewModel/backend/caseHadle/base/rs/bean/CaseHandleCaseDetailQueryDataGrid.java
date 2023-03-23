package gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-案件查詢共用清單
 */
@ApiModel(description = "後台案件處理-案件查詢共用清單")
public class CaseHandleCaseDetailQueryDataGrid extends CaseHandleCaseQueryDataGrid {

	/** 案件編號 */
	@ApiModelProperty(notes = "案件編號")
	private String caseId;

	/** 申請人 */
	@ApiModelProperty(notes = "申請人")
	private String applicant;

	/** 服務案件名稱 */
	@ApiModelProperty(notes = "服務案件名稱")
	private String caseName;

	/** 限辦日期 */
	@ApiModelProperty(notes = "限辦日期")
	private String limitDate;

	/** 案件狀態 */
	@ApiModelProperty(notes = "案件狀態")
	private String status;

	/** 申請單類別（系統權限／服務案件/服務申辦） */
	@ApiModelProperty(notes = "申請單類別（系統權限／服務案件/服務申辦）")
	private String type;
	
	/** 申請日期 */
	@ApiModelProperty(notes = "申請日期")
	private String applyDate;

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

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

}
