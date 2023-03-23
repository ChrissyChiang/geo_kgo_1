package gov.kcg.kgo.viewModel.frontend.hotSearch.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 熱門查詢-查詢
 */
@ApiModel(description = "熱門查詢-查詢")
public class HotSearchQueryViewForm extends BaseViewForm {

    /** 熱門查詢結果清單 **/
    @ApiModelProperty(value = "熱門查詢-列出所有查詢結果")
    private List<HotSearchQueryDataGrid> grids;

    /** 總頁數 **/
    @ApiModelProperty(value = "總頁數")
    private String totalPages;

    public List<HotSearchQueryDataGrid> getGrids() {
        return grids;
    }

    public void setGrids(List<HotSearchQueryDataGrid> grids) {
        this.grids = grids;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }
}
