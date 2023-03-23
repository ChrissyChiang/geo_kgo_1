package gov.kcg.kgo.viewModel.backend.taskMaintain.save.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean.TaskMaintainEditHomeFileViewForm;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任務及公告管理-任務維護儲存 View Form
 */
@ApiModel(description = "任務及公告管理-任務維護儲存 View Form")
public class TaskMaintainSaveViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 執行結果訊息 **/
	@ApiModelProperty(value = "執行結果訊息")
	private String msg;

	@ApiModelProperty(value = "附件")
	private List<TaskMaintainEditHomeFileViewForm> files;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<TaskMaintainEditHomeFileViewForm> getFiles() {
		return files;
	}

	public void setFiles(List<TaskMaintainEditHomeFileViewForm> files) {
		this.files = files;
	}

}
