package gov.kcg.kgo.geoenum;

/**
 * Geo 20221104 add 驗證不同項目的身份驗證
 *
 **/
public enum GeoValidateType {

    APPOINTMENT("預約", 1),

    CASE_SET("服務案件", 2),

    MY_DATA_QUERY("mydatat查詢", 3);

    private String validateTypeName;

    private int validateTypeCode;

    public String getValidateTypeName() {
        return validateTypeName;
    }

    public void setValidateTypeName(String validateTypeName) {
        this.validateTypeName = validateTypeName;
    }

    public int getValidateTypeCode() {
        return validateTypeCode;
    }

    public void setValidateTypeCode(int validateTypeCode) {
        this.validateTypeCode = validateTypeCode;
    }

    private GeoValidateType(String validateTypeName, int validateTypeCode) {
        this.validateTypeName = validateTypeName;
        this.validateTypeCode = validateTypeCode;
    }

    public static GeoValidateType geoValidateTypeName(int validateTypeCode) {
        for (GeoValidateType validateType : (values())) {
            if (validateType.validateTypeCode == validateTypeCode) {
                return validateType;
            }
        }
        return null;
    }
}


