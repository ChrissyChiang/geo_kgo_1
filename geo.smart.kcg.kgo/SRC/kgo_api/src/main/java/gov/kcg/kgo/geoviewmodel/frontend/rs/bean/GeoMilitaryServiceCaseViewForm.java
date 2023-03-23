package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoMilitaryServiceCaseModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211115 add for 民政局五種服務轉成B流程
 * 提供民政局兵役類案件資料查詢(依時間區間) ViewForm
 */
@ApiModel(description = "民政局兵役類案件資料  ViewForm")
public class GeoMilitaryServiceCaseViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "案件清單")
    private List<GeoMilitaryServiceCaseModel> caseList;

    public List<GeoMilitaryServiceCaseModel> getCaseList() {
        return caseList;
    }

    public void setCaseList(List<GeoMilitaryServiceCaseModel> caseList) {
        this.caseList = caseList;
    }
}
