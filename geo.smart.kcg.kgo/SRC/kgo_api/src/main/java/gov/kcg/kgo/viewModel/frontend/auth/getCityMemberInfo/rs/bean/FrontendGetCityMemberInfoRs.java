package gov.kcg.kgo.viewModel.frontend.auth.getCityMemberInfo.rs.bean;

import io.swagger.annotations.ApiModel;


/**
 * 前臺登入取得市民科技會員資訊rs.
 */
@ApiModel(description = "前臺登入取得市民科技會員資訊rs")
public class FrontendGetCityMemberInfoRs {
	
	private String rtnCode;
	
	private String rtnMessage;
	
	/** 市民科技會員資訊 */
	private CityMemberInfo result;

	public String getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getRtnMessage() {
		return rtnMessage;
	}

	public void setRtnMessage(String rtnMessage) {
		this.rtnMessage = rtnMessage;
	}

	public CityMemberInfo getResult() {
		return result;
	}

	public void setResult(CityMemberInfo result) {
		this.result = result;
	}
}
