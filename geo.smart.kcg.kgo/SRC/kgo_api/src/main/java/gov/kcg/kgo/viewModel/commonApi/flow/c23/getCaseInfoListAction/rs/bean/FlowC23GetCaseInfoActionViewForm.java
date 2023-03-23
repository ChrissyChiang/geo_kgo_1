package gov.kcg.kgo.viewModel.commonApi.flow.c23.getCaseInfoListAction.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.commonApi.flow.common.rs.bean.FlowGetCaseInfoViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 提供單一案件類型下所有案件編號給稅捐，C-2_3稅捐 Form
 */
@ApiModel(description = "提供單一案件類型下所有案件編號給稅捐，C-2_3稅捐 ViewForm")
public class FlowC23GetCaseInfoActionViewForm extends BaseViewForm {
	
	private static final long serialVersionUID = 1L;
	
	/** 案件資訊清單. */
	@ApiModelProperty(value = "案件資訊清單")
	private List<FlowGetCaseInfoViewForm> caseInfoList;

	public List<FlowGetCaseInfoViewForm> getCaseInfoList() {
		return caseInfoList;
	}

	public void setCaseInfoList(List<FlowGetCaseInfoViewForm> caseInfoList) {
		this.caseInfoList = caseInfoList;
	}
}
