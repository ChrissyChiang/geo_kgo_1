package gov.kcg.kgo.viewModel.backend.actFlowManager.save.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台 - 動態流程管理 - 儲存 ViewForm
 */
@ApiModel(description = "後台 - 動態流程管理 - 儲存 ViewForm")
public class ActFlowManagerSaveFlowViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "flowId")
	private String flowId;

	@ApiModelProperty(value = "flowDefId")
	private String flowDefId;

	
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFlowDefId() {
		return flowDefId;
	}

	public void setFlowDefId(String flowDefId) {
		this.flowDefId = flowDefId;
	}
}
