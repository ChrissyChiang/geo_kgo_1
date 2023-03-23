package gov.kcg.kgo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import gov.kcg.kgo.common.LoginUserInfo;
import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.constant.SecurityConstant;
import gov.kcg.kgo.enums.common.JwtParseType;
import gov.kcg.kgo.enums.common.SysType;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoCityMember;
import gov.kcg.kgo.georepository.GeoCityMemberRepos;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCityCoinMembershipViewForm;
import gov.kcg.kgo.service.CityCoinAPIService;
import gov.kcg.kgo.service.impl.helper.AuthServiceHelper;
import gov.kcg.kgo.sso.apiLib.ApiClient;
import gov.kcg.kgo.sso.apiLib.ApiInfoExchangeClient;
import gov.kcg.kgo.sso.util.JwtToken;
import gov.kcg.kgo.util.helper.SsoUtilHelper;
import gov.kcg.kgo.viewModel.sso.appBasicAuth.rs.Wsz01a001Rs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rq.WsZ0503aRq;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.base.QueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.facebook.FacebookQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.google.GoogleQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.hca.HcaCardQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgEgov.KcgEgovQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgUserBasic.KcgUserBasicInfoQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.line.LineQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.moicaCard.MoicaCardQueryInfoRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.twfido.TwFidoQueryInfoRs;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SsoUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SsoUtil.class);

    /**
     * 登入類型: 前台、後台 SESSION KEY.
     */
    private final static String SESSION_KEY_SYS_TYPE = "sysType";

    /**
     * 後台 - 應用系統身分認證 Object.
     */
    private static Wsz01a001Rs beAppBasicAuthObject = null;

    /**
     * 前台 - 應用系統身分認證 Object
     */
    private static Wsz01a001Rs feAppBasicAuthObject = null;

    /**
     * 登入使用者資訊JsonStr SESSION KEY.
     */
    public final static String SESSION_KEY_USER_LIGIN_INO_JSON_STR = "userLoginInfoJsonStr";

    /** 登入使用者資訊Object SESSION KEY. */
