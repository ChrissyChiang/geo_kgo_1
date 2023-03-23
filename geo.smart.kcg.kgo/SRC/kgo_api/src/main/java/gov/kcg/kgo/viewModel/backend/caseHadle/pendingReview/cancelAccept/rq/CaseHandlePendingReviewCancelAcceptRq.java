package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.cancelAccept.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveActionColumnViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "後台案件處理-待審核匣-取消簽收-rq")
public class CaseHandlePendingReviewCancelAcceptRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	/**
	 *
	 * 機關=1
	 * 承辦=2
	 */
	@ApiModelProperty(value = "類型代碼")
	private String rejectTo;

	@ApiModelProperty(value = "案件編號")
	private String caseId;

	@ApiModelProperty(value = "案件階段編號")
	private String taskId;

	/** GEO 20211101 add 增加簽核意見*/
	@ApiModelProperty(value = "簽核意見")
	private String taskComment;

	/** GEO 20211109 add 機關審核表單*/
	@ApiModelProperty(notes = "欄位")
	private List<SaveActionColumnViewForm> columnData;

	/** GEO 20211109 add 機關審核表單*/
	@ApiModelProperty(notes = "服務版本號")
	private Integer version;

	/** GEO 20211109 add 機關審核表單*/
	@ApiModelProperty(notes = "機關審核表單版本號")
	private Integer organFormVersion;


	public String getRejectTo() {
		return rejectTo;
	}

	public void setRejectTo(String rejectTo) {
		this.rejectTo = rejectTo;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskComment() {
		return taskComment;
	}

	public void setTaskComment(String taskComment) {
		this.taskComment = taskComment;
	}

	public List<SaveActionColumnViewForm> getColumnData() {
		return columnData;
	}

	public void setColumnData(List<SaveActionColumnViewForm> columnData) {
		this.columnData = columnData;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getOrganFormVersion() {
		return organFormVersion;
	}

	public void setOrganFormVersion(Integer organFormVersion) {
		this.organFormVersion = organFormVersion;
	}
}
