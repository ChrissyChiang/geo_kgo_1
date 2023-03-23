package gov.kcg.kgo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the KGO_KCD database table.
 * 
 */
@Entity
@Table(name = "KGO_KCD")
@NamedQuery(name = "KgoKcd.findAll", query = "SELECT k FROM KgoKcd k")
public class KgoKcd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "KCD", unique = true, nullable = false, length = 30)
	private String kcd;

	@Column(name = "KCNT")
	private String kcnt;

	@Column(name = "KRMK", length = 30)
	private String krmk;

	public KgoKcd() {
	}

	public String getKcd() {
		return kcd;
	}

	public void setKcd(String kcd) {
		this.kcd = kcd;
	}

	public String getKcnt() {
		return kcnt;
	}

	public void setKcnt(String kcnt) {
		this.kcnt = kcnt;
	}

	public String getKrmk() {
		return krmk;
	}

	public void setKrmk(String krmk) {
		this.krmk = krmk;
	}
}