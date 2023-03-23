package gov.kcg.kgo.viewModel.frontend.hotSearch.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "熱門搜尋-查詢 rq")
public class HotSearchQueryRq extends ApiRequest {

    @ApiModelProperty(value = "關鍵字")
    private String gstrKeyword;

    @ApiModelProperty(value = "頁數")
    private Integer pageNumber;

    @ApiModelProperty(value = "每頁筆數")
    private Integer pageSize;

    public String getGstrKeyword() {
        return gstrKeyword;
    }

    public void setGstrKeyword(String gstrKeyword) {
        this.gstrKeyword = gstrKeyword;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
