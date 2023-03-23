package gov.kcg.kgo.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import gov.kcg.kgo.model.KgoF3area;
import gov.kcg.kgo.model.KgoZip;

public class KgoZipF3AreaDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ZIP", unique = true, nullable = false, length = 30)
	private String zip;

	@Column(name = "ZIPName", length = 200)
	private String ZIPName;

	@Column(name = "F3Seq", length = 30)
	private String f3Seq;

	@Column(name = "F3Name", length = 200)
	private String f3Name;

	public KgoZipF3AreaDto() {
		super();
	}

	public KgoZipF3AreaDto(KgoZip ZIP, KgoF3area f3Area) {
		super();
		this.zip = ZIP.getZip();
		this.ZIPName = ZIP.getZIPName();
		this.f3Seq = f3Area.getF3Seq();
		this.f3Name = f3Area.getF3Name();
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

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

	public String getZIPName() {
		return ZIPName;
	}

	public void setZIPName(String zIPName) {
		ZIPName = zIPName;
	}

}
