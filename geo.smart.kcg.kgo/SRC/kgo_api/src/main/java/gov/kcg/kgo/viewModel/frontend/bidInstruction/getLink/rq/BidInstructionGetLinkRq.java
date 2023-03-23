package gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.rq;

import gov.kcg.kgo.viewModel.frontend.base.FrontendValidateCodeRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "申辦說明頁-取得站外連結 rq")
public class BidInstructionGetLinkRq extends FrontendValidateCodeRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件種類ID")
	private String caseSetId;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

}
