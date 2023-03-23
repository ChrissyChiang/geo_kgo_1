package gov.kcg.kgo.viewModel.backend.pushmsg.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 服務推播訊息-查詢
 */
@ApiModel(description = "服務推播訊息-查詢")
public class PushMsgCaseSetQueryViewForm extends BaseViewForm {

    @ApiModelProperty(value = "服務案件編號")
    private String caseSetId;

    @ApiModelProperty(value = "服務訊息清單")
    private List<PushMsgCaseSetQueryDataGrid> pushMsgManagementHomeDataGridList;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public List<PushMsgCaseSetQueryDataGrid> getPushMsgManagementHomeDataGridList() {
        return pushMsgManagementHomeDataGridList;
    }

    public void setPushMsgManagementHomeDataGridList(List<PushMsgCaseSetQueryDataGrid> pushMsgManagementHomeDataGridList) {
        this.pushMsgManagementHomeDataGridList = pushMsgManagementHomeDataGridList;
    }

}
