package gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-表單設定-欄位群組維護-進版儲存 View Form
 */
@ApiModel(description = "網路申辦-表單設定-欄位群組維護-進版儲存 View Form")
public class InternetApplyFormSetGroupColumnSaveViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 進版號 **/
	@ApiModelProperty(value = "進版號")
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
