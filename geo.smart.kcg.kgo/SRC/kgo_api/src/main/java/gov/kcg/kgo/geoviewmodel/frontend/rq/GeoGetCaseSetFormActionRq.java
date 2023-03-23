package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20220711 add for 智能客服
 * 提供智能客服對應表單欄位
 */
@ApiModel(description = "提供智能客服對應表單欄位 rq")
public class GeoGetCaseSetFormActionRq extends ApiCityCommonRequest {

    @ApiModelProperty(value = "服務案件Id")
    private String caseSetId;

    @ApiModelProperty(value = "申請者登入方式 NAN：未登入,MOICA：自然人憑證,TW_FIDO:TW_FIDO登入,LINE:LINE登入,FACEBOOK:FACEBOOK登入,GOOGLE:Google登入,EGOV:我的e 政府,HCA:健保卡")
    private String loginType;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
