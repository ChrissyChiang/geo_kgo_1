package gov.kcg.kgo.geoenum;

/**
 * GEO 20211210 Add
 * 線上預約狀態 enum
 */
public enum GeoAppointmentActionType {

    /**
     * 預約成功
     */
    SUCCESS_APPOINTMENT("預約"),

    /**
     * 取消預約
     */
    CANCEL_APPOINTMENT("取消預約");

    private String actionType;

    private GeoAppointmentActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }
}
