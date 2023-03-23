package gov.kcg.kgo.viewModel.frontend.auth.login.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "前臺 登入後驗證資料")
public class FrontendLoginAuthInfo extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	/** sessionToken */
	@ApiModelProperty(notes = "sessionToken")
	private String sessionToken;

	/** 登入方式類別 */
	@ApiModelProperty(notes = "登入方式類別")
	private String loginAuthTokenType;

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getLoginAuthTokenType() {
		return loginAuthTokenType;
	}

	public void setLoginAuthTokenType(String loginAuthTokenType) {
		this.loginAuthTokenType = loginAuthTokenType;
	}
}
