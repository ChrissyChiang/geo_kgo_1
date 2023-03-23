package gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待審核匣-補正作業-補正紀錄
 */
@ApiModel(description = "後台案件處理-待審核匣-補正作業-補正紀錄")
public class CaseHandleCorrectRecord {

	/** 案件狀態 */
	@ApiModelProperty(notes = "案件狀態")
	private String status;

	/** 處理人 */
	@ApiModelProperty(notes = "處理人")
	private String taker;
	
	/** 處理內容 */
	@ApiModelProperty(notes = "處理內容")
	private String content;
	
	/** 處理時間 */
	@ApiModelProperty(notes = "處理時間")
	private String dealTime;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaker() {
		return taker;
	}

	public void setTaker(String taker) {
		this.taker = taker;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

}
