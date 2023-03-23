package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the KGO_KEYWORDS database table.
 * 
 */
@Entity
@Table(name = "KGO_KEYWORDS")
@NamedQuery(name = "KgoKeywords.findAll", query = "SELECT k FROM KgoKeywords k")
public class KgoKeywords implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SeqGUID", unique = true, nullable = false, length = 1)
	private String seqGUID;

	@Column(name = "CreateTime")
	private Timestamp createTime;

	@Column(name = "CreateUser", length = 50)
	private String createUser;

	@Column(name = "Keyword")
	private String keyword;

	public KgoKeywords() {
	}

	public String getSeqGUID() {
		return this.seqGUID;
	}

	public void setSeqGUID(String seqGUID) {
		this.seqGUID = seqGUID;
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

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}