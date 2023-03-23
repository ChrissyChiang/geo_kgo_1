package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20221110 add
 * <p>
 * 跨機關callback rq
 */

@ApiModel(description = "跨機關callback rq")
public class GeoCrossOrganResultRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "caseId", required = true)
    private String caseId;

    @ApiModelProperty(value = "result", required = true)
    private Boolean result;

    @ApiModelProperty(value = "reason")
    private String reason;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
