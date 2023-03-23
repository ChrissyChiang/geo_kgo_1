package gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.complete.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待簽收匣-初始畫面 View Form
 */
@ApiModel(description = "後台案件處理-待審核匣-結案-初始畫面 View Form")
public class CaseHandlePendingSignCompleteHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件編號")
	private String caseId;

	@ApiModelProperty(value = "審核人員")
	private String reviewer;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

}
