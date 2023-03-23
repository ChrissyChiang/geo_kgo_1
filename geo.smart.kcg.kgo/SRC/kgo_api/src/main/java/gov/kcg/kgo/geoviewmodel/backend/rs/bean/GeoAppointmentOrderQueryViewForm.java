package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoAppointmentOrderQueryModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃-預約登錄管理:搜尋該預約服務已登陸預約單 ViewForm
 */

@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:搜尋該預約服務已登陸預約單  ViewForm")
public class GeoAppointmentOrderQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登錄預約主檔清單")
    private List<GeoKgoAppointmentOrderQueryModel> dataList;

    public List<GeoKgoAppointmentOrderQueryModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<GeoKgoAppointmentOrderQueryModel> dataList) {
        this.dataList = dataList;
    }
}
