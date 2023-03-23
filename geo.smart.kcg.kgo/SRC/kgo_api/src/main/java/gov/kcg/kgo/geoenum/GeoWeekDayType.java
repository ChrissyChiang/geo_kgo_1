package gov.kcg.kgo.geoenum;

/** 通用  Enum **/

public enum GeoWeekDayType {

    SUNDAY(0, "日"),
    MONDAY(1, "一"),
    TUESDAY(2, "二"),
    WEDNESDAY(3, "三"),
    THURSDAY(4, "四"),
    FRIDAY(5, "五"),
    SATURDAY(6, "六"),;

    private int code;
    private String label;

    GeoWeekDayType(int code, String label) {
        this.code = code;
        this.label = label;
    }
    public int getCode() {
        return code;
    }
    public String getLabel() {
        return label;
    }

    public static GeoWeekDayType valueOfCode(int code) {
        GeoWeekDayType newType = null;
        for (GeoWeekDayType type : values()) {
            if (type.code==code) {
                newType = type;
                break;
            }
        }
        return newType;
    } //valueOfCode
}
