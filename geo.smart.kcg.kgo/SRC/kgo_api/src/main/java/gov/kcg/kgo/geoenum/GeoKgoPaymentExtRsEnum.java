package gov.kcg.kgo.geoenum;

import java.util.Arrays;

public enum GeoKgoPaymentExtRsEnum {

  SUCCESS("0000","成功"),
  CITY0001("CITY-0001","案件編號錯誤"),
  CITY0002("CITY-0002","案件服務編號錯誤"),
  CITY0003("CITY-0003","查無案件"),
  CITY0004("CITY-0004","Status= O(其他)時，狀態說明「狀態說明」欄位為必填"),
  CITY0005("CITY-0005","書表維護類別錯誤"),
  CITY0006("CITY-0006","查詢條件不可為空"),
  CITY0007("CITY-0007","日期格式錯誤"),
  CITY0008("CITY-0008","案件狀態不可為空"),
  CITY0009("CITY-0009","請輸入查詢機關代碼"),
  CITY0010("CITY-0010","欄位未填寫"),
  CITY0011("CITY-0011","驗證碼空"),
  CITY0012("CITY-0012","驗證碼不存在"),
  CITY9999("CITY-9999","未知錯誤");

  private final String rtnCode;
  private final String rtnMsg;

  GeoKgoPaymentExtRsEnum(String code, String msg){
    this.rtnCode = code;
    this.rtnMsg = msg;
  }

  public static GeoKgoPaymentExtRsEnum getEnum(String code){
    return Arrays.stream(values())
        .filter(e -> e.getCode().equals(code))
        .findAny()
        .orElse(CITY9999);
  }

  public String getCode() {
    return rtnCode;
  }

  public String getMsg() {
    return rtnMsg;
  }
}
