package gov.kcg.kgo.viewModel.sso.appBasicAuth.rq;

import io.swagger.annotations.ApiModel;

/**
 * WS-Z01-A0-01 進行應用系統進行身分驗證 rq.
 */
@ApiModel(description = "WS-Z01-A0-01 進行應用系統進行身分驗證 rq")
public class Wsz01a001Rq {

	/** APP ID. */
	private String appPrivateId;

	/** APP 權杖. */
	private String appPrivatePasswd;

	public String getAppPrivateId() {
		return appPrivateId;
	}

	public void setAppPrivateId(String appPrivateId) {
		this.appPrivateId = appPrivateId;
	}

	public String getAppPrivatePasswd() {
		return appPrivatePasswd;
	}

	public void setAppPrivatePasswd(String appPrivatePasswd) {
		this.appPrivatePasswd = appPrivatePasswd;
	}

}
