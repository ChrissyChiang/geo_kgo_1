package gov.kcg.kgo.viewModel.sso.queryInfo.rs.twfido.bean;

import io.swagger.annotations.ApiModel;

/**
 * 	KCG_GOOGLE_SSO_INFO rs.
 */

/**
 * 用戶認證位址		AUTH_FROM_ADDRESS
 * 用戶認證時間		AUTH_DATE	自 TWFIDO 成功取回資料
 * 用戶身份證字號		TWFIDO_USER_TW_SSN
 * */
@ApiModel(description = "KCG_TWFIDO_SSO_INFO Tw FidO 用戶基本資訊 rs")
public class KcgTwfidoSsoInfo {
	
	/** 用戶認證位址 ex:"61.216.190.109". */
	private String authFromAddress;
	
	/** 用戶認證時間 ex:"20200925115529". */
	private String authDate;
	
	/** 用戶身份證字號. */
	private String twfidoUserTwSSn;


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

	public String getTwfidoUserTwSSn() {
		return twfidoUserTwSSn;
	}

	public void setTwfidoUserTwSSn(String twfidoUserTwSSn) {
		this.twfidoUserTwSSn = twfidoUserTwSSn;
	}
}
