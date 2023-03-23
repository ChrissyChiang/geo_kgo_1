package gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataComboBox.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-表單設定-MYDAYA維護-取得Mydata資料集下拉式選單 View Form
 */
@ApiModel(description = "網路申辦-表單設定-MYDAYA維護-取得Mydata資料集下拉式選單 View Form")
public class InternetApplyFormSetMydataComboBoxViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Mydata 資料集下拉式選單")
	private ComboBox mydataComboBox;

	public ComboBox getMydataComboBox() {
		return mydataComboBox;
	}

	public void setMydataComboBox(ComboBox mydataComboBox) {
		this.mydataComboBox = mydataComboBox;
	}

}
