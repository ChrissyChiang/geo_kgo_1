package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoAppointmentNumbersSearchModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單 ViewForm
 */

@ApiModel(description = "後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單 ViewForm")
public class GeoAppointmentNumbersSearchViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "該預約單當日之後的預約名額與資訊清單")
    private List<GeoKgoAppointmentNumbersSearchModel> dataList;

    public List<GeoKgoAppointmentNumbersSearchModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<GeoKgoAppointmentNumbersSearchModel> dataList) {
        this.dataList = dataList;
    }
}
