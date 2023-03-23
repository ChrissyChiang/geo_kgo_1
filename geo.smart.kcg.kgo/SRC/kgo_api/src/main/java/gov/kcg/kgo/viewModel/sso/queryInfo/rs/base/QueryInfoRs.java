package gov.kcg.kgo.viewModel.sso.queryInfo.rs.base;

/**
 * WS-Z05-03a 查詢交換資訊rs.
 */
public class QueryInfoRs {
	/** 錯誤碼. */
	private String errorCode;
	
	/** 交換資訊上次異動的時間. */
	private String publishInfoLastUpdateTime;
	
	/** 交換資訊上次異動的識別. */
	private String publishInfoLastUpdateTag;
	

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getPublishInfoLastUpdateTime() {
		return publishInfoLastUpdateTime;
	}

	public void setPublishInfoLastUpdateTime(String publishInfoLastUpdateTime) {
		this.publishInfoLastUpdateTime = publishInfoLastUpdateTime;
	}

	public String getPublishInfoLastUpdateTag() {
		return publishInfoLastUpdateTag;
	}

	public void setPublishInfoLastUpdateTag(String publishInfoLastUpdateTag) {
		this.publishInfoLastUpdateTag = publishInfoLastUpdateTag;
	}
}
