package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the KGO_CASESET_OFFICER database table.
 * 
 */
@Entity
@Table(name = "KGO_CASESET_OFFICER")
@NamedQuery(name = "KgoCasesetOfficer.findAll", query = "SELECT k FROM KgoCasesetOfficer k")
public class KgoCasesetOfficer implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCasesetOfficerPK id;

	public KgoCasesetOfficer() {
	}

	public KgoCasesetOfficerPK getId() {
		return id;
	}

	public void setId(KgoCasesetOfficerPK id) {
		this.id = id;
	}

}