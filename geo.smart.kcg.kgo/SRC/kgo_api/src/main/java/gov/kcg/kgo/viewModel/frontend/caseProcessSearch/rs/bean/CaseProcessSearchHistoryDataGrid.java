package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-案件檢視-案件檢視-處理歷程
 */
@ApiModel(description = "後台案件處理-案件檢視-案件檢視-處理歷程")
public class CaseProcessSearchHistoryDataGrid {

	/** 案件狀態 */
	@ApiModelProperty(notes = "案件狀態")
	private String status;

	/** 處理機關 */
	@ApiModelProperty(notes = "處理機關")
	private String organ;

	/** 內容 */
	@ApiModelProperty(notes = "內容")
	private String content;

	/** 承辦人 */
	@ApiModelProperty(notes = "承辦人")
	private String taker;

	/** 處理時間 */
	@ApiModelProperty(notes = "處理時間")
	private String dealTime;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTaker() {
		return taker;
	}

	public void setTaker(String taker) {
		this.taker = taker;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

}
