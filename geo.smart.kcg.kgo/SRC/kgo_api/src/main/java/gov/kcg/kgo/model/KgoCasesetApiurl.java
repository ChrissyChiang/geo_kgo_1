package gov.kcg.kgo.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the KGO_CASESET_APIURL database table.
 * 
 */
@Entity
@Table(name = "KGO_CASESET_APIURL")
@NamedQuery(name = "KgoCasesetApiurl.findAll", query = "SELECT k FROM KgoCasesetApiurl k")
public class KgoCasesetApiurl implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCasesetApiurlPK id;

	@Column(name = "APIUrl", length = 30)
	private String apiurl;

	@Column(name = "CreateTime")
	private Timestamp createTime;

	@Column(name = "CreateUser", length = 50)
	private String createUser;

	@Column(name = "UpdateTime")
	private Timestamp updateTime;

	@Column(name = "UpdateUser", length = 50)
	private String updateUser;

	public KgoCasesetApiurlPK getId() {
		return id;
	}

	public void setId(KgoCasesetApiurlPK id) {
		this.id = id;
	}

	public String getApiurl() {
		return apiurl;
	}

	public void setApiurl(String apiurl) {
		this.apiurl = apiurl;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}
