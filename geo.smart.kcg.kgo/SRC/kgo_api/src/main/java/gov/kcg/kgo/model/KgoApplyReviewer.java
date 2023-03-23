package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the KGO_APPLY_REVIEWER database table.
 * 
 */
@Entity
@Table(name="KGO_APPLY_REVIEWER")
@NamedQuery(name="KgoApplyReviewer.findAll", query="SELECT k FROM KgoApplyReviewer k")
public class KgoApplyReviewer implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoApplyReviewerPK id;

	@Column(name="CaseType")
	private String caseType;

	@Column(name="CreateTime")
	private Timestamp createTime;

	@Column(name="CreateUser")
	private String createUser;

	@Column(name="IsReview")
	private Boolean isReview;

	@Column(name="Reviewer")
	private String reviewer;

	@Column(name="UpdateTime")
	private Timestamp updateTime;

	@Column(name="UpdateUser")
	private String updateUser;

	public KgoApplyReviewer() {
	}

	public KgoApplyReviewerPK getId() {
		return this.id;
	}

	public void setId(KgoApplyReviewerPK id) {
		this.id = id;
	}

	public String getCaseType() {
		return this.caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
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

	public Boolean getIsReview() {
		return this.isReview;
	}

	public void setIsReview(Boolean isReview) {
		this.isReview = isReview;
	}

	public String getReviewer() {
		return this.reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
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

}