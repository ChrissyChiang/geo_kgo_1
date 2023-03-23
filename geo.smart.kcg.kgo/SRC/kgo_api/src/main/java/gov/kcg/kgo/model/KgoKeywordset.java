package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the KGO_KEYWORDSET database table.
 * 
 */
@Entity
@Table(name="KGO_KEYWORDSET")
@NamedQuery(name="KgoKeywordset.findAll", query="SELECT k FROM KgoKeywordset k")
public class KgoKeywordset implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Seq", unique=true, nullable=false)
	private Integer seq;

	@Column(name="CreateTime")
	private Timestamp createTime;

	@Column(name="CreateUser", length=50)
	private String createUser;

	@Column(name="Keyword")
	private String keyword;

	@Column(name="OrderNum")
	private Integer orderNum;

	@Column(name="Source", length=30)
	private String source;

	@Column(name="UpdateTime")
	private Timestamp updateTime;

	@Column(name="UpdateUser", length=50)
	private String updateUser;

	public KgoKeywordset() {
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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

	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
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