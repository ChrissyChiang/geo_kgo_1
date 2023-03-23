package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KGO_CASESET_MANAGER database table.
 * 
 */
@Embeddable
public class KgoCasesetManagerPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CaseSetId")
	private String caseSetId;

	@Column(name="Manager")
	private String manager;

	public KgoCasesetManagerPK() {
	}
	public String getCaseSetId() {
		return this.caseSetId;
	}
	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}
	public String getManager() {
		return this.manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoCasesetManagerPK)) {
			return false;
		}
		KgoCasesetManagerPK castOther = (KgoCasesetManagerPK)other;
		return 
			this.caseSetId.equals(castOther.caseSetId)
			&& this.manager.equals(castOther.manager);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.caseSetId.hashCode();
		hash = hash * prime + this.manager.hashCode();
		
		return hash;
	}
}