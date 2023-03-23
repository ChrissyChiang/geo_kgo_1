package gov.kcg.kgo.dto.Activiti;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Activiti-歷史資料集合")
public class HistoryDataDto {

	@ApiModelProperty(notes = "案件狀態")
	private String status;

	@ApiModelProperty(notes = "處理機關")
	private String organName;

	@ApiModelProperty(notes = "內容")
	private String content;

	@ApiModelProperty(notes = "承辦人")
	private String officer;

	@ApiModelProperty(notes = "處理時間")
	private String dealTime;
	
	@ApiModelProperty(notes = "任務節點名稱")
	private String taskNode;

	/** GEO 20211101 add 增加簽核意見*/
	@ApiModelProperty(notes = "comment id")
	private String commentId;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOfficer() {
		return officer;
	}

	public void setOfficer(String officer) {
		this.officer = officer;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getTaskNode() {
		return taskNode;
	}

	public void setTaskNode(String taskNode) {
		this.taskNode = taskNode;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
}
