package gov.kcg.kgo.viewModel.frontend.hotSearch.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 熱門查詢-初始畫面-關鍵字查詢
 */
@ApiModel(description = "熱門查詢-初始畫面-關鍵字查詢")
public class FrontendHotSearchHomeViewForm extends BaseViewForm {

    /** 關鍵字集合 **/
    @ApiModelProperty(value = "關鍵字集合")
    private List<String> keywords;

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
