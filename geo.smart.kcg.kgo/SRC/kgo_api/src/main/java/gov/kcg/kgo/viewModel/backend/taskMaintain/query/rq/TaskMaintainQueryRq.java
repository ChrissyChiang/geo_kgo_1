package gov.kcg.kgo.viewModel.backend.taskMaintain.query.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "任務及公告管理-任務以及公告查詢 rq")
public class TaskMaintainQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "活動名稱")
	private String titleName;

	@ApiModelProperty(value = "上下架時間")
	private String[] publishTime;

	@ApiModelProperty(value = "發布對象(F:前台B:後台)")
	private String releaseObject;

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String[] getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String[] publishTime) {
		this.publishTime = publishTime;
	}

	public String getReleaseObject() {
		return releaseObject;
	}

	public void setReleaseObject(String releaseObject) {
		this.releaseObject = releaseObject;
	}

}
