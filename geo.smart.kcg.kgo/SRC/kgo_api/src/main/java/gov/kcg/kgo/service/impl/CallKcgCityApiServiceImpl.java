package gov.kcg.kgo.service.impl;

import gov.kcg.kgo.enums.common.kcgCityApi.KcgCityApiServiceType;
import gov.kcg.kgo.enums.error.CityApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.service.CallKcgCityApiService;
import gov.kcg.kgo.service.bean.kgo.city.api.viewModel.coin.rs.CoinCompletedRs;
import gov.kcg.kgo.service.bean.kgo.city.api.viewModel.coin.rs.CoinInProgressRs;
import gov.kcg.kgo.service.bean.kgo.city.api.viewModel.coin.rs.CoinSearchRs;
import gov.kcg.kgo.service.bean.kgo.city.api.viewModel.landnum.rs.LandNumRs;
import gov.kcg.kgo.service.impl.helper.CallKcgCityApiServiceHelper;
import gov.kcg.kgo.service.impl.helper.CallKcgTTCApiServiceHelper;
import gov.kcg.kgo.util.HttpRequest;
import gov.kcg.kgo.util.JsonUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.completed.rq.CompletedRq;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.completed.rs.CompletedRs;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.inProgress.rq.InProgressRq;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.inProgress.rs.InProgressRs;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.search.rs.SearchRs;
import gov.kcg.kgo.viewModel.frontend.auth.getCityMemberInfo.rq.FrontendGetCityMemberInfoRq;
import gov.kcg.kgo.viewModel.frontend.auth.getCityMemberInfo.rs.bean.FrontendGetCityMemberInfoRs;
import gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.Push0001Rq;
import gov.kcg.kgo.viewModel.pushApi.push.push0001.rs.Push0001Rs;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 呼叫 城市資料平臺 Service.
 */
