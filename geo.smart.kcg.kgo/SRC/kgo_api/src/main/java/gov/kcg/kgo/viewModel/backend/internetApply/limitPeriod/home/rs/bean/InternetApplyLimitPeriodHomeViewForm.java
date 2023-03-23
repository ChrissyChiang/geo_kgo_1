package gov.kcg.kgo.viewModel.backend.internetApply.limitPeriod.home.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-限辦期限設定-初始畫面 View Form
 */
@ApiModel(description = "網路申辦-限辦期限設定-初始畫面 View Form")
public class InternetApplyLimitPeriodHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 限辦期限天數 **/
	@ApiModelProperty(value = "限辦期限天數")
	private Integer limitPeriod;

	/** 電子郵件 **/
	@ApiModelProperty(value = "電子郵件")
	private String mail;

	public Integer getLimitPeriod() {
		return limitPeriod;
	}

	public void setLimitPeriod(Integer limitPeriod) {
		this.limitPeriod = limitPeriod;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
