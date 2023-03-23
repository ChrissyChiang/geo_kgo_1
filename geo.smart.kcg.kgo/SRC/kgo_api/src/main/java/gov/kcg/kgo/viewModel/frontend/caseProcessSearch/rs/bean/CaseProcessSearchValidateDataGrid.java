package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 案件進度查詢-查詢結果-資料集合
 */
@ApiModel(description = "案件進度查詢-查詢結果-資料集合")
public class CaseProcessSearchValidateDataGrid {

    /** 申請日期 */
    @ApiModelProperty(notes = "申請日期")
    private String applyDate;

    /** 案件編號 */
    @ApiModelProperty(notes = "案件編號")
    private String caseId;

    /** 案件名稱 */
    @ApiModelProperty(notes = "案件名稱")
    private String caseSetName;

    /** 狀態 */
    @ApiModelProperty(notes = "狀態")
    private String status;

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseSetName() {
        return caseSetName;
    }

    public void setCaseSetName(String caseSetName) {
        this.caseSetName = caseSetName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
