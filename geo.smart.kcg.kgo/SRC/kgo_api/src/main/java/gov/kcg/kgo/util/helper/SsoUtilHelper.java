package gov.kcg.kgo.util.helper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import gov.kcg.kgo.viewModel.sso.queryInfo.rs.twfido.TwFidoQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.twfido.bean.KcgTwfidoSsoInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.twfido.bean.TwFidoPublishInfoContent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.enums.common.SysType;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.common.sso.QueryInfoType;
import gov.kcg.kgo.sso.SSOProperties;
import gov.kcg.kgo.sso.apiLib.ApiClient;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.JsonUtil;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.viewModel.sso.appBasicAuth.rs.Wsz01a001Rs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rq.WsZ0503aRq;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.base.QueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.facebook.FacebookQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.facebook.bean.FacebookPublishInfoContent;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.facebook.bean.KcgFacebookSsoInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.google.GoogleQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.google.bean.GooglePublishInfoContent;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.google.bean.KcgGoogleSsoInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.hca.HcaCardQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.hca.bean.HcaCardPublishInfoContent;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.hca.bean.KcgHcaCardSsoInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgEgov.KcgEgovQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgEgov.bean.KcgEgovInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgEgov.bean.KcgEgovPublishInfoContent;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgUserBasic.KcgUserBasicInfoQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgUserBasic.bean.KcgUserBasicInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgUserBasic.bean.KcgUserBasicPublishInfoContent;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.line.LineQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.line.bean.LineInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.line.bean.LinePublishInfoContent;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.moicaCard.MoicaCardQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.moicaCard.bean.KcgMoicaCardInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.moicaCard.bean.MoicaCardPublishInfoContent;

/**
 * SSOUtil 輔助程式.
 */
