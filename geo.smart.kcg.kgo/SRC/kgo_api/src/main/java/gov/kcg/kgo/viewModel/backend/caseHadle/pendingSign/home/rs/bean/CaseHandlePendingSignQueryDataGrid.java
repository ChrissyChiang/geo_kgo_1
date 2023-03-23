package gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待簽收匣-案件集合
 */
@ApiModel(description = "後台案件處理-待簽收匣-案件集合")
public class CaseHandlePendingSignQueryDataGrid {

	/** 案件編號 */
	@ApiModelProperty(notes = "案件編號")
	private String caseId;

	/** 申請人員 */
	@ApiModelProperty(notes = "申請人員")
	private String applyUser;

	/** 申請日期 */
	@ApiModelProperty(notes = "申請日期")
	private String applyDate;

	/** 案件名稱 */
	@ApiModelProperty(notes = "案件名稱")
	private String caseSetName;

	/** 限辦日期 */
	@ApiModelProperty(notes = "限辦日期")
	private String deadlineDate;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(String deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

}
