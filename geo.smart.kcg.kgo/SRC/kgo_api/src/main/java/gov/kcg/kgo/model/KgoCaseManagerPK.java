package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KGO_CASE_MANAGER database table.
 * 
 */
@Embeddable
public class KgoCaseManagerPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "CaseId", insertable = false, updatable = false, unique = true, nullable = false, length = 50)
	private String caseId;

	@Column(name = "Manager", insertable = false, updatable = false, unique = true, nullable = false, length = 50)
	private String manager;

	public KgoCaseManagerPK() {
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoCaseManagerPK)) {
			return false;
		}
		KgoCaseManagerPK castOther = (KgoCaseManagerPK) other;
		return this.caseId.equals(castOther.caseId) && this.manager.equals(castOther.manager);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.caseId.hashCode();
		hash = hash * prime + this.manager.hashCode();

		return hash;
	}
}