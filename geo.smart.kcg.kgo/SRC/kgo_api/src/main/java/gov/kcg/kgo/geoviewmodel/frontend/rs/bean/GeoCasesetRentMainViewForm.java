package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoCasesetRentModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
@ApiModel(description = "前台-線上預約:取得場地活動服務案件 ViewForm")
public class GeoCasesetRentMainViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;
    private List<GeoKgoCasesetRentModel> rentMainList;

    @ApiModelProperty(name="服務案件清單")
    public List<GeoKgoCasesetRentModel> getRentMainList() {
        return rentMainList;
    }

    public void setRentMainList(List<GeoKgoCasesetRentModel> rentMainList) {
        this.rentMainList = rentMainList;
    }
}
