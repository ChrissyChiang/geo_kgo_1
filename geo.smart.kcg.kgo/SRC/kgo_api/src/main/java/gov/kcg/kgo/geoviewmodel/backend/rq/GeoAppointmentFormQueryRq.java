package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台-線上預約臨櫃-編輯：取得該預約對應表單 rq")
public class GeoAppointmentFormQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "線上預約臨櫃Id")
    private String appointmentMainId;

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }
}
