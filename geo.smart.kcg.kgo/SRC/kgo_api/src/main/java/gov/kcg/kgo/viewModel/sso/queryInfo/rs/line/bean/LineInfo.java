package gov.kcg.kgo.viewModel.sso.queryInfo.rs.line.bean;

/**
 * 	LINE用戶基本資訊 KCG_LINE_SSO_INFO rs.
/**
	用戶認證位址		AUTH_FROM_ADDRESS	
	用戶認證時間		AUTH_DATE	自 LINE 成功取回資料
	用戶帳號		LINE_USER_ID	
	用戶姓名		LINE_USER_DISPLAYNAME	


	sample:
	"KCG_LINE_SSO_INFO":{  
			"AUTH_FROM_ADDRESS":"101.12.240.31",
			"AUTH_DATE":"20160316083748+0800",
			"LINE_USER_ID":"8xxue3kx84",
			"LINE_USER_DISPLAYNAME":"Dominic Lee"
		}
*/
public class LineInfo {
	
	/** 用戶認證位址 ex:"61.216.190.109". */
	private String authFromAddress;
	
	/** 用戶認證時間 ex:"20200925115529". */
	private String authDate;
	
	/** 用戶認證方式 */
	private String lineUserId;
	
	/** 用戶帳號 */
	private String lineUserDisplayname;

	public String getAuthFromAddress() {
		return authFromAddress;
	}

	public void setAuthFromAddress(String authFromAddress) {
		this.authFromAddress = authFromAddress;
	}

	public String getAuthDate() {
		return authDate;
	}

	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}

	public String getLineUserId() {
		return lineUserId;
	}

	public void setLineUserId(String lineUserId) {
		this.lineUserId = lineUserId;
	}

	public String getLineUserDisplayname() {
		return lineUserDisplayname;
	}

	public void setLineUserDisplayname(String lineUserDisplayname) {
		this.lineUserDisplayname = lineUserDisplayname;
	}
}
