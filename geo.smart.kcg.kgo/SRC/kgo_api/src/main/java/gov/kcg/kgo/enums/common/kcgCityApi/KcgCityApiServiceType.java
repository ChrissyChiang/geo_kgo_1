package gov.kcg.kgo.enums.common.kcgCityApi;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

/**
 * 城市資料平台 類型 enum
 */
public enum KcgCityApiServiceType {

    //GEO 20210815 add --> 以下 serviceId 可查官方 swagger(但參數資料不全)：https://api.kcg.gov.tw/Service/Doc#/
    //api身份驗證token參數於api.properties

    /**
     * 低收入戶資格查詢
     */
    LOW_MIDDLE_INCOME("ba5ab006-321e-495c-be27-14386b5da9cc", HttpMethod.GET),

    /**
     * 中低收入戶資格查詢
     */
    LOW_AND_MIDDLE_INCOME("9d76ebff-a285-4451-980d-d916cc3a45e6", HttpMethod.GET),

    /**
     * 高雄市身心障礙資格查詢
     **/
    DISABILITY("7d65cf3d-6030-446e-b86d-7c0fe59c5624", HttpMethod.GET),

    /**
     * 高雄土地段代碼清單-地政局
     **/
    LAND_NUM("b6f2002b-e11b-460f-8e25-f757bfc02af4", HttpMethod.GET),

    /**
     * 市民科技整合服務─訊息推播(單則對單人、多則對多人)
     **/
    PUSH_NOTIFICATION("79cfe743-769d-47b0-ba06-e1a57651ee23", HttpMethod.POST),

    /**
     * 市民科技整合服務─城市幣任務內容
     **/
    CITY_COIN_TASK_INFO("222ef0b1-eaef-4f80-996f-503ed6249a8c", HttpMethod.GET),

    /**
     * 市民科技整合服務─城市幣任務獎勵
     **/
    CITY_COIN_COMPLETED("ceb4df5d-b809-419b-a13d-5196c35ad1d1", HttpMethod.POST),

    /**
     * 市民科技-任務得點
     */
    CITY_EXTERNAL_TASK("21ee53ab-e995-447a-9f26-0b9c1283a59f",HttpMethod.POST),
    /**
     * 市民科技整合服務─市民會員資訊
     **/
    CITIZEN_MEMBER_INFO("d5a772ca-0ff6-4387-a512-a10a2e7619ed", HttpMethod.POST),

    /**
     * 便民一路通─取得案件編號 (A流程)
     **/
    KGO_GET_CASEID("e6c9b3df-a7d7-46c4-8220-6b45d372ab70", HttpMethod.POST),

    /**
     * 市民支付平台-繳費入帳查詢(測試機)
     **/
    KGO_PAYMENT_RECORD_DATA("0cdbe773-3b1c-40c0-8520-936bbf74931b", HttpMethod.POST),

    /**
     * 市民支付平台-繳費導頁至繳費平台(測試機)
     **/
    KGO_PAYMENT_WEB_PAGE("0cdbe773-3b1c-40c0-8520-936bbf74931b", HttpMethod.POST),

    /**
     * 市民支付平台-退費呼叫支付平台(測試機)
     **/
    KGO_REFEND("27962db2-3365-486f-b111-80e80a6a8db0", HttpMethod.POST),

    /**
     * 市民支付平台-繳費入帳查詢
     */
    KGO_CITY_ERTRYDATE_LIST("0cdbe773-3b1c-40c0-8520-936bbf74931b",HttpMethod.POST),
    /**
     * 區政E指通黑名單(POST))
     **/
    KGO_POST_BLACKLIST("abca99ce-9af2-4573-9502-0eb7fdd4ec7e", HttpMethod.POST),

    /**
     * 跨機關自動入案(POST))
     **/
    KGO_POST_CROSS_ORGAN("b9297656-76db-4826-9cd2-8852866fe24f", HttpMethod.POST);

    /**
     * 服務ID
     */
    private String serviceId;
    private HttpMethod method;

    private KcgCityApiServiceType(String serviceId, HttpMethod method) {
        this.serviceId = serviceId;
        this.method = method;
    }

    public static KcgCityApiServiceType getKcgCityApiServiceType(String serviceId) {
        for (KcgCityApiServiceType type : values()) {
            if (StringUtils.equals(type.getServiceId(), serviceId)) {
                return type;
            }
        }
        return null;
    }

    public String getServiceId() {
        return serviceId;
    }

    public HttpMethod getMethod() {
        return method;
    }

}
