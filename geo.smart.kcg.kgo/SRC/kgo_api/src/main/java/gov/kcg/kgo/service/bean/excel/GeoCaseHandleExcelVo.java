package gov.kcg.kgo.service.bean.excel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean.CaseHandleCaseViewSaCaseApplyDataDataGrid;

import java.util.List;

/**
 * GEO 20211105 add
 * 案件檢視 ExcelVo.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCaseHandleExcelVo {
    // 序號
    private Integer order;

    /** 案件服務編號 */
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

    /** 登入來源 */
    private String login;

    /** 服務案件編號版本 */
    private String version;

    /** 已勾選欄位清單 || 動態欄位表單 */
    private List<CaseHandleCaseViewSaCaseApplyDataDataGrid> dataGrids;

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

    public List<CaseHandleCaseViewSaCaseApplyDataDataGrid> getDataGrids() {
        return dataGrids;
    }

    public void setDataGrids(List<CaseHandleCaseViewSaCaseApplyDataDataGrid> dataGrids) {
        this.dataGrids = dataGrids;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
