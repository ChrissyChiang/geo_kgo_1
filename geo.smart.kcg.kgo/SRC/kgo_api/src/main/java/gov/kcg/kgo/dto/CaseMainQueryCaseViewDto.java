package gov.kcg.kgo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@ApiModel(description = "後台案件處理-案件資料集合")
@Entity
public class CaseMainQueryCaseViewDto {

	/** 案件ID */
	@Id
	@ApiModelProperty(notes = "案件ID")
	@Column(name = "CASE_ID")
	private String caseId;

	/** 申請日期 */
	@ApiModelProperty(notes = "申請日期")
	@Column(name = "APPLY_DATE")
	private Timestamp applyDate;

	/** 申請人員 */
	@ApiModelProperty(notes = "申請人員")
	@Column(name = "APPLY_USER")
	private String applyUser;

	/** 案件名稱 */
	@ApiModelProperty(notes = "案件名稱")
	@Column(name = "CASESET_NAME")
	private String caseSetName;

	/** 限辦日期 */
	@ApiModelProperty(notes = "限辦日期")
	@Column(name = "DEADLINE_DATE")
	private Timestamp deadlineDate;

	/** 案件狀態 */
	@ApiModelProperty(notes = "案件狀態")
	@Column(name = "STATUS")
	private String status;

	/** 流程引擎 */
	@ApiModelProperty(notes = "流程引擎")
	@Column(name = "TYPE")
	private String type;

	/** 承辦人 */
	@ApiModelProperty(notes = "承辦人")
	@Column(name = "CASE_OFFICER")
	private String caseOfficer;

	/** 流程ID */
	@ApiModelProperty(notes = "流程ID")
	@Column(name = "PROCESS_ID")
	private String processId;

	/** 案件受理設定 */
	@ApiModelProperty(notes = "案件受理設定")
	@Column(name = "ACCEPT_SET")
	private String acceptSet;

	/** 案件受理設定 */
	@ApiModelProperty(notes = "服務案件編號")
	@Column(name = "CASE_SET_ID")
	private String caseSetId;

	/** 結案日期 */
	@ApiModelProperty(notes = "結案日期")
	@Column(name = "FDATE")
	private Timestamp fdate;

	public CaseMainQueryCaseViewDto() {
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Timestamp getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Timestamp applyDate) {
		this.applyDate = applyDate;
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

	public Timestamp getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(Timestamp deadlineDate) {
		this.deadlineDate = deadlineDate;
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

	public String getCaseOfficer() {
		return caseOfficer;
	}

	public void setCaseOfficer(String caseOfficer) {
		this.caseOfficer = caseOfficer;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getAcceptSet() {
		return acceptSet;
	}

	public void setAcceptSet(String acceptSet) {
		this.acceptSet = acceptSet;
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public Timestamp getFdate() {
		return fdate;
	}

	public void setFdate(Timestamp fdate) {
		this.fdate = fdate;
	}
}
