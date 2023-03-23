package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KGO_CASESET_OFFICER database table.
 * 
 */
@Embeddable
public class KgoCasesetOfficerPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "CaseSetId", unique = true, nullable = false, length = 30)
	private String caseSetId;

	@Column(name = "CaseOfficer", unique = true, nullable = false, length = 50)
	private String caseOfficer;

	public KgoCasesetOfficerPK() {
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getCaseOfficer() {
		return caseOfficer;
	}

	public void setCaseOfficer(String caseOfficer) {
		this.caseOfficer = caseOfficer;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoCasesetOfficerPK)) {
			return false;
		}
		KgoCasesetOfficerPK castOther = (KgoCasesetOfficerPK) other;
		return this.caseSetId.equals(castOther.caseSetId) && this.caseOfficer.equals(castOther.caseOfficer);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.caseSetId.hashCode();
		hash = hash * prime + this.caseOfficer.hashCode();
		return hash;
	}
}