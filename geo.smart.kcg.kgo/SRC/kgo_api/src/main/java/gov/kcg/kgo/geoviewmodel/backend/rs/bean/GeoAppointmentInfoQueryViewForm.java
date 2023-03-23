package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoAppointmentContactUserInsertModel;
import gov.kcg.kgo.geomodel.GeoKgoAppointmentDetailInsertModel;
import gov.kcg.kgo.geomodel.GeoKgoAppointmentMainQueryModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃-編輯:取得該預約單資料 ViewForm
 */

@ApiModel(description = "後台-線上預約臨櫃-編輯:取得該預約單資料  ViewForm")
public class GeoAppointmentInfoQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "線上預約臨櫃主檔")
    private GeoKgoAppointmentMainQueryModel appointmentMain;

    @ApiModelProperty(notes = "線上預約臨櫃細節")
    private List<GeoKgoAppointmentDetailInsertModel> detailList;

    @ApiModelProperty(notes = "線上預約臨櫃承辦人")
    private List<GeoKgoAppointmentContactUserInsertModel> contactUserList;

    public GeoKgoAppointmentMainQueryModel getAppointmentMain() {
        return appointmentMain;
    }

    public void setAppointmentMain(GeoKgoAppointmentMainQueryModel appointmentMain) {
        this.appointmentMain = appointmentMain;
    }

    public List<GeoKgoAppointmentDetailInsertModel> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<GeoKgoAppointmentDetailInsertModel> detailList) {
        this.detailList = detailList;
    }

    public List<GeoKgoAppointmentContactUserInsertModel> getContactUserList() {
        return contactUserList;
    }

    public void setContactUserList(List<GeoKgoAppointmentContactUserInsertModel> contactUserList) {
        this.contactUserList = contactUserList;
    }
}
