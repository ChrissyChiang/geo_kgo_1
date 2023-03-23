package gov.kcg.kgo.viewModel.testApi.homeAction.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 使用者Bean.
 */
@ApiModel(description = "使用者Bean")
public class UserBean {

	@ApiModelProperty(value = "使用者Id")
	private String userId = "001";
	
	@ApiModelProperty(value = "使用者名稱")
	private String userName = "Test";
	
	@ApiModelProperty(value = "員工編號")
	private String empNo = "emp001";

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
}
