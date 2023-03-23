package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.home.rs.bean;

import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean.CaseHandleCaseViewSaCaseHomeViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * 後台案件處理-待審核匣-補正作業-初始畫面 View 紀錄 Form
 */
@ApiModel(description = "後台案件處理-待審核匣-補正作業-初始畫面 View 紀錄 Form")
public class CaseHandlePendingReviewCorrectHomeViewRecordForm {

	private static final long serialVersionUID = 1L;

	/** 案件狀態 **/
	@ApiModelProperty(value = "案件狀態")
	private String status;

	@ApiModelProperty(value = "案件狀態名稱")
	private String statusName;

	/** 處理人 **/
	@ApiModelProperty(value = "處理人")
	private String handler;

	/** 處理人名稱 **/
	@ApiModelProperty(value = "處理人名稱")
	private String handlerName;

	/** 處理內容 **/
	@ApiModelProperty(value = "處理內容")
	private String memo;

	/** 處理時間 **/
	@ApiModelProperty(value = "處理時間")
	private String handleTime;


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
