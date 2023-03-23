package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台-場地建立者下拉ComboBox rq")
public class GeoSiteEditUserComboBoxQueryRq extends ApiRequest {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "科室代碼")
    private String unitId;

    public String getUnitId() {
        return unitId;
    }
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}
