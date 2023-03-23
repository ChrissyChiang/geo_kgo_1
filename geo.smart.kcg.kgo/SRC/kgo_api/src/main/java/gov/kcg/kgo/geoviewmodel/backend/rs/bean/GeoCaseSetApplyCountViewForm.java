package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoCaseSetApplyCountRankModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211005 add
 * 服務申辦統計-取得申辦服務名次列表 ViewForm
 */

@ApiModel(description = "服務申辦統計-取得申辦服務名次列表 ViewForm")
public class GeoCaseSetApplyCountViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "申辦服務名次資料清單")
    private List<GeoCaseSetApplyCountRankModel> dataList;

    public List<GeoCaseSetApplyCountRankModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<GeoCaseSetApplyCountRankModel> dataList) {
        this.dataList = dataList;
    }
}
