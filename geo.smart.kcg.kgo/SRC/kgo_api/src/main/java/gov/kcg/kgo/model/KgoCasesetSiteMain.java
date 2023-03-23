package gov.kcg.kgo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the GEO_KGO_CASESET_REFUND_RATIO database table.
 * 
 */
//@Entity
//@Table(name = "GEO_KGO_CASESET_SITE_MAIN")
public class KgoCasesetSiteMain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "site_main_id")
	private Long siteMainId;

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
	
	@Column(name = "edit_time ")
	private Timestamp editTime ;
	
	@Column(name = "is_delete ")
	private Timestamp isDelete ;

	public Long getSiteMainId() {
		return siteMainId;
	}

	public void setSiteMainId(Long siteMainId) {
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



	public Timestamp getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Timestamp isDelete) {
		this.isDelete = isDelete;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	

}
