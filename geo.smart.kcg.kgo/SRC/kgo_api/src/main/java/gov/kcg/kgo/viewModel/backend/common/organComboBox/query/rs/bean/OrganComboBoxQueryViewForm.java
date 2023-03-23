package gov.kcg.kgo.viewModel.backend.common.organComboBox.query.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 共用-機關單位下拉選單 View Form
 */
@ApiModel(description = "共用-機關單位下拉選單 View Form")
public class OrganComboBoxQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關下拉式選單")
	private ComboBox organComboBox;

	public ComboBox getOrganComboBox() {
		return organComboBox;
	}

	public void setOrganComboBox(ComboBox organComboBox) {
		this.organComboBox = organComboBox;
	}

}
