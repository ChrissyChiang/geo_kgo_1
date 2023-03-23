package gov.kcg.kgo.viewModel.backend.auth.login.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 後臺登入 rq.
 */
@ApiModel(description = "後臺登入 rq")
public class BackendLoginRq extends ApiRequest  {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "後臺登入 jwt token")
	private String jwt;
	

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
