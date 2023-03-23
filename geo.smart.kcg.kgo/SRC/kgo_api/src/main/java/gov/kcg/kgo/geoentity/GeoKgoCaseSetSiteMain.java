package gov.kcg.kgo.geoentity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the GEO_KGO_CASESET_SITE_MAIN database table.
 */
@Entity
@Table(name = "GEO_KGO_CASESET_SITE_MAIN")
public class GeoKgoCaseSetSiteMain implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "site_main_id")
	private String siteMainId;
	
	@Column(name = "organ_id")
	private String organId;
	
	@Column(name = "unit_id")
	private String unitId;
	
	@Column(name = "site_name")
	private String siteName;
	
	@Column(name = "site_amount")
	private Integer siteAmount;
	
	@Column(name = "site_status")
	private Integer siteStatus;
	
	@Column(name = "serviceHtml")
	private String serviceHtml;
	
	@Column(name = "edit_user")
	private String editUser;
	
	@Column(name = "edit_time")
	private Timestamp editTime;

	@Column(name = "create_user")
	private String createUser;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "site_type")
	private String siteType;

	public String getSiteMainId() {
		return siteMainId;
	}

	public void setSiteMainId(String siteMainId) {
		this.siteMainId = siteMainId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Integer getSiteAmount() {
		return siteAmount;
	}

	public void setSiteAmount(Integer siteAmount) {
		this.siteAmount = siteAmount;
	}

	public Integer getSiteStatus() {
		return siteStatus;
	}

	public void setSiteStatus(Integer siteStatus) {
		this.siteStatus = siteStatus;
	}

	public String getServiceHtml() {
		return serviceHtml;
	}

	public void setServiceHtml(String serviceHtml) {
		this.serviceHtml = serviceHtml;
	}


	public String getEditUser() {
		return editUser;
	}

	public void setEditUser(String editUser) {
		this.editUser = editUser;
	}

	public Timestamp getEditTime() {
		return editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getSiteType() {return siteType;}

	public void setSiteType(String siteType) {this.siteType = siteType;}
}
