package gov.kcg.kgo.geomodel.ktcpay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "外部交易查詢請求物件")
public class GeoKgoPaymentExtQueryRqModel extends GeoKgoPaymentExtModelBase {

  @ApiModelProperty(notes = "一路通案件編號")
  private String caseId;

  public String getCaseId() {
    return caseId;
  }

  public void setCaseId(String caseId) {
    this.caseId = caseId;
  }
}
