package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "後台案件處理-案件檢視-初始畫面 rq")
public class CaseHandleCaseViewQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "服務案件編號,非必填")
	private String caseSetId;

	@ApiModelProperty(value = "案件編號")
	private String caseId;

	@ApiModelProperty(value = "申請人姓名")
	private String applicant;

	@ApiModelProperty(value = "申請時間起迄日(yyyymmdd)")
	private List<String> applyDate;

	@ApiModelProperty(value = "服務案件名稱")
	private String caseName;

	@ApiModelProperty(value = "案件狀態")
	private String status;

	@ApiModelProperty(value = "作業流程分類")
	private String caseFlowType;

	@ApiModelProperty(value = "身分證字號")
	private String id;

	@ApiModelProperty(value = "手機")
	private String cellPhone;

	@ApiModelProperty(value = "版本號")
	private Integer version;

	@ApiModelProperty(value = "申請人機關")
	private String organId;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCaseFlowType() {
		return caseFlowType;
	}

	public void setCaseFlowType(String caseFlowType) {
		this.caseFlowType = caseFlowType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
}
