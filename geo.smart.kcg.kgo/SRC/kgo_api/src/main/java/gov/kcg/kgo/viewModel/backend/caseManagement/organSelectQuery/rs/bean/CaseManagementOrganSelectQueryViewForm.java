package gov.kcg.kgo.viewModel.backend.caseManagement.organSelectQuery.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211115 add for 跨機關檢視
 * 服務案件清單-取得所有機關選單 View Form
 */
@ApiModel(description = "服務案件清單-取得所有機關選單 View Form")
public class CaseManagementOrganSelectQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "機關comboBox元件")
    private ComboBox organComboBox;

    public ComboBox getOrganComboBox() {
        return organComboBox;
    }

    public void setOrganComboBox(ComboBox organComboBox) {
        this.organComboBox = organComboBox;
    }

}
