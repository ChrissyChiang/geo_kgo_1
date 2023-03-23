package gov.kcg.kgo.geoenum;

/**
 * GEO 20211005 add
 * 前台熱門服務排序頻率 搜尋區間 Enum
 */

public enum GeoCaseSetApplyCountType {

    DAY(1, "每日"),
    WEEK(2, "每週"),
    MONTH(3, "每月"),
    CUSTOM_DATE(4,"自訂");

    private int code;
    private String label;

    GeoCaseSetApplyCountType(int code, String label) {
        this.code = code;
        this.label = label;
    }
    public int getCode() {
        return code;
    }
    public String getLabel() {
        return label;
    }

    public static GeoCaseSetApplyCountType valueOfCode(int code) {
        GeoCaseSetApplyCountType newType = null;
        for (GeoCaseSetApplyCountType type : values()) {
            if (type.code==code) {
                newType = type;
                break;
            }
        }
        return newType;
    } //valueOfCode
}
