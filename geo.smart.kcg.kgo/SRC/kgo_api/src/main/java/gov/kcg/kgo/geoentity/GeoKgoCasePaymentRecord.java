package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "GEO_KGO_CASE_PAYMENT_RECORD")
public class GeoKgoCasePaymentRecord implements Serializable {

  private static final long serialVersionUID = 1L;
  private Long payRecordId;
  private String caseId;
  private String paymentStatus;// 0:尚未繳退款 1:已成功繳退費
  private String paymentType;// P:繳費 R:退費
  private Timestamp createDate;
  private BigDecimal amount;
  private String isExternal;// 是否為外部紀錄 Y/N

  @Id
  @Column(name="pay_record_id")
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  public Long getPayRecordId() {
    return payRecordId;
  }

  public void setPayRecordId(Long payRecordId) {
    this.payRecordId = payRecordId;
  }

  @Column(name="case_id")
  public String getCaseId() {
    return caseId;
  }

  public void setCaseId(String caseId) {
    this.caseId = caseId;
  }

  @Column(name="payment_status")
  public String getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(String paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  @Column(name="payment_type")
  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  @Column(name="create_date")
  public Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
  }

  @Column(name="amount")
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @Column(name="is_external")
  public String getIsExternal() {
    return isExternal;
  }

  public void setIsExternal(String isExternal) {
    this.isExternal = isExternal;
  }
}
