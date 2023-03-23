package gov.kcg.kgo.viewModel.sso.queryInfo.rs.line.bean;

/**
 * WS-Z05-03a LINE用戶基本資訊 查詢交換資訊rs.
 */
public class LinePublishInfoContent {
	
	/** LINE用戶基本資訊用戶資料. */
	LineInfo lineInfo;

	public LineInfo getLineInfo() {
		return lineInfo;
	}

	public void setLineInfo(LineInfo lineInfo) {
		this.lineInfo = lineInfo;
	}
}
