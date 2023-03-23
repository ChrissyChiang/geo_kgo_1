package gov.kcg.kgo.viewModel.backend.classManagement.classUpdateHome.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分類維護功能-類別新增/維護-畫面初始 View Form
 */
@ApiModel(description = "分類維護功能-類別新增/維護-畫面初始 View Form")
public class ClassManagementClassUpdateHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "類別名稱")
	private String name;

	@ApiModelProperty(value = "隸屬主分類下拉式選單")
	private ComboBox mainTypeComboBox;

	@ApiModelProperty(value = "機關下拉式選單")
	private ComboBox organComboBox;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ComboBox getMainTypeComboBox() {
		return mainTypeComboBox;
	}

	public void setMainTypeComboBox(ComboBox mainTypeComboBox) {
		this.mainTypeComboBox = mainTypeComboBox;
	}

	public ComboBox getOrganComboBox() {
		return organComboBox;
	}

	public void setOrganComboBox(ComboBox organComboBox) {
		this.organComboBox = organComboBox;
	}

}
