package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KGO_ROLE_FUNCTION database table.
 * 
 */
@Embeddable
public class KgoRoleFunctionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="FunctionId", length=50)
	private String functionId;
	
	@Column(name="RoleId", unique=true, nullable=false, length=50)
	private String roleId;

	public KgoRoleFunctionPK() {
	}
	public String getRoleId() {
		return this.roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoRoleFunctionPK)) {
			return false;
		}
		KgoRoleFunctionPK castOther = (KgoRoleFunctionPK)other;
		return 
			this.roleId.equals(castOther.roleId)
			&& (functionId.equals(castOther.functionId));
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.roleId.hashCode();
		hash = hash * prime + this.functionId.hashCode();
		
		return hash;
	}
}