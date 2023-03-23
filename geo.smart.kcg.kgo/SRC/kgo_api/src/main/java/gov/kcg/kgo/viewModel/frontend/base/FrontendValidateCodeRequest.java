package gov.kcg.kgo.viewModel.frontend.base;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 前臺需驗證碼Rq 共用object.
 */
@ApiModel(description = "前臺需驗證碼 rq")
public class FrontendValidateCodeRequest extends ApiRequest  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "驗證碼", position = 0)
	private String validateCode;

	@ApiModelProperty(value = "驗證碼token", position = 0)
	private String validateCodeToken;

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getValidateCodeToken() {
		return validateCodeToken;
	}

	public void setValidateCodeToken(String validateCodeToken) {
		this.validateCodeToken = validateCodeToken;
	}
}
