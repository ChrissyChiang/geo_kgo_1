package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "案件進度-查詢 rq")
public class CaseProcessSearchQueryRq extends ApiRequest {

    @ApiModelProperty(value = "案件編號")
    private String caseId;

    @ApiModelProperty(value = "案件名稱")
    private String caseSetName;

    @ApiModelProperty(value = "申請日期起訖 : [ yyyy/MM/dd , yyyy/MM/dd ]")
    private String[] applyDate;

    @ApiModelProperty(value = "狀態")
    private String status;

//    @ApiModelProperty(value = "頁數")
//    private Integer pageNumber;
//
//    @ApiModelProperty(value = "每頁筆數")
//    private Integer pageSize;

    @ApiModelProperty(value = "非自然人憑證登入情況下身分證字號")
    private String moicaUserTwSsn;

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

    public String[] getApplyDate() {return applyDate;}

    public void setApplyDate(String[] applyDate) {this.applyDate = applyDate;}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public Integer getPageNumber() {
//        return pageNumber;
//    }
//
//    public void setPageNumber(Integer pageNumber) {
//        this.pageNumber = pageNumber;
//    }
//
//    public Integer getPageSize() {
//        return pageSize;
//    }
//
//    public void setPageSize(Integer pageSize) {
//        this.pageSize = pageSize;
//    }

    public String getMoicaUserTwSsn() {
        return moicaUserTwSsn;
    }

    public void setMoicaUserTwSsn(String moicaUserTwSsn) {
        this.moicaUserTwSsn = moicaUserTwSsn;
    }

}
