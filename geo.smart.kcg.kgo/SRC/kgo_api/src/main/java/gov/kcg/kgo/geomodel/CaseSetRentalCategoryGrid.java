package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("線上租借資訊")
public class CaseSetRentalCategoryGrid extends CaseSetCategoryGrid {

    @ApiModelProperty(notes="是否為線上租借案件")
    private Boolean rentalCase;
    @ApiModelProperty(notes="租用單位")
    private String caseOrganName;
    @ApiModelProperty(notes="租用位置")
    private String siteName;
    @ApiModelProperty(notes="租用起始時間")
    private String rentTimeStart;
    @ApiModelProperty(notes="租用結束時間")
    private String rentTimeEnd;
    @ApiModelProperty(notes="租用金額")
    private String amount;

    public String getCaseOrganName() {
        return caseOrganName;
    }

    public void setCaseOrganName(String caseOrganName) {
        this.caseOrganName = caseOrganName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getRentTimeStart() {
        return rentTimeStart;
    }

    public void setRentTimeStart(String rentTimeStart) {
        this.rentTimeStart = rentTimeStart;
    }

    public String getRentTimeEnd() {
        return rentTimeEnd;
    }

    public void setRentTimeEnd(String rentTimeEnd) {
        this.rentTimeEnd = rentTimeEnd;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Boolean getRentalCase() {
        return rentalCase;
    }

    public void setRentalCase(Boolean rentalCase) {
        this.rentalCase = rentalCase;
    }
}
