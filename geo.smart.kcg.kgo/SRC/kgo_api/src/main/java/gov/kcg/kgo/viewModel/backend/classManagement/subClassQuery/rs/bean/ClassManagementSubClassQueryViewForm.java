package gov.kcg.kgo.viewModel.backend.classManagement.subClassQuery.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分類維護功能-取得次類別 View Form
 */
@ApiModel(description = "分類維護功能-取得次類別 View Form")
public class ClassManagementSubClassQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "次類別下拉式選單")
	private ComboBox subTypeComboBox;

	public ComboBox getSubTypeComboBox() {
		return subTypeComboBox;
	}

	public void setSubTypeComboBox(ComboBox subTypeComboBox) {
		this.subTypeComboBox = subTypeComboBox;
	}

}
