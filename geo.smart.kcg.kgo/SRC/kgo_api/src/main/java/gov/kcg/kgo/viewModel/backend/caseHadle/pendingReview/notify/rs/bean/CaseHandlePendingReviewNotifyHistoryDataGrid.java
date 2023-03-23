package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * 後台案件處理-待簽收匣-訊息通知集合
 */
@ApiModel(description = "後台案件處理-待簽收匣-訊息通知集合")
public class CaseHandlePendingReviewNotifyHistoryDataGrid {

	/** 案件狀態 */
	@ApiModelProperty(notes = "案件狀態")
	private String status;

	/** 處理人 */
	@ApiModelProperty(notes = "處理人")
	private String handler;

	/** 處理內容 */
	@ApiModelProperty(notes = "處理內容")
	private String memo;

	/** 處理時間 */
	@ApiModelProperty(notes = "處理時間")
	private Timestamp handleTime;

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

    public Timestamp getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Timestamp handleTime) {
        this.handleTime = handleTime;
    }

}
