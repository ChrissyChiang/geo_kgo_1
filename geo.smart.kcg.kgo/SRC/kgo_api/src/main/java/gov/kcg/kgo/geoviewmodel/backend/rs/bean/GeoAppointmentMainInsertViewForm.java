package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoAppointmentMainModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211005 add
 * 後台-線上預約臨櫃:新增預約主檔
 */

@ApiModel(description = "後台-線上預約臨櫃:新增預約主檔 ViewForm")
public class GeoAppointmentMainInsertViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "線上預約臨櫃主檔")
    private GeoKgoAppointmentMainModel appointmentMain;

    public GeoKgoAppointmentMainModel getAppointmentMain() {
        return appointmentMain;
    }

    public void setAppointmentMain(GeoKgoAppointmentMainModel appointmentMain) {
        this.appointmentMain = appointmentMain;
    }
}
