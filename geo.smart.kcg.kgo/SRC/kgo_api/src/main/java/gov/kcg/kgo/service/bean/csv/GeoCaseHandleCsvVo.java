package gov.kcg.kgo.service.bean.csv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;

/**
 * GEO 20211129 add
 * 案件檢視 Csv
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCaseHandleCsvVo {

    /** 案件名稱 */
    private String subject;

    /** 案件編號 */
    private String caseId;

    /** 地點 */
    private String location ;

    /** 活動起始日期 */
    private String startDate ;

    /** 活動結束日期 */
    private String endDate ;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
