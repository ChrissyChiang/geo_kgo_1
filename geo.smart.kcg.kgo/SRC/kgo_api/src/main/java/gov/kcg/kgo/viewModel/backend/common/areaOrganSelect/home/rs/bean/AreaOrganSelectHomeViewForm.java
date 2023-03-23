package gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.common.organSelect.query.rs.bean.OrganSelectQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 共用-區機關選擇-初始畫面 View Form
 */
@ApiModel(description = "共用-區機關選擇-初始畫面 View Form")
public class AreaOrganSelectHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關下拉式元件")
	private ComboBox organComboBox;

	@ApiModelProperty(value = "區機關下拉式元件")
	private ComboBox countyComboBox;

	@ApiModelProperty(value = "科室名稱清單")
	private List<OrganSelectQueryDataGrid> grid;

	public ComboBox getOrganComboBox() {
		return organComboBox;
	}

	public void setOrganComboBox(ComboBox organComboBox) {
		this.organComboBox = organComboBox;
	}

	public ComboBox getCountyComboBox() {
		return countyComboBox;
	}

	public void setCountyComboBox(ComboBox countyComboBox) {
		this.countyComboBox = countyComboBox;
	}

	public List<OrganSelectQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<OrganSelectQueryDataGrid> grid) {
		this.grid = grid;
	}

}
