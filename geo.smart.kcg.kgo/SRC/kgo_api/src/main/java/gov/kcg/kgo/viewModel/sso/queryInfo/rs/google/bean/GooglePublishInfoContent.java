package gov.kcg.kgo.viewModel.sso.queryInfo.rs.google.bean;


import io.swagger.annotations.ApiModel;

/**
 * WS-Z05-03a 查詢交換資訊rs.
 */
@ApiModel(description = "查詢交換資訊 PublishInfoContent rs")
public class GooglePublishInfoContent {
	
	/** google用戶資料. */
	private KcgGoogleSsoInfo kcgGoogleSsoInfo;

	public KcgGoogleSsoInfo getKcgGoogleSsoInfo() {
		return kcgGoogleSsoInfo;
	}

	public void setKcgGoogleSsoInfo(KcgGoogleSsoInfo kcgGoogleSsoInfo) {
		this.kcgGoogleSsoInfo = kcgGoogleSsoInfo;
	}
}
