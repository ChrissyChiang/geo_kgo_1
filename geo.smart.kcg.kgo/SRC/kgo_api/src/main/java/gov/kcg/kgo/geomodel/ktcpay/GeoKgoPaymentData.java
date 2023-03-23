package gov.kcg.kgo.geomodel.ktcpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "導轉繳費平台請求內容")
public class GeoKgoPaymentData extends ApiRequest {

  @ApiModelProperty(name = "api來源")
  @JsonProperty("S_ADR")
  private final String sAdr = "ONEGO";
  @ApiModelProperty(name = "繳費項目")
  @JsonProperty("PAY_ITEM")
  private final String payItem = "OG1";
  @ApiModelProperty(name = "驗證碼")
  @JsonProperty("ATOKEN")
  private String atoken;
  @ApiModelProperty(name = "查詢條件2 一路通案件編號caseId")
  @JsonProperty("COND1")
  private String cond1;
  @ApiModelProperty(name = "查詢條件2 市民科技會員識別sub")
  @JsonProperty("COND2")
  private String cond2;

  public GeoKgoPaymentData(String atoken, String cond1, String cond2) {
    this.atoken = atoken;
    this.cond1 = cond1;
    this.cond2 = cond2;
  }

  public String getsAdr() {
    return sAdr;
  }

  public String getPayItem() {
    return payItem;
  }

  public String getCond1() {
    return cond1;
  }

  public void setCond1(String cond1) {
    this.cond1 = cond1;
  }

  public String getAtoken() {
    return atoken;
  }

  public void setAtoken(String atoken) {
    this.atoken = atoken;
  }

  public String getCond2() {
    return cond2;
  }

  public void setCond2(String cond2) {
    this.cond2 = cond2;
  }
}
