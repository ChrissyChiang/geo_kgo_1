package gov.kcg.kgo.geomodel.ktcpay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "外部交易查詢回應物件")
public class GeoKgoPaymentExtQueryRsModel {
  @ApiModelProperty(notes = "繳費記錄列表")
  private List<GeoKgoPaymentExtQueryData> paymentList;

  public List<GeoKgoPaymentExtQueryData> getPaymentList() {
    return paymentList;
  }

  public void setPaymentList(List<GeoKgoPaymentExtQueryData> paymentList) {
    this.paymentList = paymentList;
  }

}
