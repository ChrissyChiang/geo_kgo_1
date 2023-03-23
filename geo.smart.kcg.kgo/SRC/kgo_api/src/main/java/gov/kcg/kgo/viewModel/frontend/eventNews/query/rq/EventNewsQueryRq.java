package gov.kcg.kgo.viewModel.frontend.eventNews.query.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "活動消息-任務消息查詢 rq")
public class EventNewsQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "活動序號")
	private Integer activitySeq;

	/** 發布對象(AnnounceM:公告/TaskM:任務) **/
	@ApiModelProperty(value = "發布功能(AnnounceM:公告/TaskM:任務)")
	private String functionCode;

	public Integer getActivitySeq() {
		return activitySeq;
	}

	public void setActivitySeq(Integer activitySeq) {
		this.activitySeq = activitySeq;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
}
