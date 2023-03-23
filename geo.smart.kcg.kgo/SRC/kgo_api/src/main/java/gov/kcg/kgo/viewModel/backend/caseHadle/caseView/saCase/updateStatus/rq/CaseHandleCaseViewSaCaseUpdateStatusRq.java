package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.updateStatus.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-案件檢視-服務申辦(SA)案件檢視-改變狀態 rq")
public class CaseHandleCaseViewSaCaseUpdateStatusRq extends ApiRequest {
    @ApiModelProperty(value = "案件編號")
    private String caseId;

    @ApiModelProperty(value = "狀態")
    private String status;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
