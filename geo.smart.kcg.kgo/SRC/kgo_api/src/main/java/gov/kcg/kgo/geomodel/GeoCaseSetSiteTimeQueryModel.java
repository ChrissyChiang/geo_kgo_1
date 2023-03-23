package gov.kcg.kgo.geomodel;

import java.io.Serializable;
import java.util.List;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Model for 線上場地租借-時段查詢 Roy
 */

@ApiModel(description = "線上場地租借-時段查詢")
public class GeoCaseSetSiteTimeQueryModel implements Serializable {
	private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "場地主檔id")
    private Long siteMainId;
       
    @ApiModelProperty(notes = "場地租借細節id")
    private Long siteDetailId;    
   
    @ApiModelProperty(notes = "最早可預約時間")
    private String earliestTime;
    
    @ApiModelProperty(notes = "最晚可遇約時間")
    private String latestTime;
    
    @ApiModelProperty(notes = "最早可預約天數")
    private Integer earliestDay;
    
    @ApiModelProperty(notes = "最晚可遇天數")
    private Integer latestDay;
    
    @ApiModelProperty(notes = "是否開放預約狀態")
    private Boolean isEnable;
    
    @ApiModelProperty(notes = "是否全天")
    private Boolean isAllDay;
    
    @ApiModelProperty(notes = "線上預約日期")
    private String siteDetailDate;    
	
	@ApiModelProperty(value = "預約時間設定")
	private List geoCasesetSiteDetailTime;
	
    @ApiModelProperty(notes = "服務類型")
    private String caseSetCategory;
    
    @ApiModelProperty(notes = "是否需要繳費")
    private Boolean needPay;     

	public Long getSiteMainId() {
		return siteMainId;
	}

	public void setSiteMainId(Long siteMainId) {
		this.siteMainId = siteMainId;
	}

	public Long getSiteDetailId() {
		return siteDetailId;
	}

	public void setSiteDetailId(Long siteDetailId) {
		this.siteDetailId = siteDetailId;
	}

	public Boolean getIsAllDay() {
		return isAllDay;
	}

	public void setIsAllDay(Boolean isAllDay) {
		this.isAllDay = isAllDay;
	}

	public String getEarliestTime() {
		return earliestTime;
	}

	public void setEarliestTime(String earliestTime) {
		this.earliestTime = earliestTime;
	}

	public String getLatestTime() {
		return latestTime;
	}

	public void setLatestTime(String latestTime) {
		this.latestTime = latestTime;
	}

	public Integer getEarliestDay() {
		return earliestDay;
	}

	public void setEarliestDay(Integer earliestDay) {
		this.earliestDay = earliestDay;
	}

	public Integer getLatestDay() {
		return latestDay;
	}

	public void setLatestDay(Integer latestDay) {
		this.latestDay = latestDay;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getSiteDetailDate() {
		return siteDetailDate;
	}

	public void setSiteDetailDate(String siteDetailDate) {
		this.siteDetailDate = siteDetailDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCaseSetCategory() {
		return caseSetCategory;
	}

	public void setCaseSetCategory(String caseSetCategory) {
		this.caseSetCategory = caseSetCategory;
	}

	public Boolean getNeedPay() {
		return needPay;
	}

	public void setNeedPay(Boolean needPay) {
		this.needPay = needPay;
	}


	

} 
