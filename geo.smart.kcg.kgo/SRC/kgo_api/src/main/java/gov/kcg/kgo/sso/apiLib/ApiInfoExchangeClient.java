package gov.kcg.kgo.sso.apiLib;

import org.json.JSONException;
import org.json.JSONObject;

import gov.kcg.kgo.config.Constants;
import gov.kcg.kgo.util.HttpRequest;
import gov.kcg.kgo.viewModel.sso.queryInfo.rq.WsZ0503aRq;

public class ApiInfoExchangeClient {

	public ApiInfoExchangeClient() {
	}

	private static class Loader {
		/** The Constant instance. */
		private static final ApiInfoExchangeClient instance = new ApiInfoExchangeClient();
	}

	public static ApiInfoExchangeClient getInstance() {
		return Loader.instance;
	}

	/** WS-Z05-03a : 查詢交換資訊 (使用者登入資訊). */
	public String queryInfo(WsZ0503aRq rq) {
		// KCG API 基本網址 https://accounts-api.kcg.gov.tw/
		String apiUrl = Constants.KCG_API_BASE_URL + "/info_exchange/query_info/";

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("PRIVILEGED_APP_SSO_TOKEN", rq.getPrivilegedAppToken());
			jsonObject.put("PUBLISH_INFO_KEY1", rq.getPublishInfoKey1());
			jsonObject.put("PUBLISH_INFO_KEY2", rq.getPublishInfoKey2());
			jsonObject.put("PUBLISH_INFO_KEY3", rq.getPublishInfoKey3());

		} catch (JSONException e) {
			e.printStackTrace();
		}

		HttpRequest httpRequest = new HttpRequest();
		return httpRequest.sendPost(apiUrl, jsonObject);
	}

	/** WS-Z05-03a : 查詢交換資訊-未提交密碼. */
	// TODO 改用rq object 帶入
	@Deprecated
	public String queryInfo(String key1, String key2, String key3) {
		// KCG API 基本網址 https://accounts-api.kcg.gov.tw/
		String apiUrl = Constants.KCG_API_BASE_URL + "/info_exchange/query_info/";
		JSONObject jsonObject = new JSONObject();
		try {

			// 取得前、後臺 應用系統身分認證object.PrivilegedAppSsoToken.
//        	jsonObject.put("PRIVILEGED_APP_SSO_TOKEN", SSOUtil.getAppBasicAuth().getPrivilegedAppSsoToken());

			jsonObject.put("PRIVILEGED_APP_SSO_TOKEN", "a8b14e21-f191-4a17-a221-070c64e1dcf1");
			jsonObject.put("PUBLISH_INFO_KEY1", key1);
			jsonObject.put("PUBLISH_INFO_KEY2", key2);
			jsonObject.put("PUBLISH_INFO_KEY3", key3);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		HttpRequest httpRequest = new HttpRequest();
		return httpRequest.sendPost(apiUrl, jsonObject);

		// KCG API 基本網址 https://accounts-api.kcg.gov.tw/

//        HttpRequest httpRequest = new HttpRequest(Constants.KCG_API_BASE_URL);
//        return httpRequest.sendPost(path, jsonObject);

	}

	public static void main(String[] args) {

		ApiInfoExchangeClient aec = new ApiInfoExchangeClient();
		aec.queryInfo("KCG_ONE_ACCOUNT", "KCG_GOOGLE_SSO_INFO", "dbe84bfa-c97e-4295-a687-adbb72d84bcd");
	}
}
