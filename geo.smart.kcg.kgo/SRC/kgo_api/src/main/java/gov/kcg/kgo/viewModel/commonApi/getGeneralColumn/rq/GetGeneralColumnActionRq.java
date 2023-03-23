package gov.kcg.kgo.viewModel.commonApi.getGeneralColumn.rq;

import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "通用型查詢欄位作業 rq")
public class GetGeneralColumnActionRq extends ApiCityCommonRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "服務案件編號")
	private String caseSetId;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}
}
