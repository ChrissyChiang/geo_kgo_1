package gov.kcg.kgo.sso;

import org.json.JSONException;
import org.json.JSONObject;

import gov.kcg.kgo.config.Constants;
import gov.kcg.kgo.util.ClassUtil;
import gov.kcg.kgo.util.HttpRequest;

import java.util.HashMap;

/**
 * 暫時原封不動使用範例程式來測試，之後再調整程式碼
 * 
 * @author TPIuser
 *
 */
public class KcgInfoExchangeClient {
	//TODO：原封不動搬過來，需整理程式碼......
	
	public KcgInfoExchangeClient() {
//		HttpRequest.getInstance().set_server_url(Constants.KCG_API_BASE_URL);
	}

	public String getSessionToken(String app_id, String permanent_token, String serverId) {
		System.out.println("info exchange app_id : " + app_id);
		System.out.println("info exchange permanent_token:" + permanent_token);
		String session_token = "";
		String path = Constants.KCG_SERVICE_TOKEN_BASE_URL.concat(serverId);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", permanent_token);
		HashMap<String, String> get = new HashMap<String, String>();
		get.put("appId", app_id);

		String response = ClassUtil.getInstance(HttpRequest.class).sendGet(path, get, headers);
		try {
			JSONObject jsonObject = new JSONObject(response);
			session_token = jsonObject.getString("data");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (session_token.isEmpty()) {
			try {
				throw new Exception("fail to getSessionToken(), response= " + response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return session_token;
	}

	public JSONObject getInfoExchangeData(String session_token, String public_app_user_sso_token, String app_private_id,
			String app_private_passwd, String serverId) {
		JSONObject info_exchage_data = new JSONObject();
		String path = Constants.KCG_SERVICE_GET_BASE_URL.concat(serverId);

		HashMap<String, String> data = new HashMap<String, String>();
		data.put("APP_PRIVATE_ID", app_private_id);
		data.put("APP_PRIVATE_PASSWD", app_private_passwd);
		data.put("PUBLIC_APP_USER_SSO_TOKEN", public_app_user_sso_token);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", session_token);
		String info_exchange_response = ClassUtil.getInstance(HttpRequest.class).sendGet(path, data, headers);

		boolean isSucc = false;
		try {
			JSONObject jsonObject = new JSONObject(info_exchange_response);
			isSucc = jsonObject.getBoolean("success");
			if (isSucc) {
				info_exchage_data = new JSONObject(jsonObject.getJSONObject("data").toString());
				String ERROR_CODE = info_exchage_data.getString("ERROR_CODE");
				System.out.println("ERROR_CODE:" + ERROR_CODE);
				if (!compareStr(ERROR_CODE, "0")) {
					isSucc = false;
				}
			}
		} catch (JSONException e) {
			System.out.println("fail to getInfoExchangeData. response: " + info_exchange_response);
			e.printStackTrace();
		}

		if (!isSucc) {
			try {
				throw new Exception("fail to request path: " + path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return info_exchage_data;
	}

	private boolean compareStr(String str1, String str2) {
		boolean is_equal = str1.contains(str2);
		return is_equal;
	}
}
