package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單 rq
 */

@ApiModel(description = "後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單 rq")
public class GeoAppointmentMainSearchRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "機關id", required = true)
    private String organId;

    @ApiModelProperty(notes = "科室id", required = true)
    private String unitId;

    @ApiModelProperty(notes = "線上預約臨櫃名稱關鍵字")
    private String appointmentName;

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }
}
