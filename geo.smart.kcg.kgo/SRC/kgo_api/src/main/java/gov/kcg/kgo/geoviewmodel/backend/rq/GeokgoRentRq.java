package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務案件-線上場地預約預設編輯畫面
 * */
@ApiModel(description = "線上場地/活動預約 rq")
public class GeokgoRentRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "服務案件編號")
    private String caseSetId;

    @ApiModelProperty(notes ="科室編號")
    private String unitId;
    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getUnitId() {return unitId;}

    public void setUnitId(String unitId) {this.unitId = unitId;}
}
