package gov.kcg.kgo.viewModel.backend.accountManagement.accountEditHome.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "帳號權限管理-帳號維護(新增/修改)-畫面初始 rq")
public class AccountManagementEditHomeRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	/*
	 * 使用者帳號
	 */
	@ApiModelProperty(value = "使用者帳號")
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
