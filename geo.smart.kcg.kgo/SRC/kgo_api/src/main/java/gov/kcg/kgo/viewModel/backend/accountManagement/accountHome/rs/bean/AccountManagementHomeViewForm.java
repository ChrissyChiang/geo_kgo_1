package gov.kcg.kgo.viewModel.backend.accountManagement.accountHome.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.accountManagement.accountQuery.rs.bean.AccountManagementQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 帳號權限管理-畫面初始 View Form
 */
@ApiModel(description = "帳號權限管理-畫面初始 View Form")
public class AccountManagementHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關comboBox元件")
	private ComboBox organComboBox;

	@ApiModelProperty(value = "單位comboBox元件")
	private ComboBox unitComboBox;

	@ApiModelProperty(value = "角色comboBox元件")
	private ComboBox roleComboBox;

	@ApiModelProperty(value = "帳號權限管理-初始列出所有資料")
	private List<AccountManagementQueryDataGrid> grid;

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

	public ComboBox getRoleComboBox() {
		return roleComboBox;
	}

	public void setRoleComboBox(ComboBox roleComboBox) {
		this.roleComboBox = roleComboBox;
	}

	public List<AccountManagementQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<AccountManagementQueryDataGrid> grid) {
		this.grid = grid;
	}

}
