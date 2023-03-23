package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KGO_MYDATA_SERVICE_RESOURCE database table.
 * 
 */
@Embeddable
public class KgoMydataServiceResourcePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ClientId", unique=true, nullable=false)
	private String clientId;

	@Column(name="MyDataId", unique=true, nullable=false)
	private String myDataId;

	public KgoMydataServiceResourcePK() {
	}
	public String getClientId() {
		return this.clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
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
		if (!(other instanceof KgoMydataServiceResourcePK)) {
			return false;
		}
		KgoMydataServiceResourcePK castOther = (KgoMydataServiceResourcePK)other;
		return 
			this.clientId.equals(castOther.clientId)
			&& this.myDataId.equals(castOther.myDataId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.clientId.hashCode();
		hash = hash * prime + this.myDataId.hashCode();
		
		return hash;
	}
}