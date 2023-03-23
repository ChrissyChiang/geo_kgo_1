package gov.kcg.kgo.viewModel.backend.caseManagement.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.dto.CaseManagementQueryDto;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.bean.CaseManagementQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務案件清單-初始畫面 View Form
 */
@ApiModel(description = "服務案件清單-初始畫面 View Form")
public class CaseManagementHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/**
	 * 初始時列出所有的機關分類資料的 comboBox
	 */
	@ApiModelProperty(value = "機關分類comboBox元件")
	private ComboBox organComboBox;

	/**
	 * 初始時列出所有的權責機關資料的 comboBox
	 */
	@ApiModelProperty(value = "權責機關comboBox元件")
	private ComboBox ownerOrganComboBox;

	/**
	 * 初始時顯示所有管理者身分的使用者comboBox
	 */
	@ApiModelProperty(value = "管理者comboBox元件")
	private ComboBox caseManagerComboBox;

	/** 案件搜尋結果清單 **/
	@ApiModelProperty(value = "列出所有案件資料")
	private List<CaseManagementQueryDataGrid> grid;

	public ComboBox getOrganComboBox() {
		return organComboBox;
	}

	public void setOrganComboBox(ComboBox organComboBox) {
		this.organComboBox = organComboBox;
	}

	public ComboBox getOwnerOrganComboBox() {
		return ownerOrganComboBox;
	}

	public void setOwnerOrganComboBox(ComboBox ownerOrganComboBox) {
		this.ownerOrganComboBox = ownerOrganComboBox;
	}

	public ComboBox getCaseManagerComboBox() {
		return caseManagerComboBox;
	}

	public void setCaseManagerComboBox(ComboBox caseManagerComboBox) {
		this.caseManagerComboBox = caseManagerComboBox;
	}

	public List<CaseManagementQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<CaseManagementQueryDataGrid> grid) {
		this.grid = grid;
	}

}
