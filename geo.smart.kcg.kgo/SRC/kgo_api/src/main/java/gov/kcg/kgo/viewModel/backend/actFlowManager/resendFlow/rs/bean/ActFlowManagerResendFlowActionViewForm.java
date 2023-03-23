package gov.kcg.kgo.viewModel.backend.actFlowManager.resendFlow.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台 -動態流程管理 - 畫面初始 ViewForm
 */
@ApiModel(description = "後台 - 動態流程管理 - 修改重送流程 ViewForm")
public class ActFlowManagerResendFlowActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "flowDefId")
	private String flowDefId;

	public String getFlowDefId() {
		return flowDefId;
	}

	public void setFlowDefId(String flowDefId) {
		this.flowDefId = flowDefId;
	}
}
