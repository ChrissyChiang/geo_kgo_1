package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the KGO_CASESET_AREA database table.
 * 
 */
@Entity
@Table(name="KGO_CASESET_AREA")
@NamedQuery(name="KgoCasesetArea.findAll", query="SELECT k FROM KgoCasesetArea k")
public class KgoCasesetArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCasesetAreaPK id;

	@Column(name="ZIP", length=500)
	private String zip;

	public KgoCasesetArea() {
	}

	public KgoCasesetAreaPK getId() {
		return this.id;
	}

	public void setId(KgoCasesetAreaPK id) {
		this.id = id;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}