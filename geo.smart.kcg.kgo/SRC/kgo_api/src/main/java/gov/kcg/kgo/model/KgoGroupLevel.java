package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the KGO_GROUP_LEVEL database table.
 * 
 */
@Entity
@Table(name = "KGO_GROUP_LEVEL")
@NamedQuery(name = "KgoGroupLevel.findAll", query = "SELECT k FROM KgoGroupLevel k")
public class KgoGroupLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Seq", unique = true, nullable = false)
	private Integer seq;

	@Column(name="OrganId")
	private String organId;

	@Column(name = "CreateTime")
	private Timestamp createTime;

	@Column(name = "CreateUser", length = 50)
	private String createUser;

	@Column(name = "MainType", length = 30)
	private String mainType;

	@Column(name = "Name")
	private String name;

	@Column(name = "PublishTime")
	private Timestamp publishTime;

	@Column(name = "State", length = 30)
	private String state;

	@Column(name = "UpdateTime")
	private Timestamp updateTime;

	@Column(name = "UpdateUser", length = 50)
	private String updateUser;

	public KgoGroupLevel() {
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
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

	public String getMainType() {
		return this.mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
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

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
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

	@Override
	public String toString() {
		return "KgoGroupLevel{" +
				"seq=" + seq +
				", organId='" + organId + '\'' +
				", createTime=" + createTime +
				", createUser='" + createUser + '\'' +
				", mainType='" + mainType + '\'' +
				", name='" + name + '\'' +
				", publishTime=" + publishTime +
				", state='" + state + '\'' +
				", updateTime=" + updateTime +
				", updateUser='" + updateUser + '\'' +
				'}';
	}
}
