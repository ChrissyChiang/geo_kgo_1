package gov.kcg.kgo.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the KGO_USE_LOG database table.
 * 
 */
@Entity
@Table(name="KGO_USE_LOG")
@NamedQuery(name="KgoUseLog.findAll", query="SELECT k FROM KgoUseLog k")
public class KgoUseLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator  = "generator")
	@GenericGenerator(name = "generator", strategy = "guid")
	@Column(name="SeqGUID", unique=true, nullable=false, length=1)
	private String seqGUID;

	@Column(name="Action", length=30)
	private String action;

	@Column(name="CreateTime")
	private Timestamp createTime;

	@Column(name="CreateUser", length=50)
	private String createUser;

	@Column(name="FunctionCode", length=50)
	private String functionCode;

	@Column(name="IP", length=200)
	private String ip;

	@Column(name="LoginType", length=30)
	private String loginType;

	@Column(name="Memo")
	private String memo;

	@Column(name="SystemType", length=30)
	private String systemType;

	public KgoUseLog() {
	}

	public String getSeqGUID() {
		return this.seqGUID;
	}

	public void setSeqGUID(String seqGUID) {
		this.seqGUID = seqGUID;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
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

	public String getFunctionCode() {
		return this.functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLoginType() {
		return this.loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSystemType() {
		return this.systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

}