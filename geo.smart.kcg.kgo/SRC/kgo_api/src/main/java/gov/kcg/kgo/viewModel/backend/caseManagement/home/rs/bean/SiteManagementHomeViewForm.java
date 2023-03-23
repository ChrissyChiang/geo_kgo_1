package gov.kcg.kgo.viewModel.backend.caseManagement.home.rs.bean;

import java.util.List;
import java.util.Map;

import gov.kcg.kgo.dto.CaseManagementQueryDto;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.bean.CaseManagementQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 場地初始資訊 View Form  Roy
 */
@ApiModel(description = "場地初始資訊View Form")
public class SiteManagementHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/**
	 * 初始時列出所有的機關分類資料的 comboBox
	 */
	@ApiModelProperty(value = "場地/活動下拉")
	private ComboBox categoryComboBox;

	@ApiModelProperty(value = "機關分類comboBox")
	private ComboBox organComboBox;

	@ApiModelProperty(value = "科室分類comboBox")
	private ComboBox unitComboBox;

	@ApiModelProperty(value = "場地建立者combox")
	private ComboBox editUserComboBox;


	public ComboBox getCategoryComboBox() {
		return categoryComboBox;
	}

	public void setCategoryComboBox(ComboBox categoryComboBox) {
		this.categoryComboBox = categoryComboBox;
	}

	public ComboBox getOrganComboBox() {
		return organComboBox;
	}

	public void setOrganComboBox(ComboBox organComboBox) {
		this.organComboBox = organComboBox;
	}

	public ComboBox getUnitComboBox() {
		return unitComboBox;
	}

	public void setUnitComboBox(ComboBox unitComboBox) {
		this.unitComboBox = unitComboBox;
	}

	public ComboBox getEditUserComboBox() {
		return editUserComboBox;
	}

	public void setEditUserComboBox(ComboBox editUserComboBox) {
		this.editUserComboBox = editUserComboBox;
	}
}
