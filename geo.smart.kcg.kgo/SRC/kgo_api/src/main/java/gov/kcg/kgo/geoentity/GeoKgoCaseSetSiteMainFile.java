package gov.kcg.kgo.geoentity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the GEO_KGO_CASESET_SITE_MAIN database table.
 */
@Entity
@Table(name = "GEO_KGO_CASESET_SITE_MAIN_FILE")
public class GeoKgoCaseSetSiteMainFile implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "site_file_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long siteFileId;
	
	@Column(name = "site_main_id")

	private String siteMainId;
	
	@Column(name = "file_name")
	private String fileName;

	@Column(name = "isDelete")
	private Boolean isDelete;

	public Long getSiteFileId() {
		return siteFileId;
	}

	public void setSiteFileId(Long siteFileId) {
		this.siteFileId = siteFileId;
	}

	public String getSiteMainId() {
		return siteMainId;
	}

	public void setSiteMainId(String siteMainId) {
		this.siteMainId = siteMainId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isDelete() {return isDelete;}

	public void setDelete(boolean delete) {isDelete = delete;}
}
