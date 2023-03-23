package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the KGO_CASESET_MANAGER database table.
 * 
 */
@Entity
@Table(name="KGO_CASESET_MANAGER")
@NamedQuery(name="KgoCasesetManager.findAll", query="SELECT k FROM KgoCasesetManager k")
public class KgoCasesetManager implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCasesetManagerPK id;

	public KgoCasesetManager() {
	}

	public KgoCasesetManagerPK getId() {
		return this.id;
	}

	public void setId(KgoCasesetManagerPK id) {
		this.id = id;
	}

}