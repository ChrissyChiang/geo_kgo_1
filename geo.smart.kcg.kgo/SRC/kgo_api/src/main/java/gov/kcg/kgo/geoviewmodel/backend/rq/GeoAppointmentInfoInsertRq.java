package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.geomodel.GeoKgoAppointmentContactUserInsertModel;
import gov.kcg.kgo.geomodel.GeoKgoAppointmentDetailInsertModel;
import gov.kcg.kgo.geomodel.GeoKgoAppointmentMainModel;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃:儲存預約主檔、細節 rq
 */
@ApiModel(description = "後台-線上預約臨櫃:儲存預約主檔、細節 rq")
public class GeoAppointmentInfoInsertRq extends ApiRequest {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(notes = "線上預約臨櫃主檔")
    private GeoKgoAppointmentMainModel appointmentMain;

    @ApiModelProperty(notes = "線上預約臨櫃細節")
    private List<GeoKgoAppointmentDetailInsertModel> detailList;

    @ApiModelProperty(notes = "線上預約臨櫃承辦人")
    private List<GeoKgoAppointmentContactUserInsertModel> contactUserList;

    public GeoKgoAppointmentMainModel getAppointmentMain() {
        return appointmentMain;
    }

    public void setAppointmentMain(GeoKgoAppointmentMainModel appointmentMain) {
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
