package gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgUserBasic.bean;

/**
 * WS-Z05-03a 查詢交換資訊rs.
 */
public class KcgUserBasicPublishInfoContent {
	
	/** 市府員工 - 用戶基本資訊. */
	KcgUserBasicInfo kcgUserBasicInfo;

	public KcgUserBasicInfo getKcgUserBasicInfo() {
		return kcgUserBasicInfo;
	}

	public void setKcgUserBasicInfo(KcgUserBasicInfo kcgUserBasicInfo) {
		this.kcgUserBasicInfo = kcgUserBasicInfo;
	}
}
