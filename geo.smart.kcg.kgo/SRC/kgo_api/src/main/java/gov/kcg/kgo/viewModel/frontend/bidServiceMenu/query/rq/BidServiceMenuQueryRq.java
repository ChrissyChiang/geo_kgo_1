package gov.kcg.kgo.viewModel.frontend.bidServiceMenu.query.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "申辦服務選單-申辦案件數查詢 rq")
public class BidServiceMenuQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主類別代碼")
	private String mainType;

	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

}
