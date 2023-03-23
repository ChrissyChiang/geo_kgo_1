package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the KGO_CASE_MANAGER database table.
 * 
 */
@Entity
@Table(name = "KGO_CASE_MANAGER")
@NamedQuery(name = "KgoCaseManager.findAll", query = "SELECT k FROM KgoCaseManager k")
public class KgoCaseManager implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCaseManagerPK id;

	public KgoCaseManager() {
	}

	public KgoCaseManagerPK getId() {
		return id;
	}

	public void setId(KgoCaseManagerPK id) {
		this.id = id;
	}

}