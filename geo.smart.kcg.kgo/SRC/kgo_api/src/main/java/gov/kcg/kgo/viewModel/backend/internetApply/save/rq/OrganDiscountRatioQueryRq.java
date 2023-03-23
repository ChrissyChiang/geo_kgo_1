package gov.kcg.kgo.viewModel.backend.internetApply.save.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

@ApiModel(description = "機關退費比率查詢 rq")
public class OrganDiscountRatioQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關編號")
	private String organId;

	@ApiModelProperty(value = "折扣比率ID")
	private Long organDiscountRatioId;

	@ApiModelProperty(value = "折扣比例")
	private Integer percent;

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Long getOrganDiscountRatioId() {return organDiscountRatioId;}

	public void setOrganDiscountRatioId(Long organDiscountRatioId) {this.organDiscountRatioId = organDiscountRatioId;}

	public Integer getPercent() {return percent;}

	public void setPercent(Integer percent) {this.percent = percent;}
}
