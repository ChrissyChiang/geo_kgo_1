package gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務宣告-初始畫面 View Form
 */
@ApiModel(description = "服務宣告-初始畫面 View Form")
public class ServiceAnnounceHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 案件名稱 **/
	@ApiModelProperty(value = "案件名稱")
	private String caseSetName;

	/** 服務宣告內容 **/
	@ApiModelProperty(value = "服務宣告內容")
	private String serviceHtml;

	/** 身分驗證設定CheckBox元件集合 **/
	@ApiModelProperty(value = "身分驗證設定CheckBox元件集合")
	private List<CheckBox> identityVerifyCheckBox;

	@ApiModelProperty(value = "符合登入驗證")
	private Boolean isLogin;

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getServiceHtml() {
		return serviceHtml;
	}

	public void setServiceHtml(String serviceHtml) {
		this.serviceHtml = serviceHtml;
	}

	public List<CheckBox> getIdentityVerifyCheckBox() {
		return identityVerifyCheckBox;
	}

	public void setIdentityVerifyCheckBox(List<CheckBox> identityVerifyCheckBox) {
		this.identityVerifyCheckBox = identityVerifyCheckBox;
	}

	public Boolean getLogin() {
		return isLogin;
	}

	public void setLogin(Boolean login) {
		isLogin = login;
	}
}
