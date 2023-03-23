package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the KGO_CASESET_CHECK database table.
 * 
 */
@Entity
@Table(name = "KGO_CASESET_CHECK")
@NamedQuery(name = "KgoCasesetCheck.findAll", query = "SELECT k FROM KgoCasesetCheck k")
public class KgoCasesetCheck implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCasesetCheckPK id;

	public KgoCasesetCheck() {
	}

	public KgoCasesetCheckPK getId() {
		return id;
	}

	public void setId(KgoCasesetCheckPK id) {
		this.id = id;
	}

}