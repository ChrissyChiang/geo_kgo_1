package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "服務查詢 rq")
public class caseSetQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "類別")
	private String casesetCategory;

	@ApiModelProperty(value = "狀態")
	private String status;

	public String getCasesetCategory() {
		return casesetCategory;
	}

	public void setCasesetCategory(String casesetCategory) {
		this.casesetCategory = casesetCategory;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
