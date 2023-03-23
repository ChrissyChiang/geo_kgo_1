package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "申請表單初始 rq")
public class ApplyFormValidTimeRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	 
	@ApiModelProperty(value = "場地預約時段主鍵")
	private String siteRentTimeId;

	public String getSiteRentTimeId() {
		return siteRentTimeId;
	}

	public void setSiteRentTimeId(String siteRentTimeId) {
		this.siteRentTimeId = siteRentTimeId;
	}
}
