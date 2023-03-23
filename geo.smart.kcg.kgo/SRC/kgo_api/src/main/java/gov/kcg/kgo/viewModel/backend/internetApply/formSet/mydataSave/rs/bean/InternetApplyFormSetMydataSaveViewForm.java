package gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataSave.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-表單設定-MYDAYA維護-資料集新增 View Form
 */
@ApiModel(description = "網路申辦-表單設定-MYDAYA維護-資料集新增 View Form")
public class InternetApplyFormSetMydataSaveViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 新增執行結果 **/
	@ApiModelProperty(value = "新增執行結果")
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
