package gov.kcg.kgo.viewModel.sso.queryInfo.rq;

/**
 * WS-Z05-03a : 查詢交換資訊 rq.
 */
public class WsZ0503aRq {
	/** 資訊查詢者的授權應用權杖 */
	String privilegedAppToken;
	
	/** 資訊格式代碼一 */
	String publishInfoKey1;
	
	/** 資訊格式代碼二 */
	String publishInfoKey2;
	
	/** 資訊格式代碼三 */
	String publishInfoKey3;

	public String getPrivilegedAppToken() {
		return privilegedAppToken;
	}

	public void setPrivilegedAppToken(String privilegedAppToken) {
		this.privilegedAppToken = privilegedAppToken;
	}

	public String getPublishInfoKey1() {
		return publishInfoKey1;
	}

	public void setPublishInfoKey1(String publishInfoKey1) {
		this.publishInfoKey1 = publishInfoKey1;
	}

	public String getPublishInfoKey2() {
		return publishInfoKey2;
	}

	public void setPublishInfoKey2(String publishInfoKey2) {
		this.publishInfoKey2 = publishInfoKey2;
	}

	public String getPublishInfoKey3() {
		return publishInfoKey3;
	}

	public void setPublishInfoKey3(String publishInfoKey3) {
		this.publishInfoKey3 = publishInfoKey3;
	}
}
