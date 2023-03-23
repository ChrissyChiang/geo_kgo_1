package gov.kcg.kgo.viewModel.backend.auth.login.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "登入後驗證資料")
public class BackendLoginAuthInfo extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 登入方式類別 */
	@ApiModelProperty(notes = "登入方式類別")
	private String loginAuthTokenType;

	public String getLoginAuthTokenType() {
		return loginAuthTokenType;
	}

	public void setLoginAuthTokenType(String loginAuthTokenType) {
		this.loginAuthTokenType = loginAuthTokenType;
	}
}
