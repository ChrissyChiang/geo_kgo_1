package gov.kcg.kgo.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * The persistent class for the KGO_COMMON_WORD database table.
 * 
 */
@Entity
@Table(name = "KGO_COMMON_WORD")
@NamedQuery(name = "KgoCommonWord.findAll", query = "SELECT k FROM KgoCommonWord k")
public class KgoCommonWord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Seq", unique = true, nullable = false, length = 50)
	private Integer seq;

	@Column(name = "Title")
	private String title;

	@Column(name = "Word")
	private String word;

	@Column(name = "CreateUser")
	private String createUser;

	@Column(name = "CreateTime")
	private Timestamp createTime;

	@Column(name = "UpdateUser")
	private String updateUser;

	@Column(name = "UpdateTime")
	private Timestamp UpdateTime;

	public KgoCommonWord() {
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
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