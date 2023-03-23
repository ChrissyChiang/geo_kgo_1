package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoLightModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20210814 add
 *
 * 依行政區取得路燈資料 ViewForm
 */
@ApiModel(description = "依行政區取得路燈資料  ViewForm")
public class GeoBidInstructionLightViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "路燈清單")
    private List<GeoKgoLightModel> lightList;

    public List<GeoKgoLightModel> getLightList() {
        return lightList;
    }

    public void setLightList(List<GeoKgoLightModel> lightList) {
        this.lightList = lightList;
    }
}
