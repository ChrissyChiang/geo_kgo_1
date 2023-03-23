package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KGO_CASESET_GROUP database table.
 * 
 */
@Embeddable
public class KgoCasesetGroupPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "CaseSetId", unique = true, nullable = false, length = 30)
	private String caseSetId;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GroupSeq", unique = true, nullable = false)
	private Integer groupSeq;

	@Column(name = "Version", unique = true, nullable = false)
	private Integer version;

	public KgoCasesetGroupPK() {
	}

	public String getCaseSetId() {
		return this.caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public Integer getGroupSeq() {
		return this.groupSeq;
	}

	public void setGroupSeq(Integer groupSeq) {
		this.groupSeq = groupSeq;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoCasesetGroupPK)) {
			return false;
		}
		KgoCasesetGroupPK castOther = (KgoCasesetGroupPK) other;
		return this.caseSetId.equals(castOther.caseSetId) && (this.groupSeq == castOther.groupSeq)
				&& (this.version == castOther.version);
	}

	public int hashCode() {
		final Integer prime = 31;
		int hash = 17;
		hash = hash * prime + this.caseSetId.hashCode();
		hash = hash * prime + this.groupSeq;
		hash = hash * prime + this.version;

		return hash;
	}
}