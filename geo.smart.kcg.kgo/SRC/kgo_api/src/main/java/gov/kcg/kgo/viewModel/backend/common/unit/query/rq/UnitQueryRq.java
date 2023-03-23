package gov.kcg.kgo.viewModel.backend.common.unit.query.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "共用-機關科室查詢 rq")
public class UnitQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關代碼")
	private String organId;

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
}
