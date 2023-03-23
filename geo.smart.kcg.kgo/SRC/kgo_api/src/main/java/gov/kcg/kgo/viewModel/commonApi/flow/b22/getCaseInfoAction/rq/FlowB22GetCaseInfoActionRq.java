package gov.kcg.kgo.viewModel.commonApi.flow.b22.getCaseInfoAction.rq;


import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "提供既有案件服務撈取案件資料API，B-2流程_2 rq")
public class FlowB22GetCaseInfoActionRq extends ApiCityCommonRequest {

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
