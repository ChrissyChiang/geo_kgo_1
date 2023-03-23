package gov.kcg.kgo.viewModel.backend.actFlowManager.resendFlow.rq;

import gov.kcg.kgo.viewModel.backend.actFlowManager.save.rq.bean.TpiFlowBean;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台 - 動態流程管理 - 修改重送流程 rq")
public class ActFlowManagerResendFlowRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件編號")
	private String caseId;

	@ApiModelProperty(value = "流程資料")
	private TpiFlowBean tpiFlow;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public TpiFlowBean getTpiFlow() {
		return tpiFlow;
	}

	public void setTpiFlow(TpiFlowBean tpiFlow) {
		this.tpiFlow = tpiFlow;
	}
}
