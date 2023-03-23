package gov.kcg.kgo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * The persistent class for the KGO_URA_APPLY database table.
 * 
 */
@Entity
@Table(name="KGO_URA_APPLY")
@NamedQuery(name="KgoUraApply.findAll", query="SELECT k FROM KgoUraApply k")
public class KgoUraApply implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CaseId", unique=true, nullable=false, length=30)
	private String caseId;

	@Column(name="ApplyOrgan", length=50)
	private String applyOrgan;

	@Column(name="ApplyRole", length=200)
	private String applyRole;

	@Column(name="ApplyUnit", length=50)
	private String applyUnit;

	@Column(name="ApplyUser", length=50)
	private String applyUser;

	@Column(name="CaseSetId", length=30)
	private String caseSetId;

	@Column(name="CreateTime")
	private Timestamp createTime;

	@Column(name="CreateUser", length=50)
	private String createUser;

	@Column(name="Email", length=200)
	private String email;

	@Column(name="Phone", length=30)
	private String phone;

	@Column(name="ProcessId", length=200)
	private String processId;

	@Column(name="Result")
	private String result;

	@Column(name="Status", length=30)
	private String status;

	@Column(name="UpdateTime")
	private Timestamp updateTime;

	@Column(name="UpdateUser", length=50)
	private String updateUser;

	@Column(name="manager2", length=100)
	private String manager2;

	@Column(name="manager1", length=100)
	private String manager1;

	public KgoUraApply() {
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getApplyOrgan() {
		return this.applyOrgan;
	}

	public void setApplyOrgan(String applyOrgan) {
		this.applyOrgan = applyOrgan;
	}

	public String getApplyRole() {
		return this.applyRole;
	}

	public void setApplyRole(String applyRole) {
		this.applyRole = applyRole;
	}

	public String getApplyUnit() {
		return this.applyUnit;
	}

	public void setApplyUnit(String applyUnit) {
		this.applyUnit = applyUnit;
	}

	public String getApplyUser() {
		return this.applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getCaseSetId() {
		return this.caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProcessId() {
		return this.processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getManager2() {
		return manager2;
	}

	public void setManager2(String manager2) {
		this.manager2 = manager2;
	}

	public String getManager1() {
		return manager1;
	}

	public void setManager1(String manager1) {
		this.manager1 = manager1;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		KgoUraApply that = (KgoUraApply) o;
		return Objects.equals(caseId, that.caseId) &&
				Objects.equals(applyOrgan, that.applyOrgan) &&
				Objects.equals(applyRole, that.applyRole) &&
				Objects.equals(applyUnit, that.applyUnit) &&
				Objects.equals(applyUser, that.applyUser) &&
				Objects.equals(caseSetId, that.caseSetId) &&
				Objects.equals(createTime, that.createTime) &&
				Objects.equals(createUser, that.createUser) &&
				Objects.equals(email, that.email) &&
				Objects.equals(phone, that.phone) &&
				Objects.equals(processId, that.processId) &&
				Objects.equals(result, that.result) &&
				Objects.equals(status, that.status) &&
				Objects.equals(updateTime, that.updateTime) &&
				Objects.equals(updateUser, that.updateUser) &&
				Objects.equals(manager2, that.manager2) &&
				Objects.equals(manager1, that.manager1);
	}

	@Override
	public int hashCode() {
		return Objects.hash(caseId, applyOrgan, applyRole, applyUnit, applyUser, caseSetId, createTime, createUser, email, phone, processId, result, status, updateTime, updateUser, manager2, manager1);
	}
}