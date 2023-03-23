package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel(value="單月查詢每日預約案件")
public class GeoCaseSetSearchDateModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "預約當日ID")
    private String rentDateId;

    @ApiModelProperty(notes = "預約主檔id")
    private String caseRentId;

    @ApiModelProperty(notes = "預約日期 yyyy-MM-dd")
    private String detailDate;

    @ApiModelProperty(notes = "是否全天")
    private Integer isAllDay;

    public String getRentDateId() {
        return rentDateId;
    }

    public void setRentDateId(String rentDateId) {
        this.rentDateId = rentDateId;
    }

    public String getCaseRentId() {
        return caseRentId;
    }

    public void setCaseRentId(String caseRentId) {
        this.caseRentId = caseRentId;
    }

    public String getDetailDate() {
        return detailDate;
    }

    public void setDetailDate(String detailDate) {
        this.detailDate = detailDate;
    }

    public Integer getIsAllDay() {return isAllDay;}

    public void setIsAllDay(Integer isAllDay) {this.isAllDay = isAllDay;}
}
