package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoAppointmentDetailInsertModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃-預約登錄管理:取得該服務預約時段 ViewForm
 */

@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:取得該服務預約時段  ViewForm")
public class GeoAppointmentOrderDateQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "線上預約臨櫃細節")
    private List<GeoKgoAppointmentDetailInsertModel> detailList;

    public List<GeoKgoAppointmentDetailInsertModel> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<GeoKgoAppointmentDetailInsertModel> detailList) {
        this.detailList = detailList;
    }

}
