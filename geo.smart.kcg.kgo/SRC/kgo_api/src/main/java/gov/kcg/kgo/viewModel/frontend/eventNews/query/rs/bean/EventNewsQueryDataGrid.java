package gov.kcg.kgo.viewModel.frontend.eventNews.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 活動消息-任務消息資料集合
 */
@ApiModel(description = "活動消息-任務消息資料集合")
public class EventNewsQueryDataGrid {

	/** 活動名稱 **/
	@ApiModelProperty(value = "活動名稱")
	private String activityName;

	/** 活動內容 **/
	@ApiModelProperty(value = "活動內容")
	private String contentHtml;

	/** 活動日期 **/
	@ApiModelProperty(value = "活動日期")
	private String activityDate;

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getContentHtml() {
		return contentHtml;
	}

	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}

	public String getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}

}
