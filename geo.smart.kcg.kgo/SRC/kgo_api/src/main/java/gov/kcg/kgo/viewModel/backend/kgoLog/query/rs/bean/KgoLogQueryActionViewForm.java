package gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 前/後台 - 軌跡紀錄 - 查詢 ViewForm
 */
@ApiModel(description = "後台 - 軌跡紀錄 - 查詢  ViewForm")
public class KgoLogQueryActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="身分證驗證統計")
	List<LogLoginTypeCount> loginTypeCountList;

	@ApiModelProperty(value="登入/登出時間統計物件")
	LoginLogoutData loginLogoutData;

	@ApiModelProperty(value="登入IP (TOP10)")
	List<LogLoninIpCount> top10IpCountList;

	@ApiModelProperty(value="後台: 功能使用分析、前台: 案件申辦統計(TOP10)")
	List<LogFuncOrServiceCount> top10FuncOrServiceCountList;


	public List<LogLoginTypeCount> getLoginTypeCountList() {
		return loginTypeCountList;
	}

	public void setLoginTypeCountList(List<LogLoginTypeCount> loginTypeCountList) {
		this.loginTypeCountList = loginTypeCountList;
	}

	public LoginLogoutData getLoginLogoutData() {
		return loginLogoutData;
	}

	public void setLoginLogoutData(LoginLogoutData loginLogoutData) {
		this.loginLogoutData = loginLogoutData;
	}

	public List<LogLoninIpCount> getTop10IpCountList() {
		return top10IpCountList;
	}

	public void setTop10IpCountList(List<LogLoninIpCount> top10IpCountList) {
		this.top10IpCountList = top10IpCountList;
	}

	public List<LogFuncOrServiceCount> getTop10FuncOrServiceCountList() {
		return top10FuncOrServiceCountList;
	}

	public void setTop10FuncOrServiceCountList(List<LogFuncOrServiceCount> top10FuncOrServiceCountList) {
		this.top10FuncOrServiceCountList = top10FuncOrServiceCountList;
	}
}
