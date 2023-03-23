package gov.kcg.kgo.viewModel.frontend.auth.login.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * ApiResponse 共用object.
 */
@ApiModel(description = "前臺登入 rq")
public class FrontendLoginRequest extends ApiRequest  {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "前臺登入 jwt token")
	private String jwt;
	
	@ApiModelProperty(value = "前臺登入 - 與其他系統介接交換資訊")
	private String exchange;

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
}
