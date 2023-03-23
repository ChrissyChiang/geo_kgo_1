package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.geomodel.GeokgoRentCaseSetModel;
import gov.kcg.kgo.geomodel.GeokgoRentDateInsertModel;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "線上場地/活動預約 rq")
public class GeoKgoRentCaseRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

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
