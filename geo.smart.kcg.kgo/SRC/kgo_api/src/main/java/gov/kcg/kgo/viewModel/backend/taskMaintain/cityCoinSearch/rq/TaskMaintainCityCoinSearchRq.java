package gov.kcg.kgo.viewModel.backend.taskMaintain.cityCoinSearch.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "任務及公告管理– 城市幣任務查詢 rq")
public class TaskMaintainCityCoinSearchRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "活動代碼")
	private String taskSeq;

	public String getTaskSeq() {
		return taskSeq;
	}

	public void setTaskSeq(String taskSeq) {
		this.taskSeq = taskSeq;
	}

}
