package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the KGO_CASESET_UNIT database table.
 * 
 */
@Entity
@Table(name = "KGO_CASESET_UNIT")
@NamedQuery(name = "KgoCasesetUnit.findAll", query = "SELECT k FROM KgoCasesetUnit k")
public class KgoCasesetUnit implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCasesetUnitPK id;

	public KgoCasesetUnit() {
	}

	public KgoCasesetUnitPK getId() {
		return id;
	}

	public void setId(KgoCasesetUnitPK id) {
		this.id = id;
	}

}