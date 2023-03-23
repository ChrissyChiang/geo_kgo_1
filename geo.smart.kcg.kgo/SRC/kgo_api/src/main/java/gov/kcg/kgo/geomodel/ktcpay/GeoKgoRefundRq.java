package gov.kcg.kgo.geomodel.ktcpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel(description = "退費申請回應")
public class GeoKgoRefundRq {

  @JsonProperty("PAYMENT_NO")
  @ApiModelProperty(notes = "繳費單號(16)")
  private String paymentNo;
  @JsonProperty("REFUND_AMOUNT")
  @ApiModelProperty(notes = "退費金額")
  private String refundAmount;
  @JsonProperty("REFUND_KCOIN")
  @ApiModelProperty(notes = "K幣退費金額")
  private String refundKcoin;

  public GeoKgoRefundRq(String paymentNo, String refundAmount, String refundKcoin) {
    this.paymentNo = paymentNo;
    this.refundAmount = refundAmount;
    this.refundKcoin = refundKcoin;
  }

  public String getPaymentNo() {
    return paymentNo;
  }

  public void setPaymentNo(String paymentNo) {
    this.paymentNo = paymentNo;
  }

  public String getRefundAmount() {
    return refundAmount;
  }

  public void setRefundAmount(String refundAmount) {
    this.refundAmount = refundAmount;
  }

  public String getRefundKcoin() {
    return refundKcoin;
  }

  public void setRefundKcoin(String refundKcoin) {
    this.refundKcoin = refundKcoin;
  }
}
