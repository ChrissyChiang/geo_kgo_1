package gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataColumnComboBox.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路申辦-表單設定-欄位維護-MYDATA_COLUMN下拉式選單rq")
public class InternetApplyFormSetMyDataColumnComboBoxRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "MyData資料集ID")
	private String myDataId;

	public String getMyDataId() {
		return myDataId;
	}

	public void setMyDataId(String myDataId) {
		this.myDataId = myDataId;
	}

}
