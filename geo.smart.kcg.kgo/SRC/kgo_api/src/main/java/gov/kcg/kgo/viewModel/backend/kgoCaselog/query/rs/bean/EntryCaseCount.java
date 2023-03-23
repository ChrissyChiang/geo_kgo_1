package gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台 - 軌跡紀錄 - 查詢 - 進案時間統計清單
 */
@ApiModel(description = "後台 - 軌跡紀錄 - 查詢 - 進案時間統計清單")
public class EntryCaseCount {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "服務名稱")
	private String caseSetName;

	@ApiModelProperty(value = "日期")
	private String date;

	@ApiModelProperty(value = "數量")
	private Integer count;

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
