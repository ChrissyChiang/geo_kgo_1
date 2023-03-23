package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台-案件關聯服務:刪除關聯案件 rq")
public class GeoCaseSetAssociateDeleteRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "服務id", required = true)
    private String casesetId;

    @ApiModelProperty(notes = "關聯服務id", required = true)
    private String associateCasesetId;


    public String getCasesetId() {
        return casesetId;
    }

    public void setCasesetId(String casesetId) {
        this.casesetId = casesetId;
    }

    public String getAssociateCasesetId() {
        return associateCasesetId;
    }

    public void setAssociateCasesetId(String associateCasesetId) {
        this.associateCasesetId = associateCasesetId;
    }
}
