package gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataHome.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-表單設定-MYDAYA維護-初始畫面 View Form
 */
@ApiModel(description = "網路申辦-表單設定-MYDAYA維護-初始畫面 View Form")
public class InternetApplyFormSetMydataHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 來源下拉式選單 **/
	@ApiModelProperty(value = "來源下拉式選單")
	private ComboBox sTypeComboBox;

	/** 服務下拉式選單 **/
	@ApiModelProperty(value = "服務下拉式選單")
	private ComboBox serviceComboBox;

	/** MYDATA資料集合名稱下拉式選單 **/
	@ApiModelProperty(value = "MYDATA資料集合名稱下拉式選單")
	private ComboBox myDataSetComboBox;

	/** MYDATA設定檔清單 **/
	@ApiModelProperty(value = "MYDATA設定檔清單")
	private List<InternetApplyFormSetMydataHomeDataGrid> grid;

	public ComboBox getsTypeComboBox() {
		return sTypeComboBox;
	}

	public void setsTypeComboBox(ComboBox sTypeComboBox) {
		this.sTypeComboBox = sTypeComboBox;
	}

	public ComboBox getServiceComboBox() {
		return serviceComboBox;
	}

	public void setServiceComboBox(ComboBox serviceComboBox) {
		this.serviceComboBox = serviceComboBox;
	}

	public ComboBox getMyDataSetComboBox() {
		return myDataSetComboBox;
	}

	public void setMyDataSetComboBox(ComboBox myDataSetComboBox) {
		this.myDataSetComboBox = myDataSetComboBox;
	}

	public List<InternetApplyFormSetMydataHomeDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<InternetApplyFormSetMydataHomeDataGrid> grid) {
		this.grid = grid;
	}

}
