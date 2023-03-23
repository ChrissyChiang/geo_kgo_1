package gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.dispatch.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.batchAaccept.rq.bean.CaseHandlePendingReviewBatchAcceptForm;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-待簽收匣-分案/批次分文 rq")
public class CaseHandlePendingSignDispatchRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "分案類型, UNIT或OFFICER")
	private String acceptType;

	@ApiModelProperty(value = "資料集合")
	private List<CaseHandlePendingReviewBatchSignForm> forms;

	@ApiModelProperty(value = "分案人員或機關")
	private String acceptor;

	/** GEO 20211101 add 增加簽核意見*/
	@ApiModelProperty(value = "簽核意見")
	private String taskComment;

	public String getAcceptType() {
		return acceptType;
	}

	public void setAcceptType(String acceptType) {
		this.acceptType = acceptType;
	}

	public List<CaseHandlePendingReviewBatchSignForm> getForms() {
		return forms;
	}

	public void setForms(List<CaseHandlePendingReviewBatchSignForm> forms) {
		this.forms = forms;
	}

	public String getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(String acceptor) {
		this.acceptor = acceptor;
	}

	public String getTaskComment() {
		return taskComment;
	}

	public void setTaskComment(String taskComment) {
		this.taskComment = taskComment;
	}
}
