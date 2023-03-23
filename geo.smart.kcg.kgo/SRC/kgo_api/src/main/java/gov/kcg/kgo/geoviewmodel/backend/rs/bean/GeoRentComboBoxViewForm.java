package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台-線上場地/活動預約- 科室和場地 ComboBox ViewForm")
public class GeoRentComboBoxViewForm extends BaseViewForm {

    @ApiModelProperty(value = "場地建立者ComboBox")
    private ComboBox unitComboBox;
    @ApiModelProperty(value = "場地建立者ComboBox")
    private ComboBox siteComboBox;

    public ComboBox getUnitComboBox() {
        return unitComboBox;
    }

    public void setUnitComboBox(ComboBox unitComboBox) {
        this.unitComboBox = unitComboBox;
    }

    public ComboBox getSiteComboBox() {
        return siteComboBox;
    }

    public void setSiteComboBox(ComboBox siteComboBox) {
        this.siteComboBox = siteComboBox;
    }
}
