package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "活動消息-已上架活動清單")
@Entity
public class EventNewsHomeDataGridDto {

	@EmbeddedId
	private TaskAndAnnounceQueryDtoPK id;

	/** 標題名稱 */
	@ApiModelProperty(notes = "標題名稱")
	@Column(name = "NAME")
	private String name;

	/** 活動日期 */
	@ApiModelProperty(notes = "活動日期")
	@Column(name = "ACTIVITY_DATE")
	private String activityDate;

	/** 上架時間 */
	@ApiModelProperty(notes = "上架時間")
	@Column(name = "PUBLISH_TIME")
	private String publishTime;

	/** 是否上架 */
	@ApiModelProperty(notes = "是否上架")
	@Column(name = "IS_PUBLISH")
	private boolean isPublish;

	public TaskAndAnnounceQueryDtoPK getId() {
		return id;
	}

	public void setId(TaskAndAnnounceQueryDtoPK id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public boolean isPublish() {
		return isPublish;
	}

	public void setPublish(boolean isPublish) {
		this.isPublish = isPublish;
	}

}
