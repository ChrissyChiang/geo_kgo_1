package gov.kcg.kgo.viewModel.sso.queryInfo.rs.moicaCard.bean;

/**
 * WS-Z05-03a 查詢交換資訊rs.
 */
public class MoicaCardPublishInfoContent {
	
	/** 自然人憑證用戶資料. */
	KcgMoicaCardInfo kcgMoicaCardInfo;

	public KcgMoicaCardInfo getKcgMoicaCardInfo() {
		return kcgMoicaCardInfo;
	}

	public void setKcgMoicaCardInfo(KcgMoicaCardInfo kcgMoicaCardInfo) {
		this.kcgMoicaCardInfo = kcgMoicaCardInfo;
	}

}
