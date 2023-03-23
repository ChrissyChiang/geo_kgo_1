package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211104 add
 * 後台-線上預約臨櫃:查詢黑名單清單 rq
 */
@ApiModel(description = "後台-線上預約臨櫃:查詢黑名單清單 rq")
public class GeoBlockUserQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "線上臨櫃預約主檔id", required = true)
    private String appointmentMainId;

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }
}
