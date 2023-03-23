package gov.kcg.kgo.viewModel.backend.actFlowManager.taskDetail.rs.bean;

import gov.kcg.kgo.viewModel.backend.actFlowManager.save.rq.bean.TpiFlowBean;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台 -動態流程管理 - 取得任務明細 ViewForm
 */
@ApiModel(description = "動態流程管理 - 取得任務明細  ViewForm")
public class ActFlowManagerTaskDetailActionViewForm extends BaseViewForm {

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
