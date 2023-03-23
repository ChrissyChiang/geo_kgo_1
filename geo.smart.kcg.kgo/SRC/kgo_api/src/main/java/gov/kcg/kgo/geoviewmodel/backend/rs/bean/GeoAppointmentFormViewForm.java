package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoAppointmentFormQueryDataModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台-線上預約臨櫃-編輯：取得該預約對應表單 View Form
 */
@ApiModel(description = "後台-線上預約臨櫃-編輯：取得該預約對應表單 View Form")
public class GeoAppointmentFormViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "線上預約臨櫃Id")
    private String appointmentId;

    @ApiModelProperty(value = "表單群組版本號")
    private Integer version;

    @ApiModelProperty(value = "線上預約臨櫃表單設定所有群組欄位資料集合")
    private List<GeoAppointmentFormQueryDataModel> grid;

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<GeoAppointmentFormQueryDataModel> getGrid() {
        return grid;
    }

    public void setGrid(List<GeoAppointmentFormQueryDataModel> grid) {
        this.grid = grid;
    }
}
