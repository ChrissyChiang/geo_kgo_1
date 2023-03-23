package gov.kcg.kgo.sso;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.config.Constants;
import gov.kcg.kgo.enums.common.ServiceType;
import gov.kcg.kgo.util.ClassUtil;
import gov.kcg.kgo.util.HttpRequest;

/**
 * 暫時原封不動使用範例程式來測試，之後再調整程式碼
 * 
 * @author TPIuser
 *
 */
public class KcgTempClient {
	//TODO：原封不動搬過來，需整理程式碼......
	//TODO: 職人工事有IP綁定

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KcgTempClient.class);

	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String PARAMS_APPID = "appId";
	private static final String RESPONCE_DATA = "data";
	private static final String PID = "PID";
	private static final String PARAMS_FILTERS = "filters";
	private static final String PARAMS_FIELDS = "fields";
	private static final String RESPONCE_SUCCESS = "success";

	public KcgTempClient() {
//		HttpRequest.getInstance().setRequestUrl(Constants);
	}

	public String getSessionToken(String app_id, String permanent_token) {
		LOGGER.info("temp app_id : {} ", app_id);
		LOGGER.info("temp permanent_token: {}", permanent_token);

		String session_token = StringUtils.EMPTY;
		String path = Constants.KCG_SERVICE_TOKEN_BASE_URL + ServiceType.STAFF_DB.getServiceId();

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(HEADER_AUTHORIZATION, permanent_token);
		HashMap<String, String> get = new HashMap<String, String>();
		get.put(PARAMS_APPID, app_id);

		String response = ClassUtil.getInstance(HttpRequest.class).sendGet(path, get, headers);
		LOGGER.info(">>>>>>>>>> HTTP GET Response str = {}", response);
		try {
			JSONObject jsonObject = new JSONObject(response);
			session_token = jsonObject.getString(RESPONCE_DATA);
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

	public JSONObject getTempData(String pid, String session_token) {
		LOGGER.info("pid : " + pid);
		LOGGER.info("session_token : " + session_token);
		String path = Constants.KCG_SERVICE_GET_BASE_URL + ServiceType.STAFF_DB.getServiceId();

		String fields = "TITLEID,GRADEID,CHDTIME,PNAME,UNITID,UNITNAME,BIRTH,DIST,FOFDATE,SLIMDATE,RNKID,ORGANNM,SEPDATE,SEPCODE,DELCODE,RNKNM,TITLENAME,ORGANID,PID";

		JSONObject filters = new JSONObject();
		try {
			filters.put(PID, pid);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		HashMap<String, String> data = new HashMap<String, String>();
		data.put(PARAMS_FILTERS, filters.toString());
		data.put(PARAMS_FIELDS, fields);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(HEADER_AUTHORIZATION, session_token);

		String response = ClassUtil.getInstance(HttpRequest.class).sendGet(path, data, headers);
		JSONObject jsonObject = new JSONObject();
		boolean isSucc = false;
		try {
			jsonObject = new JSONObject(response);
			JSONArray jsonArray = new JSONObject(response).getJSONArray(RESPONCE_DATA);
			isSucc = jsonObject.getBoolean(RESPONCE_SUCCESS);
			jsonObject = jsonArray.getJSONObject(0);
		} catch (JSONException e) {
			LOGGER.info("fail to getTempData. response: " + response);
			e.printStackTrace();
		}
		if (!isSucc) {
			try {
				throw new Exception("fail to request path: " + path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}

	private boolean compareStr(String str1, String str2) {
		boolean is_equal = str1.contains(str2);
		return is_equal;
	}
}
