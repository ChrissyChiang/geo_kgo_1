package gov.kcg.kgo.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;

import gov.kcg.kgo.enums.error.KgoFrontEndApiRtnCode;
import gov.kcg.kgo.exception.KgoApiException;

/**
 * 暫時原封不動使用範例程式來測試，之後再調整程式碼
 * 
 * @author TPIuser
 *
 */
public class HttpRequest {
	// TODO：原封不動搬過來，需整理程式碼......

	private static final int TIMEOUT = 3000;
	private static final String RESPONSE_HEADER_RTNCODE = "rtnCode";
	private static final String RESPONSE_HEADER_RTNMSG = "rtnMsg";

	public HttpRequest() {
	}

	/**
	 * Send post (json 格式).
	 *
	 * @param apiUrl     the api url
	 * @param jsonObject the json object
	 * @return the string
	 */
	public String sendPost(String apiUrl, JSONObject jsonObject) {
		HashMap<String, String> headers = new HashMap<String, String>();
		String requestString = jsonObject.toString();

		/**
		 * 設置請求屬性
		 */
		byte[] requestStringBytes = requestString.getBytes();
		headers.put("Content-length", "" + requestStringBytes.length);
		headers.put("Content-Type", "" + "application/json; utf-8");
		headers.put("Accept", "application/json");
		headers.put("Connection", "Keep-Alive");
		headers.put("Charset", "UTF-8");

		return post(apiUrl, requestStringBytes, headers);
	}

	/**
	 * Send post (自定義格試).
	 *
	 * @param apiUrl     the api url
	 * @param jsonObject the json object
	 * @return the string
	 */
	public String sendPost(String apiUrl, String requestString, Map<String, String> headers) {
		byte[] requestStringBytes = requestString.getBytes();
		return post(apiUrl, requestStringBytes, headers);
	}

	/**
	 * POST method
	 * 
	 * input & output format = json
	 * 
	 * @param pathUrl    //request url
	 * @param jsonObject //content data
	 * @param headers    //header data
	 * @return
	 */
	public String post(String pathUrl, byte[] requestStringBytes, Map<String, String> headers) {

		String responseStr = StringUtils.EMPTY;
		HttpURLConnection httpConn = null;
		// 添加下面这行代码
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		try {
			/**
			 * 建立連結
			 */
			URL url = new URL(pathUrl);
			System.out.println("\n post pathUrl: " + pathUrl);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setConnectTimeout(TIMEOUT);

			/**
			 * 設置連結屬性
			 */
			httpConn.setDoOutput(true);// 使用 URL 連接進行輸出
			httpConn.setDoInput(true);// 使用 URL 連接進行輸入
			httpConn.setUseCaches(false);// 忽略暫存
			httpConn.setRequestMethod(RequestMethod.POST.name());// 設置URL請求方法

			/**
			 * 設置標頭屬性
			 */
			if (headers != null) {
				System.out.println("post headers");
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpConn.setRequestProperty(entry.getKey(), entry.getValue());
					System.out.println(entry.getKey() + ":" + entry.getValue());
				}
			}

			/**
			 * 是否 https
			 */
			boolean useHttps = pathUrl.contains("https");
			if (useHttps) {
				HttpsURLConnection https = (HttpsURLConnection) httpConn;
				SSLSocketFactory oldSocketFactory = trustAllHosts(https);
				HostnameVerifier oldHostnameVerifier = https.getHostnameVerifier();
				https.setHostnameVerifier(DO_NOT_VERIFY);
			}

			// URLConnection
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);

			/**
			 * 建立輸入並將資料寫入
			 */
			OutputStream outputStream = httpConn.getOutputStream();
			outputStream.write(requestStringBytes);
			outputStream.close();

