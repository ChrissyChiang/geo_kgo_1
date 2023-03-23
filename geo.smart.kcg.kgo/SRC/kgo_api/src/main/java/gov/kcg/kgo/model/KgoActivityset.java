package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the KGO_ACTIVITYSET database table.
 * 
 */
@Entity
@Table(name = "KGO_ACTIVITYSET")
@NamedQuery(name = "KgoActivityset.findAll", query = "SELECT k FROM KgoActivityset k")
public class KgoActivityset implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ActivitySeq", unique = true, nullable = false)
	private Integer activitySeq;

	@Column(name = "ActivityName")
	private String activityName;

	@Column(name = "CaseSetId", nullable = false, length = 30)
	private String caseSetId;

	@Column(name = "CreateTime")
	private Timestamp createTime;

	@Column(name = "CreateUser", length = 50)
	private String createUser;

	@Column(length = 500)
	private String icon;

	@Column(name = "State", length = 30)
	private String state;

	private String item;

	@Column(name = "PublishTime")
	private Timestamp publishTime;

	@Column(name = "UpdateTime")
	private Timestamp updateTime;

	@Column(name = "UpdateUser", length = 50)
	private String updateUser;

	@Column(name = "Url", length = 2147483647)
	private String url;

	public KgoActivityset() {
	}

	public Integer getActivitySeq() {
		return this.activitySeq;
	}

	public void setActivitySeq(Integer activitySeq) {
		this.activitySeq = activitySeq;
	}

	public String getActivityName() {
		return this.activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getCaseSetId() {
		return this.caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Timestamp getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}