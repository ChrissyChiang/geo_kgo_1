package gov.kcg.kgo.geoenum;

public enum GeoAppointmentTimeType {
    /**
     * 上午
     */
    MORNING("早上"),

    /**
     * 下午
     */
    AFTER("下午");

    private String actionType;

    private GeoAppointmentTimeType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }
}
