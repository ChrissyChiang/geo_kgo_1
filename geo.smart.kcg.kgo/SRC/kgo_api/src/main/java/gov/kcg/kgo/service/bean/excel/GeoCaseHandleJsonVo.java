package gov.kcg.kgo.service.bean.excel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean.CaseHandleCaseViewSaCaseApplyDataDataGrid;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * GEO 20211109 add
 * 案件檢視 Json.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCaseHandleJsonVo {

    /** 隸屬案件 */
    private String caseSetId;

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

    /** 已勾選欄位清單 */
    private HashMap columnNameAndValue;

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

    public HashMap getColumnNameAndValue() {
        return columnNameAndValue;
    }

    public void setColumnNameAndValue(HashMap column) {
        this.columnNameAndValue = column;
    }

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }
}
