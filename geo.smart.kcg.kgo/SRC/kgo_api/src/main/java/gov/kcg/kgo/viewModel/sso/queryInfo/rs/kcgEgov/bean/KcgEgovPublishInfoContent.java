package gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgEgov.bean;

/**
 * WS-Z05-03a 查詢交換資訊rs.
 */
public class KcgEgovPublishInfoContent {
	
	/** 我的 e 政府用戶資料. */
	KcgEgovInfo kcgEgovInfo;

	public KcgEgovInfo getKcgEgovInfo() {
		return kcgEgovInfo;
	}

	public void setKcgEgovInfo(KcgEgovInfo kcgEgovInfo) {
		this.kcgEgovInfo = kcgEgovInfo;
	}
}
