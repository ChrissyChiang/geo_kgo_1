package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "任務及公告管理-任務以及公告查詢")
@Entity
public class TaskAndAnnounceQueryDto {

	@EmbeddedId
	private TaskAndAnnounceQueryDtoPK id;

	/** 標題名稱 */
	@ApiModelProperty(notes = "標題名稱")
	@Column(name = "NAME")
	private String name;

	/** 上架時間 */
	@ApiModelProperty(notes = "上架時間")
	@Column(name = "PUBLISH_TIME")
	private String publishTime;

	/** 是否上架 */
	@ApiModelProperty(notes = "是否上架")
	@Column(name = "IS_PUBLISH")
	private boolean isPublish;

	/** 上架日期 */
	@ApiModelProperty(notes = "上架日期")
	@Column(name = "UPDATE_TIME")
	private String updateTime;

	public TaskAndAnnounceQueryDto() {
	}

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

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public boolean getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(boolean isPublish) {
		this.isPublish = isPublish;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
