package gov.kcg.kgo.viewModel.sso.queryInfo.rs.line;

import gov.kcg.kgo.viewModel.sso.queryInfo.rs.base.QueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.line.bean.LinePublishInfoContent;

/**
 * WS-Z05-03a 查詢交換資訊 - LINE用戶基本資訊 rs.
 */
public class LineQueryInfoRs extends QueryInfoRs {
	
	/** LINE用戶基本資訊 交換資訊內容. */
	LinePublishInfoContent publishInfoContent;

	public LinePublishInfoContent getPublishInfoContent() {
		return publishInfoContent;
	}

	public void setPublishInfoContent(LinePublishInfoContent publishInfoContent) {
		this.publishInfoContent = publishInfoContent;
	}

}
