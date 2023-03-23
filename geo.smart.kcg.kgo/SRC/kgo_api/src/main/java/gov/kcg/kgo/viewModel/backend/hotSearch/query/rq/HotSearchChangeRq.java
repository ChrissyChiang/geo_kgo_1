package gov.kcg.kgo.viewModel.backend.hotSearch.query.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "熱門搜尋-切換前台系統引擎 rq")
public class HotSearchChangeRq extends ApiRequest {

    @ApiModelProperty(value = "切換搜尋引擎，treu=市府引擎,false=一路通")
    private Boolean isOpenKgoSearch;

    public Boolean getOpenKgoSearch() {
        return isOpenKgoSearch;
    }

    public void setOpenKgoSearch(Boolean openKgoSearch) {
        isOpenKgoSearch = openKgoSearch;
    }
}
