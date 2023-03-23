package gov.kcg.kgo.viewModel.sso.queryInfo.rs.google;

import gov.kcg.kgo.viewModel.sso.queryInfo.rs.base.QueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.google.bean.GooglePublishInfoContent;

/**
 * WS-Z05-03a 查詢交換資訊 - google rs.
 */
public class GoogleQueryInfoRs extends QueryInfoRs {
	
	/** google用戶 交換資訊內容. */
	public GooglePublishInfoContent publishInfoContent;

	public GooglePublishInfoContent getPublishInfoContent() {
		return publishInfoContent;
	}

	public void setPublishInfoContent(GooglePublishInfoContent publishInfoContent) {
		this.publishInfoContent = publishInfoContent;
	}
}
