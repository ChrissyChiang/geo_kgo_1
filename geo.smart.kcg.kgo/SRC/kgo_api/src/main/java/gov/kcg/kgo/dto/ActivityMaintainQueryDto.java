package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "任務維護-任務查詢")
@Entity
public class ActivityMaintainQueryDto {

	/** 序號 */
	@Id
	@ApiModelProperty(notes = "序號")
	@Column(name = "ActivitySeq")
	private Integer activitySeq;

	/** 分類代碼 */
	@ApiModelProperty(notes = "分類代碼")
	@Column(name = "ActivityName")
	private String activityName;

	/** 標題 */
	@ApiModelProperty(notes = "標題")
	@Column(name = "State")
	private String state;

	public ActivityMaintainQueryDto() {

	}

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
