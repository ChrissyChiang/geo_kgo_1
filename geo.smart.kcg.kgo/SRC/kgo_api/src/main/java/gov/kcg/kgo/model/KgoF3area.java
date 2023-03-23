package gov.kcg.kgo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the KGO_COUNTY database table.
 * 
 */
@Entity
@Table(name = "KGO_F3AREA")
@NamedQuery(name = "KgoF3area.findAll", query = "SELECT k FROM KgoF3area k")
public class KgoF3area implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "F3Seq", unique = true, nullable = false, length = 30)
	private String f3Seq;

	@Column(name = "F3Name", length = 200)
	private String f3Name;

	public String getF3Seq() {
		return f3Seq;
	}

	public void setF3Seq(String f3Seq) {
		this.f3Seq = f3Seq;
	}

	public String getF3Name() {
		return f3Name;
	}

	public void setF3Name(String f3Name) {
		this.f3Name = f3Name;
	}

}
