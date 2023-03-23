package gov.kcg.kgo.sso.apiLib;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import gov.kcg.kgo.config.Constants;
import gov.kcg.kgo.util.ClassUtil;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.HttpRequest;
import gov.kcg.kgo.util.JsonUtil;
import gov.kcg.kgo.viewModel.sso.appBasicAuth.rs.Wsz01a001Rs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

public class ApiClient {

	public static final String PRIVATE_APP_SSO_TOKEN = "PRIVATE_APP_SSO_TOKEN";
	public static final String PRIVILEGED_APP_SSO_TOKEN = "PRIVILEGED_APP_SSO_TOKEN";
	public static final String PUBLIC_APP_SSO_TOKEN = "PUBLIC_APP_SSO_TOKEN";
	public static final String PRIVATE_APP_SSO_TOKEN_EXPIRY_DATE = "PRIVATE_APP_SSO_TOKEN_EXPIRY_DATE";
	public static final String PRIVILEGED_APP_SSO_TOKEN_EXPIRY_DATE = "PRIVILEGED_APP_SSO_TOKEN_EXPIRY_DATE";
	public static final String PUBLIC_APP_SSO_TOKEN_EXPIRY_DATE = "PUBLIC_APP_SSO_TOKEN_EXPIRY_DATE";

	public ApiClient() {
	}

	private static class Loader {
		/** The Constant instance. */
		private static final ApiClient instance = new ApiClient();
	}

	public static ApiClient getInstance() {
		return Loader.instance;
	}

	/**
	 * WS-Z01-A0-01: 進行應用系統進行身分驗證.
	 *
	 * @param app_private_id     the app private id
	 * @param app_private_passwd the app private passwd
	 * @return the string
	 */
	public String appRequestBasicAuthentication(String app_private_id, String app_private_passwd) {
		// KCG API 基本網址 https://accounts-api.kcg.gov.tw/
		String apiUrl = Constants.KCG_API_BASE_URL + "/app/request_basic_authentication/";

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("APP_PRIVATE_ID", app_private_id);
			jsonObject.put("APP_PRIVATE_PASSWD", app_private_passwd);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		HttpRequest httpRequest = new HttpRequest();
		return httpRequest.sendPost(apiUrl, jsonObject);
	}

	/**
	 * 取得應用系統驗證後資訊
	 * 
	 * @param response
	 * @return
	 */
	public Wsz01a001Rs getAppRequestBasicAuthenticationResponse(String reponseStr) {
		Wsz01a001Rs beAppAuthObject = new Wsz01a001Rs();
		try {
			Map<String, String> authRsMap = JsonUtil.getObject(reponseStr, Map.class);

			beAppAuthObject.setErrorCode(authRsMap.get("ERROR_CODE"));
			beAppAuthObject.setPublicAppSsoToken(authRsMap.get("PUBLIC_APP_SSO_TOKEN"));
			beAppAuthObject.setPrivilegedAppSsoToken(authRsMap.get("PRIVILEGED_APP_SSO_TOKEN"));
			beAppAuthObject.setPrivateAppSsoToken(authRsMap.get("PRIVATE_APP_SSO_TOKEN"));
			Date publicAppSSoTokenExDate = DateUtil.strToDate(authRsMap.get("PUBLIC_APP_SSO_TOKEN_EXPIRY_DATE"),
					DateUtil.PATTEN_TIMEZONE);

			beAppAuthObject.setPublicAppSsoTokenExpiryDate(publicAppSSoTokenExDate);

			Date privilegedAppSSoTokenExDate = DateUtil.strToDate(authRsMap.get("PRIVILEGED_APP_SSO_TOKEN_EXPIRY_DATE"),
					DateUtil.PATTEN_TIMEZONE);
			beAppAuthObject.setPrivilegedAppSsoTokenExpiryDate(privilegedAppSSoTokenExDate);

			Date privateAppSSoTokenExDate = DateUtil.strToDate(authRsMap.get("PRIVATE_APP_SSO_TOKEN_EXPIRY_DATE"),
					DateUtil.PATTEN_TIMEZONE);
			beAppAuthObject.setPrivateAppSsoTokenExpiryDate(privateAppSSoTokenExDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return beAppAuthObject;
	}

//    private void set_app_sso_token(String private_app_sso_token, String privileged_app_sso_token
//            , String public_app_sso_token, String private_app_sso_token_expiry_date
//            , String privileged_app_sso_token_expiry_date
//            , String public_app_sso_token_expiry_date){
//        preferences.put("PRIVATE_APP_SSO_TOKEN"   , private_app_sso_token);
//        preferences.put("PRIVILEGED_APP_SSO_TOKEN", privileged_app_sso_token);
//        preferences.put("PUBLIC_APP_SSO_TOKEN"    , public_app_sso_token);
//
//        //檢查日期格式
//        preferences.put("PRIVATE_APP_SSO_TOKEN_EXPIRY_DATE"   , api_time_to_datetime(private_app_sso_token_expiry_date));
//        preferences.put("PRIVILEGED_APP_SSO_TOKEN_EXPIRY_DATE", api_time_to_datetime(privileged_app_sso_token_expiry_date));
//        preferences.put("PUBLIC_APP_SSO_TOKEN_EXPIRY_DATE"    , api_time_to_datetime(public_app_sso_token_expiry_date));
//    }

	private String api_time_to_datetime(String api_time) {
		if (api_time == null) {
			return "";
		}

		// 轉換api_time 為 20160725192500+0800
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssz");
		Date date = null;

		try {
			date = sdf.parse(api_time);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}

		if (date == null) {
			return "";
		}

		return date.toString();
	}

	// WS-Z01-B0-04：驗證應用入口網使用者公開權杖有效性
	public String validate_sso_token(String privileged_app_token_to_validate,
			String public_app_user_sso_token_to_validate) {
		// KCG API 基本網址 https://accounts-api.kcg.gov.tw/
		String apiUrl = Constants.KCG_API_BASE_URL + "/app_user/validate_sso_token/";

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("PRIVILEGED_APP_SSO_TOKEN", privileged_app_token_to_validate);
			jsonObject.put("PUBLIC_APP_USER_SSO_TOKEN_TO_VALIDATE", public_app_user_sso_token_to_validate);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		HttpRequest httpRequest = new HttpRequest();
		return httpRequest.sendPost(apiUrl, jsonObject);

	}

	public static void main(String[] args) {
		ApiClient ap = new ApiClient();
		System.out.print(ap.appRequestBasicAuthentication("123", "123"));

	}
}
