package gov.kcg.kgo.viewModel.commonApi.flow.b2.notifyNewCase.rq;


import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "B-2流程_1 通知既有案件服務有新進案件，B-2流程_1 rq")
public class NotifyNewCaseActionRq extends ApiCityCommonRequest {

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
