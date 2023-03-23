package gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.edit.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "服務申請-服務案件申請-編輯 rq")
public class ServiceApplyServiceCaseEditRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "申請人員機關")
	private String applyOrgan;

	@ApiModelProperty(value = "申請人員科室")
	private String applyUnit;

	@ApiModelProperty(value = "申請人員")
	private String applyUser;

	@ApiModelProperty(value = "案件名稱")
	private String caseSetName;

	@ApiModelProperty(value = "聯絡電話")
	private String phone;

	@ApiModelProperty(value = "電子郵件")
	private String email;

	@ApiModelProperty(value = "權責機關")
	private String ownerOrgan;

	@ApiModelProperty(value = "案件管理者")
	private String manager;

	@ApiModelProperty(value = "審核主管")
	private String reviewer;

	/** 作業流程分類 */
	@ApiModelProperty(notes = "作業流程分類")
	private String caseFlowType;

	@ApiModelProperty(value = "整合流程分類")
	private String caseType;

	@ApiModelProperty(value = "站外連結方式")
	private String linkType;

	@ApiModelProperty(value = "站外連結網址")
	private String linkUrl;

	@ApiModelProperty(value = "機關分類")
	private String organ;

	@ApiModelProperty(value = "角色分類")
	private String role;

	@ApiModelProperty(value = "服務分類")
	private String service;

	@ApiModelProperty(value = "服務啟用")
	private String caseSetType;

	public String getApplyOrgan() {
		return applyOrgan;
	}

	public void setApplyOrgan(String applyOrgan) {
		this.applyOrgan = applyOrgan;
	}

	public String getApplyUnit() {
		return applyUnit;
	}

	public void setApplyUnit(String applyUnit) {
		this.applyUnit = applyUnit;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOwnerOrgan() {
		return ownerOrgan;
	}

	public void setOwnerOrgan(String ownerOrgan) {
		this.ownerOrgan = ownerOrgan;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getCaseSetType() {
		return caseSetType;
	}

	public void setCaseSetType(String caseSetType) {
		this.caseSetType = caseSetType;
	}

	public String getCaseFlowType() {
		return caseFlowType;
	}

	public void setCaseFlowType(String caseFlowType) {
		this.caseFlowType = caseFlowType;
	}
}
