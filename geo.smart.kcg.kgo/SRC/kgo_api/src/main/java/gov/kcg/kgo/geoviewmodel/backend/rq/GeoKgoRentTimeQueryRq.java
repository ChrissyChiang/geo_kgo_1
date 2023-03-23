package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description = "後台-場地/活動線上租借編輯 - 年月週查詢 rq")
public class GeoKgoRentTimeQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes = "服務案件主檔id", required = true)
    private String caseSetId;

    @ApiModelProperty(notes = "場地/活動主檔id", required = true)
    private String serviceId;

    @ApiModelProperty(notes = "租借案件主檔id")
    private String caseRentId;

    @ApiModelProperty(notes = "年", required = true)
    private Integer year;

    @ApiModelProperty(notes = "月", required = true)
    private Integer month;

    @ApiModelProperty(notes = "週次，每週次從星期一開始", required = true)
    private Integer week;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCaseRentId() {
        return caseRentId;
    }

    public void setCaseRentId(String caseRentId) {
        this.caseRentId = caseRentId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }
}
