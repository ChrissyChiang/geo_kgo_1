package gov.kcg.kgo.viewModel.backend.common.unitComboBox.query.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 共用-人員清單Popup - 單位下拉式選單查詢 View Form
 */
@ApiModel(description = "共用-人員清單Popup - 單位下拉式選單查詢 View Form")
public class UnitComboBoxQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "科室下拉式選單")
	private ComboBox unitComboBox;

	public ComboBox getUnitComboBox() {
		return unitComboBox;
	}

	public void setUnitComboBox(ComboBox unitComboBox) {
		this.unitComboBox = unitComboBox;
	}

}
