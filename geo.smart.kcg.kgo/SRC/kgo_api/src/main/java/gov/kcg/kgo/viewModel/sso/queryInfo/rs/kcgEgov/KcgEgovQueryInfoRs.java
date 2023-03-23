package gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgEgov;

import gov.kcg.kgo.viewModel.sso.queryInfo.rs.base.QueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgEgov.bean.KcgEgovPublishInfoContent;

/**
 * WS-Z05-03a 查詢交換資訊 - 我的 e 政府用戶基本資訊 rs.
 */
public class KcgEgovQueryInfoRs extends QueryInfoRs {
	
	/** 我的 e 政府 交換資訊內容. */
	KcgEgovPublishInfoContent publishInfoContent;

	public KcgEgovPublishInfoContent getPublishInfoContent() {
		return publishInfoContent;
	}

	public void setPublishInfoContent(KcgEgovPublishInfoContent publishInfoContent) {
		this.publishInfoContent = publishInfoContent;
	}
}
