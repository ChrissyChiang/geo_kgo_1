package gov.kcg.kgo.viewModel.backend.organUnitManagement.comboBox.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 機關科室管理-取得單位下拉式選單 View Form
 */
@ApiModel(description = "機關科室管理-取得單位下拉式選單 View Form")
public class OrganUnitManagementUnitComboBoxViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "單位下拉式選單")
	private ComboBox unitComboBox;

	public ComboBox getUnitComboBox() {
		return unitComboBox;
	}

	public void setUnitComboBox(ComboBox unitComboBox) {
		this.unitComboBox = unitComboBox;
	}
}
