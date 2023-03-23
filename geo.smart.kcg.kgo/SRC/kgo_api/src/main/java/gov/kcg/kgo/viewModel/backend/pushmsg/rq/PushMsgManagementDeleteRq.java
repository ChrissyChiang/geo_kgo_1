package gov.kcg.kgo.viewModel.backend.pushmsg.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "推播訊息管理-刪除 rq")
public class PushMsgManagementDeleteRq extends ApiRequest {

    @ApiModelProperty(value = "受理機關")
    private String organId;

    @ApiModelProperty(value = "案件狀態")
    private String status;

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
