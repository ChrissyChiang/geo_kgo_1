package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the KGO_UNIT database table.
 * 
 */
@Entity
@Table(name="KGO_UNIT")
@NamedQuery(name="KgoUnit.findAll", query="SELECT k FROM KgoUnit k")
public class KgoUnit implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoUnitPK id;

	@Column(name="CreateTime")
	private Timestamp createTime;

	@Column(name="CreateUser", length=50)
	private String createUser;

	@Column(name="UnitName", nullable=false)
	private String unitName;

	@Column(name="UpdateTime")
	private Timestamp updateTime;

	@Column(name="UpdateUser", length=50)
	private String updateUser;

	public KgoUnit() {
	}

	public KgoUnitPK getId() {
		return this.id;
	}

	public void setId(KgoUnitPK id) {
		this.id = id;
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

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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