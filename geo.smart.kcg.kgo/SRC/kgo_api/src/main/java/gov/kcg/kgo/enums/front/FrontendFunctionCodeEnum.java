package gov.kcg.kgo.enums.front;

import org.apache.commons.lang3.StringUtils;

/**
 * 前台 - 功能類別Enum
 *
 * @author TPIuser
 */
public enum FrontendFunctionCodeEnum {
/**
 CaseApply	案件申辦
 CaseQry	案件查詢
 Eservice	智能客服
 HotSearch	熱門搜尋
 Login	登入
 Logout	登出
 OrganType	機關分類
 QAndA	常見問題
 RoleType	角色分類
 Search	快速搜尋
 ServiceType	服務分類
 PayAndRefund   線上繳費與退費

 * */

    /**
     * 案件申辦.
     */
    CaseApply("CaseApply", "kgo.frontend.function.caseapply"),

    /**
     * 案件查詢.
     */
    CaseQry("CaseQry", "kgo.frontend.function.caseqry"),

    /**
     * 智能客服.
     */
    Eservice("Eservice", "kgo.frontend.function.eservice"),

    /**
     * 熱門搜尋.
     */
    HotSearch("HotSearch", "kgo.frontend.function.hotsearch"),

    /**
     * 登入.
     */
    Login("Login", "kgo.frontend.function.login"),

    /**
     * 員工登入.
     */
    StaffLogin("StaffLogin", "kgo.frontend.function.staff.login"),

    /**
     * 登出.
     */
    Logout("Logout", "kgo.frontend.function.logout"),

    /**
     * 機關分類.
     */
    OrganType("OrganType", "kgo.frontend.function.organtype"),

    /**
     * 常見問題.
     */
    QAndA("QAndA", "kgo.frontend.function.qanda"),

    /**
     * 角色分類.
     */
    RoleType("RoleType", "kgo.frontend.function.roletype"),

    /**
     * 快速搜尋.
     */
    Search("Search", "kgo.frontend.function.search"),

    /**
     * MyData Case 搜尋.
     * Geo 20221009 add_Jim
     */
    MyDataCaseSearch("MyDataCaseSearch", "kgo.frontend.function.myDataCaseSearch"),

    /**
     * 服務分類.
     */
    ServiceType("ServiceType", "kgo.frontend.function.servicetype"),

    /**
     * 線上場地活動預約
     */
    RentalCase("RentalCase","kgo.kgo.frontend.function.rentalcase"),

    /**
     * 線上繳費與退費
     */
    PayAndRefund("PayAndRefund","kgo.frontend.function.payandrefund");


    /**
     * 功能代碼
     */
    private String functionCode;

    /**
     * 功能名稱 i18n
     */
    private String functionNameI18n;


    private FrontendFunctionCodeEnum(String functionCode, String functionNameI18n) {
        this.functionCode = functionCode;
        this.functionNameI18n = functionNameI18n;
    }

    /**
     * 取得 後台 - 功能類別
     */
    public static FrontendFunctionCodeEnum getFrontendFunctionEnum(String functionCode) {
        for (FrontendFunctionCodeEnum e : values()) {
            if (StringUtils.equals(e.getFunctionCode(), functionCode)) {
                return e;
            }
        }
        return null;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public String getFunctionNameI18n() {
        return functionNameI18n;
    }
}
