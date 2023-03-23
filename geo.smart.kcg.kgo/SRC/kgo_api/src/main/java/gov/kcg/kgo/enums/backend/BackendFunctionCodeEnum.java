package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 後台 - 功能類別Enum
 *
 * @author TPIuser
 */
public enum BackendFunctionCodeEnum {
/**
 AccountM	帳號權限管理
 AnnounceM	公告管理
 CaseM	案件管理
 Cassign	案件分文
 Cend	案件結案
 Creview	案件審核
 Csign	案件簽收
 Ctransfer	案件異動管理
 HotSearchM	熱門搜尋管理
 Login	登入
 Logout	登出
 OrganM	機關科室管理
 OWordSet	常用詞庫設定
 SA	服務申辦申請
 SCA	服務案件新增申請
 TaskM	任務管理
 TypeM	分類管理
 URA	系統權限申請


 * */

    /**
     * 帳號權限管理.
     */
    AccountM("AccountM", "kgo.backend.function.accountm"),

    /**
     * 公告管理.
     */
    AnnounceM("AnnounceM", "kgo.backend.function.announcem"),

    /**
     * 案件管理.
     */
    CaseM("CaseM", "kgo.backend.function.casem"),

    /**
     * 案件分文.
     */
    Cassign("Cassign", "kgo.backend.function.cassign"),

    /**
     * 案件結案.
     */
    Cend("Cend", "kgo.backend.function.cend"),

    /**
     * 案件審核.
     */
    Creview("Creview", "kgo.backend.function.creview"),

    /**
     * 案件簽收.
     */
    Csign("Csign", "kgo.backend.function.csign"),

    /**
     * 案件異動管理.
     */
    Ctransfer("Ctransfer", "kgo.backend.function.ctransfer"),

    /**
     * 熱門搜尋管理.
     */
    HotSearchM("HotSearchM", "kgo.backend.function.hotsearchm"),

    /**
     * 登入.
     */
    Login("Login", "kgo.backend.function.login"),

    /**
     * GEO 20211115 add 非市府員工登入.
     */
    CityMemberLogin("CityMemberLogin", "kgo.backend.function.citymember.login"),

    /**
     * 登出.
     */
    Logout("Logout", "kgo.backend.function.logout"),

    /**
     * 機關科室管理.
     */
    OrganM("OrganM", "kgo.backend.function.organm"),

    /**
     * 常用詞庫設定.
     */
    OWordSet("OWordSet", "kgo.backend.function.owordset"),

    /**
     * 服務申辦申請.
     */
    SA("SA", "kgo.backend.function.sa"),

    /**
     * 服務案件新增申請.
     */
    SCA("SCA", "kgo.backend.function.sca"),

    /**
     * 任務管理.
     */
    TaskM("TaskM", "kgo.backend.function.taskm"),

    /**
     * 分類管理.
     */
    TypeM("TypeM", "kgo.backend.function.typem"),

    /**
     * 系統權限申請.
     */
    URA("URA", "kgo.backend.function.ura"),

    /**
     * 案件申辦.
     */
    CaseApply("CaseApply", "kgo.frontend.function.caseapply"),

    /**
     * 取得使用者資訊.
     */
    SearchUsers("SearchUsers", "kgo.backend.function.queryUsers"),

    /**
     * 線上預約臨櫃.
     */
    Appointment("Appointment", "kgo.backend.function.appointment"),

    /**
     * 場地管理
     */
    SiteManagement("siteManagement","kgo.backend.function.siteManagement"),

    /**
     * 線上預約時間設定
     */
    RentalCase("RentalCase","kgo.backend.function.rentalcase");



    /**
     * 功能代碼
     */
    private String functionCode;

    /**
     * 功能名稱 i18n
     */
    private String functionNameI18n;


    private BackendFunctionCodeEnum(String functionCode, String functionNameI18n) {
        this.functionCode = functionCode;
        this.functionNameI18n = functionNameI18n;
    }

    /**
     * 取得 後台 - 功能類別
     */
    public static BackendFunctionCodeEnum getBackendFunctionEnum(String functionCode) {
        for (BackendFunctionCodeEnum e : values()) {
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
