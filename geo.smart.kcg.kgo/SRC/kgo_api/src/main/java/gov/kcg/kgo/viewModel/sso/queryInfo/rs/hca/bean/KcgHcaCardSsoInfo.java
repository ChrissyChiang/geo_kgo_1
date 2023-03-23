package gov.kcg.kgo.viewModel.sso.queryInfo.rs.hca.bean;

import io.swagger.annotations.ApiModel;

/**
 * 	KCG_GOOGLE_SSO_INFO rs.
 */

/**
用戶認證位址		AUTH_FROM_ADDRESS
用戶認證時間		AUTH_DATE
憑證身份證字號	HCA_USER_TW_SSN
憑證用戶名稱		HCA_USER_NAME
卡片號碼		    HCA_CARD_NUMBER
	
 * */
@ApiModel(description = "KCG_HcaCard_SSO_INFO HcaCard 用戶基本資訊")
public class KcgHcaCardSsoInfo {
	
	/** 用戶認證位址 ex:"61.216.190.109". */
	private String authFromAddress;
	
	/** 用戶認證時間 ex:"20200925115529". */
	private String authDate;
	
	/** 憑證身份證字號. */
	private String hcaUserTwSsn;
	
	/** 憑證用戶名稱 */
	private String hcaUserName;
	
	/** 卡片號碼	 */
	private String hcaCardNumber;

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

	public String getHcaUserTwSsn() {
		return hcaUserTwSsn;
	}

	public void setHcaUserTwSsn(String hcaUserTwSsn) {
		this.hcaUserTwSsn = hcaUserTwSsn;
	}

	public String getHcaUserName() {
		return hcaUserName;
	}

	public void setHcaUserName(String hcaUserName) {
		this.hcaUserName = hcaUserName;
	}

	public String getHcaCardNumber() {
		return hcaCardNumber;
	}

	public void setHcaCardNumber(String hcaCardNumber) {
		this.hcaCardNumber = hcaCardNumber;
	}
}
