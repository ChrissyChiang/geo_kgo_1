package gov.kcg.kgo.viewModel.backend.formDownload.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 表單下載-初始畫面 View Form
 */
@ApiModel(description = "表單下載-初始畫面 View Form")
public class FormDownloadHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 分類下拉式元件 **/
	@ApiModelProperty(value = "分類下拉式元件")
	private ComboBox formTypeComboBox;

	/** 案件書表維護資料集合 **/
	@ApiModelProperty(value = "案件書表維護資料集合")
	private List<FormDownloadQueryDataGrid> grid;

	public ComboBox getFormTypeComboBox() {
		return formTypeComboBox;
	}

	public void setFormTypeComboBox(ComboBox formTypeComboBox) {
		this.formTypeComboBox = formTypeComboBox;
	}

	public List<FormDownloadQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<FormDownloadQueryDataGrid> grid) {
		this.grid = grid;
	}

}
