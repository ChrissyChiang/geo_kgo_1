package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "服務案件清單-案件維護-任務查詢")
@Entity
public class TaskSetQueryByCaseSetIdDto {

	/** 任務流水號 */
	@Id
	@ApiModelProperty(notes = "任務流水號")
	@Column(name = "TASK_SEQ")
	private String taskSeq;

	/** 活動序號 */
	@ApiModelProperty(notes = "活動序號")
	@Column(name = "ACTIVITY_NAME")
	private String activityName;

	@Column(name = "ActivityDate")
	private String activityDate;

	@Column(name = "IsPublish")
	private Boolean isPublish;

	@Column(name = "APP_KEY")
	private String appKey;

	public TaskSetQueryByCaseSetIdDto() {
	}

	public String getTaskSeq() {
		return taskSeq;
	}

	public void setTaskSeq(String taskSeq) {
		this.taskSeq = taskSeq;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}

	public Boolean getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}

}
