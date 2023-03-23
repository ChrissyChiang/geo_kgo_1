package gov.kcg.kgo.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsonUtil {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoJsonUtil.class);

    public static JSONObject stringToJson(String jsonStr) {
        try {
            JSONObject json = new JSONObject(jsonStr);
            //LOGGER.info("GeoJsonUtil stringToJson json: "+json.toString());
            return json;
        } catch (JSONException e) {
            LOGGER.error("\n >>>>>>>stringToJson>>> " + e.getMessage(), e);
        }
        return null;
    } //stringToJson

    public static JSONArray stringToJsonArray(String jsonStr) {
        try {
            JSONArray jsonArr = new JSONArray(jsonStr);
            //LOGGER.info("GeoJsonUtil stringToJsonArray json: "+json.toString());
            return jsonArr;
        } catch (JSONException e) {
            LOGGER.error("\n >>>>>>>stringToJsonArray>>> " + e.getMessage(), e);
        }
        return null;
    } //stringToJson
}