@Service("CallKcgCityApiService")
public class CallKcgCityApiServiceImpl implements CallKcgCityApiService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CallKcgCityApiServiceImpl.class);

    /**
     * 呼叫城市資料平臺 ServiceHelper.
     */
    private CallKcgCityApiServiceHelper helper = CallKcgCityApiServiceHelper.getInstance();

    private CallKcgTTCApiServiceHelper helperTTC = CallKcgTTCApiServiceHelper.getInstance();
    private CallKcgCityApiServiceHelper callKcgCityApiServiceHelper = CallKcgCityApiServiceHelper.getInstance();

    /**
     * 市民科技 系統識別碼.
     */
    public final static String EXTERNAL_AGENCY_APPID = "AP002";

    @Autowired
    @Qualifier("ttcRestTemplate")
    private RestTemplate ttcRestTemplate;

    /**
     * 市民科技 系統API URL.
     */
    // 正試區 https://10.100.91.204:443/api/
    // 測試 https://ttcapi.kcg.gov.tw/api/
    @Value("${citizen.technology.apiurl}")
    private String ctApiurl;

    @Value("${citizen.technology.puch.apiurl}")
    private String ctPuchApiurl;

    /**
     * (前臺) 呼叫社會局 城市資料平臺 service 是否符合 1. 低收、2. 中低、3. 身心障礙 身分.
     *
     * @param serviceType the service type
     * @return true, if successful
     */
    public boolean socbuKcgCityApiValidate(KcgCityApiServiceType serviceType) {
        // 是否符合
        boolean isFit = false;
        try {
            // 非自然人憑證登入 拋錯
            if (!KgoUtil.isMoicaLogin()) {
                throw new KgoApiException(new ErrorResult(CityApiError.NOT_MOICA_LOGIN));
            }

            // this.getAuthToken(serviceType.getServiceId());
            // 1. 共通 - 取得城市資料平台 - 自然人憑證登入 API資料.
            String rsJsonStr = helper.getCityApiDataWithMoicaLogin(serviceType.getServiceId());

            // 2. 解析rsJsonStr 確認是否符合資格
            isFit = helper.resolveIsFit(rsJsonStr);

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new KgoApiException(">>>>> socbuKcgCityApiValidate error, " + e.getMessage(), e);
        }
        return isFit;
    }

    /**
     * (前臺) 自然人憑證登入 - 呼叫城市資料平臺 service 回傳response jsons strin.
     *
     * @param serviceType the service type
     * @return the kcg city api json str with moica login
     */
    @Override
    public String getKcgCityApiJsonStrWithMoicaLogin(KcgCityApiServiceType serviceType, Map<String, String> paramsMap) {
        String rsJsonStr = StringUtils.EMPTY;
        ;
        try {
            // 非自然人憑證登入 拋錯
            if (!KgoUtil.isMoicaLogin()) {
                throw new KgoApiException(new ErrorResult(CityApiError.NOT_MOICA_LOGIN));
            }

            // 1. 共通 - 取得城市資料平台 - 自然人憑證登入 API資料.
            rsJsonStr = helper.getCityApiDataWithMoicaLogin(serviceType.getServiceId(), paramsMap);

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new KgoApiException(">>>>> getKcgCityApiJsonStrWithMoicaLogin error, " + e.getMessage(), e);
        }
        return rsJsonStr;
    }

    /**
     * (前臺) 非自然人憑證登入 - 呼叫城市資料平臺 service 回傳response Object.
     *
     * @param <T>         the generic type
     * @param <S>         the generic type
     * @param serviceType the service type
     * @param rsClazz     the rs clazz
     * @param rq          the rq
     * @return the kcg city api json str without moica login
     * @throws Exception the exception
     */
    @Override
    public <T, S> T getKcgCityApiJsonStrNoMoicaLogin(KcgCityApiServiceType serviceType, Class<T> rsClazz, S rq) throws Exception {
        T rs = helper.postCityApiDatanNoMoicaLogin(serviceType.getServiceId(), rsClazz, rq);
        return rs;
    }

    public LandNumRs landNumKcgCityApi() {
        Map<String, String> paramsMap = new HashMap<String, String>();
        LandNumRs landNumRs = null;

        try {
            paramsMap.put("WSID", "UDB_0004");
            landNumRs = this.helper.getCityApiDatanNoMoicaLogin(KcgCityApiServiceType.LAND_NUM.getServiceId(), LandNumRs.class, paramsMap);
        } catch (Exception e) {
            LOGGER.error("landNumOption", e);
        }

        return landNumRs;
    }

    @Override
    public SearchRs getCoinSearch(Integer taskSeq) {
        SearchRs rs = getCityApiCoinSearch(taskSeq);
        if (ObjectUtils.isEmpty(rs)) {
            rs = getTTCApiCoinSearch(taskSeq);
        }
        return rs;
//		if (kgoServiceHelper.isDevMode()) {
//			return getTTCApiCoinSearch(taskSeq);
//		} else {
//			return getCityApiCoinSearch(taskSeq);
//		}
    }

    @Override
    public CompletedRs getCoinCompleted(CompletedRq rq) {
        CompletedRs rs = getCityApiCoinCompleted(rq);
        if (ObjectUtils.isEmpty(rs)) {
            rs = getTTCApiCoinCompleted(rq);
        }

        return rs;
    }

    private FrontendGetCityMemberInfoRs getTTCApiCityMemberInfo(FrontendGetCityMemberInfoRq rq) {
        String apiUrl = String.format("%s/%s", "member", "GetMemberInfo");
        FrontendGetCityMemberInfoRs data = null;

        try {
            data = helperTTC.sendPostTTCApi(apiUrl, FrontendGetCityMemberInfoRs.class, rq);
        } catch (Exception e) {
            LOGGER.error("getTTCApiCityMemberInfo", e);
        }

        return data;
    }

    private SearchRs getCityApiCoinSearch(Integer taskSeq) {
        // TODO Auto-generated method stub

        if (helper.isDevMode())
            return null;
        try {
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("seq", taskSeq.toString());
            CoinSearchRs data = this.helper.getCityApiDatanNoMoicaLogin(KcgCityApiServiceType.CITY_COIN_TASK_INFO.getServiceId(), CoinSearchRs.class, paramsMap);
            return ObjectUtils.isEmpty(data) ? null : data.getData();
        } catch (Exception e) {
            LOGGER.error("getCityApiCoinSearch", e);
        }

        return null;
    }

    private SearchRs getTTCApiCoinSearch(Integer taskSeq) {

        String apiUrl = String.format("%s/%s/%s", "Mission", "data", taskSeq);
        Map<String, String> paramsMap = new HashMap<String, String>();
        SearchRs data = null;

        try {
            data = helperTTC.sendGetTTCApi(apiUrl, SearchRs.class, paramsMap);
        } catch (Exception e) {
            LOGGER.error("getTTCCityCoinSearch", e);
        }

        return data;
    }

    private InProgressRs getTTCApiCoinInProgress(String organizerCode) {

        String apiUrl = String.format("%s/%s", "Mission", "InProgress");
        InProgressRs data = null;

        try {
            InProgressRq rq = new InProgressRq(organizerCode);
            data = helperTTC.sendPostTTCApi(apiUrl, InProgressRs.class, rq);
        } catch (Exception e) {
            LOGGER.error("getTTCCityCoinInProgress", e);
        }

        return data;
    }

    private CompletedRs getCityApiCoinCompleted(CompletedRq rq) {
        if (helper.isDevMode())
            return null;
        try {
            CoinCompletedRs data = this.helper.postCityApiDatanNoMoicaLogin(KcgCityApiServiceType.CITY_COIN_COMPLETED.getServiceId(), CoinCompletedRs.class, rq);
            return ObjectUtils.isEmpty(data) ? null : data.getData();
        } catch (Exception e) {
            LOGGER.error("getCityApiCoinCompleted", e);
        }

        return null;
    }

    private CompletedRs getTTCApiCoinCompleted(CompletedRq rq) {
        String apiUrl = String.format("%s/%s", "Mission", "Completed");
        CompletedRs data = null;

        try {
            data = helperTTC.sendPostTTCApi(apiUrl, CompletedRs.class, rq);
        } catch (Exception e) {
            LOGGER.error("getTTCApiCoinCompleted", e);
        }

        return null;
    }

    private Push0001Rs getTTCApiPushNotification(Push0001Rq rq) {
        String api = String.format("%s/%s", "push", "push0001");
        String apiUrl = String.format("%s%s", helperTTC.getPuchApiUrl(), api);

        Push0001Rs data = null;

        try {
            data = helperTTC.sendPostTTCApi2(Push0001Rs.class, rq, apiUrl);
        } catch (Exception e) {
            LOGGER.error("getTTCCityCoinInProgress", e);
        }

        return data;
    }

    private <T> T sendGetTTCApi(String api, Class<T> valueType, Map<String, String> paramsMap) {
        String jsonString = this.sendGetTTCApi(api, paramsMap);
        return JsonUtil.getObject(jsonString, valueType);
    }

    private String sendGetTTCApi1(String api, Map<String, String> paramsMap) {

        // 添加下面这行代码
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

        String apiUrl = String.format("%s%s", ctApiurl, api);
        LOGGER.info(" url = " + apiUrl);

        ttcRestTemplate.setRequestFactory(new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                if (connection instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) connection).setHostnameVerifier(DO_NOT_VERIFY);
                }
                super.prepareConnection(connection, httpMethod);
            }
        });

        ResponseEntity<String> responseEntity = ttcRestTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity(getHttpHeader()), String.class);

        if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
            if (!responseEntity.getBody().isEmpty()) {
                return responseEntity.getBody();
            }
        }

        System.setProperty("sun.net.http.allowRestrictedHeaders", "false");
        return "";

    }

    private HttpHeaders getHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("platform", "WEB");
        headers.set("Host", "ttcapi.kcg.gov.tw");
        return headers;
    }

    private String sendGetTTCApi(String api, Map<String, String> paramsMap) {
        LOGGER.info(">>>>> sendGetTTCApi >>>>>>");
        HttpRequest httpRequest = new HttpRequest();
        Map<String, String> headers = new HashMap<>();
        headers.put("content-Type", "application/json; charset=utf-8");
        headers.put("Host", "ttcapi.kcg.gov.tw");
        LOGGER.info(" headers = " + JsonUtil.toJSONString(headers));

        String apiUrl = String.format("%s%s", ctApiurl, api);
        LOGGER.info(" url = " + apiUrl);
        String rsJsonStr = httpRequest.sendGet(apiUrl, paramsMap, headers);
        LOGGER.info(">>>>> sendGetTTCApi () rsJsonStr = " + rsJsonStr);

        return rsJsonStr;
    }

    private <T, S> T sendPostTTCApi(String api, Class<T> valueType, S request) {
        String jsonString = this.sendPostTTCApi(api, request);
        return JsonUtil.getObject(jsonString, valueType);
    }

    private <T, S> T sendPostTTCApi(Class<T> valueType, S request, String apiUrl) {
        String jsonString = this.sendPostTTCApi(request, apiUrl);
        return JsonUtil.getObject(jsonString, valueType);
    }

    private <T> String sendPostTTCApi(String api, T request) {

        String apiUrl = String.format("%s%s", ctApiurl, api);
        return sendPostTTCApi(request, apiUrl);
    }

    private <T> String sendPostTTCApi(T request, String apiUrl) {

        LOGGER.info(" url = " + apiUrl);

        String dataJson = JsonUtil.toJSONString(request);
        LOGGER.info(" rq = " + dataJson);

        HttpEntity<String> httpEntity = new HttpEntity<>(dataJson, getHttpHeader());

        ttcRestTemplate.setRequestFactory(new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                if (connection instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) connection).setHostnameVerifier(DO_NOT_VERIFY);
                }
                super.prepareConnection(connection, httpMethod);
            }
        });

        ResponseEntity<String> responseEntity = ttcRestTemplate.postForEntity(apiUrl, httpEntity, String.class);

        if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
            if (!responseEntity.getBody().isEmpty()) {
                return responseEntity.getBody();
            }
        }

        return "";

    }

    private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private <T, S> T sendPostTTCApi2(Class<T> valueType, S request, String apiUrl) {
        String jsonString = this.sendPostTTCApi2(request, apiUrl);
        return JsonUtil.getObject(jsonString, valueType);
    }

    private <T> String sendPostTTCApi2(String api, T request) {

        String apiUrl = String.format("%s%s", ctApiurl, api);
        return sendPostTTCApi2(request, apiUrl);
    }

    private <T> String sendPostTTCApi2(T request, String apiUrl) {
        HttpRequest httpRequest = new HttpRequest();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Host", "ttcapi.kcg.gov.tw");
        LOGGER.info(" headers = " + JsonUtil.toJSONString(headers));

        LOGGER.info(" url = " + apiUrl);
        String requestString = "";
        if (ObjectUtils.isNotEmpty(request)) {
            requestString = JsonUtil.toJSONString(request);
        }
        LOGGER.info(" request = " + requestString);
        String rsJsonStr = httpRequest.sendPost(apiUrl, requestString, headers);
        LOGGER.info(">>>>> sendPostTTCApi () rsJsonStr = " + rsJsonStr);

        return rsJsonStr;
    }

    @Override
    public Push0001Rs pushNotificationByParams(Push0001Rq rq, Map<String,String> params) {
        final String clientId = SpringUtil.getProperty("pushMessage.connection.clienid");
        final String clientSecret = SpringUtil.getProperty("pushMessage.connection.clientsecret");
        Push0001Rs rs = new Push0001Rs();
        String response = "unknow user";

        try {
            String authEncoded = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
            String param = "pubunit=" + rq.getBody().getSysUID() +
                    "&categoryMsg=Activity" +
                    "&categoryGroup=Child" +
                    "&categoryArea=" +
                    "&categoryMymsg=kgo" +
                    "&subject=" + rq.getBody().getDataList().get(0).getPushMsg().getMsgTitle() +
                    "&content=" + rq.getBody().getDataList().get(0).getPushMsg().getClickDetail() +
                    "&push=Y" +
                    "&pid=" + StringUtils.defaultIfBlank(params.get("idn"),"") +
                    "&account=" +StringUtils.defaultIfBlank(params.get("account"),"") +
                    "&accountShort=";
            LOGGER.info("param =>" + param);
            Map<String,String> headerMap = new HashMap<>();
            headerMap.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            headerMap.put("AuthorizationKTC", "Basic " + authEncoded);
            LOGGER.info("Authorization => Basic " + authEncoded);
            response = callKcgCityApiServiceHelper.postCityApiData(KcgCityApiServiceType.PUSH_NOTIFICATION.getServiceId(),headerMap,param);

            if (StringUtils.isNotBlank(response)) {
                LOGGER.info("pushNotificationByTwSsn responseJson =>" + response );
                rs.setBody(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(">>> 推播訊息: " + response);
        }
        return rs;
    }

}
