package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoAppointmentFormQueryDataModel;
import gov.kcg.kgo.geomodel.GeoAppointmentOrderFormQueryDataModel;
import gov.kcg.kgo.geomodel.GeoKgoAppointmentOrderEditModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.GroupViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.OptionViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台-線上預約臨櫃-預約登錄管理:新增/編輯取得預約對應表單 ViewForm
 */

@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:新增/編輯取得預約對應表單 ViewForm")
public class GeoAppointmentOrderFormQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "線上預約臨櫃Id")
    private String appointmentMainId;

    @ApiModelProperty(notes = "版本號")
    private Integer version;

    @ApiModelProperty(value = "線上預約臨櫃表單設定所有群組欄位資料集合")
    private List<GeoAppointmentOrderFormQueryDataModel> modelList;

    @ApiModelProperty(notes = "欄位資料")
    private List<OptionViewForm> options;

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<GeoAppointmentOrderFormQueryDataModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<GeoAppointmentOrderFormQueryDataModel> modelList) {
        this.modelList = modelList;
    }

    public List<OptionViewForm> getOptions() {
        return options;
    }

    public void setOptions(List<OptionViewForm> options) {
        this.options = options;
    }
}
