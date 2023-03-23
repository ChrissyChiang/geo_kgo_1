package gov.kcg.kgo.viewModel.backend.questMaintain.edit.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 常見問題維護-問題維護(新增/修改) View Form
 */
@ApiModel(description = "常見問題維護-問題維護(新增/修改) View Form")
public class QuestionMaintainEditViewForm extends BaseViewForm {

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