			responseStr = getResponse(httpConn);
		}catch (HttpClientErrorException e) {
			return e.getResponseBodyAsString();
		} catch (Exception e) {
			System.out.println(" Exception: " + e);
			throw new KgoApiException(e.getMessage());
		}

		System.setProperty("sun.net.http.allowRestrictedHeaders", "false");
		return responseStr;
	}

	public String sendGet(String apiUrl) {
		return sendGet(apiUrl, null, null);
	}

	public String sendGet(String apiUrl, Map<String, String> data) {
		return sendGet(apiUrl, data, null);
	}

	public String sendGet(String apiUrl, Map<String, String> data, Map<String, String> headers) {
		return get(apiUrl, data, headers);
	}

	private String get1(String pathUrl, Map<String, String> data, Map<String, String> headers) {
		HttpURLConnection c = null;
		String responseStr = "";
		try {
			if (data != null) {
				if (data.size() > 0) {
					pathUrl = pathUrl + "?";
					for (Map.Entry<String, String> entry : data.entrySet()) {
						pathUrl = pathUrl + entry.getKey() + "=" + entry.getValue() + "&";
					}

					pathUrl = pathUrl.substring(0, pathUrl.length() - 1);
				}
			}
			URL u = new URL(pathUrl);
			c = (HttpURLConnection) u.openConnection();

			c.setRequestMethod("GET");

			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.setConnectTimeout(TIMEOUT);
			c.setReadTimeout(TIMEOUT);

			// add request header
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					c.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}

			c.connect();

			int status = c.getResponseCode();
			BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null)
				sb.append(line + "\n");
			br.close();
			responseStr = sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseStr;
	}

	/**
	 * GET method
	 * 
	 * output format = json
	 * 
	 * @param pathUrl
	 * @param data
	 * @param headers
	 * @return
	 */
	private String get(String pathUrl, Map<String, String> data, Map<String, String> headers) {
		String responseStr = "";
		HttpURLConnection httpConn = null;
		// 添加下面这行代码
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		try {
			if (data != null) {
				if (data.size() > 0) {
					pathUrl = pathUrl + "?";
					for (Map.Entry<String, String> entry : data.entrySet()) {
						pathUrl = pathUrl + entry.getKey() + "=" + entry.getValue() + "&";
					}

					pathUrl = pathUrl.substring(0, pathUrl.length() - 1);
				}
			}

			URL obj = new URL(pathUrl);
			System.out.println("GET URL: " + JsonUtil.toJSONString(obj));
			httpConn = (HttpURLConnection) obj.openConnection();
			// optional default is GET
			httpConn.setRequestMethod("GET");

			httpConn.setRequestProperty("Content-length", "0");
			httpConn.setUseCaches(false);
			httpConn.setAllowUserInteraction(false);
			httpConn.setConnectTimeout(TIMEOUT);
			httpConn.setReadTimeout(TIMEOUT);

			// add request header
			if (headers != null) {
				System.out.println("headers");
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpConn.setRequestProperty(entry.getKey(), entry.getValue());
					System.out.println(entry.getKey() + ":" + entry.getValue());
				}
			}

			HttpsURLConnection https;
			boolean useHttps = pathUrl.contains("https");
			if (useHttps) {
				https = (HttpsURLConnection) httpConn;
				SSLSocketFactory oldSocketFactory = trustAllHosts(https);
				HostnameVerifier oldHostnameVerifier = https.getHostnameVerifier();
				https.setHostnameVerifier(DO_NOT_VERIFY);
			}

			// URLConnection
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);

			httpConn.connect();

			// responseStr = getResponse(httpConn);

			int status = httpConn.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));

			String line = "";
			StringBuffer buffer = new StringBuffer();
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			in.close();
			responseStr = buffer.toString();
		} catch (Exception e) {
			System.out.println(" Exception: " + e);
			throw new KgoApiException(e.getMessage());
		}

		System.setProperty("sun.net.http.allowRestrictedHeaders", "false");
		return responseStr;
	}

	/**
	 * 取得 一般呼叫的回應
	 * 
	 * @param httpConn
	 * @return
	 * @throws Exception
	 */
	private String getResponse(HttpURLConnection httpConn) throws Exception {
		String responseStr = StringUtils.EMPTY;
		int responseCode = httpConn.getResponseCode();
		if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功
//			// 当正确响应时处理数据
//			StringBuffer sb = new StringBuffer();
//			// 处理响应流，必须与服务器响应流输出的编码一致
//			BufferedInputStream in = new BufferedInputStream(httpConn.getInputStream());
//			byte[] contents = new byte[1024];
//
//			int bytesRead = 0;
//			String strFileContents = "";
//			while ((bytesRead = in.read(contents)) != -1) {
//				strFileContents += new String(contents, 0, bytesRead);
//			}

			InputStream ist = httpConn.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(ist, "utf-8"));
			StringBuffer sb = new StringBuffer();
			char[] c = new char[1];
			while (in.read(c, 0, 1) == 1) {
				sb.append(c[0]);
			}

			responseStr = sb.toString();
			System.out.println(" response: " + responseStr);
		}
		return responseStr;
	}

	/**
	 * 送出http request 推播 (json 格式)
	 * 
	 * @param pathUrl
	 * @param jsonObject
	 * @param headers
	 * @return
	 */
	public String pushApiSendPost(String pathUrl, JSONObject jsonObject, HashMap<String, String> headers) {
		return pushApiPost(pathUrl, jsonObject, headers);
	}

	/**
	 * API POST method
	 * 
	 * input & output format = json
	 * 
	 * @param pathUrl    //request url
	 * @param jsonObject //content data
	 * @param headers    //header data
	 * @return
	 */
	private String pushApiPost(String pathUrl, JSONObject jsonObject, HashMap<String, String> headers) {

		String responseStr = StringUtils.EMPTY;
		try {
			/**
			 * 建立連結
			 */
			URL url = new URL(pathUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setConnectTimeout(TIMEOUT);

			/**
			 * 設置連結屬性
			 */
			httpConn.setDoOutput(true);// 使用 URL 連接進行輸出
			httpConn.setDoInput(true);// 使用 URL 連接進行輸入
			httpConn.setUseCaches(false);// 忽略暫存
			httpConn.setRequestMethod(RequestMethod.POST.name());// 設置URL請求方法
			String requestString = jsonObject.toString();

			/**
			 * 設置請求屬性
			 */
			byte[] requestStringBytes = requestString.getBytes();
			httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);
			httpConn.setRequestProperty("Content-Type", "application/json; utf-8"); // Set “content-type” request header
																					// to “application/json” to send the
																					// request content in JSON form.
			httpConn.setRequestProperty("Accept", "application/json"); // Set the “Accept” request header to
																		// “application/json” to read the response in
																		// the desired format
			httpConn.setRequestProperty("Connection", "Keep-Alive"); // 維持連結
			httpConn.setRequestProperty("Charset", "UTF-8");

			/**
			 * 設置標頭屬性
			 */
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpConn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}

			/**
			 * 是否 https
			 */
			boolean useHttps = pathUrl.contains("https");
			if (useHttps) {
				HttpsURLConnection https = (HttpsURLConnection) httpConn;
				SSLSocketFactory oldSocketFactory = trustAllHosts(https);
				HostnameVerifier oldHostnameVerifier = https.getHostnameVerifier();
				https.setHostnameVerifier(DO_NOT_VERIFY);
			}

			/**
			 * 建立輸入並將資料寫入
			 */
			OutputStream outputStream = httpConn.getOutputStream();
			outputStream.write(requestStringBytes);
			outputStream.close();

			responseStr = getPushAPIResponse(httpConn);
		} catch (Exception e) {
			System.out.println(" Exception: " + e);
			throw new KgoApiException(e.getMessage());
		}
		return responseStr;
	}

	/**
	 * 取得推播API的回應
	 * 
	 * @param httpConn
	 * @return
	 * @throws Exception
	 */
	private String getPushAPIResponse(HttpURLConnection httpConn) throws Exception {
		String responseStr = StringUtils.EMPTY;
		int responseCode = httpConn.getResponseCode();
		if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功
			String rtnCode = httpConn.getHeaderField(RESPONSE_HEADER_RTNCODE);
			String rtnmsg = httpConn.getHeaderField(RESPONSE_HEADER_RTNMSG);
			if (StringUtils.equals(rtnCode, KgoFrontEndApiRtnCode.M0000.getRtnCode())) {
				BufferedInputStream in = new BufferedInputStream(httpConn.getInputStream());
				byte[] contents = new byte[1024];

				int bytesRead = 0;
				StringBuffer strFileContents = new StringBuffer();
				while ((bytesRead = in.read(contents)) != -1) {
					strFileContents.append(new String(contents, 0, bytesRead));
				}
				responseStr = strFileContents.toString();
			} else {
				throw new Exception("Calling push api occurs error = [ " + rtnCode + ":" + rtnmsg + " ]");
			}
		} else {
			throw new Exception("HTTP response error : " + httpConn.getResponseMessage());
		}
		return responseStr;
	}

	/**
	 * 覆盖java默认的证书验证
	 */
	private static final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	} };

	/**
	 * 设置不验证主机
	 */
	private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	/**
	 * 信任所有
	 * 
	 * @param connection
	 * @return
	 */
	private static SSLSocketFactory trustAllHosts(HttpsURLConnection connection) {
		SSLSocketFactory oldFactory = connection.getSSLSocketFactory();
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			SSLSocketFactory newFactory = sc.getSocketFactory();
			connection.setSSLSocketFactory(newFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oldFactory;
	}

	public String Xml2Json(String xml) {
		String result = "";
		try {
			JSONObject xmlJSONObj = XML.toJSONObject(xml);
			result = xmlJSONObj.toString();
			String jsonPrettyPrintString = xmlJSONObj.toString(4);
			System.out.println(jsonPrettyPrintString);
		} catch (Exception je) {
			System.out.println(je.toString());
		}

		return result;
	}

}
