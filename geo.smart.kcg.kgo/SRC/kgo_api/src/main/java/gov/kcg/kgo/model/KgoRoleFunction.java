package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the KGO_ROLE_FUNCTION database table.
 * 
 */
@Entity
@Table(name="KGO_ROLE_FUNCTION")
@NamedQuery(name="KgoRoleFunction.findAll", query="SELECT k FROM KgoRoleFunction k")
public class KgoRoleFunction implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoRoleFunctionPK id;

	public KgoRoleFunction() {
	}

	public KgoRoleFunctionPK getId() {
		return this.id;
	}

	public void setId(KgoRoleFunctionPK id) {
		this.id = id;
	}

}