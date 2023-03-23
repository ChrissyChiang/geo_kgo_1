package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.bean.CaseManagementQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211015 add
 * 後台-案件關聯服務:案件查詢 ViewForm
 */

@ApiModel(description = "後台-案件關聯服務:案件查詢  ViewForm")
public class GeoCaseSetAssociateQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    /** 案件搜尋結果清單 **/
    @ApiModelProperty(value = "列出所有案件資料")
    private List<CaseManagementQueryDataGrid> grid;

    public List<CaseManagementQueryDataGrid> getGrid() {
        return grid;
    }

    public void setGrid(List<CaseManagementQueryDataGrid> grid) {
        this.grid = grid;
    }
}
