package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台-場地查詢建立者下拉ComboBox ViewForm")
public class GeoSiteEditUserComboxQueryViewForm extends BaseViewForm {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "場地建立者ComboBox")
    private ComboBox userComboBox;

    public ComboBox getUserComboBox() {
        return userComboBox;
    }

    public void setUserComboBox(ComboBox userComboBox) {
        this.userComboBox = userComboBox;
    }
}
