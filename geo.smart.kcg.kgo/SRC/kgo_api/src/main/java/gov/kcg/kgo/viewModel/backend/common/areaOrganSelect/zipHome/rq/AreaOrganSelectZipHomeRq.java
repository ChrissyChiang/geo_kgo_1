package gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.zipHome.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "共用-區機關選擇-鄉鎮選擇-初始畫面 rq")
public class AreaOrganSelectZipHomeRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "區機關代碼")
	private String countyId;

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

}
