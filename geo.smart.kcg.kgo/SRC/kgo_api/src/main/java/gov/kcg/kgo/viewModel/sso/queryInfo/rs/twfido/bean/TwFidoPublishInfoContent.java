package gov.kcg.kgo.viewModel.sso.queryInfo.rs.twfido.bean;


import io.swagger.annotations.ApiModel;

/**
 * WS-Z05-03a 查詢交換資訊rs.
 */
@ApiModel(description = "查詢交換資訊 PublishInfoContent rs")
public class TwFidoPublishInfoContent {
	
	/** Tw FidO用戶資料. */
	private KcgTwfidoSsoInfo kcgTwFidoSsoInfo;

	public KcgTwfidoSsoInfo getKcgTwFidoSsoInfo() {
		return kcgTwFidoSsoInfo;
	}

	public void setKcgTwFidoSsoInfo(KcgTwfidoSsoInfo kcgTwFidoSsoInfo) {
		this.kcgTwFidoSsoInfo = kcgTwFidoSsoInfo;
	}
}
