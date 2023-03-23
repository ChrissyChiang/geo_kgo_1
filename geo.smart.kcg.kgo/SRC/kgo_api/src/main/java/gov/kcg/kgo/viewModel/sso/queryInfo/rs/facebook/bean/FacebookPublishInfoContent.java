package gov.kcg.kgo.viewModel.sso.queryInfo.rs.facebook.bean;


import io.swagger.annotations.ApiModel;

/**
 * WS-Z05-03a 查詢交換資訊rs.
 */
@ApiModel(description = "查詢交換資訊 PublishInfoContent rs")
public class FacebookPublishInfoContent {
	
	/** facebook用戶資料. */
	private KcgFacebookSsoInfo kcgFacebookSsoInfo;

	public KcgFacebookSsoInfo getKcgFacebookSsoInfo() {
		return kcgFacebookSsoInfo;
	}

	public void setKcgFacebookSsoInfo(KcgFacebookSsoInfo kcgFacebookSsoInfo) {
		this.kcgFacebookSsoInfo = kcgFacebookSsoInfo;
	}
}
