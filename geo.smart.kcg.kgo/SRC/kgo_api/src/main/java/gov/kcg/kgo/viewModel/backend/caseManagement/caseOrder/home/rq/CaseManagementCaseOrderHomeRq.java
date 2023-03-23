package gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.home.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "服務案件清單-案件排序-初始畫面 rq")
public class CaseManagementCaseOrderHomeRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關分類代碼")
	private Integer organId;

	public Integer getOrganId() {
		return organId;
	}

	public void setOrganId(Integer organId) {
		this.organId = organId;
	}

}
