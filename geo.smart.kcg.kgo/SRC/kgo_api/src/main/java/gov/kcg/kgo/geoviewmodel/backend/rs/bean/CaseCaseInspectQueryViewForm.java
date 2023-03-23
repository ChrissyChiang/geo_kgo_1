package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoCaseInspectDataModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * GEO 20211030 add
 * 後台-案件稽核管理:案件查詢 ViewForm
 */
@ApiModel(description = "後台-案件稽核管理:案件查詢")
public class CaseCaseInspectQueryViewForm extends BaseViewForm {

    @ApiModelProperty(notes = "案件清單")
    private List<GeoCaseInspectDataModel> dataList;

    public List<GeoCaseInspectDataModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<GeoCaseInspectDataModel> dataList) {
        this.dataList = dataList;
    }
}
