package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the KGO_USER_ROLE database table.
 * 
 */
@Entity
@Table(name="KGO_USER_ROLE")
@NamedQuery(name="KgoUserRole.findAll", query="SELECT k FROM KgoUserRole k")
public class KgoUserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoUserRolePK id;

	public KgoUserRole() {
	}

	public KgoUserRolePK getId() {
		return this.id;
	}

	public void setId(KgoUserRolePK id) {
		this.id = id;
	}

}