package gov.kcg.kgo.service.bean.excel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gov.kcg.kgo.enums.backend.ApplyCaseStatusEnum;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

/**
 * GEO 20211002 add
 * 稽核管理 ExcelVo.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCaseInspectExcelVo {
    // 序號
    private Integer order;

    /** 案件編號 */
    private String caseId;

    /** 申請日期 */
    private String applyDate;

    /** 申請人員 */
    private String applyUser;

    /** 案件名稱 */
    private String caseName;

    /** 限辦日期 */
    private String deadlineDate;

    /** 案件狀態 */
    private String status;

    /** 承辦人 */
    private String caseOfficer;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

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

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
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
}
