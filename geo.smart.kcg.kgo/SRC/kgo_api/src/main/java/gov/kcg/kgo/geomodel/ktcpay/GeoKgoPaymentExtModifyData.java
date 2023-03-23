package gov.kcg.kgo.geomodel.ktcpay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "外部交易更新狀態內容")
public class GeoKgoPaymentExtModifyData {

  @ApiModelProperty(notes = "案件繳款日期 yyyyMMddHHmmss")
  private String payDate;
  @ApiModelProperty(notes = "案件退款日期 yyyyMMddHHmmss")
  private String reFundDate;
  @ApiModelProperty(notes = "案件狀態 PY1:待付款 PY2:已付款 RF1:待退費 RF2:已退費")
  private String caseStatus;
  @ApiModelProperty(notes = "一路通案件編號")
  private String caseId;
  @ApiModelProperty(notes = "繳費/退費金額")
  private Integer amount;

  @ApiModelProperty(notes = "銷帳編號")
  private String paymentNo;

  public String getPayDate() {
    return payDate;
  }

  public void setPayDate(String payDate) {
    this.payDate = payDate;
  }

  public String getReFundDate() {
    return reFundDate;
  }

  public void setReFundDate(String reFundDate) {
    this.reFundDate = reFundDate;
  }

  public String getCaseStatus() {
    return caseStatus;
  }

  public void setCaseStatus(String caseStatus) {
    this.caseStatus = caseStatus;
  }

  public String getCaseId() {
    return caseId;
  }

  public void setCaseId(String caseId) {
    this.caseId = caseId;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public String getPaymentNo() {return paymentNo;}

  public void setPaymentNo(String paymentNo) {this.paymentNo = paymentNo;}
}
