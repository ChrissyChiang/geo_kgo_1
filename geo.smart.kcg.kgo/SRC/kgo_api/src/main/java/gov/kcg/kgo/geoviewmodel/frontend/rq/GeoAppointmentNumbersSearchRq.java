package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211015 add
 * 前台-線上預約臨櫃:取得該預約單當日之後的預約名額與資訊 rq
 */

@ApiModel(description = "前台-線上預約臨櫃:取得該預約單當日之後的預約名額與資訊 rq")
public class GeoAppointmentNumbersSearchRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id", required = true)
    private String appointmentMainId;

    @ApiModelProperty(notes = "年月字串 yyyy-MM-dd", required = true)
    private String dateStr;

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
}
