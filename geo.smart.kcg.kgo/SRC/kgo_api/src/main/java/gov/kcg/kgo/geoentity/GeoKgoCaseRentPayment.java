package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "GEO_KGO_CASE_RENT_PAYMENT")
public class GeoKgoCaseRentPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="rent_payment_id",columnDefinition = "[varchar](30) primary key NOT NULL")
    private String rentPaymentId;
    @Column(name="case_id" ,columnDefinition = "[varchar](50) NULL")
    private String caseId;
    @Column(name="payment_status",columnDefinition = "[varchar](10) NULL")
    private String paymentStatus;
    @Column(name="pay_type",columnDefinition = "[varchar](10) NULL")
    private String payType;
    @Column(name="pay_time",columnDefinition = "[datetime] NULL")
    private Timestamp payTime;
    @Column(name="pay_amount",columnDefinition ="[int] NULL")
    private Integer payAmount;
    @Column(name="edit_time",columnDefinition = "datetime2(0) not null")
    private Timestamp editTime;
    @Column(name="create_time",columnDefinition = "datetime2(0) not null")
    private Timestamp createTime;
    @Column(name="pay_deadline",columnDefinition = "datetime2(0) not null")
    private Timestamp payDeadline;
    @Column(name="receipt_num",columnDefinition = "varchar(30) null")
    private String receiptNum;

    @Column(name="sys_pay_amount",columnDefinition = "int null")
    private Integer sysPayAmount;

    @Column(name="payment_discount",columnDefinition = "int null")
    private Integer paymentDiscount;

    @Column(name="pay_assurer",columnDefinition = "varchar(30) null")
    private String payAssurer;

    @Column(name="deduct_percent",columnDefinition = "int null")
    private Integer deductPercent;

    @Column(name="refund_discount",columnDefinition = "int null")
    private Integer refundDiscount;

    @Column(name="actual_refund_amount",columnDefinition = "int null")
    private Integer actualRefundAmount;

    @Column(name="refund_assurer",columnDefinition = "varchar(30) null")
    private String refundAssurer;

    @Column(name="refund_time",columnDefinition = "datetime2(0) null")
    private Timestamp refundTime;

    @Column(name="sub_uuid",columnDefinition = "varchar(255) null")
    private String subUUid;

    @Column(name="edit_user")
    private String editUser;

    public String getRentPaymentId() {return rentPaymentId;}

    public void setRentPaymentId(String rentPaymentId) {this.rentPaymentId = rentPaymentId;}

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getPayDeadline() {return payDeadline;}

    public void setPayDeadline(Timestamp payDeadline) {this.payDeadline = payDeadline;}

    public String getReceiptNum() {return receiptNum;}

    public void setReceiptNum(String receiptNum) {this.receiptNum = receiptNum;}

    public String getEditUser() {return editUser;}

    public void setEditUser(String editUser) {this.editUser = editUser;}

    public Integer getSysPayAmount() {
        return sysPayAmount;
    }

    public void setSysPayAmount(Integer sysPayAmount) {
        this.sysPayAmount = sysPayAmount;
    }

    public Integer getPaymentDiscount() {
        return paymentDiscount;
    }

    public void setPaymentDiscount(Integer paymentDiscount) {
        this.paymentDiscount = paymentDiscount;
    }

    public String getPayAssurer() {
        return payAssurer;
    }

    public void setPayAssurer(String payAssurer) {
        this.payAssurer = payAssurer;
    }

    public Integer getDeductPercent() {
        return deductPercent;
    }

    public void setDeductPercent(Integer deductPercent) {
        this.deductPercent = deductPercent;
    }

    public Integer getRefundDiscount() {
        return refundDiscount;
    }

    public void setRefundDiscount(Integer refundDiscount) {
        this.refundDiscount = refundDiscount;
    }

    public Integer getActualRefundAmount() {
        return actualRefundAmount;
    }

    public void setActualRefundAmount(Integer actualRefundAmount) {
        this.actualRefundAmount = actualRefundAmount;
    }

    public String getRefundAssurer() {
        return refundAssurer;
    }

    public void setRefundAssurer(String refundAssurer) {
        this.refundAssurer = refundAssurer;
    }

    public Timestamp getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Timestamp refundTime) {
        this.refundTime = refundTime;
    }

    public String getSubUUid() {
        return subUUid;
    }

    public void setSubUUid(String subUUid) {
        this.subUUid = subUUid;
    }
}
