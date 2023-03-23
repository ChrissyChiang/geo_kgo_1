package gov.kcg.kgo.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "KGO_ATT_FILE")
@NamedQuery(name = "KgoAttFile.findAll", query = "SELECT k FROM KgoAttFile k")
public class KgoAttFile {

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "Guid", unique = true, nullable = false, length = 1)
	private String guid;

	@Column(name = "F_Seq", nullable = false)
	private Integer fSeq;

	@Column(name = "Type", nullable = false)
	private String type;

	@Column(name = "FileName", nullable = false)
	private String fileName;

	@Column(name = "Status", nullable = false)
	private String status;

	@Column(name = "CreateUser", length = 50)
	private String createUser;

	@Column(name = "CreateTime")
	private Timestamp createTime;

	@Column(name = "UpdateUser", length = 50)
	private String updateUser;

	@Column(name = "UpdateTime")
	private Timestamp updateTime;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Integer getfSeq() {
		return fSeq;
	}

	public void setfSeq(Integer fSeq) {
		this.fSeq = fSeq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
