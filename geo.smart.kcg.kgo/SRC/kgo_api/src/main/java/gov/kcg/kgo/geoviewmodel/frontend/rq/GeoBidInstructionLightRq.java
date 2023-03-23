package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20210814 add
 *
 * 依行政區取得路燈資料 rq
 */

@ApiModel(description = "依行政區取得路燈資料 rq")
public class GeoBidInstructionLightRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "行政區名", position=1)
    private String districtName;

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}
