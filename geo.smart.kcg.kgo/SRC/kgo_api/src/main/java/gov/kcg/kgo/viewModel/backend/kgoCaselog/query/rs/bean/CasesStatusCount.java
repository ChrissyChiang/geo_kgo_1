package gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台 - 軌跡紀錄 - 查詢 - 案件狀態統計分析
 */
@ApiModel(description = "後台 - 軌跡紀錄 - 查詢 - 案件狀態統計分析")
public class CasesStatusCount {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件狀態")
	private String caseStatus;


	@ApiModelProperty(value = "數量")
	private Integer count;


	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
