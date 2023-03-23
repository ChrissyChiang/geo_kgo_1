package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.scaCase.home.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-案件檢視-服務案件新增(SCA)案件檢視-初始畫面 rq")
public class CaseHandleCaseViewScaCaseHomeRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件編號")
	private String caseId;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

}
