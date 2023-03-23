package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the KGO_CASESET_TASK database table.
 * 
 */
@Entity
@Table(name="KGO_CASESET_TASK")
@NamedQuery(name="KgoCasesetTask.findAll", query="SELECT k FROM KgoCasesetTask k")
public class KgoCasesetTask implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCasesetTaskPK id;

	public KgoCasesetTask() {
	}

	public KgoCasesetTaskPK getId() {
		return this.id;
	}

	public void setId(KgoCasesetTaskPK id) {
		this.id = id;
	}

}