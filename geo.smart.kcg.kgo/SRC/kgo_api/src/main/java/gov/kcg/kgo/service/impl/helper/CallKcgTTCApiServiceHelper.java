package gov.kcg.kgo.service.impl.helper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import gov.kcg.kgo.util.HttpRequest;
import gov.kcg.kgo.util.JsonUtil;
import gov.kcg.kgo.util.SpringUtil;

public class CallKcgTTCApiServiceHelper extends KgoServiceHelper {
	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CallKcgTTCApiServiceHelper.class);

	private RestTemplate ttcRestTemplate;

	private String ctApiurl;

	private String ctPuchApiurl;

	public <T> T sendGetTTCApi(String api, Class<T> valueType, Map<String, String> paramsMap) {
		String jsonString = this.sendGetTTCApi(api, paramsMap);
		return JsonUtil.getObject(jsonString, valueType);
	}

	public String sendGetTTCApi1(String api, Map<String, String> paramsMap) {

		initialize();
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

	public String sendGetTTCApi(String api, Map<String, String> paramsMap) {
		initialize();
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

	public <T, S> T sendPostTTCApi(String api, Class<T> valueType, S request) {
		String jsonString = this.sendPostTTCApi(api, request);
		return JsonUtil.getObject(jsonString, valueType);
	}

	public <T, S> T sendPostTTCApi(Class<T> valueType, S request, String apiUrl) {
		String jsonString = this.sendPostTTCApi(request, apiUrl);
		return JsonUtil.getObject(jsonString, valueType);
	}

	public <T> String sendPostTTCApi(String api, T request) {
		initialize();
		String apiUrl = String.format("%s%s", ctApiurl, api);
		return sendPostTTCApi(request, apiUrl);
	}

	public <T> String sendPostTTCApi(T request, String apiUrl) {
		initialize();

		LOGGER.info(" url = " + apiUrl);

		String dataJson = JsonUtil.toJSONString(request);
		LOGGER.info(" rq = " + dataJson);

		// 添加下面这行代码
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
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

		

		try {
			ResponseEntity<String> responseEntity = ttcRestTemplate.postForEntity(apiUrl, httpEntity, String.class);
			if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
				if (!responseEntity.getBody().isEmpty()) {
					return responseEntity.getBody();
				}
			}
		} catch (HttpClientErrorException e) {
			// TODO: handle exception
			return e.getResponseBodyAsString();
		}finally {
			System.setProperty("sun.net.http.allowRestrictedHeaders", "false");
		}

		return "";

	}

	private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	public <T, S> T sendPostTTCApi2(Class<T> valueType, S request, String apiUrl) {
		String jsonString = this.sendPostTTCApi2(request, apiUrl);
		return JsonUtil.getObject(jsonString, valueType);
	}

	public <T> String sendPostTTCApi2(String api, T request) {
		initialize();
		String apiUrl = String.format("%s%s", ctApiurl, api);
		return sendPostTTCApi2(request, apiUrl);
	}

	public <T> String sendPostTTCApi2(T request, String apiUrl) {
		initialize();
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

	public String getPuchApiUrl() {
		if (StringUtils.isBlank(this.ctPuchApiurl))
			initialize();
		return this.ctPuchApiurl;
	}

	public String getApiUrl() {
		if (StringUtils.isBlank(this.ctApiurl))
			initialize();
		return this.ctApiurl;
	}

	private void initialize() {
		initialize(false);
	}

	public void initialize(boolean reInit) {
		if (ObjectUtils.isEmpty(this.ttcRestTemplate) || reInit) {
			this.ttcRestTemplate = SpringUtil.getBean("ttcRestTemplate", RestTemplate.class);
			this.ctApiurl = SpringUtil.getProperty("citizen.technology.apiurl");
			this.ctPuchApiurl = SpringUtil.getProperty("citizen.technology.puch.apiurl");
		}
	}

	/**
	 * Instantiates a new common service helper.
	 */
	public CallKcgTTCApiServiceHelper() {

	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final CallKcgTTCApiServiceHelper instance = new CallKcgTTCApiServiceHelper();
	}

	/**
	 * Gets the single instance of CommonServiceHelper.
	 *
	 * @return single instance of CommonServiceHelper
	 */
	public static CallKcgTTCApiServiceHelper getInstance() {
		return Loader.instance;
	}

}
