package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCorrectCasesetColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待審核匣-補正作業-群組資料
 */
@ApiModel(description = "後台案件處理-待審核匣-補正作業-群組資料")
public class CaseHandleDetailCasesetGroup {

    /** 群組id **/
    @ApiModelProperty(value = "群組id")
    private String groupId;

    /** 群組名稱 **/
    @ApiModelProperty(value = "群組名稱")
    private String groupName;

    /** 群組欄位 **/
    @ApiModelProperty(value = "群組欄位")
    private List<CaseHandleCorrectCasesetColumn> caseHandleCorrectCasesetColumns;

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

	public List<CaseHandleCorrectCasesetColumn> getCaseHandleCorrectCasesetColumns() {
		return caseHandleCorrectCasesetColumns;
	}

	public void setCaseHandleCorrectCasesetColumns(List<CaseHandleCorrectCasesetColumn> caseHandleCorrectCasesetColumns) {
		this.caseHandleCorrectCasesetColumns = caseHandleCorrectCasesetColumns;
	}
    
}
