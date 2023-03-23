package gov.kcg.kgo.geomodel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
@ApiModel(value="租借關聯物件")
public class GeoRentalCaseDto {

    @ApiModelProperty(name="使用者案件ID")
    private String caseId;
    @ApiModelProperty(name="租借服務案件ID")
    private String casesetId;
    @ApiModelProperty(name="租借繳費ID")
    private String rentPaymentId;
    @ApiModelProperty(name="租借服務案件名稱")
    private String caseSetName;
    @ApiModelProperty(name="租借場地/活動名稱")
    private String siteName;
    @ApiModelProperty(name="管理機關")
    private String organId;
    @ApiModelProperty(name="費用")
    private Integer amount;
    @ApiModelProperty(name="案件狀態")
    private String caseStatus;
    @ApiModelProperty(name="預約狀態")
    private String rentStatus;
    @ApiModelProperty(name="繳費狀態")
    private String payStatus;
    @ApiModelProperty(name="預約時段ID")
    private String rentTimeId;
    @ApiModelProperty(name="預約起始時間")
    private Timestamp rentTimeStart;
    @ApiModelProperty(name="預約結束時間")
    private Timestamp rentTimeEnd;
    @ApiModelProperty(name="可使用總人數")
    private Integer availableQuota;
    @ApiModelProperty(name="以使用人數")
    private Integer usedUserNum;
    @ApiModelProperty(name="服務類型")
    private String category;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCasesetId() {
        return casesetId;
    }

    public void setCasesetId(String casesetId) {
        this.casesetId = casesetId;
    }

    public String getRentPaymentId() {
        return rentPaymentId;
    }

    public void setRentPaymentId(String rentPaymentId) {
        this.rentPaymentId = rentPaymentId;
    }

    public String getCaseSetName() {
        return caseSetName;
    }

    public void setCaseSetName(String caseSetName) {
        this.caseSetName = caseSetName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getRentStatus() {
        return rentStatus;
    }

    public void setRentStatus(String rentStatus) {
        this.rentStatus = rentStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getRentTimeId() {
        return rentTimeId;
    }

    public void setRentTimeId(String rentTimeId) {
        this.rentTimeId = rentTimeId;
    }

    public Timestamp getRentTimeStart() {
        return rentTimeStart;
    }

    public void setRentTimeStart(Timestamp rentTimeStart) {
        this.rentTimeStart = rentTimeStart;
    }

    public Timestamp getRentTimeEnd() {
        return rentTimeEnd;
    }

    public void setRentTimeEnd(Timestamp rentTimeEnd) {
        this.rentTimeEnd = rentTimeEnd;
    }

    public Integer getAvailableQuota() {
        return availableQuota;
    }

    public void setAvailableQuota(Integer availableQuota) {
        this.availableQuota = availableQuota;
    }

    public Integer getUsedUserNum() {
        return usedUserNum;
    }

    public void setUsedUserNum(Integer usedUserNum) {
        this.usedUserNum = usedUserNum;
    }

    public String getCaseStatus() {return caseStatus;}

    public void setCaseStatus(String caseStatus) {this.caseStatus = caseStatus;}

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}

    @Override
    public String toString() {
        return "GeoRentalCaseDto{" +
                "caseId='" + caseId + '\'' +
                ", casesetId='" + casesetId + '\'' +
                ", rentPaymentId='" + rentPaymentId + '\'' +
                ", caseSetName='" + caseSetName + '\'' +
                ", siteName='" + siteName + '\'' +
                ", organId='" + organId + '\'' +
                ", amount=" + amount +
                ", caseStatus='" + caseStatus + '\'' +
                ", rentStatus='" + rentStatus + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", rentTimeId='" + rentTimeId + '\'' +
                ", rentTimeStart=" + rentTimeStart +
                ", rentTimeEnd=" + rentTimeEnd +
                ", availableQuota=" + availableQuota +
                ", usedUserNum=" + usedUserNum +
                '}';
    }
}
