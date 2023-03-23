package gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.dto.AccountManagementQueryDto;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 共用-人員選擇畫面(with機關科室) View Form
 */
@ApiModel(description = "共用-機關科室人員選擇初始畫面 View Form")
public class OrganUnitUserSelectHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關下拉式元件")
	private ComboBox organComboBox;

	@ApiModelProperty(value = "科室下拉式元件")
	private ComboBox unitComboBox;

	/** 機關科室所屬的人員清單 **/
	@ApiModelProperty(value = "列出該機關科室所屬的人員清單")
	private List<AccountManagementQueryDto> grid;

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

	public List<AccountManagementQueryDto> getGrid() {
		return grid;
	}

	public void setGrid(List<AccountManagementQueryDto> grid) {
		this.grid = grid;
	}

}
