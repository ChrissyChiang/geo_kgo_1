package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = " 服務案件編輯-機關繳費折扣下拉 ViewForm")
public class GeoDiscountComboxViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes = "機關繳費折下拉")
    private ComboBox discountComboBox;

    public ComboBox getDiscountComboBox() {
        return discountComboBox;
    }

    public void setDiscountComboBox(ComboBox discountComboBox) {
        this.discountComboBox = discountComboBox;
    }
}
