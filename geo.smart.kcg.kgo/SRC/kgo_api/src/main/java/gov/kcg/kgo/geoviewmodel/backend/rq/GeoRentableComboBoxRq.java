package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description = "可租借時段下拉選單")
public class GeoRentableComboBoxRq extends ApiRequest {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes ="機關編號")
    private String organId;
    @ApiModelProperty(notes ="科室編號")
    private String unitId;
    @ApiModelProperty(notes ="場地/活動編號")
    private String siteMainId;
    @ApiModelProperty(notes ="預約類型")
    private String siteType;

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getSiteMainId() {
        return siteMainId;
    }

    public void setSiteMainId(String siteMainId) {
        this.siteMainId = siteMainId;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }
}
