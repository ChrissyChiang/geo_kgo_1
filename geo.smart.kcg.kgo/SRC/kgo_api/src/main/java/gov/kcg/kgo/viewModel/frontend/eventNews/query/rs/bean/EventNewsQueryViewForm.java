package gov.kcg.kgo.viewModel.frontend.eventNews.query.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean.TaskMaintainEditHomeFileViewForm;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 活動消息-任務消息查詢 View Form
 */
@ApiModel(description = "活動消息-任務消息查詢 View Form")
public class EventNewsQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 活動名稱 **/
	@ApiModelProperty(value = "活動名稱")
	private String activityName;

	/** 活動內容 **/
	@ApiModelProperty(value = "活動內容")
	private String contentHtml;

	/** 活動日期 **/
	@ApiModelProperty(value = "活動日期")
	private String activityDate;

	/** 服務案件名稱 **/
	@ApiModelProperty(value = "服務案件名稱")
	private String caseSetName;

	@ApiModelProperty(value = "附件")
	private List<TaskMaintainEditHomeFileViewForm> files;

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getContentHtml() {
		return contentHtml;
	}

	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}

	public String getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public List<TaskMaintainEditHomeFileViewForm> getFiles() {
		return files;
	}

	public void setFiles(List<TaskMaintainEditHomeFileViewForm> files) {
		this.files = files;
	}

}
