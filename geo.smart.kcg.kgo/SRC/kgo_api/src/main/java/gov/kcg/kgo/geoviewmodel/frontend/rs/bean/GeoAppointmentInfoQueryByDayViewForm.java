package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoAppointmentInfoQueryByDayModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211015 add
 * 前台-線上預約臨櫃:取得單日預約資料 ViewForm
 */

@ApiModel(description = "前台-線上預約臨櫃:取得單日預約資料 ViewForm")
public class GeoAppointmentInfoQueryByDayViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "單日預約資料清單")
    private List<GeoKgoAppointmentInfoQueryByDayModel> dataList;

    public List<GeoKgoAppointmentInfoQueryByDayModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<GeoKgoAppointmentInfoQueryByDayModel> dataList) {
        this.dataList = dataList;
    }
}
