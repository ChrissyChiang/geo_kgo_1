package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoAgentListModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211026 add
 * 後台-設定代理人:查詢代理人清單 ViewForm
 */

@ApiModel(description = "後台-設定代理人:查詢代理人清單 ViewForm")
public class GeoAgentQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "代理人清單")
    private List<GeoKgoAgentListModel> dataList;

    public List<GeoKgoAgentListModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<GeoKgoAgentListModel> dataList) {
        this.dataList = dataList;
    }
}
