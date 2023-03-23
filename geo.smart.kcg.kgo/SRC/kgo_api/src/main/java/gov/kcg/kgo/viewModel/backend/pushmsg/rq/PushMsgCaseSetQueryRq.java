package gov.kcg.kgo.viewModel.backend.pushmsg.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "服務推播訊息-查詢 rq")
public class PushMsgCaseSetQueryRq extends ApiRequest {

    @ApiModelProperty(value = "服務案件編號")
    private String caseSetId;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

}
