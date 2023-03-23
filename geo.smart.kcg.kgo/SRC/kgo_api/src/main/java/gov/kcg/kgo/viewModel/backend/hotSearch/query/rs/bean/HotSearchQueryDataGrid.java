package gov.kcg.kgo.viewModel.backend.hotSearch.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務申請-申請案件查詢資料集合
 */
@ApiModel(description = "服務申請-申請案件查詢資料集合")
public class HotSearchQueryDataGrid {

	/** 案件類型 */
	@ApiModelProperty(notes = "案件類型")
	private String caseType;

	/** 顯示名稱 */
	@ApiModelProperty(notes = "顯示名稱")
	private String caseSetName;

	public HotSearchQueryDataGrid(String caseType, String caseSetName) {
		this.caseType = caseType;
		this.caseSetName = caseSetName;
	}
	
	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

}
