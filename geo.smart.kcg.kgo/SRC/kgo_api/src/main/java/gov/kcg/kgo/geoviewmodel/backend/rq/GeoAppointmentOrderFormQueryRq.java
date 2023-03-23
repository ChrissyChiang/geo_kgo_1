package gov.kcg.kgo.geoviewmodel.backend.rq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:新增/編輯取得預約對應表單 rq")
public class GeoAppointmentOrderFormQueryRq {

    @ApiModelProperty(value = "線上預約臨櫃Id")
    private String appointmentMainId;

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }
}
