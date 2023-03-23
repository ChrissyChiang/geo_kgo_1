package gov.kcg.kgo.viewModel.backend.classManagement.classHome.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.classManagement.classQuery.rs.bean.ClassManagementClassQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分類維護功能-畫面初始 View Form
 */
@ApiModel(description = "分類維護功能-畫面初始 View Form")
public class ClassManagementClassHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主類別下拉式選單")
	private ComboBox mainTypeComboBox;

	@ApiModelProperty(value = "次類別下拉式選單")
	private ComboBox subTypeComboBox;

	/** 分類搜尋結果清單 **/
	@ApiModelProperty(value = "分類搜尋-預設所有清單")
	private List<ClassManagementClassQueryDataGrid> grid;

	public ComboBox getMainTypeComboBox() {
		return mainTypeComboBox;
	}

	public void setMainTypeComboBox(ComboBox mainTypeComboBox) {
		this.mainTypeComboBox = mainTypeComboBox;
	}

	public ComboBox getSubTypeComboBox() {
		return subTypeComboBox;
	}

	public void setSubTypeComboBox(ComboBox subTypeComboBox) {
		this.subTypeComboBox = subTypeComboBox;
	}

	public List<ClassManagementClassQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<ClassManagementClassQueryDataGrid> grid) {
		this.grid = grid;
	}

}
