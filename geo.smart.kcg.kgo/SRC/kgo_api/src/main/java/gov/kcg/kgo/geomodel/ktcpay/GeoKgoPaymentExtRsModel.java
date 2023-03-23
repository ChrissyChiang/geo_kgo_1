package gov.kcg.kgo.geomodel.ktcpay;

import gov.kcg.kgo.geoenum.GeoKgoPaymentExtRsEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "外部交易修改回應物件")
public class GeoKgoPaymentExtRsModel {

  @ApiModelProperty(notes = "回應代碼 成功:0000")
  private String rtnCode;
  @ApiModelProperty(notes = "錯誤提示訊息")
  private String msg;
  @ApiModelProperty(notes = "功能封裝物件")
  private Object data;

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public String getRtnCode() {
    return rtnCode;
  }

  public void setRtnCode(String rtnCode) {
    this.rtnCode = rtnCode;
  }

  public GeoKgoPaymentExtRsModel(GeoKgoPaymentExtRsEnum e) {
    this.rtnCode = e.getCode();
    this.msg = e.getMsg();
  }
}
