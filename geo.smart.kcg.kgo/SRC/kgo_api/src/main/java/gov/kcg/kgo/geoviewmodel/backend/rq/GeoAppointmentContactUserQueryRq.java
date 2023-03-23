package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃:承辦人帳號搜尋 rq
 */

@ApiModel(description = "後台-線上預約臨櫃:承辦人帳號搜尋 rq")
public class GeoAppointmentContactUserQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "機關代碼", required = true)
    private String organId;

    @ApiModelProperty(value = "單位代碼")
    private String unitId;

    @ApiModelProperty(value = "使用者名稱")
    private String name;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id", required = true)
    private String appointmentMainId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }
}
