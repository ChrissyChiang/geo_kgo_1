package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CaseProcessSearchQueryViewForm extends BaseViewForm {
    /** 案件進度查詢結果清單 **/
    @ApiModelProperty(value = "案件進度查詢-列出所有查詢結果")
    private List<CaseProcessSearchValidateDataGrid> grids;

    /** 總頁數 **/
    @ApiModelProperty(value = "總頁數")
    private String totalPages;

    public List<CaseProcessSearchValidateDataGrid> getGrids() {
        return grids;
    }

    public void setGrids(List<CaseProcessSearchValidateDataGrid> grids) {
        this.grids = grids;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }
}
