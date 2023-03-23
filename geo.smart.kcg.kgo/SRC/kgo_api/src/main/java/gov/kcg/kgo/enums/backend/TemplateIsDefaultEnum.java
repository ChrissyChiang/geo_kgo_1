package gov.kcg.kgo.enums.backend;

public enum TemplateIsDefaultEnum {

    ZERO("0", "非預設"),
    ONE("1", "預設");


    /** 代碼 */
    private String value;

    /** 顯示值 */
    private String label;

    TemplateIsDefaultEnum(String value, String label) {
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
