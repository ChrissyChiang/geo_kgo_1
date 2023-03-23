package gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataDelete.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-表單設定-MYDAYA維護-資料集刪除 View Form
 */
@ApiModel(description = "網路申辦-表單設定-MYDAYA維護-資料集刪除 View Form")
public class InternetApplyFormSetMydataDeleteViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 刪除執行結果 **/
	@ApiModelProperty(value = "刪除執行結果")
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
