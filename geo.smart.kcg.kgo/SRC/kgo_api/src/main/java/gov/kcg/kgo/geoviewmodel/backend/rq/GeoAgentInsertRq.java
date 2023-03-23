package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211026 add
 * 後台-設定代理人:新增代理人 rq
 */
@ApiModel(description = "後台-設定代理人:新增代理人 rq")
public class GeoAgentInsertRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "被代理人id", required = true)
    private String principalUserId;

    @ApiModelProperty(notes = "代理人id", required = true)
    private String agentUserId;

    @ApiModelProperty(notes = "代理起始時間 yyyy-MM-dd", required = true)
    private String startTime;

    @ApiModelProperty(notes = "代理結束時間 yyyy-MM-dd", required = true)
    private String endTime;

    public String getPrincipalUserId() {
        return principalUserId;
    }

    public void setPrincipalUserId(String principalUserId) {
        this.principalUserId = principalUserId;
    }

    public String getAgentUserId() {
        return agentUserId;
    }

    public void setAgentUserId(String agentUserId) {
        this.agentUserId = agentUserId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
