package gov.kcg.kgo.viewModel.frontend.eventNews.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 活動消息-已上架活動清單
 */
@ApiModel(description = "活動消息-已上架活動清單")
public class EventNewsHomeDataGrid {

	/** 活動序號 **/
	@ApiModelProperty(value = "活動序號")
	private Integer activitySeq;

	/** 活動名稱 **/
	@ApiModelProperty(value = "活動名稱")
	private String activityName;

	/** 活動日期 **/
	@ApiModelProperty(value = "活動日期")
	private String activityDate;

	/** 發布功能(AnnounceM:公告/TaskM:任務) **/
	@ApiModelProperty(value = "發布功能(AnnounceM:公告/TaskM:任務)")
	private String functionCode;

	public Integer getActivitySeq() {
		return activitySeq;
	}

	public void setActivitySeq(Integer activitySeq) {
		this.activitySeq = activitySeq;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

}
