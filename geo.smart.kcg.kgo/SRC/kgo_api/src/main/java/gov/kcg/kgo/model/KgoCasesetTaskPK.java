package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KGO_CASESET_TASK database table.
 * 
 */
@Embeddable
public class KgoCasesetTaskPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ActivitySeq", unique=true, nullable=false)
	private Integer activitySeq;

	@Column(name="CaseSetId", unique=true, nullable=false, length=30)
	private String caseSetId;

	public KgoCasesetTaskPK() {
	}
	public Integer getActivitySeq() {
		return this.activitySeq;
	}
	public void setActivitySeq(Integer activitySeq) {
		this.activitySeq = activitySeq;
	}
	public String getCaseSetId() {
		return this.caseSetId;
	}
	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoCasesetTaskPK)) {
			return false;
		}
		KgoCasesetTaskPK castOther = (KgoCasesetTaskPK)other;
		return 
			(this.activitySeq == castOther.activitySeq)
			&& this.caseSetId.equals(castOther.caseSetId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.activitySeq;
		hash = hash * prime + this.caseSetId.hashCode();
		
		return hash;
	}
}