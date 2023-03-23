package gov.kcg.kgo.geomodel.ktcpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "繳費入帳資料格式")
public class GeoKgoPaymentRecordData {

  @JsonProperty("notice_no")
  @ApiModelProperty(notes = "繳費單編號")
  private String noticeNo;
  @ApiModelProperty(notes = "處分發文日期")
  private String levy;
  @JsonProperty("doc_no")
  @ApiModelProperty(notes = "處分書發文字號")
  private String docNo;
  @JsonProperty("agent_name")
  @ApiModelProperty(notes = "機關名稱")
  private String agentName;
  @ApiModelProperty(notes = "金額")
  private String amount;
  @JsonProperty("payment_deadline")
  @ApiModelProperty(notes = "繳款期限")
  private String paymentDeadline;
  @JsonProperty("item_no")
  @ApiModelProperty(notes = "科目")
  private String itemNo;
  @JsonProperty("item_name")
  @ApiModelProperty(notes = "項目名稱")
  private String itemName;
  @JsonProperty("pay_way")
  @ApiModelProperty(notes = "繳費工具 001=高雄銀行")
  private String payWay;
  @JsonProperty("pay_etime")
  @ApiModelProperty(notes = "繳費完成時間 yyyy/MM/dd HH:mm:ss")
  private String payEtime;
  @JsonProperty("entry_date")
  @ApiModelProperty(notes = "入帳日期 yyyyMMdd")
  private String entryDate;
  @JsonProperty("query_key")
  @ApiModelProperty(notes = "非會員查詢條件")
  private String queryKey;
  @ApiModelProperty(notes = "備註")
  private String desc;
  @JsonProperty("seq_no")
  @ApiModelProperty(notes = "案件期數")
  private String seqNo;
  @ApiModelProperty(notes = "備註1")
  private String memo1;
  @ApiModelProperty(notes = "備註2")
  private String memo2;
  @ApiModelProperty(notes = "繳費狀態 01=成功 90=失敗")
  private String status;
  @JsonProperty("status_desc")
  @ApiModelProperty(notes = "繳費狀態說明")
  private String statusDesc;

  public String getNoticeNo() {
    return noticeNo;
  }

  public void setNoticeNo(String noticeNo) {
    this.noticeNo = noticeNo;
  }

  public String getLevy() {
    return levy;
  }

  public void setLevy(String levy) {
    this.levy = levy;
  }

  public String getDocNo() {
    return docNo;
  }

  public void setDocNo(String docNo) {
    this.docNo = docNo;
  }

  public String getAgentName() {
    return agentName;
  }

  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getPaymentDeadline() {
    return paymentDeadline;
  }

  public void setPaymentDeadline(String paymentDeadline) {
    this.paymentDeadline = paymentDeadline;
  }

  public String getItemNo() {
    return itemNo;
  }

  public void setItemNo(String itemNo) {
    this.itemNo = itemNo;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getPayWay() {
    return payWay;
  }

  public void setPayWay(String payWay) {
    this.payWay = payWay;
  }

  public String getPayEtime() {
    return payEtime;
  }

  public void setPayEtime(String payEtime) {
    this.payEtime = payEtime;
  }

  public String getEntryDate() {
    return entryDate;
  }

  public void setEntryDate(String entryDate) {
    this.entryDate = entryDate;
  }

  public String getQueryKey() {
    return queryKey;
  }

  public void setQueryKey(String queryKey) {
    this.queryKey = queryKey;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(String seqNo) {
    this.seqNo = seqNo;
  }

  public String getMemo1() {
    return memo1;
  }

  public void setMemo1(String memo1) {
    this.memo1 = memo1;
  }

  public String getMemo2() {
    return memo2;
  }

  public void setMemo2(String memo2) {
    this.memo2 = memo2;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatusDesc() {
    return statusDesc;
  }

  public void setStatusDesc(String statusDesc) {
    this.statusDesc = statusDesc;
  }
}
