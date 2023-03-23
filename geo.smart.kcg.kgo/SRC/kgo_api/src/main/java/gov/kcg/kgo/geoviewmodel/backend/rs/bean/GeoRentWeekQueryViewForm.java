package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeokgoRentCaseSetModel;
import gov.kcg.kgo.geomodel.GeokgoRentDateInsertModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
@ApiModel(description = "後台-線上場地/活動預約編輯- 周單位表格 ViewForm")
public class GeoRentWeekQueryViewForm extends BaseViewForm {

    @ApiModelProperty(notes = "場地/活動線上預約個別場所細節")
    private GeokgoRentCaseSetModel rentCaseSetModel;
    @ApiModelProperty(notes = "場地/活動線上預約當日細節")
    private List<GeokgoRentDateInsertModel> rentDateModelList;

    public GeokgoRentCaseSetModel getRentCaseSetModel() {
        return rentCaseSetModel;
    }

    public void setRentCaseSetModel(GeokgoRentCaseSetModel rentCaseSetModel) {
        this.rentCaseSetModel = rentCaseSetModel;
    }

    public List<GeokgoRentDateInsertModel> getRentDateModelList() {
        return rentDateModelList;
    }

    public void setRentDateModelList(List<GeokgoRentDateInsertModel> rentDateModelList) {
        this.rentDateModelList = rentDateModelList;
    }
}
