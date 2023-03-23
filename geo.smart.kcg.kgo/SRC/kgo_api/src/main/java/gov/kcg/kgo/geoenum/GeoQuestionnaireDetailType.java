package gov.kcg.kgo.geoenum;

/**
 * GEO 20210828 add
 *
 * 子題目類型
 **/

public enum GeoQuestionnaireDetailType {

    CHOOSE_ONE_TYPE(1, "單選不含配分"),
    CHOOSE_ONE_SCORE_TYPE(2, "單選含配分"),
    CHOOSE_MULTI_TYPE(3, "複選"),
    TEXT_TYPE(4, "問答");

    /** 代碼 */
    private int value;

    /** 顯示值 */
    private String label;


    private GeoQuestionnaireDetailType(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static GeoQuestionnaireDetailType getColumnTypeEnum(int value) {
        for (GeoQuestionnaireDetailType cEnum : values()) {
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
