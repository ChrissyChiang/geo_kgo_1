package gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaSave.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-受理機關設定-受理區機關新增 View Form
 */
@ApiModel(description = "網路申辦-受理機關設定-受理區機關新增 View Form")
public class InternetApplyAcceptSetAreaSaveViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 執行結果訊息 **/
	@ApiModelProperty(value = "執行結果訊息")
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
