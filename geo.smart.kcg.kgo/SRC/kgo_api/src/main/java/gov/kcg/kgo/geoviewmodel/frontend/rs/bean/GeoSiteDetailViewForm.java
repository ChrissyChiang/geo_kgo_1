package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoCaseSetSiteDetailModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel(value ="場地資訊")
public class GeoSiteDetailViewForm  extends BaseViewForm {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "場地詳細資訊")
    private List<GeoCaseSetSiteDetailModel>siteDetail;

    public List<GeoCaseSetSiteDetailModel> getSiteDetail() {
        return siteDetail;
    }

    public void setSiteDetail(List<GeoCaseSetSiteDetailModel> siteDetail) {
        this.siteDetail = siteDetail;
    }
}
