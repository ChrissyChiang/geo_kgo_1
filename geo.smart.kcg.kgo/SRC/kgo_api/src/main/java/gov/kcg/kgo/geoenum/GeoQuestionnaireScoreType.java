package gov.kcg.kgo.geoenum;

/**
 * GEO 20210828 add
 *
 * 子題目類型
 **/

public enum GeoQuestionnaireScoreType {

    A_TYPE(1, "單選A"),
    B_TYPE(2, "單選B"),
    C_TYPE(3, "單選C"),
    D_TYPE(4, "單選D"),
    E_TYPE(5, "單選E");

    /** 代碼 */
    private int value;

    /** 顯示值 */
    private String label;


    private GeoQuestionnaireScoreType(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static GeoQuestionnaireScoreType getColumnTypeEnum(int value) {
        for (GeoQuestionnaireScoreType cEnum : values()) {
            if (cEnum.getValue()==value) {
                return cEnum;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
