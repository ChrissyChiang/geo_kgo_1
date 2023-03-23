package gov.kcg.kgo.viewModel.backend.taskMaintain.onOff.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任務維護-任務查詢 View Form
 */
@ApiModel(description = "任務及公告管理-任務上下架 View Form")
public class TaskMaintainOnOffViewForm extends BaseViewForm {

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
