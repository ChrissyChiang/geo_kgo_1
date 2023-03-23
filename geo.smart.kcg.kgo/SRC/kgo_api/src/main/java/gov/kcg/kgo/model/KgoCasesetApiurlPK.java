package gov.kcg.kgo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the KGO_CASESET_APIURL database table.
 * 
 */
@Embeddable
public class KgoCasesetApiurlPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "OrganId", unique = true, nullable = false, length = 50)
	private String organId;

	@Column(name = "CaseFlowType", unique = true, nullable = false, length = 50)
	private String caseFlowType;

	public KgoCasesetApiurlPK() {
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getCaseFlowType() {
		return this.caseFlowType;
	}

	public void setCaseFlowType(String caseFlowType) {
		this.caseFlowType = caseFlowType;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoCasesetApiurlPK)) {
			return false;
		}
		KgoCasesetApiurlPK castOther = (KgoCasesetApiurlPK) other;
		return this.organId.equals(castOther.getOrganId()) && this.caseFlowType.equals(castOther.getCaseFlowType());
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.organId.hashCode();
		hash = hash * prime + this.caseFlowType.hashCode();

		return hash;
	}
}