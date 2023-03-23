package gov.kcg.kgo.viewModel.backend.taskMaintain.save.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean.TaskMaintainEditHomeFileViewForm;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "任務及公告管理-任務維護儲存 rq")
public class TaskMaintainSaveRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	/** 序號 **/
	@ApiModelProperty(value = "序號")
	private Integer seq;

	/** 發布對象(F:前台/B:後台) **/
	@ApiModelProperty(value = "發布對象(F:前台/B:後台)")
	private String releaseObject;

	/** 發布功能(AnnounceM:公告/TaskM:任務) **/
	@ApiModelProperty(value = "發布功能(AnnounceM:公告/TaskM:任務)")
	private String functionCode;

	/**
	 * ===================== common =====================
	 */
	/** 活動名稱 **/
	@ApiModelProperty(value = "活動名稱")
	private String activityName;

	/** 活動內容 **/
	@ApiModelProperty(value = "活動內容")
	private String activityContent;

	@ApiModelProperty(value = "附件")
	private List<TaskMaintainEditHomeFileViewForm> files;

	/**
	 * ===================== only for KGO_TASK_SET =====================
	 */
	/** 活動日期 **/
	@ApiModelProperty(value = "活動日期")
	private String[] pfDate;

	/** 被選取活動項目的代碼 **/
	@ApiModelProperty(value = "被選取活動項目的代碼")
	private String taskSeq;

	/** 服務案件-案件 **/
	@ApiModelProperty(value = "服務案件-案件代碼")
	private List<String> caseSetId;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getReleaseObject() {
		return releaseObject;
	}

	public void setReleaseObject(String releaseObject) {
		this.releaseObject = releaseObject;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityContent() {
		return activityContent;
	}

	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}

	public String[] getPfDate() {
		return pfDate;
	}

	public void setPfDate(String[] pfDate) {
		this.pfDate = pfDate;
	}

	public String getTaskSeq() {
		return taskSeq;
	}

	public void setTaskSeq(String taskSeq) {
		this.taskSeq = taskSeq;
	}

	public List<String> getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(List<String> caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public List<TaskMaintainEditHomeFileViewForm> getFiles() {
		return files;
	}

	public void setFiles(List<TaskMaintainEditHomeFileViewForm> files) {
		this.files = files;
	}

}
