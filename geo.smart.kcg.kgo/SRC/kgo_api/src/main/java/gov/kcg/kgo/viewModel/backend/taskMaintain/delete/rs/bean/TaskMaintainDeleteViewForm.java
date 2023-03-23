package gov.kcg.kgo.viewModel.backend.taskMaintain.delete.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任務及公告管理-任務刪除 View Form
 */
@ApiModel(description = "任務及公告管理-任務刪除 View Form")
public class TaskMaintainDeleteViewForm extends BaseViewForm {

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
