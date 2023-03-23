package gov.kcg.kgo.viewModel.commonApi.updateCaseStatus.rq;


import java.util.List;

import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import gov.kcg.kgo.viewModel.commonApi.updateCaseStatus.rq.bean.UpdateCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "更新案件狀態 rq")
public class UpdateCaseStatusActionRq extends ApiCityCommonRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "案件編號清單")
	private List<UpdateCase> caseList;

	public List<UpdateCase> getCaseList() {
		return caseList;
	}

	public void setCaseList(List<UpdateCase> caseList) {
		this.caseList = caseList;
	}
}
