package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.bean.CaseManagementQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rs.bean.BidCaseListQueryDataGrid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃:刪除黑名單 ViewForm
 */

@ApiModel(description = "後台-線上預約臨櫃:刪除黑名單  ViewForm")
public class GeoCaseOpenForOrganQueryActionViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    /** 案件搜尋結果清單 **/
    @ApiModelProperty(value = "列出所有案件資料")
    private List<CaseManagementQueryDataGrid> grid;

    @ApiModelProperty(value = "caseApply歷程圖")
    private String caseImage;

    @ApiModelProperty(value = "authApply歷程圖")
    private String authImage;

    public String getCaseImage() {
        return caseImage;
    }

    /** 申辦案件資料集合 **/
    @ApiModelProperty(value = "申辦案件資料集合")
    private List<BidCaseListQueryDataGrid> dataGrid;

    public void setCaseImage(String caseImage) {
        this.caseImage = caseImage;
    }

    public String getAuthImage() {
        return authImage;
    }

    public void setAuthImage(String authImage) {
        this.authImage = authImage;
    }

    public List<CaseManagementQueryDataGrid> getGrid() {
        return grid;
    }

    public void setGrid(List<CaseManagementQueryDataGrid> grid) {
        this.grid = grid;
    }

    public List<BidCaseListQueryDataGrid> getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(List<BidCaseListQueryDataGrid> dataGrid) {
        this.dataGrid = dataGrid;
    }
}
