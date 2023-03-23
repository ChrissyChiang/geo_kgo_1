package gov.kcg.kgo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the KGO_CASE_DETAIL database table.
 * 
 */
@Embeddable
public class KgoCaseDetailPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "CaseId", unique = true, nullable = false, length = 50)
	private String caseId;

	@Column(name = "ColumnId", unique = true, nullable = false, length = 50)
	private String columnId;

	@Column(name = "Version", unique = true, nullable = false)
	private Integer version;

	@Column(name = "CColumnId", unique = true, nullable = false, length = 50)
	private String CColumnId;

	public KgoCaseDetailPK() {
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getColumnId() {
		return this.columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCColumnId() {
		return CColumnId;
	}

	public void setCColumnId(String cColumnId) {
		CColumnId = cColumnId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoCaseDetailPK)) {
			return false;
		}
		KgoCaseDetailPK castOther = (KgoCaseDetailPK) other;
		return this.caseId.equals(castOther.caseId) && this.columnId.equals(castOther.columnId)
				&& (this.version == castOther.version) && this.CColumnId.equals(castOther.CColumnId);
	}

	public int hashCode() {
		final Integer prime = 31;
		int hash = 17;
		hash = hash * prime + this.caseId.hashCode();
		hash = hash * prime + this.columnId.hashCode();
		hash = hash * prime + this.version;
		hash = hash * prime + this.CColumnId.hashCode();

		return hash;
	}
}