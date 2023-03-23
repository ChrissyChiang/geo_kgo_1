package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rq;

import gov.kcg.kgo.viewModel.frontend.base.FrontendValidateCodeRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "案件進度-檢核 rq")
public class CaseProcessSearchDetailCheckRq extends FrontendValidateCodeRequest {

    @ApiModelProperty(value = "案件編號")
    private String gstrCaseId;

    @ApiModelProperty(value = "身分證後四碼")
    private String idCheck;

    @ApiModelProperty(value = "身分證")
    private String idCheckFull;

    @ApiModelProperty(value = "電話")
    private String phone;

    public String getGstrCaseId() {
        return gstrCaseId;
    }

    public void setGstrCaseId(String gstrCaseId) {
        this.gstrCaseId = gstrCaseId;
    }

    public String getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(String idCheck) {
        this.idCheck = idCheck;
    }

    public String getIdCheckFull() {
        return idCheckFull;
    }

    public void setIdCheckFull(String idCheckFull) {
        this.idCheckFull = idCheckFull;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
