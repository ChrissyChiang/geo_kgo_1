package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the KGO_PARAM_SET database table.
 * 
 */
@Entity
@Table(name="KGO_PARAM_SET")
@NamedQuery(name="KgoParamSet.findAll", query="SELECT k FROM KgoParamSet k")
public class KgoParamSet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Type", unique=true, nullable=false, length=30)
	private String type;

	@Column(name="CreateTime")
	private Timestamp createTime;

	@Column(name="CreateUser", length=50)
	private String createUser;

	@Column(name="DetailType", nullable=false, length=10)
	private String detailType;

	@Column(name="UpdateTime")
	private Timestamp updateTime;

	@Column(name="UpdateUser", length=50)
	private String updateUser;

	@Column(name="Value", length=30)
	private String value;

	public KgoParamSet() {
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getDetailType() {
		return this.detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
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

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}