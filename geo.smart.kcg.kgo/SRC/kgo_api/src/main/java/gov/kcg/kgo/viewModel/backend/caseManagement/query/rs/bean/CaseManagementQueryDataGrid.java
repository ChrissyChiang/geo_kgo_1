package gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.bean;

import javax.persistence.Column;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務案件清單-案件排序-初始畫面
 */
@ApiModel(description = "服務案件清單-案件排序-初始畫面")
public class CaseManagementQueryDataGrid {

	/** 案件服務對象 */
	@ApiModelProperty(notes = "案件服務對象")
	private String serviceTo;

	/** 案件種類ID */
	@ApiModelProperty(notes = "案件種類ID")
	private String caseSetId;

	/** 案件名稱 */
	@ApiModelProperty(notes = "案件名稱")
	private String caseSetName;

	/**
	 * 機關代碼
	 */
	@ApiModelProperty(notes = "機關代碼")
	@Column(name = "ORGAN_ID")
	private String organId;
	/**
	 * 機關名稱
	 */
	@ApiModelProperty(notes = "機關名稱")
	@Column(name = "ORGAN_NAME")
	private String organName;
	/**
	 * 權責機關代碼
	 */
	@ApiModelProperty(notes = "權責機關代碼")
	@Column(name = "OWNER_ORGAN_ID")
	private String ownerOrganId;
	/**
	 * 權責機關名稱
	 */
	@ApiModelProperty(notes = "權責機關名稱")
	@Column(name = "OWNER_ORGAN_NAME")
	private String ownerOrganName;

	/** 管理者姓名 */
	@ApiModelProperty(notes = "管理者姓名")
	private String managerName;

	/** 案件狀態 */
	@ApiModelProperty(notes = "案件狀態")
	private String status;

	/** 作業流程 */
	@ApiModelProperty(notes = "作業流程")
	private String caseType;

	/** 是否允許刪除 */
	@ApiModelProperty(notes = "是否允許刪除")
	private String allowDelete;

	/** 流程名稱 */
	@ApiModelProperty(notes = "流程名稱")
	private String taskName;
	
	/** 流程ID */
	@ApiModelProperty(notes = "流程ID")
	private String flowId;

	@ApiModelProperty(notes = "是否已選")
	private Boolean isSelected;

	public String getServiceTo() {
		return serviceTo;
	}

	public void setServiceTo(String serviceTo) {
		this.serviceTo = serviceTo;
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getOwnerOrganId() {
		return ownerOrganId;
	}

	public void setOwnerOrganId(String ownerOrganId) {
		this.ownerOrganId = ownerOrganId;
	}

	public String getOwnerOrganName() {
		return ownerOrganName;
	}

	public void setOwnerOrganName(String ownerOrganName) {
		this.ownerOrganName = ownerOrganName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getAllowDelete() {
		return allowDelete;
	}

	public void setAllowDelete(String allowDelete) {
		this.allowDelete = allowDelete;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public Boolean getSelected() {
		return isSelected;
	}

	public void setSelected(Boolean selected) {
		isSelected = selected;
	}
}
