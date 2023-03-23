package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20220711 add for 智能客服
 * 提供案件服務清單
 */
@ApiModel(description = "提供案件服務清單 rq")
public class GeoGetCaseSetListActionRq extends ApiCityCommonRequest {

    @ApiModelProperty(value = "服務案件關鍵字，空字串代表查詢全部")
    private String gstrKeyword;

    public String getGstrKeyword() {
        return gstrKeyword;
    }

    public void setGstrKeyword(String gstrKeyword) {
        this.gstrKeyword = gstrKeyword;
    }
}
