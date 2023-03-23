package gov.kcg.kgo.geomodel.dto;

import gov.kcg.kgo.dto.CaseMainQueryCaseViewListDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
@ApiModel(description = "後台案件處理-場地案件資料集合")
public class SiteCaseMainQueryViewListDto extends CaseMainQueryCaseViewListDto {
    @ApiModelProperty(notes="場地名稱")
    private String siteName;
    @ApiModelProperty(notes="預約起始時間")
    private Timestamp startTime;
    @ApiModelProperty(notes="租借金額")
    private Integer amount;
    @ApiModelProperty(notes="預約狀態")
    private String rentStatus;
    @ApiModelProperty(notes="繳費狀態")
    private String paymentStatus;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getRentStatus() {return rentStatus;}

    public void setRentStatus(String rentStatus) {this.rentStatus = rentStatus;}

    public String getPaymentStatus() {return paymentStatus;}

    public void setPaymentStatus(String paymentStatus) {this.paymentStatus = paymentStatus;}
}
