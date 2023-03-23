package gov.kcg.kgo.viewModel.sso.queryInfo.rs.google.bean;

import io.swagger.annotations.ApiModel;

/**
 * 	KCG_GOOGLE_SSO_INFO rs.
 */

/**
 * 用戶認證位址     AUTH_FROM_ADDRESS	
   用戶認證時間	AUTH_DATE	自 Google 成功取回資料
   用戶帳號		GOOGLE_USER_ACCOUNT	
   用戶姓名		GOOGLE_USER_NAME	
 * */
@ApiModel(description = "KCG_GOOGLE_SSO_INFO Google 用戶基本資訊 rs")
public class KcgGoogleSsoInfo {
	
	/** 用戶認證位址 ex:"61.216.190.109". */
	private String authFromAddress;
	
	/** 用戶認證時間 ex:"20200925115529". */
	private String authDate;
	
	/** 用戶帳號 googel信箱帳號. */
	private String googleUserAccount;
	
	/** 用戶姓名. {中文名字} {英文名字} */
	private String googleUserName;

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

	public String getGoogleUserAccount() {
		return googleUserAccount;
	}

	public void setGoogleUserAccount(String googleUserAccount) {
		this.googleUserAccount = googleUserAccount;
	}

	public String getGoogleUserName() {
		return googleUserName;
	}

	public void setGoogleUserName(String googleUserName) {
		this.googleUserName = googleUserName;
	}
}
