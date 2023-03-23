package gov.kcg.kgo.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.model.KgoCaseset;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "待簽收案件集合")
@Entity
public class PendingSignCaseQueryDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(notes = "案件編號")
	@Column(name = "CaseId")
	private String caseId;

	@ApiModelProperty(notes = "申請時間，格式：yyyy/MM/dd")
	@Column(name = "ApplyDate")
	private Date applyDate;

	@ApiModelProperty(notes = "申請人，隱碼中間為Ｏ")
	@Column(name = "ApplyUserName")
	private String applyUserName;

	@ApiModelProperty(notes = "案件名稱")
	@Column(name = "CaseSetName")
	private String caseSetName;

	@ApiModelProperty(notes = "限辦日期，格式：yyyy/MM/dd")
	@Column(name = "DeadlineDate")
	private Date deadlineDate;

	@ApiModelProperty(notes = "案件狀態")
	@Column(name = "Status")
	private String status;
	
	/** 申請單類別（系統權限／服務案件/服務申辦） */
	@ApiModelProperty(notes = "申請單類別（系統權限／服務案件/服務申辦）")
	@Column(name = "Type")
	private String type;

	@ApiModelProperty(notes = "流程ID")
	@Column(name = "ProcessId")
	private String processId;
	
	@ApiModelProperty(notes = "受理機關")
	@Column(name = "CaseOrgan")
	private String caseOrgan;
	
	/** 案件受理設定 */
	@ApiModelProperty(notes = "案件受理設定")
	@Column(name = "AcceptSet")
	private String acceptSet;

	public PendingSignCaseQueryDto() {
	}

	public PendingSignCaseQueryDto(KgoCaseMain cm, KgoCaseset s) {
		this.caseId = cm.getCaseId();
		this.applyDate = cm.getApplyDate();
		this.applyUserName = cm.getApplyUserName();
		this.caseSetName = s.getCaseSetName();
		this.deadlineDate = cm.getDeadlineDate();
		this.status = cm.getStatus();
		this.type = s.getCaseType();
		this.processId = cm.getProcessId();
		this.acceptSet = s.getAcceptSet();
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public Date getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(Date deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCaseOrgan() {
		return caseOrgan;
	}

	public void setCaseOrgan(String caseOrgan) {
		this.caseOrgan = caseOrgan;
	}

	public String getAcceptSet() {
		return acceptSet;
	}

	public void setAcceptSet(String acceptSet) {
		this.acceptSet = acceptSet;
	}

}
