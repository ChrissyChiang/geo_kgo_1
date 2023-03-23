package gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rq;

import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rq.bean.CaseSetForm;
import gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rq.bean.CaseSetMemo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "通用型入案作業 rq")
public class GenGeneralCaseActionRq extends ApiCityCommonRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "服務案件編號", position=1)
	private String caseSetId;

	@ApiModelProperty(value = "網路申辦(E)", position=2)
	private CaseSetMemo caseSetMemoE;
	
	@ApiModelProperty(value = "臨櫃申辦(C)", position=3)
	private CaseSetMemo caseSetMemoC;
	
	@ApiModelProperty(value = "書表維護", position=4)
	private CaseSetForm caseSetForm;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public CaseSetMemo getCaseSetMemoE() {
		return caseSetMemoE;
	}

	public void setCaseSetMemoE(CaseSetMemo caseSetMemoE) {
		this.caseSetMemoE = caseSetMemoE;
	}

	public CaseSetMemo getCaseSetMemoC() {
		return caseSetMemoC;
	}

	public void setCaseSetMemoC(CaseSetMemo caseSetMemoC) {
		this.caseSetMemoC = caseSetMemoC;
	}

	public CaseSetForm getCaseSetForm() {
		return caseSetForm;
	}

	public void setCaseSetForm(CaseSetForm caseSetForm) {
		this.caseSetForm = caseSetForm;
	}
}
