package gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataComboBox.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路申辦-表單設定-MYDAYA維護-取得Mydata資料集下拉式選單 rq")
public class InternetApplyFormSetMydataComboBoxRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Mydata服務ID")
	private String clientId;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
