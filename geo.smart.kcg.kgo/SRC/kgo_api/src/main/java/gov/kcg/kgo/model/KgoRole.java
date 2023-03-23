package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the KGO_ROLE database table.
 * 
 */
@Entity
@Table(name="KGO_ROLE")
@NamedQuery(name="KgoRole.findAll", query="SELECT k FROM KgoRole k")
public class KgoRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RoleId", unique=true, nullable=false, length=50)
	private String roleId;

	@Column(name="RoleName")
	private String roleName;

	public KgoRole() {
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}