package gov.kcg.kgo.viewModel.sso.queryInfo.rs.hca.bean;


import io.swagger.annotations.ApiModel;

/**
 * WS-Z05-03a 查詢交換資訊rs.
 */
@ApiModel(description = "查詢交換資訊 PublishInfoContent rs")
public class HcaCardPublishInfoContent {
	
	/** HcaCard用戶資料. */
	private KcgHcaCardSsoInfo kcgHcaCardSsoInfo;

	public KcgHcaCardSsoInfo getKcgHcaCardSsoInfo() {
		return kcgHcaCardSsoInfo;
	}

	public void setKcgHcaCardSsoInfo(KcgHcaCardSsoInfo kcgHcaCardSsoInfo) {
		this.kcgHcaCardSsoInfo = kcgHcaCardSsoInfo;
	}
}
