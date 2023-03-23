package gov.kcg.kgo.viewModel.commonApi.getCaseId.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 取得案件編號 ViewForm
 */
@ApiModel(description = "取得案件編號  ViewForm")
public class GetCaseIdActionViewForm extends BaseViewForm {

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
