package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "服務退費比率")
public class GeoCaseSetRefundModel extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "起日")
	private Integer fromDays;

	@ApiModelProperty(value = "迄日")
	private Integer endDays;
	
	@ApiModelProperty(value = "退費比率")
	private Integer refundRatio;

	public Integer getFromDays() {
		return fromDays;
	}

	public void setFromDays(Integer fromDays) {
		this.fromDays = fromDays;
	}

	public Integer getEndDays() {
		return endDays;
	}

	public void setEndDays(Integer endDays) {
		this.endDays = endDays;
	}

	public Integer getRefundRatio() {
		return refundRatio;
	}

	public void setRefundRatio(Integer refundRatio) {
		this.refundRatio = refundRatio;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
