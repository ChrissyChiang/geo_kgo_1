package gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台 - 軌跡紀錄 - 查詢 - 登入/登出時間統計物件
 */
@ApiModel(description = "後台 - 軌跡紀錄 - 查詢 - 登入/登出時間統計物件")
public class LoginLogoutData {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="登入時間統計清單")
	List<LogLoginLogoutCount> loginCountList;
	
	@ApiModelProperty(value="登出時間統計清單")
	List<LogLoginLogoutCount> logoutCountList;

	public List<LogLoginLogoutCount> getLoginCountList() {
		return loginCountList;
	}

	public void setLoginCountList(List<LogLoginLogoutCount> loginCountList) {
		this.loginCountList = loginCountList;
	}

	public List<LogLoginLogoutCount> getLogoutCountList() {
		return logoutCountList;
	}

	public void setLogoutCountList(List<LogLoginLogoutCount> logoutCountList) {
		this.logoutCountList = logoutCountList;
	}
}
