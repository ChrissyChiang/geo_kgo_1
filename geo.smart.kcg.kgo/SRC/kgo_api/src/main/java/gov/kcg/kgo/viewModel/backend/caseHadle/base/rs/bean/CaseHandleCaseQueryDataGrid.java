package gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean;

import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * 後台案件處理-案件查詢共用清單
 */
@ApiModel(description = "後台案件處理-案件查詢共用清單")
public class CaseHandleCaseQueryDataGrid {

	/** 案件編號 */
	@ApiModelProperty(notes = "案件編號")
	private String caseId;

	/** 申請人 */
	@ApiModelProperty(notes = "申請人")
	private String applicant;

	/** 服務案件名稱 */
	@ApiModelProperty(notes = "服務案件名稱")
	private String caseName;

	/** GEO 20211224 add 新增欄位 辨識是否有代理人 */
	/** 被代理人之案件 */
	@ApiModelProperty(notes = "是否為被代理人之案件 true為是 false為否(為自己的案件)")
	private boolean isAgent;

	/** 限辦日期 */
	@ApiModelProperty(notes = "限辦日期")
	private String limitDate;

	/** 案件狀態 */
	@ApiModelProperty(notes = "案件狀態名稱")
	private String statusName;
	
	/** 案件狀態 */
	@ApiModelProperty(notes = "案件狀態類型")
	private String statusType;

	/** 申請單類別（系統權限／服務案件/服務申辦） */
	@ApiModelProperty(notes = "申請單類別（系統權限／服務案件/服務申辦）")
	private String type;
	
	/** 申請日期 */
	@ApiModelProperty(notes = "申請日期")
	private String applyDate;
	
	/** 案件處理更新時間 */
	@ApiModelProperty(notes = "案件處理更新時間")
	private String updateTime;
	
	/** 案件受理設定 */
	@ApiModelProperty(notes = "案件受理設定")
	private String acceptSet;

	/** 承辦人姓名 */
	@ApiModelProperty(notes = "承辦人姓名")
	private String officerName;

	/** 承辦人 */
	@ApiModelProperty(notes = "承辦人")
	private String officer;

	/** 案件階段編號 */
	@ApiModelProperty(notes = "案件階段編號")
	private String taskId;

	/** organ下拉選單 */
	@ApiModelProperty(notes = "organ下拉選單")
	private ComboBox comboBox;

	/** 機關代碼 */
	@ApiModelProperty(value = "機關代碼")
	private String organId;

	/** ura階段 */
	@ApiModelProperty(value = "ura階段")
	private String uraStage;

	/** GEO 20211102 add 申請人登入方式 */
	@ApiModelProperty(notes = "申請人登入方式")
	private String applyUserLoginType;

	/** GEO 20211102 add for 欄位勾選 */
	@ApiModelProperty(notes = "服務id")
	private String casesetId;

	/** GEO 20211102 add for 欄位勾選 */
	@ApiModelProperty(notes = "服務版本號")
	private Integer version;

	/** GEO 20221030 add for 場地租借 */
	@ApiModelProperty(name="場地名稱")
	private String siteName;

	@ApiModelProperty(name="租借起始時間 yyyy-MM-dd HH:mm")
	private String startTime;

	@ApiModelProperty(name="租借費用")
	private Integer amount;

	@ApiModelProperty(name="繳費狀態")
	private String  paymentStatus;

	@ApiModelProperty(name="租借狀態")
	private String rentStatus;


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

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
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

	public String getOfficer() {
		return officer;
	}

	public void setOfficer(String officer) {
		this.officer = officer;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getAcceptSet() {
		return acceptSet;
	}

	public void setAcceptSet(String acceptSet) {
		this.acceptSet = acceptSet;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public ComboBox getComboBox() {
		return comboBox;
	}

	public void setComboBox(ComboBox comboBox) {
		this.comboBox = comboBox;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOfficerName() {
		return officerName;
	}

	public void setOfficerName(String officerName) {
		this.officerName = officerName;
	}

	public String getUraStage() {
		return uraStage;
	}

	public void setUraStage(String uraStage) {
		this.uraStage = uraStage;
	}

	public String getApplyUserLoginType() {
		return applyUserLoginType;
	}

	public void setApplyUserLoginType(String applyUserLoginType) {
		this.applyUserLoginType = applyUserLoginType;
	}

	public String getCasesetId() {
		return casesetId;
	}

	public void setCasesetId(String casesetId) {
		this.casesetId = casesetId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean isAgent() {
		return isAgent;
	}

	public void setAgent(boolean agent) {
		isAgent = agent;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getPaymentStatus() {return paymentStatus;}

	public void setPaymentStatus(String paymentStatus) {this.paymentStatus = paymentStatus;}

	public String getRentStatus() {return rentStatus;}

	public void setRentStatus(String rentStatus) {this.rentStatus = rentStatus;}
}
