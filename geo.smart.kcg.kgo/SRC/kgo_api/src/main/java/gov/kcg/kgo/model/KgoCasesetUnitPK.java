package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KGO_CASESET_UNIT database table.
 * 
 */
@Embeddable
public class KgoCasesetUnitPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "CaseSetId", unique = true, nullable = false, length = 30)
	private String caseSetId;

	@Column(name = "Organ", unique = true, nullable = false, length = 30)
	private String organ;

	public KgoCasesetUnitPK() {
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoCasesetUnitPK)) {
			return false;
		}
		KgoCasesetUnitPK castOther = (KgoCasesetUnitPK) other;
		return this.caseSetId.equals(castOther.caseSetId) && this.organ.equals(castOther.organ);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.caseSetId.hashCode();
		hash = hash * prime + this.organ.hashCode();
		return hash;
	}
}