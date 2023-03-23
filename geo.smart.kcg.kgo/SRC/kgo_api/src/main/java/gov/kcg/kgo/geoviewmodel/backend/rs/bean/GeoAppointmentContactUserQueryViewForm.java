package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoAppointmentContactUserModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃:承辦人帳號搜尋 ViewForm
 */

@ApiModel(description = "後台-線上預約臨櫃:承辦人帳號搜尋 ViewForm")
public class GeoAppointmentContactUserQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "線上預約臨櫃承辦人清單")
    private List<GeoKgoAppointmentContactUserModel> dataList;

    public List<GeoKgoAppointmentContactUserModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<GeoKgoAppointmentContactUserModel> dataList) {
        this.dataList = dataList;
    }
}
