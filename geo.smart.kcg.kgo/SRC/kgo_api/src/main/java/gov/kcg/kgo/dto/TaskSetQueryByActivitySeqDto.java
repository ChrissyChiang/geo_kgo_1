package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "任務及公告管理-任務維護(新增/修改)-城市幣任務資料集合")
@Entity
public class TaskSetQueryByActivitySeqDto {

	/** 活動名稱 */
	@Id
	@ApiModelProperty(notes = "活動名稱")
	@Column(name = "ACTIVITY_NAME")
	private String activityName;

	/** 活動內容 */
	@ApiModelProperty(notes = "活動內容")
	@Column(name = "CONTENT_HTML")
	private String contentHtml;

	/** 活動日期 */
	@ApiModelProperty(notes = "活動日期")
	@Column(name = "ACTIVITY_DATE")
	private String activityDate;

	/** 活動項目 */
	@ApiModelProperty(notes = "活動項目")
	@Column(name = "TASK_SEQ")
	private String taskSeq;

	public TaskSetQueryByActivitySeqDto() {
	}

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

	public String getTaskSeq() {
		return taskSeq;
	}

	public void setTaskSeq(String taskSeq) {
		this.taskSeq = taskSeq;
	}

}
