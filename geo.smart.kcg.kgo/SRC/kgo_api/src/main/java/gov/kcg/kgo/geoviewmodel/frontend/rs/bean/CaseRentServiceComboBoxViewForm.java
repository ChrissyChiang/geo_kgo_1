package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="前台-線上租借回傳下拉")
public class CaseRentServiceComboBoxViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "回傳場地/活動下拉選單")
    private ComboBox serviceComboBox;

    public ComboBox getServiceComboBox() {
        return serviceComboBox;
    }

    public void setServiceComboBox(ComboBox serviceComboBox) {
        this.serviceComboBox = serviceComboBox;
    }
}
