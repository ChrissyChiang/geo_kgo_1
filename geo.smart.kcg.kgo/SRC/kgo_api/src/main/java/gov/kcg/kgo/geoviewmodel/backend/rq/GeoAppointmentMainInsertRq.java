package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.geomodel.GeoKgoAppointmentMainModel;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃:新增預約主檔 rq
 */

@ApiModel(description = "後台-線上預約臨櫃:新增預約主檔 rq")
public class GeoAppointmentMainInsertRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "線上預約臨櫃主檔", required = true)
    private GeoKgoAppointmentMainModel appointmentMain;

    public GeoKgoAppointmentMainModel getAppointmentMain() {
        return appointmentMain;
    }

    public void setAppointmentMain(GeoKgoAppointmentMainModel appointmentMain) {
        this.appointmentMain = appointmentMain;
    }
}
