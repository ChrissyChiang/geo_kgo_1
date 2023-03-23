package gov.kcg.kgo.geomodel.ktcpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "退費申請回應")
public class GeoKgoRefundRs {

  @JsonProperty("RETURN_CODE")
  @ApiModelProperty(notes = "API執行結果 200/500")
  private String returnCode;
  @JsonProperty("RETURN_MSG")
  @ApiModelProperty(notes = "API執行結果訊息 SUCCESS/MSG")
  private String returnMsg;
  @JsonProperty("FISISH_DT")
  @ApiModelProperty(notes = "交易完成時間 yyyyMMddHHmmss")
  private String finishDt;

  @JsonProperty("STATUS_CODE")
  @ApiModelProperty(notes="成功:9999 失敗:帶入狀態碼")
  private String statusCode;
  @JsonProperty("ERROR_MSG")
  @ApiModelProperty(notes="高銀回覆錯誤時,帶入錯誤訊息。")
  private String errorMsg;

  public String getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(String returnCode) {
    this.returnCode = returnCode;
  }

  public String getReturnMsg() {
    return returnMsg;
  }

  public void setReturnMsg(String returnMsg) {
    this.returnMsg = returnMsg;
  }

  public String getFinishDt() {
    return finishDt;
  }

  public void setFinishDt(String finishDt) {
    this.finishDt = finishDt;
  }

  public String getStatusCode() {return statusCode;}

  public void setStatusCode(String statusCode) {this.statusCode = statusCode;}

  public String getErrorMsg() {return errorMsg;}

  public void setErrorMsg(String errorMsg) {this.errorMsg = errorMsg;}
}
