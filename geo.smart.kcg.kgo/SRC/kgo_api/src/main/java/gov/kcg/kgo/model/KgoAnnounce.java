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
 * The persistent class for the KGO_ANNOUNCE database table.
 * 
 */
@Entity
@Table(name = "KGO_ANNOUNCE")
@NamedQuery(name = "KgoAnnounce.findAll", query = "SELECT k FROM KgoAnnounce k")
public class KgoAnnounce implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Seq", unique = true, nullable = false)
	private Integer seq;

	@Column(name = "ContentHtml")
	private String contentHtml;

	@Column(name = "CreateTime")
	private Timestamp createTime;

	@Column(name = "CreateUser", length = 50)
	private String createUser;

	@Column(name = "IsPublish")
	private Boolean isPublish;

	@Column(name = "Name")
	private String name;

	@Column(name = "PublishTime")
	private Timestamp publishTime;

	@Column(name = "UpdateTime")
	private Timestamp updateTime;

	@Column(name = "UpdateUser", length = 50)
	private String updateUser;

	@Column(name = "ReleaseTo", length = 1)
	private String releaseTo;

	public KgoAnnounce() {
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getContentHtml() {
		return this.contentHtml;
	}

	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
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

	public Boolean getIsPublish() {
		return this.isPublish;
	}

	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getReleaseTo() {
		return releaseTo;
	}

	public void setReleaseTo(String releaseTo) {
		this.releaseTo = releaseTo;
	}

}