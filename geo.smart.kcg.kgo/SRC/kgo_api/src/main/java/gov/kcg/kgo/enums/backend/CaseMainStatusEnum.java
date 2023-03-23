package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * @author TPIuser
 */
public enum CaseMainStatusEnum {

    //todo:要加「待會簽」

    /**
     * 【B3,C3】
     */
    A("A", "已轉導"), W3("W3", "待簽收"), A3("A3", "待分文"), P3("P3", "待處理"), C3("C3", "補正中"), F3("F3", "結案"), J3("J3", "結案不通過"),
    /**
     * 【A,B1,B2,C1,C2】
     **/
    W("W", "待簽收"), P("P", "待處理"), F("F", "結案"), S3("S3", "結案存查"), O("O", "其他"), OTHERS("OTHERS", "其他"),
    /**
     * modify 2021.01.08 KGO API 呼叫補正中 狀態 -> C
     */
    C("C", "補正中"),

    /**
     * GEO modify 2021.08.13 (這裡的 TYPE1 表 ActTaskTypeEnum.TASK_TYPE_1 的會簽)
     */
    TYPE13("13", "待會簽"),
    R("R", "處理結果"),
    WO("WO", "待跨機關處理");

    /**
     * 代碼
     */
    private String value;

    /**
     * 顯示值
     */
    private String label;

    CaseMainStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 取得對應狀態類別.
     */
    public static CaseMainStatusEnum getCaseMainStatusEnum(String value) {
        for (CaseMainStatusEnum aEnum : values()) {
            if (StringUtils.equalsIgnoreCase(aEnum.getValue(), value)) {
                return aEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
