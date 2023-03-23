package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoAppointmentBlockUserModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃:新增黑名單、刪除黑名單 ViewForm
 */

@ApiModel(description = "後台-線上預約臨櫃:新增黑名單、刪除黑名單 ViewForm")
public class GeoAppointmentBlockUserEditViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "線上預約臨櫃承辦人清單")
    private GeoKgoAppointmentBlockUserModel blockUser;

    public GeoKgoAppointmentBlockUserModel getBlockUser() {
        return blockUser;
    }

    public void setBlockUser(GeoKgoAppointmentBlockUserModel blockUser) {
        this.blockUser = blockUser;
    }
}
