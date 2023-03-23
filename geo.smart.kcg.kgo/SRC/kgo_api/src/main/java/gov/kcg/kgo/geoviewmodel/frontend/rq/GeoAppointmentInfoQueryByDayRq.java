package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211015 add
 * 前台-線上預約臨櫃:取得單日預約資料 rq
 */

@ApiModel(description = "前台-線上預約臨櫃:取得單日預約資料 rq")
public class GeoAppointmentInfoQueryByDayRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "線上預約臨櫃細節id")
    private String appointmentDetailId;

    public String getAppointmentDetailId() {
        return appointmentDetailId;
    }

    public void setAppointmentDetailId(String appointmentDetailId) {
        this.appointmentDetailId = appointmentDetailId;
    }
}
