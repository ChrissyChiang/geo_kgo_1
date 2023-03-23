package gov.kcg.kgo.sso;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import gov.kcg.kgo.util.ClassUtil;
import gov.kcg.kgo.util.HttpRequest;

import java.util.HashMap;

/**
 * 暫時原封不動使用範例程式來測試，之後再調整程式碼
 * 
 * @author TPIuser
 *
 */
public class KcgIkpdClient {
	//TODO：原封不動搬過來，需整理程式碼......
	
    private String base_url;
    private String[] protocol = new String[]{"http://", "https://"};

    public KcgIkpdClient(){
        this.base_url = "https://ikpd.kcg.gov.tw/ckpd/";
        /**
         * 檢查base_url的protocol
         */
        boolean without_protocol = true;
        int size = protocol.length;
        for (int i=0; i<size; i++){
            if(compareStr(this.base_url, protocol[i])){
                without_protocol = false;
            }
        }

        if (without_protocol){
            this.base_url = "https://"+this.base_url;
        }else {
            this.base_url = base_url;
        }

//        HttpRequest.getInstance().set_server_url(this.base_url);
    }

    public JSONObject GetUserData(String Org, String ServiceID, String idno) {
        String token = _GetAuth(Org, ServiceID);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("B01IDNO", idno);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject user_data = _GetDATA(ServiceID, token, jsonObject);
        return user_data;
    }

    private String _GetAuth(String Org, String ServiceID){
        String path = "/PCSPService.asmx/GetAuth";
        HashMap<String, String> get = new HashMap<String, String>();
        get.put("sCardNo", "");
        get.put("Org", Org);
        get.put("ServiceID", ServiceID);

        String response = ClassUtil.getInstance(HttpRequest.class).sendGet(path, get);
        //json in, xml out
        System.out.println("/PCSPService.asmx/GetAuth, response: "+response);
        /*
        {
            "ERROR_CODE":"0",
            "PUBLISH_INFO_LAST_UPDATE_TIME":"20180611133611+0800",
            "PUBLISH_INFO_LAST_UPDATE_TAG":"d4aad0a735dfaa318eb014dbd736e78d",
            "PUBLISH_INFO_CONTENT":{
                "KCG_USER_BASIC_INFO":{
                "APP_COMPANY_ID":"KCG",
                "APP_USER_LOGIN_ID":"shyu96",
                "AUTH_FROM_ADDRESS":"128.5.240.43",
                "AUTH_DATE":"20180611053611",
                "APP_USER_TW_SSN":"S122239341"
                }
            }
        }
        */

        /**
         * parse xml
         */
        String token = "";
        try {
            JSONObject jsonObject = new JSONObject(ClassUtil.getInstance(HttpRequest.class).Xml2Json(response));
            token = jsonObject.getJSONObject("string").getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(token.isEmpty()){
            try {
                throw new Exception("fail to IKPD GetAuth, got empty Token, response= "+response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return token;
    }

    private JSONObject _GetDATA(String ServiceID, String Token, JSONObject QryPara){
        String path = "/PCSPService.asmx/GetDATA";

        HashMap<String, String> get = new HashMap<String, String>();
        get.put("ServiceID", ServiceID);
        get.put("Token", Token);
        get.put("QryPara", QryPara.toString());

        String response = ClassUtil.getInstance(HttpRequest.class).sendGet(path, get);
        JSONObject response_json_object = new JSONObject();
        JSONObject return_json_object = new JSONObject();
        try {
            response_json_object    = new JSONObject(ClassUtil.getInstance(HttpRequest.class).Xml2Json(response));
            String base64Str        = response_json_object.getJSONObject("string").getString("content");
            String base64Str_decode = StringUtils.newStringUtf8(Base64.decodeBase64(base64Str));
            return_json_object      = new JSONArray(base64Str_decode).getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * $data_array[0] ->
         * {
         * "B02SORCOD":"397200000A",
         * "B02POLDAT":"",
         * "B01NAME":"呂雅婷",
         * "B02TITCOD":"1102",
         * "B02ARVDAT":"1010317"
         * }
         */

        return return_json_object;
    }

    private boolean compareStr(String str1, String str2){
        int strlen = str2.length();
        boolean is_equal = str1.substring(0, strlen-1).equals(str2);
        return !is_equal;
    }
}
