package gov.kcg.kgo.service.impl.helper;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.enums.error.CityApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geomodel.ktcpay.GeoKgoPaymentRecordRq;
import gov.kcg.kgo.util.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 呼叫城市資料平臺 ServiceHelper.
 */
public class CallKcgCityApiServiceHelper extends KgoServiceHelper {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CallKcgCityApiServiceHelper.class);

    // 城市資料平台 service url, https://api.kcg.gov.tw/api/service
    private String kcgServiceUrl;

    private String kcgAppId;

    private String kcgToken;

    private String kcgServiceKey;


    /**
     * 共通 - 取得城市資料平台 - 非自然人憑證登入 API資料.
     *
     * @param <T>       Object Type
     * @param serviceId the service id
     * @param valueType Object Class
     * @return Object
     * @throws Exception
     */
    public <T> T getCityApiDataWithMoicaLogin(String serviceId, Class<T> valueType) throws Exception {
        return getCityApiDataWithMoicaLogin(serviceId, valueType, new HashMap<String, String>());
    }

    /**
     * 共通 - 取得城市資料平台 - 非自然人憑證登入 API資料.
     *
     * @param <T>
     * @param serviceId
     * @param valueType
     * @param rqMap
     * @return
     * @throws Exception
     */
    public <T> T getCityApiDataWithMoicaLogin(String serviceId, Class<T> valueType, Map<String, String> rqMap) throws Exception {
        String jsonString = this.getCityApiDataWithMoicaLogin(serviceId, rqMap);
        return JsonUtil.getObject(jsonString, valueType);
    }

    /**
     * 共通 - 取得城市資料平台 - 自然人憑證登入 API資料.
     *
     * @param serviceId the service id
     * @return the city api data with moica login
     * @throws Exception the exception
     */
    public String getCityApiDataWithMoicaLogin(String serviceId) throws Exception {

        return getCityApiDataWithMoicaLogin(serviceId, new HashMap<String, String>());
    }

    /**
     * 共通 - 取得城市資料平台 - 自然人憑證登入 API資料.
     *
     * @param serviceId
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public String getCityApiDataWithMoicaLogin(String serviceId, Map<String, String> paramsMap) throws Exception {
        String rsData = StringUtils.EMPTY;
        // 是否為自然人憑證登入.
        if (!KgoUtil.isMoicaLogin()) {
            throw new KgoApiException(new ErrorResult(CityApiError.NOT_MOICA_LOGIN));
        }

        // 1.連線資訊初始化.
        ininConnection();

        // 2.取得授權token
        String authToken = getAuthToken(serviceId);

        if (StringUtils.isNotBlank(authToken)) {

            // 3.取得城市資料平台 - 自然人憑證登入 API資料
            rsData = getCityApiDataByMoicaLogin(authToken, serviceId, paramsMap);
        }
        return rsData;

    }

    /**
     * 共通 - 取得城市資料平台 - 非自然人憑證登入 API資料.
     *
     * @param <T>       Object Type
     * @param serviceId the service id
     * @param valueType Object Class
     * @return Object
     * @throws Exception
     */
    public <T> T getCityApiDatanNoMoicaLogin(String serviceId, Class<T> valueType) throws Exception {

        return getCityApiDatanNoMoicaLogin(serviceId, valueType, new HashMap<String, String>());
    }

    /**
     * 共通 - 取得城市資料平台 - 非自然人憑證登入 API資料.
     *
     * @param <T>
     * @param serviceId
     * @param valueType
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public <T> T getCityApiDatanNoMoicaLogin(String serviceId, Class<T> valueType, Map<String, String> paramsMap) throws Exception {
        String jsonString = this.getCityApiDatanNoMoicaLogin(serviceId, paramsMap);
        return JsonUtil.getObject(jsonString, valueType);
    }

    /**
     * 共通 - 取得城市資料平台 - 非自然人憑證登入 API資料.
     *
     * @param serviceId the service id
     * @return the city api data with moica login
     * @throws Exception the exception
     */
    public String getCityApiDatanNoMoicaLogin(String serviceId) throws Exception {
        return getCityApiDatanNoMoicaLogin(serviceId, new HashMap<String, String>());
    }

    /**
     * 共通 - 取得城市資料平台 - 非自然人憑證登入 API資料.
     *
     * @param serviceId
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public String getCityApiDatanNoMoicaLogin(String serviceId, Map<String, String> paramsMap) throws Exception {
        String rsData = StringUtils.EMPTY;

        // 1.連線資訊初始化.
        ininConnection();

        // 2.取得授權token
        String authToken = getAuthToken(serviceId);

        if (StringUtils.isNotBlank(authToken)) {

            // 3.取得城市資料平台 - 非自然人憑證登入 API資料
            rsData = getCityApiDataNoMoicaLogin(authToken, serviceId, paramsMap);
        }
        return rsData;
    }

    /**
     * 共通 - 取得城市資料平台 - 非自然人憑證登入 API資料.
     *
     * @param <T>       Object Type
     * @param serviceId the service id
     * @param valueType Object Class
     * @return Object
     * @throws Exception
     */
    public <T> T postCityApiDatanNoMoicaLogin(String serviceId, Class<T> valueType) throws Exception {

        return postCityApiDatanNoMoicaLogin(serviceId, valueType, null);
    }

    /**
     * 共通 - 取得城市資料平台 - 非自然人憑證登入 API資料.
     *
     * @param <T>
     * @param serviceId
     * @param valueType
     * @return
     * @throws Exception
     */
    public <T, S> T postCityApiDatanNoMoicaLogin(String serviceId, Class<T> valueType, S request) throws Exception {
        String jsonString = this.postCityApiDatanNoMoicaLogin(serviceId, request);
        return JsonUtil.getObject(jsonString, valueType);
    }

    /**
     * 共通 - 取得城市資料平台 - 非自然人憑證登入 API資料.
     *
     * @param serviceId the service id
     * @return the city api data with moica login
     * @throws Exception the exception
     */
    public String postCityApiDatanNoMoicaLogin(String serviceId) throws Exception {
        LOGGER.info("call postCityApiDatanNoMoicaLogin serviceId=" + serviceId);
        return postCityApiDatanNoMoicaLogin(serviceId, null);
    }

    /**
     * 共通 - 取得城市資料平台 - 非自然人憑證登入 API資料.
     *
     * @param <T>
     * @param serviceId
     * @param request
     * @return
     * @throws Exception
     */
    public <T> String postCityApiDatanNoMoicaLogin(String serviceId, T request) throws Exception {

        LOGGER.info("postCityApiDatanNoMoicaLogin serviceId=" + serviceId);

        String rsData = StringUtils.EMPTY;

        // 1.連線資訊初始化.
        ininConnection();

        // 2.取得授權token
        String authToken = getAuthToken(serviceId);

        if (StringUtils.isNotBlank(authToken)) {

            // 3.取得城市資料平台 - 非自然人憑證登入 API資料
            rsData = postCityApiDataNoMoicaLogin(authToken, serviceId, request);
        }
        return rsData;
    }

    /**
     * 取得授權token.
     *
     * @return the auth token
     * @throws Exception the exception
     */
    private String getAuthToken(String serviceId) throws Exception {
        HttpRequest httpRequest = new HttpRequest();

        // 1.取得授權token
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", kcgToken);

        Map<String, String> data = new HashMap<>();
        data.put("appId", kcgAppId);

        String rsJsonStr = httpRequest.sendGet(kcgServiceUrl + "Token/" + serviceId, data, headers);

        LOGGER.info("getAuthToken rsJsonStr" + rsJsonStr);

        String authToken = JsonUtil.getValueByKey(rsJsonStr, "data");
        LOGGER.info(">>>>> getSocbAuthToken authToken = " + authToken);
        return authToken;
    }

    /**
     * 取得城市資料平台 - 自然人憑證登入 API資料
     *
     * @return the unit data
     * @throws Exception
     */
    private String getCityApiDataByMoicaLogin(String authToken, String serviceId, Map<String, String> paramsMap) throws Exception {
        FrontendLoginUserInfo loginUser = KgoUtil.getFrontendLoginUser();

        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("PRIVILEGED_APP_SSO_TOKEN", SsoUtil.getFrontendPrivilegedAppSsoToken());

        // TODO loginUser.getUserSsoToken() 為空 就須重新登入
        headersMap.put("KCG_MOICA_USER_SSO_TOKEN", loginUser.getUserSsoToken());

        paramsMap.put("key", kcgServiceKey);
        // 身分證號
        // TODO 先hardcode demo用
//		final String mockId = "S201963906";
        paramsMap.put("id_", loginUser.getTwSSn());
//		paramsMap.put("id_", mockId);

        return this.sendGetKgoCityApi(authToken, serviceId, headersMap, paramsMap);
    }

    /**
     * 取得城市資料平台 - 非自然人憑證登入 API資料
     *
     * @return the unit data
     * @throws Exception
     */
    private String getCityApiDataNoMoicaLogin(String authToken, String serviceId, Map<String, String> paramsMap) throws Exception {
        return this.sendGetKgoCityApi(authToken, serviceId, null, paramsMap);

    }

    /**
     * 取得城市資料平台 - 非自然人憑證登入 API資料
     *
     * @return the unit data
     * @throws Exception
     */
    private <T> String postCityApiDataNoMoicaLogin(String authToken, String serviceId, T request) throws Exception {
        return this.sendPostKgoCityApi(authToken, serviceId, null, null, request);
    }

    /**
     * 判斷1. 低收、2. 中低、3. 身心障礙 身分 解析rsJsonStr 確認是否符合資格.
     *
     * @param rsJsonStr the rs json str
     * @return true, if is fit
     */
    public boolean resolveIsFit(String rsJsonStr) {
        boolean isFit = false;
        JSONObject obj = JSONObject.parseObject(rsJsonStr);
        Object parObj = obj.get("data");

        // 有資料 符合資格
        if (parObj instanceof Map) {
            isFit = true;
            LOGGER.info(">>>>> isFit() Map 符合資格 : " + parObj);
        }

        if (parObj instanceof String) {
            isFit = false;
            LOGGER.info(">>>>> isFit() String 不符合資格 : " + parObj);
        }
        return isFit;
    }

    /**
     * Call 城市資料平台 Get 方式
     *
     * @param authToken
     * @param serviceId
     * @param headersMap
     * @param paramsMap
     * @return
     * @throws Exception
     */
    private String sendGetKgoCityApi(String authToken, String serviceId, Map<String, String> headersMap, Map<String, String> paramsMap) throws Exception {
        HttpRequest httpRequest = new HttpRequest();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authToken);

        if (headersMap != null) {
            headersMap.forEach((key, value) -> {
                headers.put(key, value);
            });
        }

        Map<String, String> data = new HashMap<>();
        data.put("appId", kcgAppId);

        // 新增 Request params
        if (paramsMap != null) {
            paramsMap.forEach((key, value) -> {
                data.put(key, value);
            });
        }

        String rsJsonStr = httpRequest.sendGet(kcgServiceUrl + "get/" + serviceId, data, headers);
        LOGGER.info(">>>>> sendGetKgoCityApi () url = " + kcgServiceUrl + "get/" + serviceId);
        LOGGER.info(">>>>> sendGetKgoCityApi () rsJsonStr = " + rsJsonStr);

        return rsJsonStr;
    }

    /**
     * Call 城市資料平台 Post 方式
     *
     * @param authToken
     * @param serviceId
     * @param headersMap
     * @return
     * @throws Exception
     */
    private <T> String sendPostKgoCityApi(String authToken, String serviceId, Map<String, String> headersMap, Map<String, String> parames, T request) throws Exception {
        HttpRequest httpRequest = new HttpRequest();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authToken);

        if (headersMap != null) {
            headersMap.forEach((key, value) -> {
                headers.put(key, value);
            });
        }

        String apiUrl = this.getCityApiUrl(serviceId, "post", parames);
        String requestString = "";
        if (ObjectUtils.isNotEmpty(request)) {
            requestString = JsonUtil.toJSONString(request);
        }

        LOGGER.info(">>>>> sendPostKgoCityApi () url = " + apiUrl);
        LOGGER.info(">>>>> sendPostKgoCityApi () resquestsJsonStr = " + requestString); //GEO 20210815 fixed.

        String rsJsonStr = httpRequest.sendPost(apiUrl, requestString, headers);// (kcgServiceUrl + "post/" + serviceId,

        LOGGER.info(">>>>> sendPostKgoCityApi () responseJsonStr = " + rsJsonStr); //GEO 20210815 fixed.

        return rsJsonStr;
    } //sendPostKgoCityApi

    /**
     * 取得城市資料平台 API URL
     *
     * @param serviceId
     * @param HttpMethod
     * @param parames
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getCityApiUrl(String serviceId, String HttpMethod, Map<String, String> parames) throws UnsupportedEncodingException {

        Map<String, String> data = new HashMap<>();
        data.put("appId", kcgAppId);
        if (ObjectUtils.isNotEmpty(parames)) {
            data.putAll(parames);
        }
        String parameStr = getParamesStr(data);
        return String.format("%s%s/%s?%s", kcgServiceUrl, HttpMethod, serviceId, parameStr);
    }

    /**
     * 取的 Parames 產生 Query String
     *
     * @param parames
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getParamesStr(Map<String, String> parames) throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();
        for (Entry<String, String> item : parames.entrySet()) {
            if (result.length() > 0) {
                result.append("&");
            }
            result.append(URLEncoder.encode(item.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(item.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    /**
     * 連線資訊初始化.
     */
    private void ininConnection() {
        LOGGER.info("ininConnection ...");
        ApiProperties apiProperties = SpringUtil.getBean(ApiProperties.class);
        // 城市資料平台 service url, https://api.kcg.gov.tw/api/service
        this.kcgServiceUrl = kcgServiceUrl == null ? apiProperties.getKcgServiceUrl() : kcgServiceUrl;

        this.kcgAppId = kcgAppId == null ? apiProperties.getKcgAppId() : kcgAppId;

        this.kcgToken = kcgToken == null ? apiProperties.getKcgToken() : kcgToken;

        this.kcgServiceKey = kcgServiceKey == null ? apiProperties.getKcgServiceKey() : kcgServiceKey;
    }

    /**
     * Instantiates a new common service helper.
     */
    public CallKcgCityApiServiceHelper() {

    }

    // TODO 共通邏輯方法
    private static class Loader {
        /**
         * The Constant instance.
         */
        private static final CallKcgCityApiServiceHelper instance = new CallKcgCityApiServiceHelper();
    }

    /**
     * Gets the single instance of CommonServiceHelper.
     *
     * @return single instance of CommonServiceHelper
     */
    public static CallKcgCityApiServiceHelper getInstance() {
        return Loader.instance;
    }
    /**
     *   城市資料平台呼叫POST
     *   header需要傳入各自需要的Content-Type
     **/
    public String postCityApiData(String serviceId, Map<String,String> headerMap, String requestStr){
        ininConnection();
        String rtn = null ;
        HttpRequest httpRequest = new HttpRequest();
        try{
            String authToken = this.getAuthToken(serviceId);
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", authToken);
//            headers.put("Content-Type", "application/json");
            if (headerMap != null) {
                headerMap.forEach((key, value) -> {
                    headers.put(key, value);
                });
            }

            if (ObjectUtils.isNotEmpty(authToken)) {
                String apiUrl = kcgServiceUrl + "post/" + serviceId;
                LOGGER.info(">>>>> sendPostKgoCityApi () url = " + apiUrl);
                LOGGER.info(">>>>> sendPostKgoCityApi () resquestStr = " + requestStr);
                String result = httpRequest.sendPost(apiUrl, requestStr, headers);
                LOGGER.info(">>>>> sendPostKgoCityApi () responseJsonStr = " + result);
                org.json.JSONObject rtnJson = new org.json.JSONObject(result);
                if( true == rtnJson.getBoolean("success")){
                    rtn = rtnJson.getString("data");
                }else{
                    LOGGER.info(">>>>> sendPostKgoCityApi () response Message: "+ rtnJson.getString("message"));
                }
            }


        }catch (Exception e){
            LOGGER.info("城市資料平台呼叫api失敗: serviceId = "+serviceId +" request: "+requestStr);
        }
        return rtn ;
    }

    /**
     * 20221028 取得城市資料平台 - 非自然人憑證登入 API資料.
     */
    public org.json.JSONObject postCityApiData(String serviceId, org.json.JSONObject request) throws Exception {
        LOGGER.info("postCityApiData=" + serviceId);
        org.json.JSONObject bodyjson = null;
        // 1.連線資訊初始化
        ininConnection();
        // 2.取得授權token
        String authToken = getAuthToken(serviceId);
        // 3.取得城市資料平台 - 非自然人憑證登入 API資料
        if (StringUtils.isNotBlank(authToken)) {
            Map<String, String> headers = new HashMap<>();
            //向城市資料平台上的api取資料
            String apiUrl = "https://api.kcg.gov.tw/api/service/post/" + serviceId;
            String requestString = request == null ? new org.json.JSONObject().toString() : request.toString();
            /**
             * 設置請求屬性
             */
            byte[] requestStringBytes = requestString.getBytes();
            headers.put("Content-length", "" + requestStringBytes.length);
            headers.put("Content-Type", "" + "application/json; utf-8");
            headers.put("Accept", "application/json");
            headers.put("Connection", "Keep-Alive");
            headers.put("Charset", "UTF-8");
            headers.put("Authorization", authToken);

            HttpRequest httpRequestS = new HttpRequest();
            String res = httpRequestS.post(apiUrl, requestStringBytes, headers);
            if (res != null) {
                bodyjson = GeoJsonUtil.stringToJson(res);
            }
        }
        return bodyjson;
    }

    /**
     * 市民支付平台-繳費入帳查詢(測試機)
     */
    public org.json.JSONObject postCityApiData(String serviceId, String kcgToken, String kcgServiceUrl, String kcgAppId) throws Exception {
        LOGGER.info("市民支付平台 postCityApiData=" + serviceId);
        org.json.JSONObject bodyjson = null;

        // 2.取得城市資料平台授權token
        /********************************************/
        HttpRequest httpRequest = new HttpRequest();

        Map<String, String> headersCity = new HashMap<>();
        headersCity.put("Content-Type", "application/json");
        headersCity.put("Authorization", kcgToken);

        Map<String, String> data = new HashMap<>();
        data.put("appId", kcgAppId);

        String rsJsonStr = httpRequest.sendGet(kcgServiceUrl + "Token/" + serviceId, data, headersCity);

        LOGGER.info("getAuthToken rsJsonStr" + rsJsonStr);

        String authToken = JsonUtil.getValueByKey(rsJsonStr, "data");

        /********************************************/

        // 3.從城市資料平台 取得市民科技資料
        if (StringUtils.isNotBlank(authToken)) {
            Map<String, String> headers = new HashMap<>();
            //向城市資料平台上的api取資料
            String apiUrl = "https://10.101.182.132/api/service/post/" + serviceId;

            // 組裝請求
            GeoKgoPaymentRecordRq rq = new GeoKgoPaymentRecordRq();
            rq.setEntryDate("20221101");
            ObjectMapper objectMapper = new ObjectMapper();
            String requestStr = objectMapper.writeValueAsString(rq);

            /**
             * 設置請求屬性
             */
            byte[] requestStringBytes = requestStr.getBytes();
            headers.put("Content-length", "" + requestStringBytes.length);
            headers.put("Content-Type", "" + "application/json; utf-8");
            headers.put("Accept", "application/json");
            headers.put("Connection", "Keep-Alive");
            headers.put("Charset", "UTF-8");
            headers.put("Authorization", authToken);

            HttpRequest httpRequestS = new HttpRequest();
            String res = httpRequestS.post(apiUrl, requestStringBytes, headers);
            if (res != null) {
                bodyjson = GeoJsonUtil.stringToJson(res);
            }
        }
        return bodyjson;
    }
}
