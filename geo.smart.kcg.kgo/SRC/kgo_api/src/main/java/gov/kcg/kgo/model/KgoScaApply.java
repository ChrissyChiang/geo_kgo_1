package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the KGO_SCA_APPLY database table.
 * 
 */
@Entity
@Table(name="KGO_SCA_APPLY")
@NamedQuery(name="KgoScaApply.findAll", query="SELECT k FROM KgoScaApply k")
public class KgoScaApply implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CaseId", unique=true, nullable=false, length=30)
	private String caseId;

	@Column(name="ApplyOrgan", length=50)
	private String applyOrgan;

	@Column(name="ApplyUnit", length=50)
	private String applyUnit;

	@Column(name="ApplyUser", length=50)
	private String applyUser;

	@Column(name="CaseFlowType", length=50)
	private String caseFlowType;

	@Column(name="CaseSetId", length=30)
	private String caseSetId;

	@Column(name="CaseSetName")
	private String caseSetName;

	@Column(name="CaseType", length=50)
	private String caseType;

	@Column(name="CreateTime")
	private Timestamp createTime;

	@Column(name="CreateUser", length=50)
	private String createUser;

	@Column(name="Email", length=200)
	private String email;

	@Column(name="LinkType", length=50)
	private String linkType;

	@Column(name="LinkUrl", length=500)
	private String linkUrl;

	@Column(name="Organ", length=50)
	private String organ;

	@Column(name="OwnerOrgan", length=50)
	private String ownerOrgan;

	@Column(name="Phone", length=30)
	private String phone;

	@Column(name="ProcessId", length=200)
	private String processId;

	@Column(name="Result")
	private String result;

	@Column(name="Role", length=50)
	private String role;

	@Column(name="Service", length=50)
	private String service;

	@Column(name="Status", length=30)
	private String status;

	@Column(name="UpdateTime")
	private Timestamp updateTime;

	@Column(name="UpdateUser", length=50)
	private String updateUser;

	public KgoScaApply() {
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getApplyOrgan() {
		return this.applyOrgan;
	}

	public void setApplyOrgan(String applyOrgan) {
		this.applyOrgan = applyOrgan;
	}

	public String getApplyUnit() {
		return this.applyUnit;
	}

	public void setApplyUnit(String applyUnit) {
		this.applyUnit = applyUnit;
	}

	public String getApplyUser() {
		return this.applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getCaseFlowType() {
		return this.caseFlowType;
	}

	public void setCaseFlowType(String caseFlowType) {
		this.caseFlowType = caseFlowType;
	}

	public String getCaseSetId() {
		return this.caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getCaseSetName() {
		return this.caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getCaseType() {
		return this.caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLinkType() {
		return this.linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getLinkUrl() {
		return this.linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getOrgan() {
		return this.organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getOwnerOrgan() {
		return this.ownerOrgan;
	}

	public void setOwnerOrgan(String ownerOrgan) {
		this.ownerOrgan = ownerOrgan;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProcessId() {
		return this.processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}