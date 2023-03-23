package gov.kcg.kgo.viewModel.sso.queryInfo.rs.facebook.bean;

import io.swagger.annotations.ApiModel;

/**
 * 	KCG_GOOGLE_SSO_INFO rs.
 */

/**
 * 用戶認證位址		AUTH_FROM_ADDRESS
用戶認證時間		AUTH_DATE
用戶帳號		FACEBOOK_USER_ACCOUNT
用戶姓名		FACEBOOK_USER_NAME
	
 * */
@ApiModel(description = "KCG_FACEBOOK_SSO_INFO Facebook 用戶基本資訊 rs")
public class KcgFacebookSsoInfo {
	
	/** 用戶認證位址 ex:"61.216.190.109". */
	private String authFromAddress;
	
	/** 用戶認證時間 ex:"20200925115529". */
	private String authDate;
	
	/** 用戶帳號 facebook帳號. */
	private String facebookUserAccount;
	
	/** 用戶姓名. {中文名字} {英文名字} */
	private String facebookUserName;

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

	public String getFacebookUserAccount() {
		return facebookUserAccount;
	}

	public void setFacebookUserAccount(String facebookUserAccount) {
		this.facebookUserAccount = facebookUserAccount;
	}

	public String getFacebookUserName() {
		return facebookUserName;
	}

	public void setFacebookUserName(String facebookUserName) {
		this.facebookUserName = facebookUserName;
	}
}
