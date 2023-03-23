package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 案件進度查詢-明細-群組資料
 */
@ApiModel(description = "案件進度查詢-明細-群組資料")
public class CaseProcessSearchDetailCasesetGroup {

	/** 群組id **/
	@ApiModelProperty(value = "群組id")
	private String groupId;

	/** 群組名稱 **/
	@ApiModelProperty(value = "群組名稱")
	private String groupName;

	/** 群組欄位 **/
	@ApiModelProperty(value = "群組欄位")
	private List<CaseProcessSearchDetailCasesetColumn> caseProcessSearchDetailCasesetColumns;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<CaseProcessSearchDetailCasesetColumn> getCaseProcessSearchDetailCasesetColumns() {
		return caseProcessSearchDetailCasesetColumns;
	}

	public void setCaseProcessSearchDetailCasesetColumns(List<CaseProcessSearchDetailCasesetColumn> caseProcessSearchDetailCasesetColumns) {
		this.caseProcessSearchDetailCasesetColumns = caseProcessSearchDetailCasesetColumns;
	}
}
