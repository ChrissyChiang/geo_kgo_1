package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211026 add
 * 後台-設定代理人:查詢代理人清單 rq
 */
@ApiModel(description = "後台-設定代理人:查詢代理人清單 rq")
public class GeoAgentQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "機關id")
    private String OrganId;

    @ApiModelProperty(notes = "單位id")
    private String unitId;

    @ApiModelProperty(notes = "被代理人姓名")
    private String principalName;
    

    public String getOrganId() {
        return OrganId;
    }

    public void setOrganId(String organId) {
        this.OrganId = organId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }
}
