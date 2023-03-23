package gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgUserBasic;

import gov.kcg.kgo.viewModel.sso.queryInfo.rs.base.QueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgUserBasic.bean.KcgUserBasicPublishInfoContent;

/**
 * WS-Z05-03a 查詢交換資訊 - 市府員工登入 - 用戶基本資訊 rs.
 */
public class KcgUserBasicInfoQueryInfoRs extends QueryInfoRs {
	
	/** 市府員工 - 用戶基本資訊用戶 交換資訊內容. */
	KcgUserBasicPublishInfoContent publishInfoContent;

	public KcgUserBasicPublishInfoContent getPublishInfoContent() {
		return publishInfoContent;
	}

	public void setPublishInfoContent(KcgUserBasicPublishInfoContent publishInfoContent) {
		this.publishInfoContent = publishInfoContent;
	}
}
