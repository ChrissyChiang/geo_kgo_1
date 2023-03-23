package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 案件進度查詢-明細-案件資料
 */
@ApiModel(description = "案件進度查詢-明細-案件資料")
public class CaseProcessSearchDetailCaseset {

    /** 案件名稱 **/
    @ApiModelProperty(value = "案件名稱")
    private String casesetName;

    /** 案件狀態 **/
    @ApiModelProperty(value = "案件狀態")
    private String status;

    @ApiModelProperty(value = "案件狀態中文")
    private String statusDscr;

    /** 案件編號 **/
    @ApiModelProperty(value = "案件編號")
    private String caseId;

    /** 申請日期 **/
    @ApiModelProperty(value = "申請日期")
    private String applyDate;

    public String getStatusDscr() {
        return statusDscr;
    }

    public void setStatusDscr(String statusDscr) {
        this.statusDscr = statusDscr;
    }

    public String getCasesetName() {
        return casesetName;
    }

    public void setCasesetName(String casesetName) {
        this.casesetName = casesetName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
