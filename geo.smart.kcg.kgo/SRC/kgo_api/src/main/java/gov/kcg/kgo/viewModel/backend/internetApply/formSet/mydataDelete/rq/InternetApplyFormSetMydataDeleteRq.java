package gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataDelete.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路申辦-表單設定-MYDAYA維護-資料集刪除 rq")
public class InternetApplyFormSetMydataDeleteRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "服務案件編號")
	private String caseSetId;

	@ApiModelProperty(value = "MYDATA資料集代碼")
	private String mydataId;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getMydataId() {
		return mydataId;
	}

	public void setMydataId(String mydataId) {
		this.mydataId = mydataId;
	}

}
