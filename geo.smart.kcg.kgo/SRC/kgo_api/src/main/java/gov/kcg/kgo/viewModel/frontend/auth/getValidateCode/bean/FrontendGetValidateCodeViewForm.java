package gov.kcg.kgo.viewModel.frontend.auth.getValidateCode.bean;

import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "前臺 取得驗證碼 ViewForm")
public class FrontendGetValidateCodeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	/** 驗證碼 */
	@ApiModelProperty(notes = "驗證碼")
	String validateCode = StringUtils.EMPTY;
	
	/** 驗證碼token */
	@ApiModelProperty(notes = "驗證碼token")
	String validateCodeToken = StringUtils.EMPTY;

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
