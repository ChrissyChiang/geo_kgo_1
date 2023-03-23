package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃-預約登錄管理:搜尋該預約服務已登錄預約單 rq
 */
@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:搜尋該預約服務已登錄預約單 rq")
public class GeoAppointmentOrderQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "線上預約名稱主檔id", required = true)
    private String appointmentMainId;

    @ApiModelProperty(value = "起始日 yyyy-MM-dd")
    private String dateStart;

    @ApiModelProperty(value = "結束日 yyyy-MM-dd")
    private String dateEnd;

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
