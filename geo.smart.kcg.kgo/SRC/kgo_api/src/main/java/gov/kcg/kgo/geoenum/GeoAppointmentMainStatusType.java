package gov.kcg.kgo.geoenum;

/**
 * GEO 20211015 add
 * 線上預約臨櫃 啟用狀態 Enum
 */

public enum GeoAppointmentMainStatusType {

    ON(1, "on"),
    OFF(2, "off"),
    DELETE(3, "delete");

    private int code;
    private String label;

    GeoAppointmentMainStatusType(int code, String label) {
        this.code = code;
        this.label = label;
    }
    public int getCode() {
        return code;
    }
    public String getLabel() {
        return label;
    }

    public static GeoAppointmentMainStatusType valueOfCode(int code) {
        GeoAppointmentMainStatusType newType = null;
        for (GeoAppointmentMainStatusType type : values()) {
            if (type.code==code) {
                newType = type;
                break;
            }
        }
        return newType;
    } //valueOfCode
}
