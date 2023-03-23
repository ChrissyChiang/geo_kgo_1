package gov.kcg.kgo.viewModel.sso.queryInfo.rs.moicaCard;

import gov.kcg.kgo.viewModel.sso.queryInfo.rs.base.QueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.moicaCard.bean.MoicaCardPublishInfoContent;

/**
 * WS-Z05-03a 查詢交換資訊 - 自然人憑證 rs.
 */
public class MoicaCardQueryInfoRs extends QueryInfoRs {
	
	/** 自然人憑證用戶 交換資訊內容. */
	MoicaCardPublishInfoContent publishInfoContent;

	public MoicaCardPublishInfoContent getPublishInfoContent() {
		return publishInfoContent;
	}

	public void setPublishInfoContent(MoicaCardPublishInfoContent publishInfoContent) {
		this.publishInfoContent = publishInfoContent;
	}
}
