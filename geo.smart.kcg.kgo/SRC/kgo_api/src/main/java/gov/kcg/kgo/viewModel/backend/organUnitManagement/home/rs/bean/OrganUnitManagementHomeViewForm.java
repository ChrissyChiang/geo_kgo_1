package gov.kcg.kgo.viewModel.backend.organUnitManagement.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 機關科室管理-初始畫面 View Form
 */
@ApiModel(description = "機關科室管理-初始畫面 View Form")
public class OrganUnitManagementHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/**
	 * 初始時列出所有的機關資料的 comboBox
	 */
	@ApiModelProperty(value = "機關comboBox元件")
	private ComboBox organComboBox;

	/**
	 * 初始時只會顯示預設值的comboBox
	 */
	@ApiModelProperty(value = "單位comboBox元件")
	private ComboBox unitComboBox;

	/** 分類搜尋結果清單 **/
	@ApiModelProperty(value = "機關科室管理-列出所有機關科室資料")
	private List<OrganUnitManagementHomeDataGrid> grid;

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

	public List<OrganUnitManagementHomeDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<OrganUnitManagementHomeDataGrid> grid) {
		this.grid = grid;
	}

}
