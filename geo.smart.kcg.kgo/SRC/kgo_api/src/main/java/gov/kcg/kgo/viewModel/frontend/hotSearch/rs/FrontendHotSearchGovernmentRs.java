package gov.kcg.kgo.viewModel.frontend.hotSearch.rs;

import gov.kcg.kgo.geomodel.GeoHotSearchGovernmentModel;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * 20220729 前台切換搜尋引擎
 * 市府搜尋引擎查詢結果
 */
@ApiModel(description = "熱門搜尋-市府搜尋引擎查詢結果 rs")
public class FrontendHotSearchGovernmentRs extends ApiBaseResponse {

    @ApiModelProperty(notes = "總數量")
    private Integer total;

    @ApiModelProperty(notes = "查詢結果物件")
    private List<GeoHotSearchGovernmentModel> geoHotSearchGovernmentModelList;

    @ApiModelProperty(notes = "機關個別查詢結果數量")
    private List<Map> orgCount;

    @ApiModelProperty(notes = "時間範圍內查詢結果數量")
    private List<Map> dateRange;

    @ApiModelProperty(notes = "種類數量")
    private List<Map> station;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<GeoHotSearchGovernmentModel> getGeoHotSearchGovernmentModelList() {
        return geoHotSearchGovernmentModelList;
    }

    public void setGeoHotSearchGovernmentModelList(List<GeoHotSearchGovernmentModel> geoHotSearchGovernmentModelList) {
        this.geoHotSearchGovernmentModelList = geoHotSearchGovernmentModelList;
    }

    public List<Map> getOrgCount() {
        return orgCount;
    }

    public void setOrgCount(List<Map> orgCount) {
        this.orgCount = orgCount;
    }

    public List<Map> getDateRange() {
        return dateRange;
    }

    public void setDateRange(List<Map> dateRange) {
        this.dateRange = dateRange;
    }

    public List<Map> getStation() {
        return station;
    }

    public void setStation(List<Map> station) {
        this.station = station;
    }
}
