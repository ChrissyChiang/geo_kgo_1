package gov.kcg.kgo.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the KGO_TASK_SET database table.
 * 
 */
@Entity
@Table(name = "KGO_TASK_SET")
@NamedQuery(name = "KgoTaskSet.findAll", query = "SELECT k FROM KgoTaskSet k")
public class KgoTaskSet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ActivitySeq", unique = true, nullable = false)
	private Integer activitySeq;

	@Column(name = "ActivityName", length = 500)
	private String activityName;

	@Column(name = "ContentHtml")
	private String contentHtml;

	@Column(name = "ActivityDate", length = 50)
	private String activityDate;

	@Column(name = "TaskSeq", length = 50)
	private String taskSeq;

	@Column(name = "IsPublish")
	private Boolean isPublish;

	@Column(name = "PublishTime")
	private Timestamp publishTime;

	@Column(name = "CreateUser", length = 50)
	private String createUser;

	@Column(name = "UpdateUser", length = 50)
	private String updateUser;

	@Column(name = "CreateTime")
	private Timestamp createTime;

	@Column(name = "UpdateTime")
	private Timestamp updateTime;

	@Column(name = "APP_KEY")
	private String appKey;

	public KgoTaskSet() {
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

	public Boolean getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}

	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

}