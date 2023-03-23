package gov.kcg.kgo.viewModel.sso.queryInfo.rs.hca;

import gov.kcg.kgo.viewModel.sso.queryInfo.rs.base.QueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.hca.bean.HcaCardPublishInfoContent;

/**
 * WS-Z05-03a 查詢交換資訊 - HcaCard rs.
 */
public class HcaCardQueryInfoRs extends QueryInfoRs {
	
	/** HcaCard用戶 交換資訊內容. */
	public HcaCardPublishInfoContent publishInfoContent;

	public HcaCardPublishInfoContent getPublishInfoContent() {
		return publishInfoContent;
	}

	public void setPublishInfoContent(HcaCardPublishInfoContent publishInfoContent) {
		this.publishInfoContent = publishInfoContent;
	}
}
