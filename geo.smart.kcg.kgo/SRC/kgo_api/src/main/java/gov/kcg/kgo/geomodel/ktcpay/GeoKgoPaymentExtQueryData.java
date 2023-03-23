package gov.kcg.kgo.geomodel.ktcpay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "外部交易查詢資料")
public class GeoKgoPaymentExtQueryData {

  @ApiModelProperty(notes = "案件編號")
  private String caseId;
  @ApiModelProperty(notes = "繳費金額")
  private String payAmount;
  @ApiModelProperty(notes = "繳費期限 yyyyMMdd")
  private String payTime;
  @ApiModelProperty(notes = "繳費類型 P:繳費 R:退費")
  private String payType;
  @ApiModelProperty(notes = "繳費狀態 0:尚未繳退費 1:成功繳退費")
  private String paymentStatus;
  @ApiModelProperty(notes = "費用來源機關 39724040=勞教中心")
  private String payOriginOrg;

  @ApiModelProperty(notes = "市民科技會員sub")
  private String sub;
  @ApiModelProperty(notes = "建立日期 yyyyMMdd")
  private String createTime;
  @ApiModelProperty(notes = "異動日期 yyyyMMdd")
  private String editTime;


  public String getCaseId() {
    return caseId;
  }

  public void setCaseId(String caseId) {
    this.caseId = caseId;
  }

  public String getPayAmount() {
    return payAmount;
  }

  public void setPayAmount(String payAmount) {
    this.payAmount = payAmount;
  }

  public String getPayTime() {
    return payTime;
  }

  public void setPayTime(String payTime) {
    this.payTime = payTime;
  }

  public String getPayType() {
    return payType;
  }

  public void setPayType(String payType) {
    this.payType = payType;
  }

  public String getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(String paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public String getSub() {return sub;}

  public void setSub(String sub) {this.sub = sub;}

  public String getPayOriginOrg() {
    return payOriginOrg;
  }

  public void setPayOriginOrg(String payOriginOrg) {
    this.payOriginOrg = payOriginOrg;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getEditTime() {
    return editTime;
  }

  public void setEditTime(String editTime) {
    this.editTime = editTime;
  }
}
