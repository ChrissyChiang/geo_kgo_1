package gov.kcg.kgo.viewModel.backend.internetApply.serviceHtml.home.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-服務宣告設定-初始畫面 View Form
 */
@ApiModel(description = "網路申辦-服務宣告設定-初始畫面 View Form")
public class InternetApplyServiceHtmlHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 啟用服務宣告CheckBox元件 **/
	@ApiModelProperty(value = "啟用服務宣告CheckBox元件")
	private CheckBox isServiceHtmlCheckBox;

	/** 啟用服務宣告html文字 **/
	@ApiModelProperty(value = "啟用服務宣告html文字")
	private String serviceHtml;

	public CheckBox getIsServiceHtmlCheckBox() {
		return isServiceHtmlCheckBox;
	}

	public void setIsServiceHtmlCheckBox(CheckBox isServiceHtmlCheckBox) {
		this.isServiceHtmlCheckBox = isServiceHtmlCheckBox;
	}

	public String getServiceHtml() {
		return serviceHtml;
	}

	public void setServiceHtml(String serviceHtml) {
		this.serviceHtml = serviceHtml;
	}

}
