package gov.kcg.kgo.geoenum;

/** 通用 YES/NO Enum **/

public enum GeoBooleanType {

    DISABLED(0, "否"),
    ENABLED(1, "是");

    private int code;
    private String label;

    GeoBooleanType(int code, String label) {
        this.code = code;
        this.label = label;
    }
    public int getCode() {
        return code;
    }
    public String getLabel() {
        return label;
    }

    public static GeoBooleanType valueOfCode(int code) {
        GeoBooleanType newType = null;
        for (GeoBooleanType type : values()) {
            if (type.code==code) {
                newType = type;
                break;
            }
        }
        return newType;
    } //valueOfCode
}
