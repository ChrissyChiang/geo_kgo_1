package gov.kcg.kgo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the KGO_ZIP database table.
 * 
 */
@Entity
@Table(name = "KGO_ZIP")
@NamedQuery(name = "KgoZip.findAll", query = "SELECT k FROM KgoZip k")
public class KgoZip implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ZIP", unique = true, nullable = false, length = 30)
	private String zip;

	@Column(name = "CountyId", length = 30)
	private String countyId;

	@Column(name = "ZIPName", length = 200)
	private String ZIPName;

	@Column(name = "KRMK", length = 30)
	private String krmk;

	@Column(name = "F3Seq", length = 30)
	private String f3Seq;

	public KgoZip() {
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountyId() {
		return this.countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public String getZIPName() {
		return this.ZIPName;
	}

	public void setZIPName(String ZIPName) {
		this.ZIPName = ZIPName;
	}

	public String getKrmk() {
		return krmk;
	}

	public void setKrmk(String krmk) {
		this.krmk = krmk;
	}

	public String getF3Seq() {
		return f3Seq;
	}

	public void setF3Seq(String f3Seq) {
		this.f3Seq = f3Seq;
	}

}