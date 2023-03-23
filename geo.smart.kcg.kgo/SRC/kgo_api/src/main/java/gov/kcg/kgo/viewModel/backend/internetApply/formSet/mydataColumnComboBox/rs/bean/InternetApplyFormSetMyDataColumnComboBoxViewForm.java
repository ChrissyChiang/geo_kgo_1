package gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataColumnComboBox.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-表單設定-欄位維護-MYDATA_COLUMN下拉式選單 View Form
 */
@ApiModel(description = "網路申辦-表單設定-欄位維護-MYDATA_COLUMN下拉式選單 View Form")
public class InternetApplyFormSetMyDataColumnComboBoxViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** MYDATA資料集下拉式選單 **/
	@ApiModelProperty(value = "MyData對應欄位下拉式選單")
	private ComboBox myDataColumnComboBox;

	public ComboBox getMyDataColumnComboBox() {
		return myDataColumnComboBox;
	}

	public void setMyDataColumnComboBox(ComboBox myDataColumnComboBox) {
		this.myDataColumnComboBox = myDataColumnComboBox;
	}

}
