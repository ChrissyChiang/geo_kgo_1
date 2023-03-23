package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃-預約登錄管理:刪除已登錄預約 rq
 */
@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:刪除已登錄預約 rq")
public class GeoAppointmentDeleteRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "預約單id", required = true)
    private String appointmentId;

    @ApiModelProperty(notes = "線上預約臨櫃細節-號碼牌id 有則填")
    private String appointmentDetailNumbersId;

    @ApiModelProperty(notes = "預約者姓名", required = true)
    private String appointmentName;

    @ApiModelProperty(notes = "預約者電子信箱", required = true)
    private String appointmentEmail;

    public String getAppointmentDetailNumbersId() {
        return appointmentDetailNumbersId;
    }

    public void setAppointmentDetailNumbersId(String appointmentDetailNumbersId) {
        this.appointmentDetailNumbersId = appointmentDetailNumbersId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    public String getAppointmentEmail() {
        return appointmentEmail;
    }

    public void setAppointmentEmail(String appointmentEmail) {
        this.appointmentEmail = appointmentEmail;
    }
}
