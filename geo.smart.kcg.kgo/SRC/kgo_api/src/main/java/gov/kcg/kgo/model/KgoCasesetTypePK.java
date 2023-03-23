package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KGO_CASESET_TYPE database table.
 * 
 */
@Embeddable
public class KgoCasesetTypePK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "CaseSetId", unique = true, nullable = false, length = 30)
	private String caseSetId;

	@Column(name = "ApplyType", unique = true, nullable = false, length = 30)
	private String applyType;

	public KgoCasesetTypePK() {
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoCasesetTypePK)) {
			return false;
		}
		KgoCasesetTypePK castOther = (KgoCasesetTypePK) other;
		return this.caseSetId.equals(castOther.caseSetId) && this.applyType.equals(castOther.applyType);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.caseSetId.hashCode();
		hash = hash * prime + this.applyType.hashCode();

		return hash;
	}
}