package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the KGO_CASESET database table.
 * 
 */
@Entity
@Table(name = "KGO_CASESET_TYPE")
@NamedQuery(name = "KgoCasesetType.findAll", query = "SELECT k FROM KgoCasesetType k")
public class KgoCasesetType implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCasesetTypePK id;

	public KgoCasesetTypePK getId() {
		return id;
	}

	public void setId(KgoCasesetTypePK id) {
		this.id = id;
	}

}