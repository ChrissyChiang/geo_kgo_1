package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the KGO_ORGAN database table.
 * 
 */
@Entity
@Table(name = "KGO_ORGAN")
@NamedQuery(name = "KgoOrgan.findAll", query = "SELECT k FROM KgoOrgan k")
public class KgoOrgan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OrganId", unique = true, nullable = false, length = 50)
	private String organId;

	@Column(name = "CreateTime")
	private Timestamp createTime;

	@Column(name = "CreateUser", length = 50)
	private String createUser;

	@Column(name = "OrganName", nullable = false)
	private String organName;

	@Column(name = "ParentOrganId", nullable = false, length = 50)
	private String parentOrganId;

	@Column(name = "UpdateTime")
	private Timestamp updateTime;

	@Column(name = "UpdateUser", length = 50)
	private String updateUser;
	
	@Column(name = "BelongKgo")
	private Boolean belongKgo;	

	public KgoOrgan() {
	}

	public String getOrganId() {
		return this.organId;
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

	public String getOrganName() {
		return this.organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getParentOrganId() {
		return parentOrganId;
	}

	public void setParentOrganId(String parentOrganId) {
		this.parentOrganId = parentOrganId;
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

	public Boolean getBelongKgo() {
		return belongKgo;
	}

	public void setBelongKgo(Boolean belongKgo) {
		this.belongKgo = belongKgo;
	}

	@Override
	public String toString() {
		return "KgoOrgan{" +
				"organId='" + organId + '\'' +
				", createTime=" + createTime +
				", createUser='" + createUser + '\'' +
				", organName='" + organName + '\'' +
				", parentOrganId='" + parentOrganId + '\'' +
				", updateTime=" + updateTime +
				", updateUser='" + updateUser + '\'' +
				", belongKgo=" + belongKgo +
				'}';
	}
}