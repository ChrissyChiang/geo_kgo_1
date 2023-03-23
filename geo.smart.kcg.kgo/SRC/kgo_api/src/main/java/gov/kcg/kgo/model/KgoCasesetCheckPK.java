package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KGO_CASESET_CHECK database table.
 * 
 */
@Embeddable
public class KgoCasesetCheckPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "CaseSetId", unique = true, nullable = false, length = 30)
	private String caseSetId;

	@Column(name = "CheckType", unique = true, nullable = false, length = 30)
	private String checkType;

	public KgoCasesetCheckPK() {
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoCasesetCheckPK)) {
			return false;
		}
		KgoCasesetCheckPK castOther = (KgoCasesetCheckPK) other;
		return this.caseSetId.equals(castOther.caseSetId) && this.checkType.equals(castOther.checkType);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.caseSetId.hashCode();
		hash = hash * prime + this.checkType.hashCode();
		return hash;
	}
}