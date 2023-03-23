package gov.kcg.kgo.viewModel.frontend.bidCaseList.query.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "申辦案件清單-申辦案件資料查詢 rq")
public class BidCaseListQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主類別代碼")
	private String mainType;

	@ApiModelProperty(value = "被選擇類別代碼")
	private String value;

	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
