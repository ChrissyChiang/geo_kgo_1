package gov.kcg.kgo.viewModel.backend.internetApply.identityVerify.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-身分驗證設定-初始畫面 View Form
 */
@ApiModel(description = "網路申辦-身分驗證設定-初始畫面 View Form")
public class InternetApplyIdentityVerifyHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 身分驗證設定CheckBox元件集合 **/
	@ApiModelProperty(value = "身分驗證設定CheckBox元件集合")
	private List<CheckBox> identityVerifyCheckBox;

	public List<CheckBox> getIdentityVerifyCheckBox() {
		return identityVerifyCheckBox;
	}

	public void setIdentityVerifyCheckBox(List<CheckBox> identityVerifyCheckBox) {
		this.identityVerifyCheckBox = identityVerifyCheckBox;
	}

}
