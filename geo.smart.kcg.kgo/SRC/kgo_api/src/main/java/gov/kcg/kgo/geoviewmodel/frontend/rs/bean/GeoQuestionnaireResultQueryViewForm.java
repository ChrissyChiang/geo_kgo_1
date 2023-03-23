package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoQuestionnaireResultQueryDataGridModel;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.bean.CaseManagementQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211011 add
 * 後台-問卷結果查詢:服務問卷查詢 View Form
 */
@ApiModel(description = "後台-問卷結果查詢:服務問卷查詢 View Form")
public class GeoQuestionnaireResultQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    /**
     * 服務問卷搜尋結果清單
     **/
    @ApiModelProperty(value = "列出所有服務問卷資料")
    private List<GeoQuestionnaireResultQueryDataGridModel> grid;

    public List<GeoQuestionnaireResultQueryDataGridModel> getGrid() {
        return grid;
    }

    public void setGrid(List<GeoQuestionnaireResultQueryDataGridModel> grid) {
        this.grid = grid;
    }
}
