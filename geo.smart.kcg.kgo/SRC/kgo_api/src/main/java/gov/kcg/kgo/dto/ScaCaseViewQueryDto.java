package gov.kcg.kgo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@ApiModel(description = "後台案件處理-SCA案件檢視資料")
@Entity
public class ScaCaseViewQueryDto {

	/** 案件編號 */
	@Id
	@ApiModelProperty(notes = "案件編號")
	@Column(name = "CASE_ID")
	private String caseId;

	/** 服務案件編號 */
	@ApiModelProperty(notes = "服務案件編號")
	@Column(name = "CASESET_ID")
	private String caseSetId;

	/** 申請機關 **/
	@ApiModelProperty(notes = "申請機關")
	@Column(name = "APPLY_ORGAN")
	private String applyOrgan;

	/** 申請單位 */
	@ApiModelProperty(notes = "申請單位")
	@Column(name = "APPLY_UNIT")
	private String applyUnit;

	/** 申請人 */
	@ApiModelProperty(notes = "申請人")
	@Column(name = "APPLY_USER")
	private String applyUser;

	/** 電話 */
	@ApiModelProperty(notes = "電話")
	@Column(name = "PHONE")
	private String phone;

	/** 郵件 */
	@ApiModelProperty(notes = "郵件")
	@Column(name = "EMAIL")
	private String email;

	/** 案件名稱 */
	@ApiModelProperty(notes = "案件名稱")
	@Column(name = "CASESET_NAME")
	private String casesetName;

	/** 作業流程 */
	@ApiModelProperty(notes = "作業流程")
	@Column(name = "CASE_TYPE")
	private String caseType;

	/** 作業流程分類 */
	@ApiModelProperty(notes = "作業流程分類")
	@Column(name = "CASE_FLOW_TYPE")
	private String caseFlowType;

	/** 站外連結方式 */
	@ApiModelProperty(notes = "站外連結方式")
	@Column(name = "LINK_TYPE")
	private String linkType;

	/** 站外連結網址 */
	@ApiModelProperty(notes = "站外連結網址")
	@Column(name = "LINK_URL")
	private String linkUrl;

	/** 機關分類 */
	@ApiModelProperty(notes = "機關分類")
	@Column(name = "ORGAN")
	private String organ;

	/** 角色分類 */
	@ApiModelProperty(notes = "角色分類")
	@Column(name = "ROLE")
	private String role;

	/** 服務分類 */
	@ApiModelProperty(notes = "服務分類")
	@Column(name = "SERVICE")
	private String service;

	/** 權責機關 */
	@ApiModelProperty(notes = "權責機關")
	@Column(name = "OWNER_ORGAN")
	private String ownerOrgan;

	/** 案件管理者 */
	@ApiModelProperty(notes = "案件管理者")
	@Column(name = "MANAGER_NAME")
	private String managerName;

	/** 服務啟用 */
	@ApiModelProperty(notes = "服務啟用")
	@Column(name = "APPLY_TYPE")
	private String applyType;

	/** 審核主管 */
	@ApiModelProperty(notes = "審核主管")
	@Column(name = "APPLY_REVIEWER")
	private String applyReviewer;

	/** 案件歷程編號 */
	@ApiModelProperty(notes = "案件歷程編號")
	@Column(name = "PROCESS_ID")
	private String processId;

	public ScaCaseViewQueryDto() {
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

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

	public String getCasesetName() {
		return casesetName;
	}

	public void setCasesetName(String casesetName) {
		this.casesetName = casesetName;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCaseFlowType() {
		return caseFlowType;
	}

	public void setCaseFlowType(String caseFlowType) {
		this.caseFlowType = caseFlowType;
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

	public String getOwnerOrgan() {
		return ownerOrgan;
	}

	public void setOwnerOrgan(String ownerOrgan) {
		this.ownerOrgan = ownerOrgan;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getApplyReviewer() {
		return applyReviewer;
	}

	public void setApplyReviewer(String applyReviewer) {
		this.applyReviewer = applyReviewer;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
}
