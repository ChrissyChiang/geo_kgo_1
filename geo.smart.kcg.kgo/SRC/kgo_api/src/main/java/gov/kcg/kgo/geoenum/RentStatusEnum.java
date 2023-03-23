package gov.kcg.kgo.geoenum;

import org.apache.commons.lang3.StringUtils;

public enum RentStatusEnum {
    /** 預約狀態*/
    SUS("SUS","預約成功"),
    FAIL("FAIL","預約失敗"),
    PROC("PROC","待處理"),
    RCANL("RCANL","取消預約"),

    /** 繳費狀態*/
    YET("YET","未繳費"),
    WAIT("WAIT","待繳費"),
    FIN ("FIN","已繳費"),
    FREE("FREE","免繳費"),
    CANL("CANL","取消繳費"),
    WRFD("RFD","待退費"),
    FRFD("FRFD","已退費");
    /**代碼*/
    private String value;
    /**顯示值*/
    private String label;

    RentStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static  RentStatusEnum getRentStatusEnum(String value){
        for (RentStatusEnum rEnum : values() ) {
            if (StringUtils.equalsIgnoreCase( rEnum.getValue(), value)) {
                return rEnum;
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
