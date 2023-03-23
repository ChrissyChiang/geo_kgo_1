package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.cancelAccept.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待審核匣-取消簽收 View Form
 */
@ApiModel(description = "後台案件處理-待審核匣-取消簽收 View Form")
public class CaseHandlePendingReviewCancelAcceptViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value ="processId")
	private String processId;
	
	@ApiModelProperty(value = "caseId")
	private String caseId;

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
}
