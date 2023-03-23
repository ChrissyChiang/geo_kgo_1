package gov.kcg.kgo.viewModel.testApi.homeAction.rq;


import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "測試功能首頁 rq")
public class TestHomeActionRq extends ApiRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "角色代碼")
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
