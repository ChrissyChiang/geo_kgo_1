package gov.kcg.kgo.geomodel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Model for 線上場地查詢 Roy
 */

@ApiModel(description = "線上場地查詢")
public class GeoCaseSetSiteMainModel implements Serializable {
	private static final long serialVersionUID = 1L;

    private String siteMainId;
    private String organId;
    private String unitId;
    private String siteName;
    private Integer siteAmount;
    private Integer siteStatus;
    private String serviceHtml;
    private String editUser;
    private Timestamp editTime;
    private String editUserName;
    private String organName;
    private String unitName;

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
	public String getEditUserName() {
		return editUserName;
	}
	public void setEditUserName(String editUserName) {
		this.editUserName = editUserName;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
} 
