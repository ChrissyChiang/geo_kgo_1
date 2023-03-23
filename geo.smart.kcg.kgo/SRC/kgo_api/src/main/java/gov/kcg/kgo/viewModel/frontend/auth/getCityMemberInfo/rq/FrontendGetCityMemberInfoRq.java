package gov.kcg.kgo.viewModel.frontend.auth.getCityMemberInfo.rq;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.kcg.kgo.service.impl.CallKcgCityApiServiceImpl;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 前臺登入取得市民科技會員資訊rq.
 * 登入方式1 : 登入類型 + 帳號.
 * 登入方式2 : 身分證號 hashId.
 */
@ApiModel(description = "前臺登入取得市民科技會員資訊rq")
public class FrontendGetCityMemberInfoRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "登入類型")
	@JsonProperty("AuthType")
	private String authType;
	
	@ApiModelProperty(value = "登入帳號")
	@JsonProperty("UserAccount")
	private String userAccount;
	
	@ApiModelProperty(value = "身分證號 hashId")
	@JsonProperty("HashId")
	private String hashId;
	
	@ApiModelProperty(value = "市民科技 系統識別碼 AP002")
	@JsonProperty("ExternalAgencyAppID")
	private String externalAgencyAppID = CallKcgCityApiServiceImpl.EXTERNAL_AGENCY_APPID; // AP002

	public FrontendGetCityMemberInfoRq() {}
	
	/**
	 * 登入方式1 : 登入類型 + 帳號.
	 *
	 * @param authType the auth type
	 * @param userAccount the user account
	 */
	public FrontendGetCityMemberInfoRq(String authType, String userAccount) {
		this.authType = authType;
		this.userAccount = userAccount;
	}
	
	/**
	 * 登入方式2 : 身分證號 hashId.
	 *
	 * @param hashId the hash id
	 */
	public FrontendGetCityMemberInfoRq(String hashId) {
		this.hashId = hashId;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getHashId() {
		return hashId;
	}

	public void setHashId(String hashId) {
		this.hashId = hashId;
	}

	public String getExternalAgencyAppID() {
		return externalAgencyAppID;
	}

	public void setExternalAgencyAppID(String externalAgencyAppID) {
		this.externalAgencyAppID = externalAgencyAppID;
	}
}
