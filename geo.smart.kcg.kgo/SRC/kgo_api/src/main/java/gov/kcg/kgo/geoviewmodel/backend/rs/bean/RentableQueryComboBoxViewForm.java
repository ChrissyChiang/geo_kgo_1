package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("可預約時間下拉選單 viewForm")
public class RentableQueryComboBoxViewForm extends BaseViewForm {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服務類型下拉式選單")
    private ComboBox categoryComboBox;
    @ApiModelProperty(value = "機關下拉式選單")
    private ComboBox organComboBox;
    @ApiModelProperty(value = "科室下拉式選單")
    private ComboBox unitComboBox;
    @ApiModelProperty(value = "場地類型下拉式選單")
    private ComboBox siteComboBox;


    public ComboBox getOrganComboBox() {
        return organComboBox;
    }

    public void setOrganComboBox(ComboBox organComboBox) {
        this.organComboBox = organComboBox;
    }

    public ComboBox getCategoryComboBox() {
        return categoryComboBox;
    }

    public void setCategoryComboBox(ComboBox categoryComboBox) {
        this.categoryComboBox = categoryComboBox;
    }

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
