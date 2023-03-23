package gov.kcg.kgo.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * The persistent class for the KGO_COMMON_QUESTION database table.
 * 
 */
@Entity
@Table(name = "KGO_COMMON_QUESTION")
@NamedQuery(name = "KgoCommonQuestion.findAll", query = "SELECT k FROM KgoCommonQuestion k")
public class KgoCommonQuestion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Seq", unique = true, nullable = false, length = 50)
	private Integer seq;

	@Column(name = "Question")
	private String question;

	@Column(name = "Content")
	private String content;

	@Column(name = "State")
	private String state;

	@Column(name = "PublishDate")
	private Timestamp publishDate;

	@Column(name = "CreateUser")
	private String createUser;

	@Column(name = "CreateTime")
	private Timestamp createTime;

	@Column(name = "UpdateUser")
	private String updateUser;

	@Column(name = "UpdateTime")
	private Timestamp UpdateTime;

	public KgoCommonQuestion() {
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getUpdateTime() {
		return UpdateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		UpdateTime = updateTime;
	}

}