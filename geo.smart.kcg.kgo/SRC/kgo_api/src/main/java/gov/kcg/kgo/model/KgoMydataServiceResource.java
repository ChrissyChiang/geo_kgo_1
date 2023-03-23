package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the KGO_MYDATA_SERVICE_RESOURCE database table.
 * 
 */
@Entity
@Table(name="KGO_MYDATA_SERVICE_RESOURCE")
@NamedQuery(name="KgoMydataServiceResource.findAll", query="SELECT k FROM KgoMydataServiceResource k")
public class KgoMydataServiceResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoMydataServiceResourcePK id;

	public KgoMydataServiceResource() {
	}

	public KgoMydataServiceResourcePK getId() {
		return this.id;
	}

	public void setId(KgoMydataServiceResourcePK id) {
		this.id = id;
	}

}