package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "線上場地租借-每日時段查詢 rq")
public class CaseSetRentTimeQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "單日租借ID",required = true)
	private String rentDateId;

	public String getRentDateId() {
		return rentDateId;
	}

	public void setRentDateId(String rentDateId) {
		this.rentDateId = rentDateId;
	}
}
