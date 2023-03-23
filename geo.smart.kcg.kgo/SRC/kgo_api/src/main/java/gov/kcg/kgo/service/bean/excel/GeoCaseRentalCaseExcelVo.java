package gov.kcg.kgo.service.bean.excel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCaseRentalCaseExcelVo {
    // 序號
    private Integer order;

    /** 案件服務編號 */
    private String caseSetId;

    private String caseSetName;

    private String organId;

    private String organName;

    private String unitId;

    private String unitName;

    private String siteName;

    private String rentDate;

    private String rentDateId;

    private List<String> timeList;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getRentDateId() {
        return rentDateId;
    }

    public void setRentDateId(String rentDateId) {
        this.rentDateId = rentDateId;
    }

    public List<String> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<String> timeList) {
        this.timeList = timeList;
    }

    public String getCaseSetName() {return caseSetName;}

    public void setCaseSetName(String caseSetName) {this.caseSetName = caseSetName;}

    public String getUnitName() {return unitName;}

    public void setUnitName(String unitName) {this.unitName = unitName;}

    public String getOrganId() {return organId;}

    public void setOrganId(String organId) {this.organId = organId;}

    public String getUnitId() {return unitId;}

    public void setUnitId(String unitId) {this.unitId = unitId;}
}