//	public final static String SESSION_KEY_USER_LIGIN_INO_OBJECT = "userLoginInfoObject";

    /**
     * 後臺 登入使用者資訊 Object SESSION KEY.
     */
    public final static String BACKEND_USER_INO_KEY = "backendUserInfo";

    /**
     * 前臺 登入使用者資訊 Object SESSION KEY.
     */
    public final static String FRONTEND_USER_INO_KEY = "frontendUserInfo";

    /**
     * 成功代碼: 0.
     */
    public final static String SUCCESS_CODE = "0";

    /**
     * SSOUtil 輔助程式.
     */
    private static SsoUtilHelper ssoHelper = SsoUtilHelper.getInstance();

    /**
     * 授權、身分驗證等 ServiceHelper
     */
    private static AuthServiceHelper authHelper = AuthServiceHelper.getInstance();

    /**
     * 程式資料平台 單登AES加密 key&iv
     */
    private static final String CITY_IV = "B6SO4II4q9uJfdkmrWWgZw==";
    private static final String CITY_KEY = "Ijh7MKntiwK7Ee0LAeIQsX3/jjj9hxE8DLYfpkahyYs=";

    /**
     * 取得後臺 應用系統身分認證object.
     *
     * @param loginSysType 登入類型: 前台、後台.
     * @return the app basic auth
     */
    public static Wsz01a001Rs getBackendAppBasicAuth() {
        LOGGER.info("SsoUtil getBackendAppBasicAuth...");
        try {
            // WS-Z01-A0-01: 進行應用系統進行身分驗證, 應用系統身分認證處理 (驗證前後臺是否已經呼叫過身分驗證、token是否過期)
            beAppBasicAuthObject = ssoHelper.appBasicAuthObjectPreHandle(SysType.BACK, beAppBasicAuthObject,
                    feAppBasicAuthObject);

            // 後台 AppAuthObject
            return beAppBasicAuthObject;

        } catch (ParseException e) {
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.UNAUTHORIZED));
        }
    } //getBackendAppBasicAuth

    /**
     * 取得前臺 應用系統身分認證object.
     *
     * @param loginSysType 登入類型: 前台、後台.
     * @return the app basic auth
     */
    public static Wsz01a001Rs getFrontendAppBasicAuth() {
        LOGGER.info("SsoUtil getFrontendAppBasicAuth...");
        try {
            // WS-Z01-A0-01: 進行應用系統進行身分驗證, 應用系統身分認證處理 (驗證前後臺是否已經呼叫過身分驗證、token是否過期)
            feAppBasicAuthObject = ssoHelper.appBasicAuthObjectPreHandle(SysType.FRONT, beAppBasicAuthObject,
                    feAppBasicAuthObject);

            // 前臺 AppAuthObject
            return feAppBasicAuthObject;

        } catch (ParseException e) {
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.UNAUTHORIZED));
        }
    } //getFrontendAppBasicAuth

    /**
     * 取得後臺 應用系統驗證 PrivilegedAppSsoToken.
     *
     * @return the privileged app sso token
     */
    public static String getBackendPrivilegedAppSsoToken() {
        // 取得後臺 應用系統身分認證object
        Wsz01a001Rs rs = getBackendAppBasicAuth();
        return rs != null ? rs.getPrivilegedAppSsoToken() : "";
    }

    /**
     * 取得前臺 應用系統驗證 PrivilegedAppSsoToken.
     *
     * @return the privileged app sso token
     */
    public static String getFrontendPrivilegedAppSsoToken() {
        // 取得前臺 應用系統身分認證object
        Wsz01a001Rs rs = getFrontendAppBasicAuth();
        return rs != null ? rs.getPrivilegedAppSsoToken() : "";
    }

    /**
     * 後臺登入 統一處裡方法 登入失敗 throws Exception
     *
     * @param jwtToken the jwt token
     * @return the login user info
     * @throws InstantiationException the instantiation exception
     * @throws IllegalAccessException the illegal access exception
     */
    public static LoginUserInfo doBackendLogin(String jwtToken) throws Exception {
        LOGGER.info("SsoUtil doBackendLogin...");
        JwtToken jwtTokenUtil = JwtToken.getInstance();
        String parseJwtStr = jwtTokenUtil.parseJwt(jwtToken, JwtParseType.BODY);

        // 後台無各系統介接 交換資訊
        String exchange = "";
        // WS-Z05-03a :查詢交換資訊(使用者登入資訊) 存入session
        LoginUserInfo loginUserInfo = loginQueryInfoAndSaveToSession(jwtToken, parseJwtStr, SysType.BACK, exchange);
        return loginUserInfo;
    } //doBackendLogin

    /**
     * GEO 20211115 add 後臺非市府員工登入
     * 統一處裡方法 登入方式: 1.jtwToken SSO單登登入 2.exchange: 與其他系統介接 (jsonStr AES encode)
     * 登入失敗 throws Exception
     *
     * @param jwtToken
     * @return
     * @throws Exception
     */
    public static LoginUserInfo doBackendCityMemberLogin(String jwtToken, String exchange) throws Exception {
        LOGGER.info("SsoUtil doBackendCityMemberLogin...");
        JwtToken jwtTokenUtil = JwtToken.getInstance();
        String parseJwtStr = StringUtils.EMPTY;

        if (StringUtils.isNotBlank(jwtToken)) {
            parseJwtStr = jwtTokenUtil.parseJwt(jwtToken, JwtParseType.BODY);
        }

        // WS-Z05-03a :查詢交換資訊(使用者登入資訊) 存入session
        LoginUserInfo loginUserInfo = loginQueryInfoAndSaveToSession(jwtToken, parseJwtStr, SysType.BACK, exchange);
        return loginUserInfo;
    } //doBackendCityMemberLogin

    /**
     * 前臺登入 統一處裡方法 登入失敗 throws Exception
     *
     * @param jwtToken the jwt token
     * @return the login user info
     * @throws InstantiationException the instantiation exception
     * @throws IllegalAccessException the illegal access exception
     */
    public static LoginUserInfo doFrontendStaffLogin(String jwtToken) throws Exception {
        LOGGER.info("SsoUtil doFrontendStaffLogin...");
        JwtToken jwtTokenUtil = JwtToken.getInstance();
        String parseJwtStr = jwtTokenUtil.parseJwt(jwtToken, JwtParseType.BODY);

        // 後台無各系統介接 交換資訊
        String exchange = "";
        // WS-Z05-03a :查詢交換資訊(使用者登入資訊) 存入session
        LoginUserInfo loginUserInfo = loginQueryInfoAndSaveToSession(jwtToken, parseJwtStr, SysType.FRONT, exchange);
        return loginUserInfo;
    } //doBackendLogin

    /**
     * 前臺登入 統一處裡方法 登入方式: 1.jtwToken SSO單登登入 2.exchange: 與其他系統介接 (jsonStr AES encode)
     * 登入失敗 throws Exception
     *
     * @param jwtToken the jwt token
     * @return the login user info
     * @throws InstantiationException the instantiation exception
     * @throws IllegalAccessException the illegal access exception
     */
    public static LoginUserInfo doFrontendLogin(String jwtToken, String exchange) throws Exception {
        LOGGER.info("SsoUtil doFrontendLogin...");
        JwtToken jwtTokenUtil = JwtToken.getInstance();
        String parseJwtStr = StringUtils.EMPTY;

        if (StringUtils.isNotBlank(jwtToken)) {
            parseJwtStr = jwtTokenUtil.parseJwt(jwtToken, JwtParseType.BODY);
        }

        // WS-Z05-03a :查詢交換資訊(使用者登入資訊) 存入session
        LoginUserInfo loginUserInfo = loginQueryInfoAndSaveToSession(jwtToken, parseJwtStr, SysType.FRONT, exchange);
        return loginUserInfo;
    } //doFrontendLogin

    /**
     * WS-Z05-03a :查詢交換資訊(使用者登入資訊) 存入session.
     *
     * @param <T>
     * @param jwtToken the jwt token
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T extends QueryInfoRs> LoginUserInfo loginQueryInfoAndSaveToSession(String jwtToken,
                                                                                       String parseJwtStr, SysType sysType, String exchange) throws Exception {
        LOGGER.info("SsoUtil loginQueryInfoAndSaveToSession...");
        // 取得查詢交換資訊rq.
        // 授權 SSO 權杖
        String privilegedAppToken = StringUtils.EMPTY;

        LoginAuthTokenType loginAuthTokenType = null;
        String userLoginInfoJsonStr = StringUtils.EMPTY;

        // 後臺 - 市府員工
        if (SysType.BACK.equals(sysType)) {
            privilegedAppToken = getBackendPrivilegedAppSsoToken();

            // 前臺 - 一般民眾
        } else {
            privilegedAppToken = getFrontendPrivilegedAppSsoToken();

        }

        // 登入方式:
        // 1. SSO 登入jwtToken 有值
        // 2. exchange 各系統介接登入
        if (StringUtils.isNotBlank(jwtToken)) {
            // 取得單一登入用戶登入認證類型.
            loginAuthTokenType = ssoHelper.getUserLoginTokenType(parseJwtStr);
            WsZ0503aRq rq = ssoHelper.getQueryInfoRq(privilegedAppToken, parseJwtStr, loginAuthTokenType);

            // WS-Z05-03a :查詢交換資訊(使用者登入資訊).
            ApiInfoExchangeClient apiInfoExchangeClient = new ApiInfoExchangeClient();
            userLoginInfoJsonStr = apiInfoExchangeClient.queryInfo(rq);
        } else {
            // AES/CBC/PKCS7Padding 加密
            // 程式資料平台 單登AES加密 key & iv
            String exchangeUrlDecode = URLDecoder.decode(exchange, "UTF-8");
            userLoginInfoJsonStr = AESUtil.cbcPkcs7Decrypt(exchangeUrlDecode, CITY_KEY, CITY_IV);
            // 各系統介接登入認證類型.
            loginAuthTokenType = ssoHelper.getUserLoginTokenTypeByLoginInfoJsonStr(userLoginInfoJsonStr);
        }
        LOGGER.info(">>>>> userLoginInfoJsonStr = " + JsonUtil.toJSONString(userLoginInfoJsonStr));

        // 測試用
//		String privilegedAppToken = "f2acf51f-5642-4b48-8ddc-1992771802eb";

        // 取得使用者登入物件.
        T rsLoginUserInfo = ssoHelper.getLoginUerInfoObject(loginAuthTokenType, userLoginInfoJsonStr);

        LoginUserInfo loginUserInfo = null;
        LOGGER.info(">>>>> rsLoginUserInfo = " + JsonUtil.toJSONString(rsLoginUserInfo));

        // 成功代碼: 0.
        if (rsLoginUserInfo.getErrorCode().equals(SUCCESS_CODE)) {
            // 設置登入使用者資訊
            loginUserInfo = setLoginUserToSession(jwtToken, parseJwtStr, loginAuthTokenType, rsLoginUserInfo, sysType,
                    userLoginInfoJsonStr);
        } else {
            LOGGER.error(">>>>> login error rsLoginUserInfo: {} ", rsLoginUserInfo);

            // 登入失敗
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.LOGIN_ERROR));
        }

        return loginUserInfo;
    } //loginQueryInfoAndSaveToSession

    /**
     * 設置登入使用者資訊.
     *
     * @param <T>                the generic type
     * @param jwtToken           the jwt token
     * @param loginAuthTokenType the login auth token type
     * @param rsLoginUserInfo    the rs login user info
     * @throws Exception
     */
    public static <T extends QueryInfoRs> LoginUserInfo setLoginUserToSession(String jwtToken, String parseJwtStr,
                                                                              LoginAuthTokenType loginAuthTokenType, T rsLoginUserInfo, SysType sysType, String userLoginInfoJsonStr)
            throws Exception {
        LOGGER.info("SsoUtil setLoginUserToSession...");
        LOGGER.info("SsoUtil setLoginUserToSession userLoginInfoJsonStr 須去掉: " + userLoginInfoJsonStr);
        LoginUserInfo loginUser = new LoginUserInfo();
        loginUser.setJwtToken(jwtToken);
        loginUser.setLoginAuthTokenType(loginAuthTokenType);
        loginUser.setUserLoginInfoJsonStr(userLoginInfoJsonStr);
        // 設置登入時間 - 讀取系統參數設置timeout用
        loginUser.setLoginTime(new Date());

        // 各系統介接 exange資料 (AES加密).
        String exange = AESUtil.cbcPkcs7Encrypt(userLoginInfoJsonStr, CITY_KEY, CITY_IV);
        loginUser.setExchange(URLEncoder.encode(exange, "UTF-8"));

        // 取得jwt token 失效日期 (測試登入會帶空)
        if (StringUtils.isNotEmpty(jwtToken) && StringUtils.isNotEmpty(parseJwtStr)) {
            loginUser.setJwtTokenExpDate(getJwtExpDate(parseJwtStr));

            // 依據登入類型取得 userSsoToken
            String tokenName = loginAuthTokenType.getAuthTokenName();
            String userSsoToken = JsonUtil.getValueByKey(parseJwtStr, tokenName);
            loginUser.setUserSsoToken(userSsoToken);
        }
        /**GEO 20211115 add 非市府員工登入後臺*/
        // 市府員工
        if (LoginAuthTokenType.BASIC.equals(loginAuthTokenType)) {
            KcgUserBasicInfoQueryInfoRs rsInfo = (KcgUserBasicInfoQueryInfoRs) rsLoginUserInfo;
            loginUser.setKcgUserBasicInfo(rsInfo.getPublishInfoContent().getKcgUserBasicInfo());
        }

        // 非市府員工 自然人憑證 登入資訊
        if (LoginAuthTokenType.MOICA.equals(loginAuthTokenType)) {
            MoicaCardQueryInfoRs rsInfo = (MoicaCardQueryInfoRs) rsLoginUserInfo;
            loginUser.setKcgMoicaCardInfo(rsInfo.getPublishInfoContent().getKcgMoicaCardInfo());
        }
        // 非市府員工 google 登入資訊
        if (LoginAuthTokenType.GOOGLE.equals(loginAuthTokenType)) {
            GoogleQueryInfoRs rsInfo = (GoogleQueryInfoRs) rsLoginUserInfo;
            loginUser.setKcgGoogleSsoInfo(rsInfo.getPublishInfoContent().getKcgGoogleSsoInfo());
        }

        // 非市府員工 facebook 登入資訊
        if (LoginAuthTokenType.FACEBOOK.equals(loginAuthTokenType)) {
            FacebookQueryInfoRs rsInfo = (FacebookQueryInfoRs) rsLoginUserInfo;
            loginUser.setKcgFacebookSsoInfo(rsInfo.getPublishInfoContent().getKcgFacebookSsoInfo());
        }

        // 非市府員工 我的 e 政府 登入資訊
        if (LoginAuthTokenType.EGOV.equals(loginAuthTokenType)) {
            KcgEgovQueryInfoRs rsInfo = (KcgEgovQueryInfoRs) rsLoginUserInfo;
            loginUser.setKcgEgovInfo(rsInfo.getPublishInfoContent().getKcgEgovInfo());
        }

        // 非市府員工 HacCard 登入資訊
        if (LoginAuthTokenType.HCA.equals(loginAuthTokenType)) {
            HcaCardQueryInfoRs rsInfo = (HcaCardQueryInfoRs) rsLoginUserInfo;
            loginUser.setKcgHcaCardSsoInfo(rsInfo.getPublishInfoContent().getKcgHcaCardSsoInfo());
        }

        // 非市府員工 LINE用戶基本資訊 登入資訊
        if (LoginAuthTokenType.LINE.equals(loginAuthTokenType)) {
            LineQueryInfoRs rsInfo = (LineQueryInfoRs) rsLoginUserInfo;
            loginUser.setLineInfo(rsInfo.getPublishInfoContent().getLineInfo());
        }

        // 非市府員工 TW FidO用戶登入資訊
        if (LoginAuthTokenType.TW_FIDO.equals(loginAuthTokenType)) {
            TwFidoQueryInfoRs rsInfo = (TwFidoQueryInfoRs) rsLoginUserInfo;
            loginUser.setKcgTwFidoSsoInfo(rsInfo.getPublishInfoContent().getKcgTwFidoSsoInfo());
        }
        // 後臺 - 登入資訊
        if (SysType.BACK.equals(sysType)) {
            BackendLoginUserInfo beUserInfo = authHelper.setBeUserDetail(new BackendLoginUserInfo(loginUser)); // 設置 資料 (市府人員&非市府人員 姓名、機關、單位、角色清單).
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); // 後臺 使用者資訊 存入session
            WebUtils.setSessionAttribute(request, BACKEND_USER_INO_KEY, beUserInfo);
            LOGGER.info(">>>>> BACKEND_USER_INO_KEY SessionId = " + WebUtils.getSessionId(request)); //記錄在 header cookie JSESSIONID

            // 給予後臺登入者API 使用權限 預設角色.
            authSecurityUser(beUserInfo.getUserId(), "", Arrays.asList(SecurityConstant.BACKEND_ROLE));

            // 一般民眾 & 市府員工 (市府員工也可以登入前台)
        } else {
            FrontendLoginUserInfo feUserInfo = new FrontendLoginUserInfo(loginUser);
            // 前台 使用者資訊 存入session
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            WebUtils.setSessionAttribute(request, FRONTEND_USER_INO_KEY, feUserInfo);
            CityCoinAPIService cityCoinAPIService = SpringUtil.getBean(CityCoinAPIService.class);
            cityCoinAPIService.checkMemberShipByLoginType();
            String twSsn = feUserInfo.getTwSSn();
            GeoCityCoinMembershipViewForm form = new GeoCityCoinMembershipViewForm();
            if (!Strings.isBlank(twSsn)) {
                form = cityCoinAPIService.checkMemberShipByTWSsn(twSsn);
            }
            if (!form.isRealName())
                form = cityCoinAPIService.checkMemberShipByLoginType();
            if (form.isRealName()) {
                loginUser.setMemberHashID(Strings.isBlank(form.getLicense()) ? StringUtils.EMPTY : form.getLicense()); // 身分證號Hash
                loginUser.setkCoinBalance(Strings.isBlank(form.getCityCoin()) ? StringUtils.EMPTY : form.getCityCoin()); // 市民城市幣
                loginUser.setMemberName(Strings.isBlank(form.getName()) ? StringUtils.EMPTY : form.getName()); // 姓名
                GeoCityMemberRepos geoCityMemberRepos = SpringUtil.getBean(GeoCityMemberRepos.class);
                geoCityMemberRepos.save(new GeoKgoCityMember(form.getUuid().toLowerCase(), form.getLicense(), form.isRealName()));
                feUserInfo = new FrontendLoginUserInfo(loginUser);
            }
            LOGGER.info(">>>>> FRONTEND_USER: " + JSON.toJSONString(feUserInfo));
            WebUtils.setSessionAttribute(request, FRONTEND_USER_INO_KEY, feUserInfo);
            LOGGER.info(">>>>> FRONTEND_USER_INO_KEY SessionId = " + WebUtils.getSessionId(request));

            // 給予前臺登入者API 使用權限 預設角色
            authSecurityUser(feUserInfo.getUserAccount(), "", Arrays.asList(SecurityConstant.FRONTEND_ROLE));
        } //if (SysType.BACK.equals(sysType))

        LOGGER.info(">>>>> loginUser = " + JsonUtil.toJSONString(loginUser) + " 登入類型 = " + loginAuthTokenType.name());
        // 使用者資訊 jsonStr 紀錄在session
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//		WebUtils.setSessionAttribute(request, SESSION_KEY_USER_LIGIN_INO_OBJECT, loginUser);

        return loginUser;
    } //setLoginUserToSession

    /**
     * 取得jwt token 失效日期.
     *
     * @param parseJwtStr the parse jwt str
     * @return the jwt exp date
     */
    private static Date getJwtExpDate(String parseJwtStr) {
        // 設置token 失效日期
        JSONObject jsonObject = JSONObject.parseObject(parseJwtStr);
        long exp = jsonObject.getLong("exp") * 1000;
        return new Date(exp);
    }

    /**
     * 給予登入者API 使用權限 預設角色.
     *
     * @param username   the username
     * @param credential the credential
     * @param roles      the roles
     */
    private static void authSecurityUser(String username, String credential, List<String> roles) {
        List<GrantedAuthority> grantAuthorities = Lists.newArrayList();
        for (String role : roles) {
            grantAuthorities.add(new SimpleGrantedAuthority(role));
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, credential, grantAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * session 設置登入類型: 前台、後台.
     *
     * @param sysType the new login sys type
     */
//	public static void setLoginSysType(SysType sysType) {
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//		WebUtils.setSessionAttribute(request, SESSION_KEY_SYS_TYPE, sysType);
//	}

    /**
     * session 取得登入類型: 前台、後台.
     *
     * @return the app basic auth
     */
//	public static SysType getLoginSysType() {
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//		SysType sysType = (SysType) WebUtils.getSessionAttribute(request, SESSION_KEY_SYS_TYPE);
//		return sysType;
//	}
    public static void main(String[] args) throws Exception {

        // WS-Z01-A0-01: 進行應用系統進行身分驗證
        testappBaseAuth();

        // 後台 WS-Z05-03a :查詢交換資訊(使用者登入資訊).
//		doBackendLogin("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhY2NvdW50cy5rY2cuZ292LnR3IiwiaWF0IjoxNjA1MDc5ODE0LCJleHAiOjE2MDUwODE2MTQsIlBVQkxJQ19BUFBfVVNFUl9TU09fVE9LRU4iOiI2MzAxYmUwZi02NWY5LTQzOTEtODk0Ny1iMjk4M2FmOGIyYjEifQ.NBuyT29F6c3WZHYDyICdA-i4IX8xPQxvTbOnQO4EhCw");

//		Date date = new Date(1603953902l * 1000);
//		System.out.print(DateUtil.dateToString(date, DateUtil.PATTEN_FULL_TIME_MS));
    }

    private static void testappBaseAuth() throws Exception {
        ApiClient apiClient = new ApiClient();
        // 後台 kcg_accounts_api_app_private_id, kcg_accounts_api_app_private_passwd
//		String reponseStr = apiClient.appRequestBasicAuthentication("471c9e043d48cd976ba42967717f996a", "b85d8eecac17a8e807fc49eebb2467a7");

//		// 前台
        String reponseStr = apiClient.appRequestBasicAuthentication("a05be1489241177ddf13f6198284d783",
                "8d466da5b030caccc87e9f33b0dc01aa");
//
        System.out.println("reponseStr >> " + reponseStr);

//		Map<String, String> authRsMap = JsonUtil.getObject(reponseStr, Map.class);
//		System.out.println(authRsMap);
//
//		Wsz01a001Rs beAppAuthObject = new Wsz01a001Rs();
//		beAppAuthObject.setErrorCode(authRsMap.get("ERROR_CODE"));
//		beAppAuthObject.setPublicAppSsoToken(authRsMap.get("PUBLIC_APP_SSO_TOKEN"));
//		beAppAuthObject.setPrivilegedAppSsoToken(authRsMap.get("PRIVILEGED_APP_SSO_TOKEN"));
//		beAppAuthObject.setPrivateAppSsoToken(authRsMap.get("PRIVATE_APP_SSO_TOKEN"));
//		Date publicAppSSoTokenExDate = DateUtil.strToDate(authRsMap.get("PUBLIC_APP_SSO_TOKEN_EXPIRY_DATE"),
//				DateUtil.PATTEN_TIMEZONE);
//		beAppAuthObject.setPublicAppSsoTokenExpiryDate(publicAppSSoTokenExDate);
//
//		Date privilegedAppSSoTokenExDate = DateUtil.strToDate(authRsMap.get("PRIVILEGED_APP_SSO_TOKEN_EXPIRY_DATE"),
//				DateUtil.PATTEN_TIMEZONE);
//		beAppAuthObject.setPrivilegedAppSsoTokenExpiryDate(privilegedAppSSoTokenExDate);
//
//		Date privateAppSSoTokenExDate = DateUtil.strToDate(authRsMap.get("PRIVATE_APP_SSO_TOKEN_EXPIRY_DATE"),
//				DateUtil.PATTEN_TIMEZONE);
//		beAppAuthObject.setPrivateAppSsoTokenExpiryDate(privateAppSSoTokenExDate);
//
//		System.out.println(">>>>> beAppAuthObject " + JsonUtil.toJSONString(beAppAuthObject));
//
//		System.out.println(">>>>> beAppAuthObject PUBLIC_APP_SSO_TOKEN_EXPIRY_DATE : "
//				+ DateUtil.dateToString(publicAppSSoTokenExDate, DateUtil.PATTEN_FULL_TIME_SLASH));
//
//		System.out.println(">>>>> appTokenIsExpired " + ssoHelper.appTokenIsExpired(beAppAuthObject));
    }
}
