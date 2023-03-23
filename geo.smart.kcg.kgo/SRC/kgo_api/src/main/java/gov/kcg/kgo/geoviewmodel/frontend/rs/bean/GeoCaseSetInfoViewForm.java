package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoCaseSetListDtoModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Geo 20220729 前台切換搜尋引擎
 * 提供目前所有上架狀態的申辦案件資料，讓其他廠商可爬蟲使用
 */
@ApiModel(description = "提供目前所有上架狀態的申辦案件資料，讓其他廠商可爬蟲使用  ViewForm")
public class GeoCaseSetInfoViewForm extends BaseViewForm {

    /** 查詢結果清單 **/
    @ApiModelProperty(value = "列出目前已上架的案件清單")
    private List<GeoCaseSetListDtoModel> caseSetList;

    public List<GeoCaseSetListDtoModel> getCaseSetList() {
        return caseSetList;
    }

    public void setCaseSetList(List<GeoCaseSetListDtoModel> caseSetList) {
        this.caseSetList = caseSetList;
    }
}
