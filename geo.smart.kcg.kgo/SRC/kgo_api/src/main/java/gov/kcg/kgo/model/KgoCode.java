package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the KGO_CODE database table.
 * 
 */
@Entity
@Table(name="KGO_CODE")
@NamedQuery(name="KgoCode.findAll", query="SELECT k FROM KgoCode k")
public class KgoCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCodePK id;

	@Column(name="CodeName")
	private String codeName;

	public KgoCode() {
	}

	public KgoCodePK getId() {
		return this.id;
	}

	public void setId(KgoCodePK id) {
		this.id = id;
	}

	public String getCodeName() {
		return this.codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

}