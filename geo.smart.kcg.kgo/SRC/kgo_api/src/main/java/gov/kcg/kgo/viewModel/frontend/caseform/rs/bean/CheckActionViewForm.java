package gov.kcg.kgo.viewModel.frontend.caseform.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** Geo 20211202 重複檢核
 * 重複檢核 確認是否可申請
 */
@ApiModel(description = "重複檢核 確認是否可申請")
public class CheckActionViewForm extends BaseViewForm {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "是否可申請")
	private Boolean canApply;

	@ApiModelProperty(notes = "不可申請警示訊息")
	private String msg;

	public Boolean getCanApply() {
		return canApply;
	}

	public void setCanApply(Boolean canApply) {
		this.canApply = canApply;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
