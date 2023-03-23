package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.geoviewmodel.backend.rq.data.GeoAppointGroupColumnData;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台-線上預約臨櫃-編輯：表單儲存更版 rq
 */
@ApiModel(description = " 後台-線上預約臨櫃-編輯：表單儲存更版 rq")
public class GeoAppointmentFormColumnSaveActionRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "預約臨櫃編號Id")
    private String appointmentMainId;

    @ApiModelProperty(notes = "群組欄位資料")
    private List<GeoAppointGroupColumnData> groupColumnDataList;

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }

    public List<GeoAppointGroupColumnData> getGroupColumnDataList() {
        return groupColumnDataList;
    }

    public void setGroupColumnDataList(List<GeoAppointGroupColumnData> groupColumnDataList) {
        this.groupColumnDataList = groupColumnDataList;
    }
}
