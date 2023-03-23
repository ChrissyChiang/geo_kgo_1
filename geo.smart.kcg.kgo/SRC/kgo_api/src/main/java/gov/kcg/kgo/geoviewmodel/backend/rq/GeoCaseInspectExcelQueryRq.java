package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.geomodel.GeoCaseInspectDataModel;
import gov.kcg.kgo.service.bean.excel.GeoCaseInspectExcelVo;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211202 add
 * 後台-案件稽核管理:案件清單匯出 rq
 */
@ApiModel(description = "後台-案件稽核管理:案件清單匯出 rq")
public class GeoCaseInspectExcelQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(notes = "稽核管理案件清單")
    private List<GeoCaseInspectDataModel> dataList;

    public List<GeoCaseInspectDataModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<GeoCaseInspectDataModel> dataList) {
        this.dataList = dataList;
    }
}
