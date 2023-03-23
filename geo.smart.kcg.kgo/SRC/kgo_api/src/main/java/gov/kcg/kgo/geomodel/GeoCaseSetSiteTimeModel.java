package gov.kcg.kgo.geomodel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Model for 場地案件檢視(匯出EXCEL) Roy
 */

@ApiModel(description = "場地案件檢視(匯出EXCEL)")
public class GeoCaseSetSiteTimeModel implements Serializable {
	private static final long serialVersionUID = 1L;

    @ApiModelProperty
    private Integer seq;
    
	@ApiModelProperty
    private String caseId;
    
    @ApiModelProperty
    private String applyDate;    
 
    @ApiModelProperty
    private String applyUserName;
    
    @ApiModelProperty
    private String caseSetName;    

    @ApiModelProperty
    private String siteName;
    
    @ApiModelProperty
    private String startTime;
    
    @ApiModelProperty
    private String status;
    
    @ApiModelProperty
    private String caseOfficer;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCaseOfficer() {
		return caseOfficer;
	}

	public void setCaseOfficer(String caseOfficer) {
		this.caseOfficer = caseOfficer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}  
    
} 
