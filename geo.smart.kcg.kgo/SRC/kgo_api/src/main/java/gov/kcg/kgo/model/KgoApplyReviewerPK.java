package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KGO_APPLY_REVIEWER database table.
 * 
 */
@Embeddable
public class KgoApplyReviewerPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CaseId")
	private String caseId;

	@Column(name="Seq")
	private Integer seq;

	public KgoApplyReviewerPK() {
	}
	public String getCaseId() {
		return this.caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public Integer getSeq() {
		return this.seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoApplyReviewerPK)) {
			return false;
		}
		KgoApplyReviewerPK castOther = (KgoApplyReviewerPK)other;
		return 
			this.caseId.equals(castOther.caseId)
			&& (this.seq == castOther.seq);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.caseId.hashCode();
		hash = hash * prime + this.seq;
		
		return hash;
	}
}