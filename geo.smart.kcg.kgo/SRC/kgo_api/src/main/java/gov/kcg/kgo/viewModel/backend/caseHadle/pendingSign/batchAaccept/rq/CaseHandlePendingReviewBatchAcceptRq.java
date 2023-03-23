package gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.batchAaccept.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.batchAaccept.rq.bean.CaseHandlePendingReviewBatchAcceptForm;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-待簽收匣-批次簽收 rq")
public class CaseHandlePendingReviewBatchAcceptRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件階段編號")
	private List<CaseHandlePendingReviewBatchAcceptForm> forms;

	public List<CaseHandlePendingReviewBatchAcceptForm> getForms() {
		return forms;
	}

	public void setForms(List<CaseHandlePendingReviewBatchAcceptForm> forms) {
		this.forms = forms;
	}
}
