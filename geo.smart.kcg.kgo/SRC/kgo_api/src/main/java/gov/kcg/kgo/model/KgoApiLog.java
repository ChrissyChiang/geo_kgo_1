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
 * The persistent class for the KGO_API_LOG database table.
 * 
 */
@Entity
@Table(name = "KGO_API_LOG")
@NamedQuery(name = "KgoApiLog.findAll", query = "SELECT k FROM KgoApiLog k")
public class KgoApiLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "Seq", unique = true, nullable = false, length = 1)
	private String seq;

	@Column(name = "CaseId", nullable = false, length = 50)
	private String caseId;

	@Column(name = "CreateTime")
	private Timestamp createTime;

	@Column(name = "IsSuccess")
	private boolean isSuccess;

	@Column(name = "OrganId", nullable = false, length = 50)
	private String organId;

	@Column(name = "ReCount")
	private Integer reCount;

	@Column(name = "Request")
	private String request;

	@Column(name = "Response", nullable = false)
	private String response;

	@Column(name = "UpdateTime")
	private Timestamp updateTime;

	@Column(name = "Url", nullable = false, length = 200)
	private String url;

	public KgoApiLog() {
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Integer getReCount() {
		return reCount;
	}

	public void setReCount(Integer reCount) {
		this.reCount = reCount;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}