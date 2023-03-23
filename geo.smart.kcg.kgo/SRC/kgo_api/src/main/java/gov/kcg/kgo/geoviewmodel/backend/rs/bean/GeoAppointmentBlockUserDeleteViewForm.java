package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃:刪除黑名單 ViewForm
 */

@ApiModel(description = "後台-線上預約臨櫃:刪除黑名單  ViewForm")
public class GeoAppointmentBlockUserDeleteViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    /**
     * 執行結果訊息
     **/
    @ApiModelProperty(value = "執行結果訊息")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
