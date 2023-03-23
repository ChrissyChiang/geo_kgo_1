package gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataSave.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路申辦-表單設定-MYDAYA維護-資料集新增 rq")
public class InternetApplyFormSetMydataSaveRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "服務案件編號")
	private String caseSetId;

	@ApiModelProperty(value = "MYDATA資料集代碼")
	private String mydataId;

	@ApiModelProperty(value = "服務代碼")
	private String clientId;

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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
