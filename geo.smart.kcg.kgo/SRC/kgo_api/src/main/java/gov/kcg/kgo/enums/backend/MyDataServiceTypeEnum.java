package gov.kcg.kgo.enums.backend;

public enum MyDataServiceTypeEnum {
    ElectricityDiscountsForDisabled("ElectricityDiscountsForDisabled", "身心障礙者用電優惠"),;

    /** 代碼 */
    private String value;

    /** 顯示值 */
    private String label;

    private MyDataServiceTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

}
