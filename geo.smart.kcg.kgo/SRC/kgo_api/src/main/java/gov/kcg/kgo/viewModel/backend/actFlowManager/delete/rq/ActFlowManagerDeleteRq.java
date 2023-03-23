package gov.kcg.kgo.viewModel.backend.actFlowManager.delete.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台 - 動態流程管理 - 刪除 rq")
public class ActFlowManagerDeleteRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "流程編號", position = 0)
    private String flowId;

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
}
