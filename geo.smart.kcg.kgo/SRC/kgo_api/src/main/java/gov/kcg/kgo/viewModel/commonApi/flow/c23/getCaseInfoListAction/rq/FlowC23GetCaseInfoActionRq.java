package gov.kcg.kgo.viewModel.commonApi.flow.c23.getCaseInfoListAction.rq;


import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "提供單一案件類型下所有案件編號給稅捐，C-2_3稅捐  rq")
public class FlowC23GetCaseInfoActionRq extends ApiCityCommonRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "服務編號", position=1, example="A2020111000001")
	private String caseSetId;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}
}
