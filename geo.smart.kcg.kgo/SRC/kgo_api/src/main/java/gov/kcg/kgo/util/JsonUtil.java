package gov.kcg.kgo.util;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.kcg.kgo.exception.KgoApiException;

/**
 * The Class JsonUtil.
 * 
 * @author Richard
 */
public class JsonUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Json字串轉換為物件.
	 * 
	 * @param json  json字串.
	 * @param clazz 欲轉換的Java Class.
	 * @return T 轉換後的物件
	 * 
	 */
	public static <T> T getObject(String json, Class<T> clazz) {
		if (StringUtils.isBlank(json)) {
			return null;
		}

		try {
			return mapper.readValue((String) json, clazz);
		} catch (IOException e) {
			LOGGER.error("getObject", e.fillInStackTrace());
			throw new KgoApiException("JsonUtil getObject error ", e);
		}
	}

	public static String toJSONString(Object value) {
		String jsonString = null;

		try {
			jsonString = mapper.writeValueAsString(value);

		} catch (IOException e) {
			LOGGER.error("toJsonString", e.fillInStackTrace());
			throw new KgoApiException("JsonUtil toJsonString error ", e);
		}
		return jsonString;
	}

	/**
	 * set jsonObeject jsonObject1.put( key , jsonObject2.getString( key ))
	 *
	 * @param jsonObject1
	 * @param jsonObject2
	 * @param key
	 */
	public static void setJsonObject(JSONObject jsonObject1, JSONObject jsonObject2, String key) {
		try {
			jsonObject1.put(key, getJsonObjectStr(jsonObject2, key));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * return jsonObject.getString(key)
	 *
	 * @param jsonObject
	 * @param key
	 * @return
	 */
	public static String getJsonObjectStr(JSONObject jsonObject, String key) {
		String result = "";
		try {
			result = jsonObject.has(key) ? jsonObject.getString(key) : "";
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * return jsonObject(content).getString(key)
	 *
	 * @param content
	 * @param key
	 * @return
	 */
	public static String getValueByKey(String content, String key) {
		String value = "";

		if (StringUtils.isNoneBlank(content) && StringUtils.isNoneBlank(key)) {
			try {
				JSONObject jsonObject = new JSONObject(content);
				value = jsonObject.has(key) ? jsonObject.getString(key) : "";
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return value;
	}
}
