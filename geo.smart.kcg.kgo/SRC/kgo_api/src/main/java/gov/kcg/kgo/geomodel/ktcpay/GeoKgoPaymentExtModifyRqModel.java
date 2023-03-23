package gov.kcg.kgo.geomodel.ktcpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "外部交易更新狀態物件")
public class GeoKgoPaymentExtModifyRqModel extends GeoKgoPaymentExtModelBase {

  @JsonProperty("UpdateCasePayAndRefundResult")
  @ApiModelProperty(notes = "更新資料內容")
  private GeoKgoPaymentExtModifyData[] updateCasePayAndRefundResult;
  @JsonProperty("orgId")
  @ApiModelProperty(notes = "機關單位代碼,市民科技:KHCITY0000")
  private String orgId;
  @JsonProperty("txId")
  @ApiModelProperty(notes = "交易序號YYYYMMDDHHMMSS")
  private String txId;
  public GeoKgoPaymentExtModifyData[] getUpdateCasePayAndRefundResult() {
    return updateCasePayAndRefundResult;
  }

  public void setUpdateCasePayAndRefundResult(GeoKgoPaymentExtModifyData[] updateCasePayAndRefundResult) {
    this.updateCasePayAndRefundResult = updateCasePayAndRefundResult;
  }

  @Override
  public String getOrgId() {
    return orgId;
  }

  @Override
  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  @Override
  public String getTxId() {
    return txId;
  }

  @Override
  public void setTxId(String txId) {
    this.txId = txId;
  }
}
