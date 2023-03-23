package gov.kcg.kgo.geomodel.ktcpay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "繳費入帳查詢回應")
public class GeoKgoPaymentRecordRs {

  @ApiModelProperty(notes = "查詢資料")
  private List<GeoKgoPaymentRecordData> data;

  public List<GeoKgoPaymentRecordData> getData() {
    return data;
  }

  public void setData(List<GeoKgoPaymentRecordData> data) {
    this.data = data;
  }
}
