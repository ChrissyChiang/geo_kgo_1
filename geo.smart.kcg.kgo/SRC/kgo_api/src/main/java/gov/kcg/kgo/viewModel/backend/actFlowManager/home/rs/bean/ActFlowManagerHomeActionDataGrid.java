package gov.kcg.kgo.viewModel.backend.actFlowManager.home.rs.bean;

import gov.kcg.kgo.model.TpiFlow;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台 - 動態流程管理 - 畫面初始
 */
@ApiModel(description = "後台 - 動態流程管理 - 畫面初始")
public class ActFlowManagerHomeActionDataGrid {
	
	/** 流程ID. */
	@ApiModelProperty(value = "流程ID", position = 1)
	String flowId;
	
	/** 流程名稱. */
	@ApiModelProperty(value = "流程名稱", position = 2)
	String flowName;

	/** 流程說明. */
	@ApiModelProperty(value = "流程說明", position = 3)
	String flowDesc;
	
	/** 機關/單位代碼. */
	@ApiModelProperty(value = "機關/單位代碼", position = 4)
	String organId;
	
	/** 流程說明. */
	@ApiModelProperty(value = "是否為預設 true/false", position = 5)
	Boolean isDefault;
	
	/** 可編輯預設. */
	@ApiModelProperty(value = "可編輯預設", position = 6)
	Boolean canEditDefault;

	public ActFlowManagerHomeActionDataGrid() {}
	
	public ActFlowManagerHomeActionDataGrid(TpiFlow tpiFlow, Boolean canEditDefault) {
		this.flowId = tpiFlow.getFlowId();
		this.flowName = tpiFlow.getFlowName();
		this.flowDesc = tpiFlow.getFlowDesc();
		this.organId = tpiFlow.getOrganId();
		this.isDefault = tpiFlow.getIsDefault();
		this.canEditDefault = canEditDefault;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getFlowDesc() {
		return flowDesc;
	}

	public void setFlowDesc(String flowDesc) {
		this.flowDesc = flowDesc;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getCanEditDefault() {
		return canEditDefault;
	}

	public void setCanEditDefault(Boolean canEditDefault) {
		this.canEditDefault = canEditDefault;
	}
}
