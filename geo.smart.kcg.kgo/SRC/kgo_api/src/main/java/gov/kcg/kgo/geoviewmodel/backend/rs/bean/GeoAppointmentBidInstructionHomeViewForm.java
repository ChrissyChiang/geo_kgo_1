package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoAppointmentBidInstructionModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;

/**
 * 20220811 GEO add
 * 前台-線上預約臨櫃:取得同意說明頁
 *
 */
@ApiModel(description = "前台-線上預約臨櫃:取得同意說明頁 ViewForm")
public class GeoAppointmentBidInstructionHomeViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    private Boolean isLogin;

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }

    private GeoAppointmentBidInstructionModel geoAppointmentBidInstructionModel;

    public GeoAppointmentBidInstructionModel getGeoAppointmentBidInstructionModel() {
        return geoAppointmentBidInstructionModel;
    }

    public void setGeoAppointmentBidInstructionModel(GeoAppointmentBidInstructionModel geoAppointmentBidInstructionModel) {
        this.geoAppointmentBidInstructionModel = geoAppointmentBidInstructionModel;
    }
}
