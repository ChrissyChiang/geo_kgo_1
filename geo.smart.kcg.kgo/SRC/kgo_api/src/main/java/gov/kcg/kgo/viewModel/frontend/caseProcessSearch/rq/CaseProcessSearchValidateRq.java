package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "案件進度-驗證 rq")
public class CaseProcessSearchValidateRq extends ApiRequest {

    @ApiModelProperty(value = "申請編號")
    private String caseId;

    @ApiModelProperty(value = "身分證後四碼")
    private String idCheck;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(String idCheck) {
        this.idCheck = idCheck;
    }
}
