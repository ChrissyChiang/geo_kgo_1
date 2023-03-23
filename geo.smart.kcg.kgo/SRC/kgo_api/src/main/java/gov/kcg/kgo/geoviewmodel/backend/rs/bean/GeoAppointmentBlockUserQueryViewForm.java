package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoAppointmentBlockUserListModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211104 add
 * 後台-線上預約臨櫃:查詢黑名單清單 ViewForm
 */
@ApiModel(description = "後台-線上預約臨櫃:查詢黑名單清單 ViewForm")
public class GeoAppointmentBlockUserQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "線上預約臨櫃黑名單清單")
    private List<GeoKgoAppointmentBlockUserListModel> dataList;

    public List<GeoKgoAppointmentBlockUserListModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<GeoKgoAppointmentBlockUserListModel> dataList) {
        this.dataList = dataList;
    }
}
