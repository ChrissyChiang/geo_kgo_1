package gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務案件管理-服務管理者下拉式選單查詢 View Form
 */
@ApiModel(description = "服務案件管理-服務管理者下拉式選單查詢 View Form")
public class CaseManagerComboBoxQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服務管理者下拉式選單")
    private ComboBox caseManagerComboBox;

    public ComboBox getCaseManagerComboBox() {
        return caseManagerComboBox;
    }

    public void setCaseManagerComboBox(ComboBox caseManagerComboBox) {
        this.caseManagerComboBox = caseManagerComboBox;
    }
}
