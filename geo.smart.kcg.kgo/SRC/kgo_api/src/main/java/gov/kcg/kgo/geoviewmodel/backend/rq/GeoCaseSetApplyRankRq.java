package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.geomodel.GeoCaseSetApplyRankModel;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211005 add
 * 服務申辦統計-儲存申辦服務名次 rq
 */

@ApiModel(description = "服務申辦統計-儲存申辦服務名次 rq")
public class GeoCaseSetApplyRankRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "申辦服務名次對應清單", required = true)
    private List<GeoCaseSetApplyRankModel> dataList;

    public List<GeoCaseSetApplyRankModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<GeoCaseSetApplyRankModel> dataList) {
        this.dataList = dataList;
    }
}
