package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.bean.CaseManagementQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台-案件關聯服務:刪除關聯案件 ViewForm
 */

@ApiModel(description = "後台-案件關聯服務:刪除關聯案件 ViewForm")
public class GeoCaseSetAssociateDeleteViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "執行結果訊息")
    private String msg;

    /** 案件搜尋結果清單 **/
    @ApiModelProperty(value = "列出所有案件資料")
    private List<CaseManagementQueryDataGrid> grid;

    public List<CaseManagementQueryDataGrid> getGrid() {
        return grid;
    }

    public void setGrid(List<CaseManagementQueryDataGrid> grid) {
        this.grid = grid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
