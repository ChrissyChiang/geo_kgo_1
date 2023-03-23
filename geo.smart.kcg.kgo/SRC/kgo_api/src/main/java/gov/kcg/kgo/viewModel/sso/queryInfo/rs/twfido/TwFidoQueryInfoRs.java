package gov.kcg.kgo.viewModel.sso.queryInfo.rs.twfido;

import gov.kcg.kgo.viewModel.sso.queryInfo.rs.base.QueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.google.bean.GooglePublishInfoContent;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.twfido.bean.TwFidoPublishInfoContent;

/**
 * WS-Z05-03a 查詢交換資訊 - TwFido rs.
 */
public class TwFidoQueryInfoRs extends QueryInfoRs {
	
	/** TW FidO用戶 交換資訊內容. */
	public TwFidoPublishInfoContent publishInfoContent;

	public TwFidoPublishInfoContent getPublishInfoContent() {
		return publishInfoContent;
	}

	public void setPublishInfoContent(TwFidoPublishInfoContent publishInfoContent) {
		this.publishInfoContent = publishInfoContent;
	}
}
