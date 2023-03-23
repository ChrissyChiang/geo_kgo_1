package gov.kcg.kgo.viewModel.backend.actFlowManager.save.rq;

import gov.kcg.kgo.viewModel.backend.actFlowManager.save.rq.bean.TpiFlowBean;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台 - 動態流程管理 - 儲存 rq")
public class ActFlowManagerSaveRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "流程資料")
	private TpiFlowBean tpiFlow;

	public TpiFlowBean getTpiFlow() {
		return tpiFlow;
	}

	public void setTpiFlow(TpiFlowBean tpiFlow) {
		this.tpiFlow = tpiFlow;
	}
}
