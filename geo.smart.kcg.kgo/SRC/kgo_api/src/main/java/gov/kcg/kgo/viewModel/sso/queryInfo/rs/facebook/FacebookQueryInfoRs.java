package gov.kcg.kgo.viewModel.sso.queryInfo.rs.facebook;

import gov.kcg.kgo.viewModel.sso.queryInfo.rs.base.QueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.facebook.bean.FacebookPublishInfoContent;

/**
 * WS-Z05-03a 查詢交換資訊 - facebook rs.
 */
public class FacebookQueryInfoRs extends QueryInfoRs {
	
	/** facebook用戶 交換資訊內容. */
	public FacebookPublishInfoContent publishInfoContent;

	public FacebookPublishInfoContent getPublishInfoContent() {
		return publishInfoContent;
	}

	public void setPublishInfoContent(FacebookPublishInfoContent publishInfoContent) {
		this.publishInfoContent = publishInfoContent;
	}


}
