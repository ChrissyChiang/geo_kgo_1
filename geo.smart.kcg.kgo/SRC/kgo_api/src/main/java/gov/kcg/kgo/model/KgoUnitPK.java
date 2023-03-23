package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KGO_UNIT database table.
 * 
 */
@Embeddable
public class KgoUnitPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="OrganId", unique=true, nullable=false, length=50)
	private String organId;

	@Column(name="UnitId", unique=true, nullable=false, length=50)
	private String unitId;

	public KgoUnitPK() {
	}
	public String getOrganId() {
		return this.organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getUnitId() {
		return this.unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoUnitPK)) {
			return false;
		}
		KgoUnitPK castOther = (KgoUnitPK)other;
		return 
			this.organId.equals(castOther.organId)
			&& this.unitId.equals(castOther.unitId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.organId.hashCode();
		hash = hash * prime + this.unitId.hashCode();
		
		return hash;
	}
}