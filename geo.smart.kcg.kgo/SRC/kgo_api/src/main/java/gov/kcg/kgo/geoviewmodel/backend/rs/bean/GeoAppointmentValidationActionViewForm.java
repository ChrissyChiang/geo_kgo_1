package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台-線上預約臨櫃-預約登錄管理:新增/編輯預約登錄-檢核 viewForm
 */
@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:新增/編輯預約登錄-檢核 viewForm")
public class GeoAppointmentValidationActionViewForm {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "預約ID")
    private String appointmentId;

    @ApiModelProperty(notes = "分處")
    private String f3Name;

    @ApiModelProperty(notes = "錯誤訊息")
    private List<GeoAppointmentOrderEditValidationViewForm> validationMsg;

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getF3Name() {
        return f3Name;
    }

    public void setF3Name(String f3Name) {
        this.f3Name = f3Name;
    }

    public List<GeoAppointmentOrderEditValidationViewForm> getValidationMsg() {
        return validationMsg;
    }

    public void setValidationMsg(List<GeoAppointmentOrderEditValidationViewForm> validationMsg) {
        this.validationMsg = validationMsg;
    }
}
