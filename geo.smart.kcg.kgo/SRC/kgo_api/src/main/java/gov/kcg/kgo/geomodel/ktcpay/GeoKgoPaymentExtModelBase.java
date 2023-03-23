package gov.kcg.kgo.geomodel.ktcpay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "外部交易請求共用")
public class GeoKgoPaymentExtModelBase implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(notes = "交易序號")
  private String txId;
  @ApiModelProperty(notes = "發送請求的機關代碼")
  private String orgId;
  @ApiModelProperty(notes = "來源驗證碼")
  private String certification;

  public String getTxId() {
    return txId;
  }

  public void setTxId(String txId) {
    this.txId = txId;
  }

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public String getCertification() {
    return certification;
  }

  public void setCertification(String certification) {
    this.certification = certification;
  }
}
