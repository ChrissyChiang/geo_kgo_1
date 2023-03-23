package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoAppointmentOrderEditModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.ValidationViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃-預約登錄管理:新增/編輯預約登錄 ViewForm
 */

@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:新增/編輯預約登錄  ViewForm")
public class GeoAppointmentOrderEditViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "登錄預約單")
    GeoKgoAppointmentOrderEditModel appointment;

    @ApiModelProperty(notes = "錯誤訊息")
    private List<GeoAppointmentOrderEditValidationViewForm> validationMsg;

    public GeoKgoAppointmentOrderEditModel getAppointment() {
        return appointment;
    }

    public void setAppointment(GeoKgoAppointmentOrderEditModel appointment) {
        this.appointment = appointment;
    }

    public List<GeoAppointmentOrderEditValidationViewForm> getValidationMsg() {
        return validationMsg;
    }

    public void setValidationMsg(List<GeoAppointmentOrderEditValidationViewForm> validationMsg) {
        this.validationMsg = validationMsg;
    }
}
