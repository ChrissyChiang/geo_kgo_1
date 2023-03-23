package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211108 add
 * 後台-線上預約臨櫃-編輯:依時間範圍取得該預約單資料 rq
 */

@ApiModel(description = "後台-線上預約臨櫃-編輯:依時間範圍取得該預約單資料 rq")
public class GeoAppointmentInfoByTimeQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id", required = true)
    private String appointmentMainId;

    @ApiModelProperty(notes = "年", required = true)
    private Integer year;

    @ApiModelProperty(notes = "月", required = true)
    private Integer month;

    @ApiModelProperty(notes = "週次，每週次從星期一開始", required = true)
    private Integer week;

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
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
