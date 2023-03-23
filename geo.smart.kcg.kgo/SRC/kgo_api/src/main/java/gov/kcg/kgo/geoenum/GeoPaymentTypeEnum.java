package gov.kcg.kgo.geoenum;

import org.apache.commons.lang3.StringUtils;

public enum GeoPaymentTypeEnum {

    COMMON("一般付費","COMM"),
    CITY_PAY("市民線上","CITY");

    private String payTypeName;
    private String payTypeCode;

    GeoPaymentTypeEnum(String payTypeName, String payTypeCode) {
        this.payTypeName = payTypeName;
        this.payTypeCode = payTypeCode;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public String getPayTypeCode() {
        return payTypeCode;
    }

    public void setPayTypeCode(String payTypeCode) {
        this.payTypeCode = payTypeCode;
    }

    public static  GeoPaymentTypeEnum getGeoPaymentTypeEnum(String payTypeCode){
        for (GeoPaymentTypeEnum pEnum : values() ) {
            if (StringUtils.equalsIgnoreCase( pEnum.getPayTypeCode(), payTypeCode)) {
                return pEnum;
            }
        }
        return null;
    }
}
