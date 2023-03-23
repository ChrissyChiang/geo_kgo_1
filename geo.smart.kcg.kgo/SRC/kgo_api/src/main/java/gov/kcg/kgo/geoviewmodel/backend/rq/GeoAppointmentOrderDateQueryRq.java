package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃-預約登錄管理:取得該服務預約時段 rq
 */
@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:取得該服務預約時段 rq")
public class GeoAppointmentOrderDateQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id", required = true)
    private String appointmentMainId;

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }
}
