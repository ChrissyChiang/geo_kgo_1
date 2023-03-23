package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KGO_CODE database table.
 * 
 */
@Embeddable
public class KgoCodePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CodeType", unique=true, nullable=false, length=30)
	private String codeType;

	@Column(name="CodeId", unique=true, nullable=false, length=50)
	private String codeId;

	public KgoCodePK() {
	}
	public String getCodeType() {
		return this.codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getCodeId() {
		return this.codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoCodePK)) {
			return false;
		}
		KgoCodePK castOther = (KgoCodePK)other;
		return 
			this.codeType.equals(castOther.codeType)
			&& this.codeId.equals(castOther.codeId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codeType.hashCode();
		hash = hash * prime + this.codeId.hashCode();
		
		return hash;
	}
}