package gov.kcg.kgo.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.model.KgoCaseset;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-SA案件檢視資料")
@Entity
public class SaCaseViewQueryDto {

	/** 案件編號 */
	@Id
	@ApiModelProperty(notes = "案件編號")
	@Column(name = "CASE_ID")
	private String caseId;

	/** 案件名稱 **/
	@ApiModelProperty(notes = "案件名稱")
	@Column(name = "CaseSetName")
	private String caseSetName;

	/** 案件狀態 */
	@ApiModelProperty(notes = "案件狀態")
	@Column(name = "STATUS")
	private String status;

	/** 案件狀態說明 */
	@ApiModelProperty(notes = "案件狀態說明")
	@Column(name = "STATUS_DESC")
	private String statusDesc;

	/** 申請時間 */
	@ApiModelProperty(notes = "申請時間")
	@Column(name = "APPLY_DATE")
	private Date applyDate;

	/** 限辦天數 */
	@ApiModelProperty(notes = "限辦天數")
	@Column(name = "LIMITED_PERIOD")
	private Integer limitedPeriod;

	/** 限辦日期 */
	@ApiModelProperty(notes = "限辦日期")
	@Column(name = "DEADLINE_DATE")
	private Date deadlineDate;
	
	/** 補正天數 */
	@ApiModelProperty(notes = "補正天數")
	@Column(name = "CORRECT_DEAD_LINE")
	private Integer correctDeadline;

	/** 補正日期 */
	@ApiModelProperty(notes = "補正日期")
	@Column(name = "CORRECT_DATE")
	private Date correctDate;

	private String state;

	private String processId;

	private String caseFlowType;

	private String applyUserName;

	public SaCaseViewQueryDto() {
	}

	public SaCaseViewQueryDto(KgoCaseMain m, KgoCaseset s) {
		if (null != s) {
			this.caseSetName = s.getCaseSetName();
			this.limitedPeriod = s.getLimitedPeriod();
			this.caseFlowType = s.getCaseFlowType();
		}
		this.caseId = m.getCaseId();
		this.status = m.getStatus();
		this.statusDesc = m.getStatusDesc();
		this.applyDate = m.getApplyDate();
		this.deadlineDate = m.getDeadlineDate();
		this.processId = m.getProcessId();
		this.state = m.getState();
		this.applyUserName = m.getApplyUserName();
		
		this.correctDeadline = m.getCorrectDeadline();
		this.correctDate = m.getCorrectDate();
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Integer getLimitedPeriod() {
		return limitedPeriod;
	}

	public void setLimitedPeriod(Integer limitedPeriod) {
		this.limitedPeriod = limitedPeriod;
	}

	public Date getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(Date deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public Integer getCorrectDeadline() {
		return correctDeadline;
	}

	public void setCorrectDeadline(Integer correctDeadline) {
		this.correctDeadline = correctDeadline;
	}

	public Date getCorrectDate() {
		return correctDate;
	}

	public void setCorrectDate(Date correctDate) {
		this.correctDate = correctDate;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCaseFlowType() {
		return caseFlowType;
	}

	public void setCaseFlowType(String caseFlowType) {
		this.caseFlowType = caseFlowType;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
}
