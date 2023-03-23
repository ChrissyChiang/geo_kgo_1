package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KGO_CASESET_MYDATA database table.
 * 
 */
@Embeddable
public class KgoCasesetMydataPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CaseSetId", unique=true, nullable=false, length=30)
	private String caseSetId;

	@Column(name="MyDataId", unique=true, nullable=false, length=50)
	private String myDataId;

	public KgoCasesetMydataPK() {
	}
	public String getCaseSetId() {
		return this.caseSetId;
	}
	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}
	public String getMyDataId() {
		return this.myDataId;
	}
	public void setMyDataId(String myDataId) {
		this.myDataId = myDataId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoCasesetMydataPK)) {
			return false;
		}
		KgoCasesetMydataPK castOther = (KgoCasesetMydataPK)other;
		return 
			this.caseSetId.equals(castOther.caseSetId)
			&& this.myDataId.equals(castOther.myDataId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.caseSetId.hashCode();
		hash = hash * prime + this.myDataId.hashCode();
		
		return hash;
	}
}