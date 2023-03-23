package gov.kcg.kgo.viewModel.frontend.hotSearch.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 20220729 前台切換搜尋引擎
 * 熱門搜尋-顯示搜尋引擎種類
 */
@ApiModel(description = "熱門搜尋-顯示搜尋引擎種類 rs")
public class FrontendHotSearchTypeRs extends ApiBaseResponse {

    /** 搜尋引擎類別 */
    @ApiModelProperty(notes = "true:打開市府搜尋引擎,false:關掉")
    private Boolean isUseKGOSearch;

    public Boolean getUseKGOSearch() {
        return isUseKGOSearch;
    }

    public void setUseKGOSearch(Boolean useKGOSearch) {
        isUseKGOSearch = useKGOSearch;
    }
}
