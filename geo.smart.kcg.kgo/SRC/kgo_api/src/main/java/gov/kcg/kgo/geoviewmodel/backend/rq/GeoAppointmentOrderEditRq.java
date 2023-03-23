package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.geoviewmodel.backend.rq.data.GeoAppointmentOrderEditColumnData;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃-預約登錄管理:新增/編輯預約登錄 rq
 */

@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:新增/編輯預約登錄 rq")
public class GeoAppointmentOrderEditRq extends ApiRequest {

    @ApiModelProperty(notes = "預約單id 新增不填，編輯必填")
    private String appointmentId;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id",required = true)
    private String appointmentMainId;

    @ApiModelProperty(notes = "線上預約臨櫃細節-預約時段id",required = true)
    private String appointmentDetailTimeId;

    @ApiModelProperty(notes = "是否線上預約 1-是，0-不是",required = true)
    private Integer isOnline;

    @ApiModelProperty(notes = "版本號")
    private Integer version;

    @ApiModelProperty(notes = "欄位")
    private List<GeoAppointmentOrderEditColumnData> columnDataList;

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }

    public String getAppointmentDetailTimeId() {
        return appointmentDetailTimeId;
    }

    public void setAppointmentDetailTimeId(String appointmentDetailTimeId) {
        this.appointmentDetailTimeId = appointmentDetailTimeId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public List<GeoAppointmentOrderEditColumnData> getColumnDataList() {
        return columnDataList;
    }

    public void setColumnDataList(List<GeoAppointmentOrderEditColumnData> columnDataList) {
        this.columnDataList = columnDataList;
    }
}
