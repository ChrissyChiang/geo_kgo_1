package gov.kcg.kgo.viewModel.backend.auth.getUserInfo.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.common.bean.ApiLoginUserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 取得使用者資訊.
 */
@ApiModel(description = "取得使用者資訊  rs")
public class BackendGetLoginUserInfoViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "使用者資訊 model")
	private ApiLoginUserInfo userInfo;

	@ApiModelProperty(notes = "遇時參數")
	private String timeout;

	public ApiLoginUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(ApiLoginUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
}
