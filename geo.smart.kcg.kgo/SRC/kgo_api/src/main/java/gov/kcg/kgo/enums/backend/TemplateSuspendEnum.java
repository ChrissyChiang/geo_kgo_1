package gov.kcg.kgo.enums.backend;

public enum TemplateSuspendEnum {

    ZERO("0", "啟用"),
    ONE("1", "停用");


    /** 代碼 */
    private String value;

    /** 顯示值 */
    private String label;

    TemplateSuspendEnum(String value, String label) {
        this.value = value;
        this.label = label;
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
