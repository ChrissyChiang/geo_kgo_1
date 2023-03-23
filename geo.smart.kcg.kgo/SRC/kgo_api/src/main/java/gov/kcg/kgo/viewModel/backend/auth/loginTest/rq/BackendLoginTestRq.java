package gov.kcg.kgo.viewModel.backend.auth.loginTest.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後臺登入測試 rq.
 */
@ApiModel(description = "後臺登入測試 rq")
public class BackendLoginTestRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "後臺登入測試  UserId")
	private String userId;

	@ApiModelProperty(value = "後臺登入測試  Password")
	private String password;

	@ApiModelProperty(value = "後臺登入測試  type")
	private String loginType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
}
