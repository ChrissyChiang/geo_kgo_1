package gov.kcg.kgo.viewModel.backend.pushmsg.rq;

import gov.kcg.kgo.viewModel.backend.pushmsg.rs.bean.PushMsgCaseSetEditDataGrid;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "服務推播訊息-編輯 rq")
public class PushMsgCaseSetEditRq extends ApiRequest {

    @ApiModelProperty(value = "服務案件編號")
    private String caseSetId;

    @ApiModelProperty(value = "服務訊息編輯清單")
    private List<PushMsgCaseSetEditDataGrid> pushMsgCaseSetEditDataGridList;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public List<PushMsgCaseSetEditDataGrid> getPushMsgCaseSetEditDataGridList() {
        return pushMsgCaseSetEditDataGridList;
    }

    public void setPushMsgCaseSetEditDataGridList(List<PushMsgCaseSetEditDataGrid> pushMsgCaseSetEditDataGridList) {
        this.pushMsgCaseSetEditDataGridList = pushMsgCaseSetEditDataGridList;
    }

}
