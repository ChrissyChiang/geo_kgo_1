package gov.kcg.kgo.viewModel.testApi.homeAction.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 功能首頁 ViewForm
 */
@ApiModel(description = "測試功能首頁 ViewForm")
public class TestHomeActionRsViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 人員清單. */
	@ApiModelProperty(value = "人員清單")
	private List<UserBean> userList;

	public List<UserBean> getUserList() {
		return userList;
	}

	public void setUserList(List<UserBean> userList) {
		this.userList = userList;
	}
}
	