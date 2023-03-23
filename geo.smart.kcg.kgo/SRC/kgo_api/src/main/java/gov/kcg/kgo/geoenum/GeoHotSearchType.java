package gov.kcg.kgo.geoenum;

/**
 * GEO 20220729 Add
 * 搜尋引擎 enum
 */
public enum GeoHotSearchType {

    KGO_HSIUNG_EASYGO(1, "高雄一路通"),
    KGO_HSIUNG_CITY_GOVERNMENT(2, "高雄市政府");

    /** 代碼 */
    private int value;

    /** 顯示值 */
    private String label;


    private GeoHotSearchType(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static GeoHotSearchType getColumnTypeEnum(int value) {
        for (GeoHotSearchType gEnum : values()) {
            if (gEnum.getValue()==value) {
                return gEnum;
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
