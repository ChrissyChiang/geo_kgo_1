package gov.kcg.kgo.viewModel.sso.appBasicAuth.rs;

import java.util.Date;

import io.swagger.annotations.ApiModel;

/**
 * WS-Z01-A0-01 進行應用系統進行身分驗證 rs.
 */
@ApiModel(description = "WS-Z01-A0-01 進行應用系統進行身分驗證 rs")
public class Wsz01a001Rs {

	/** 錯誤代碼，用來表示該動作的結果。 0 為成功。 */
	private String errorCode;

	/** 公開 SSO 權杖. */
	private String publicAppSsoToken;

	/** 授權 SSO 權杖. */
	private String privilegedAppSsoToken;

	/** 私密 SSO 權仗. */
	private String privateAppSsoToken;

	/** 公開 SSO 權杖有效期限，需要在有效期限內更新權杖的有效時間，或是重新取得新的公開 SSO 權杖。 */
	private Date publicAppSsoTokenExpiryDate;

	/** 授權 SSO 權杖有效期限，需要在有效期限內更新權杖的有效時間，或是重新取得新的授權 SSO 權杖。 */
	private Date privilegedAppSsoTokenExpiryDate;

	/** 私密 SSO 權杖有效期限，需要在有效期限內更新權杖的有效時間。 */
	private Date privateAppSsoTokenExpiryDate;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getPublicAppSsoToken() {
		return publicAppSsoToken;
	}

	public void setPublicAppSsoToken(String publicAppSsoToken) {
		this.publicAppSsoToken = publicAppSsoToken;
	}

	public String getPrivilegedAppSsoToken() {
		return privilegedAppSsoToken;
	}

	public void setPrivilegedAppSsoToken(String privilegedAppSsoToken) {
		this.privilegedAppSsoToken = privilegedAppSsoToken;
	}

	public String getPrivateAppSsoToken() {
		return privateAppSsoToken;
	}

	public void setPrivateAppSsoToken(String privateAppSsoToken) {
		this.privateAppSsoToken = privateAppSsoToken;
	}

	public Date getPublicAppSsoTokenExpiryDate() {
		return publicAppSsoTokenExpiryDate;
	}

	public void setPublicAppSsoTokenExpiryDate(Date publicAppSsoTokenExpiryDate) {
		this.publicAppSsoTokenExpiryDate = publicAppSsoTokenExpiryDate;
	}

	public Date getPrivilegedAppSsoTokenExpiryDate() {
		return privilegedAppSsoTokenExpiryDate;
	}

	public void setPrivilegedAppSsoTokenExpiryDate(Date privilegedAppSsoTokenExpiryDate) {
		this.privilegedAppSsoTokenExpiryDate = privilegedAppSsoTokenExpiryDate;
	}

	public Date getPrivateAppSsoTokenExpiryDate() {
		return privateAppSsoTokenExpiryDate;
	}

	public void setPrivateAppSsoTokenExpiryDate(Date privateAppSsoTokenExpiryDate) {
		this.privateAppSsoTokenExpiryDate = privateAppSsoTokenExpiryDate;
	}
}
