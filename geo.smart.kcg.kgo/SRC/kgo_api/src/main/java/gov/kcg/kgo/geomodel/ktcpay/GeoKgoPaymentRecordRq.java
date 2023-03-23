package gov.kcg.kgo.geomodel.ktcpay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "繳費入帳查詢請求物件")
public class GeoKgoPaymentRecordRq {
  @ApiModelProperty(notes = "項目類別 OG1便民一路通")
  private static final String revenueType = "OG1";
  @ApiModelProperty(notes = "入帳日 yyyyMMdd")
  private String entryDate;
  @ApiModelProperty(notes = "繳費日 yyyyMMdd")
  private String payDate;

  public String getEntryDate() {
    return entryDate;
  }

  public void setEntryDate(String entryDate) {
    this.entryDate = entryDate;
  }

  public String getPayDate() {
    return payDate;
  }

  public void setPayDate(String payDate) {
    this.payDate = payDate;
  }
}