public class SsoUtilHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(SsoUtilHelper.class);

	/** response 成功代碼 */
	private static final String SUCCESS_CODE = "0";

	/** 程式資料平台 單登AES加密 key&iv */
	String cityIv = "B6SO4II4q9uJfdkmrWWgZw==";
	String cityKey = "Ijh7MKntiwK7Ee0LAeIQsX3/jjj9hxE8DLYfpkahyYs=";

	/**
	 * 應用系統身分認證處理 (驗證前後臺是否已經呼叫過身分驗證、token是否過期).
	 *
	 * @param loginSysType the login sys type
	 * @throws ParseException the parse exception
	 */
	public Wsz01a001Rs appBasicAuthObjectPreHandle(SysType loginSysType, Wsz01a001Rs beAppBasicAuthObject,
			Wsz01a001Rs feAppBasicAuthObject) throws ParseException {
		Wsz01a001Rs returnWsz01a001Rs = null;

		// 後台是否應用系統進行身分驗證
		boolean hasBackendAppBasicAuthObject = false;
		// 前台是否應用系統進行身分驗證
		boolean hasFrontendAppBasicAuthObject = false;
		// 後台
//		if(SysType.BACK.equals(loginSysType)) {
//			if(beAppBasicAuthObject == null || appTokenIsExpired(beAppBasicAuthObject)) {
//				hasBackendAppBasicAuthObject = false;
//			}
//		}

		// 前臺
//		if(SysType.FRONT.equals(loginSysType)) {
//			if(feAppBasicAuthObject == null || appTokenIsExpired(feAppBasicAuthObject)) {
//				hasFrontendAppBasicAuthObject = false;
//			}
//		}

		SSOProperties sSOProperties = SpringUtil.getBean(SSOProperties.class);
		// 後臺 應用系統身分 未驗證
		if (!hasBackendAppBasicAuthObject) {
			// 設定後台 appPrivateId、appPrivatePasswd
			String backendAppPrivateId = sSOProperties.getBEkcgAccountsApiAppPrivateId();
			String backendAppPrivatePasswd = sSOProperties.getBEkcgAccountsApiAppPrivatePasswd();
			// 後臺 應用系統進行身分驗證
			returnWsz01a001Rs = setAppBasicAuthObject(backendAppPrivateId, backendAppPrivatePasswd);
		}

		// 前臺 應用系統身分 未驗證
		if (!hasFrontendAppBasicAuthObject) {
			// 設定後台 appPrivateId、appPrivatePasswd
			String backendAppPrivateId = sSOProperties.getBEkcgAccountsApiAppPrivateId();
			String backendAppPrivatePasswd = sSOProperties.getBEkcgAccountsApiAppPrivatePasswd();
			// 後臺 應用系統進行身分驗證
			returnWsz01a001Rs = setAppBasicAuthObject(backendAppPrivateId, backendAppPrivatePasswd);
		}
		return returnWsz01a001Rs;
	}

	/**
	 * 應用系統進行身分驗證.
	 *
	 * @param loginSysType         the login sys type
	 * @param beAppBasicAuthObject the be app basic auth object
	 * @param feAppBasicAuthObject the fe app basic auth object
	 * @param appPrivateId         the app private id
	 * @param appPrivatePasswd     the app private passwd
	 * @throws ParseException
	 */
	private Wsz01a001Rs setAppBasicAuthObject(String appPrivateId, String appPrivatePasswd) throws ParseException {
		Wsz01a001Rs appAuthObject = new Wsz01a001Rs();

		// WS-Z01-A0-01: 進行應用系統進行身分驗證
		ApiClient apiClient = new ApiClient();
		String reponseStr = apiClient.appRequestBasicAuthentication(appPrivateId, appPrivatePasswd);
		LOGGER.info("appRequestBasicAuthentication reponseStr >> " + reponseStr);

		Map<String, String> authRsMap = JsonUtil.getObject(reponseStr, Map.class);

		appAuthObject.setErrorCode(authRsMap.get("ERROR_CODE"));
		appAuthObject.setPublicAppSsoToken(authRsMap.get("PUBLIC_APP_SSO_TOKEN"));
		appAuthObject.setPrivilegedAppSsoToken(authRsMap.get("PRIVILEGED_APP_SSO_TOKEN"));
		appAuthObject.setPrivateAppSsoToken(authRsMap.get("PRIVATE_APP_SSO_TOKEN"));

		Date publicAppSSoTokenExDate = DateUtil.strToDate(authRsMap.get("PUBLIC_APP_SSO_TOKEN_EXPIRY_DATE"),
				DateUtil.PATTEN_TIMEZONE);
		appAuthObject.setPublicAppSsoTokenExpiryDate(publicAppSSoTokenExDate);

		Date privilegedAppSSoTokenExDate = DateUtil.strToDate(authRsMap.get("PRIVILEGED_APP_SSO_TOKEN_EXPIRY_DATE"),
				DateUtil.PATTEN_TIMEZONE);
		appAuthObject.setPrivilegedAppSsoTokenExpiryDate(privilegedAppSSoTokenExDate);

		Date privateAppSSoTokenExDate = DateUtil.strToDate(authRsMap.get("PRIVATE_APP_SSO_TOKEN_EXPIRY_DATE"),
				DateUtil.PATTEN_TIMEZONE);
		appAuthObject.setPrivateAppSsoTokenExpiryDate(privateAppSSoTokenExDate);

		return appAuthObject;
//		// 設定後台appAuthObject
//		if (SysType.BACK.equals(loginSysType)) {
//			beAppBasicAuthObject = appAuthObject;
//		}
//		// 設定前台appAuthObject
//		if (SysType.FRONT.equals(loginSysType)) {
//			feAppBasicAuthObject = appAuthObject;
//		}
	}

	/**
	 * 驗證appToken 是否過期.
	 *
	 * @param appAuthObject the app auth object
	 * @return true, if successful
	 */
	public boolean appTokenIsExpired(Wsz01a001Rs appAuthObject) {
		if (appAuthObject == null) {
			return true;
		}
		// 離token 過前5分鐘前即失效
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -5);

		Date expiryDate = cal.getTime();
		// appToken 是否過期
		if (expiryDate.after(appAuthObject.getPublicAppSsoTokenExpiryDate())
				|| expiryDate.after(appAuthObject.getPrivilegedAppSsoTokenExpiryDate())
				|| expiryDate.after(appAuthObject.getPrivateAppSsoTokenExpiryDate())) {
			return true;
		}
		return false;
	}

	/**
	 * 取得查詢交換資訊rq.
	 *
	 * @param jwtToken the jwt token
	 * @return the query info rq
	 */
	public WsZ0503aRq getQueryInfoRq(String privilegedAppToken, String parseJwtStr,
			LoginAuthTokenType loginAuthTokenType) {
		WsZ0503aRq rq = new WsZ0503aRq();
		// app授權權杖
//		rq.setPrivilegedAppToken(getAppBasicAuth().getPrivilegedAppSsoToken());

		// TODO 測試用
		rq.setPrivilegedAppToken(privilegedAppToken);

		// 查詢交換資訊 設置 key1、key2
		QueryInfoType queryInfoType = QueryInfoType.getQueryInfoType(loginAuthTokenType);

		rq.setPublishInfoKey1(queryInfoType.getPublishInfoKey1());
		rq.setPublishInfoKey2(queryInfoType.getPublishInfoKey2());

		// 取得token值
		String tokenName = loginAuthTokenType.getAuthTokenName();
		String userLoginToken = JsonUtil.getValueByKey(parseJwtStr, tokenName);
		rq.setPublishInfoKey3(userLoginToken);

		return rq;
	}

	/**
	 * 單一登入用戶登入認證類型.
	 *
	 * @param parseJwtStr the parse jwt str
	 * @return the user login token type
	 */
	public LoginAuthTokenType getUserLoginTokenType(String parseJwtStr) {
		LoginAuthTokenType tokenType = null;
		for (LoginAuthTokenType type : LoginAuthTokenType.values()) {
			if (StringUtils.contains(parseJwtStr, type.getAuthTokenName())) {
				tokenType = type;
				break;
			}
		}
		return tokenType;
	}

	/**
	 * 各系統介接登入認證類型.
	 *
	 * @param parseJwtStr the parse jwt str
	 * @return the user login token type
	 */
	public LoginAuthTokenType getUserLoginTokenTypeByLoginInfoJsonStr(String userLoginInfoJsonStr) {
		QueryInfoType queryInfoType = null;
		for (QueryInfoType type : QueryInfoType.values()) {
			if (StringUtils.contains(userLoginInfoJsonStr, type.getPublishInfoKey2())) {
				queryInfoType = type;
				break;
			}
		}
		return queryInfoType.getLoginAuthTokenType();
	}

	/**
	 * 取得使用者登入物件.
	 *
	 * @param <T>                  the generic type
	 * @param loginAuthTokenType   the login auth token type
	 * @param userLoginInfoJsonStr the user login info json str
	 * @return the object
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public <T extends QueryInfoRs> T getLoginUerInfoObject(LoginAuthTokenType loginAuthTokenType,
			String userLoginInfoJsonStr) throws InstantiationException, IllegalAccessException {
		T rs = null;

		// 市府員工登入 使用者資料物件 (後臺)
		if (LoginAuthTokenType.BASIC.equals(loginAuthTokenType)) {
			rs = (T) setLoginUerInfoObject(loginAuthTokenType, userLoginInfoJsonStr, KcgUserBasicInfoQueryInfoRs.class);
		}

		// 一般民眾 自然人憑證 使用者資料物件 (前臺)
		if (LoginAuthTokenType.MOICA.equals(loginAuthTokenType)) {
			rs = (T) setLoginUerInfoObject(loginAuthTokenType, userLoginInfoJsonStr, MoicaCardQueryInfoRs.class);
		}

		// 一般民眾 google 使用者資料物件 (前臺)
		if (LoginAuthTokenType.GOOGLE.equals(loginAuthTokenType)) {
			rs = (T) setLoginUerInfoObject(loginAuthTokenType, userLoginInfoJsonStr, GoogleQueryInfoRs.class);
		}

		// 一般民眾 facebook 使用者資料物件 (前臺)
		if (LoginAuthTokenType.FACEBOOK.equals(loginAuthTokenType)) {
			rs = (T) setLoginUerInfoObject(loginAuthTokenType, userLoginInfoJsonStr, FacebookQueryInfoRs.class);
		}

		// 一般民眾 我的e 政府 使用者資料物件 (前臺)
		if (LoginAuthTokenType.EGOV.equals(loginAuthTokenType)) {
			rs = (T) setLoginUerInfoObject(loginAuthTokenType, userLoginInfoJsonStr, KcgEgovQueryInfoRs.class);
		}

		// 一般民眾 LINE 使用者資料物件 (前臺)
		if (LoginAuthTokenType.LINE.equals(loginAuthTokenType)) {
			rs = (T) setLoginUerInfoObject(loginAuthTokenType, userLoginInfoJsonStr, LineQueryInfoRs.class);
		}

		// 一般民眾 TW FidO 使用者資料物件 (前臺)
		if (LoginAuthTokenType.TW_FIDO.equals(loginAuthTokenType)) {
			rs = (T) setLoginUerInfoObject(loginAuthTokenType, userLoginInfoJsonStr, TwFidoQueryInfoRs.class);
		}
		// 一般民眾 HCA CARD使用者資料物件 (前臺)
		if (LoginAuthTokenType.HCA.equals(loginAuthTokenType)) {
			rs = (T) setLoginUerInfoObject(loginAuthTokenType, userLoginInfoJsonStr, HcaCardQueryInfoRs.class);
		}
				
		return rs;
	}

	/**
	 * 設置使用者登入物件.
	 *
	 * @param <T>                  the generic type
	 * @param loginAuthTokenType   the login auth token type
	 * @param userLoginInfoJsonStr the user login info json str
	 * @param clazz                the clazz
	 * @return the t
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public <T extends QueryInfoRs> T setLoginUerInfoObject(LoginAuthTokenType loginAuthTokenType,
			String userLoginInfoJsonStr, Class<T> clazz) throws InstantiationException, IllegalAccessException {
		Map<String, Object> loginInfoMap = JsonUtil.getObject(userLoginInfoJsonStr, Map.class);
		T rs = clazz.newInstance();
		// 設置 登入資訊類別
//		rs.setLoginAuthTokenType(loginAuthTokenType);

		// 設置共通欄位
		setCommonField(rs, loginInfoMap);

		// response 成功
		if (StringUtils.equals(SUCCESS_CODE, rs.getErrorCode())) {
			// 共通 PUBLISH_INFO_CONTENT:
			Map<String, Object> contentMap = (HashMap) loginInfoMap.get("PUBLISH_INFO_CONTENT");

			// 將各種登入類型組成 Object
			setLoginInfoObject(rs, contentMap);
		}
		return rs;
	}

	/**
	 * 將各種登入類型組成 Object.
	 *
	 * @param <T>        the generic type
	 * @param rs         the rs
	 * @param contentMap the content map
	 */
	private <T extends QueryInfoRs> void setLoginInfoObject(T rs, Map<String, Object> contentMap) {
		// 市府員工 用戶基本資訊 使用者資料物件 (後臺)
		if (rs instanceof KcgUserBasicInfoQueryInfoRs) {
			Map<String, Object> infoMap = (HashMap) contentMap.get("KCG_USER_BASIC_INFO");

			KcgUserBasicPublishInfoContent content = new KcgUserBasicPublishInfoContent();
			KcgUserBasicInfo info = new KcgUserBasicInfo();
			((KcgUserBasicInfoQueryInfoRs) rs).setPublishInfoContent(content);
			content.setKcgUserBasicInfo(info);

			// 市府員工 用戶基本資訊 info 欄位
			info.setAppCompanyId((String) infoMap.get("APP_COMPANY_ID"));
			info.setAppUserLoginId((String) infoMap.get("APP_USER_LOGIN_ID"));
			info.setAuthFromAddress((String) infoMap.get("AUTH_FROM_ADDRESS"));
			info.setAuthDate((String) infoMap.get("AUTH_DATE"));
			info.setAppUserTwSSn((String) infoMap.get("APP_USER_TW_SSN"));

			ArrayList<String> authMethodArrayList = (ArrayList<String>) infoMap.get("AUTH_METHOD");
			info.setAuthMethod(authMethodArrayList);
			// TODO AUTH_EXTRA_INFO 文件沒有提到
		}

		// 自然人憑證 使用者資料物件 (前臺)
		if (rs instanceof MoicaCardQueryInfoRs) {
			Map<String, String> infoMap = (HashMap) contentMap.get("KCG_MOICA_CARD_INFO");

			MoicaCardPublishInfoContent content = new MoicaCardPublishInfoContent();
			KcgMoicaCardInfo info = new KcgMoicaCardInfo();
			((MoicaCardQueryInfoRs) rs).setPublishInfoContent(content);
			content.setKcgMoicaCardInfo(info);

			// 自然人憑證 info 欄位
			info.setAuthFromAddress(infoMap.get("AUTH_FROM_ADDRESS"));
			info.setAuthDate(infoMap.get("AUTH_DATE"));
			info.setMoicaUserTwSsn(infoMap.get("MOICA_USER_TW_SSN"));
			info.setMoicaUserName(infoMap.get("MOICA_USER_NAME"));
			info.setMoicaCertSn(infoMap.get("MOICA_CERT_SN"));
		}

		// google 使用者資料物件 (前臺)
		if (rs instanceof GoogleQueryInfoRs) {
			Map<String, String> infoMap = (HashMap) contentMap.get("KCG_GOOGLE_SSO_INFO");

			GooglePublishInfoContent content = new GooglePublishInfoContent();
			((GoogleQueryInfoRs) rs).setPublishInfoContent(content);
			KcgGoogleSsoInfo info = new KcgGoogleSsoInfo();
			content.setKcgGoogleSsoInfo(info);

			// google info 欄位
			info.setAuthFromAddress(infoMap.get("AUTH_FROM_ADDRESS"));
			info.setAuthDate(infoMap.get("AUTH_DATE"));
			info.setGoogleUserAccount(infoMap.get("GOOGLE_USER_ACCOUNT"));
			info.setGoogleUserName(infoMap.get("GOOGLE_USER_NAME"));
		}

		// Facebook 使用者資料物件 (前臺)
		if (rs instanceof FacebookQueryInfoRs) {
			Map<String, String> infoMap = (HashMap) contentMap.get("KCG_FACEBOOK_SSO_INFO");

			FacebookPublishInfoContent content = new FacebookPublishInfoContent();
			((FacebookQueryInfoRs) rs).setPublishInfoContent(content);
			KcgFacebookSsoInfo info = new KcgFacebookSsoInfo();
			content.setKcgFacebookSsoInfo(info);

			// Facebook info 欄位
			info.setAuthFromAddress(infoMap.get("AUTH_FROM_ADDRESS"));
			info.setAuthDate(infoMap.get("AUTH_DATE"));
			info.setFacebookUserAccount(infoMap.get("FACEBOOK_USER_ACCOUNT"));
			info.setFacebookUserName(infoMap.get("FACEBOOK_USER_NAME"));
		}

		// 我的 e 政府 使用者資料物件 (前臺)
		if (rs instanceof KcgEgovQueryInfoRs) {
			Map<String, String> infoMap = (HashMap) contentMap.get("KCG_EGOV_SSO_INFO");

			KcgEgovPublishInfoContent content = new KcgEgovPublishInfoContent();
			((KcgEgovQueryInfoRs) rs).setPublishInfoContent(content);
			KcgEgovInfo info = new KcgEgovInfo();
			content.setKcgEgovInfo(info);

			// 我的 e 政府 info 欄位
			info.setAuthFromAddress(infoMap.get("AUTH_FROM_ADDRESS"));
			info.setAuthDate(infoMap.get("AUTH_DATE"));
			info.setEgovAuthType(infoMap.get("EGOV_AUTH_METHOD"));
			info.setEgovUserAccount(infoMap.get("EGOV_USER_ACCOUNT"));
			info.setEgovUserCn(infoMap.get("EGOV_USER_CN"));
			info.setEgovUserUid(infoMap.get("EGOV_USER_UID"));
			info.setEgovUserCerkeyStr(infoMap.get("EGOV_USER_CERKEY_STR"));
			info.setEgovUserMail(infoMap.get("EGOV_USER_MAIL"));
			info.setEgovUserType(infoMap.get("EGOV_USER_TYPE"));
		}

		// HcaCard 使用者資料物件 (前臺)
		if (rs instanceof HcaCardQueryInfoRs) {
			Map<String, String> infoMap = (HashMap) contentMap.get("KCG_HCA_CARD_INFO");

			HcaCardPublishInfoContent content = new HcaCardPublishInfoContent();
			((HcaCardQueryInfoRs) rs).setPublishInfoContent(content);
			KcgHcaCardSsoInfo info = new KcgHcaCardSsoInfo();
			content.setKcgHcaCardSsoInfo(info);

			// HcaCard info 欄位
			info.setAuthFromAddress(infoMap.get("AUTH_FROM_ADDRESS"));
			info.setAuthDate(infoMap.get("AUTH_DATE"));
			info.setHcaUserTwSsn(infoMap.get("HCA_USER_TW_SSN"));
			info.setHcaUserName(infoMap.get("HCA_USER_NAME"));
			info.setHcaCardNumber(infoMap.get("HCA_CARD_NUMBER"));
		}

		// LINE 使用者資料物件 (前臺)
		if (rs instanceof LineQueryInfoRs) {
			Map<String, String> infoMap = (HashMap) contentMap.get("KCG_LINE_SSO_INFO");

			LinePublishInfoContent content = new LinePublishInfoContent();
			((LineQueryInfoRs) rs).setPublishInfoContent(content);
			LineInfo info = new LineInfo();
			content.setLineInfo(info);

			// LINE 使用者資料欄位
			info.setAuthFromAddress(infoMap.get("AUTH_FROM_ADDRESS"));
			info.setAuthDate(infoMap.get("AUTH_DATE"));
			info.setLineUserId(infoMap.get("LINE_USER_ID"));
			info.setLineUserDisplayname(infoMap.get("LINE_USER_DISPLAYNAME"));
		}

		// TW FidO 使用者資料物件 (前臺)
		if (rs instanceof TwFidoQueryInfoRs) {
			Map<String, String> infoMap = (HashMap) contentMap.get("KCG_TWFIDO_SSO_INFO");

			TwFidoPublishInfoContent content = new TwFidoPublishInfoContent();
			((TwFidoQueryInfoRs) rs).setPublishInfoContent(content);
			KcgTwfidoSsoInfo info = new KcgTwfidoSsoInfo();
			content.setKcgTwFidoSsoInfo(info);

			// TW FidO 使用者資料欄位
			info.setAuthFromAddress(infoMap.get("AUTH_FROM_ADDRESS"));
			info.setAuthDate(infoMap.get("AUTH_DATE"));
			info.setTwfidoUserTwSSn(infoMap.get("TWFIDO_USER_TW_SSN"));
		}

		// TODO 其他登入info Object
	}

	/**
	 * 設置共通欄位.
	 *
	 * @param <T>          the generic type
	 * @param rs           the rs
	 * @param loginInfoMap the login info map
	 */
	private <T extends QueryInfoRs> void setCommonField(T rs, Map<String, Object> loginInfoMap) {
		rs.setErrorCode((String) loginInfoMap.get("ERROR_CODE"));
		rs.setPublishInfoLastUpdateTime((String) loginInfoMap.get("PUBLISH_INFO_LAST_UPDATE_TIME"));
		rs.setPublishInfoLastUpdateTag((String) loginInfoMap.get("PUBLISH_INFO_LAST_UPDATE_TAG"));
	}

	/**
	 * Instantiates a new purchase service helper.
	 */
	public SsoUtilHelper() {

	}

	private static class Loader {
		/** The Constant instance. */
		private static final SsoUtilHelper instance = new SsoUtilHelper();
	}

	/**
	 * Gets the single instance of PurchaseServiceHelper.
	 *
	 * @return single instance of PurchaseServiceHelper
	 */
	public static SsoUtilHelper getInstance() {
		return Loader.instance;
	}
}
