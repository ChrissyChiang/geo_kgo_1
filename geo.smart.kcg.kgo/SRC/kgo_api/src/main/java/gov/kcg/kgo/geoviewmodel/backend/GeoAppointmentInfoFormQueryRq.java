package gov.kcg.kgo.geoviewmodel.backend;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20220120 add
 * 後台-線上預約臨櫃-預約登錄管理-編輯:取得該預約者表單資料 rq
 */

@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理-編輯:取得該預約者表單資料 rq")
public class GeoAppointmentInfoFormQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "預約單ID")
    private String appointmentId;

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
}
