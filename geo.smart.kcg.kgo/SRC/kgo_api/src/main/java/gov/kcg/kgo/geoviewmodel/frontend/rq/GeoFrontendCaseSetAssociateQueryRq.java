package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211202 add
 * 前台-關聯服務: 取得關聯服務 rq
 */

@ApiModel(description = "前台-關聯服務: 取得關聯服務 rq")
public class GeoFrontendCaseSetAssociateQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "服務id")
    private String caseSetId;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }
}
