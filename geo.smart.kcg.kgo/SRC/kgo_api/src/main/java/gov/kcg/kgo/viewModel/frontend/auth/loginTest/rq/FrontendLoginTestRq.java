package gov.kcg.kgo.viewModel.frontend.auth.loginTest.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 前臺登入測試 rq.
 */
@ApiModel(description = "前臺登入測試 rq")
public class FrontendLoginTestRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "前臺登入測試  UserId")
	private String userId;

//	/** 登入方式類別 */
	@ApiModelProperty(notes = "登入方式類別，LoginAuthTokenType.getCode()")
	private String loginType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
}
