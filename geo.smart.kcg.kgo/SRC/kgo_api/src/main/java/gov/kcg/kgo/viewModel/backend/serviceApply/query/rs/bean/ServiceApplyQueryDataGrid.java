package gov.kcg.kgo.viewModel.backend.serviceApply.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務申請-申請案件查詢資料集合
 */
@ApiModel(description = "服務申請-申請案件查詢資料集合")
public class ServiceApplyQueryDataGrid {

	/** 服務案件編號 */
	@ApiModelProperty(notes = "服務案件編號")
	private String caseSetId;

	/** 案件類型 */
	@ApiModelProperty(notes = "案件類型")
	private String caseType;

	/** 顯示名稱 */
	@ApiModelProperty(notes = "顯示名稱")
	private String caseSetName;

	public ServiceApplyQueryDataGrid(String caseSetId, String caseType, String caseSetName) {
		this.caseSetId = caseSetId;
		this.caseType = caseType;
		this.caseSetName = caseSetName;
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
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
