package gov.kcg.kgo.viewModel.cityCoinApi.mission.completed.rq;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompletedRq {

	@JsonProperty("AppKey")
	private String appKey;

	@JsonProperty("Account")
	private String account;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
