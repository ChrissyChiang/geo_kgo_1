package gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.complete.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveActionColumnViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "後台案件處理-待審核匣-結案 rq")
public class CaseHandlePendingSignCompleteRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件編號")
	private String caseId;

	@ApiModelProperty(value = "案件階段編號")
	private String taskId;

	@ApiModelProperty(value = "結案狀態")
	private String status;

	@ApiModelProperty(value = "結案說明")
	private String description;

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

	/**  結案時折扣下拉百分比(-1為不選擇) **/
	@ApiModelProperty(notes = "結案時折扣下拉百分比(-1為不選擇)")
	private Integer discountPercent;
	
	/**  繳費優惠折扣  **/
	@ApiModelProperty(notes = "最後核定金額")
	private Integer payAmount;
	
	
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getDiscountPercent() {return discountPercent;}

	public void setDiscountPercent(Integer discountPercent) {this.discountPercent = discountPercent;}

	public Integer getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}


	
	
}
