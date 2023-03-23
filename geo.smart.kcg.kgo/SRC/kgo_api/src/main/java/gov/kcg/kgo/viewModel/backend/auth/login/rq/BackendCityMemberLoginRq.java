package gov.kcg.kgo.viewModel.backend.auth.login.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * GEO 20211115 add 非市府員工登入後臺
 * 非市府員工登入後臺 rq.
 */
@ApiModel(description = "非市府員工登入後臺 rq")
public class BackendCityMemberLoginRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "後臺登入 jwt token")
    private String jwt;

    @ApiModelProperty(value = "後臺登入 - 與其他系統介接交換資訊")
    private String exchange;

    public BackendCityMemberLoginRq(String exchange) {
        this.exchange = exchange;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getExchange() {
        return exchange;
    }
}
