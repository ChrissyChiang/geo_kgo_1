package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoAppointmentMainQueryModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * GEO 20211005 add
 * 後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單
 */

@ApiModel(description = "後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單 ViewForm")
public class GeoAppointmentMainSearchViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "線上預約臨櫃主檔")
    private List<GeoKgoAppointmentMainQueryModel> dataList;

    public List<GeoKgoAppointmentMainQueryModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<GeoKgoAppointmentMainQueryModel> dataList) {
        this.dataList = dataList;
    }
}
