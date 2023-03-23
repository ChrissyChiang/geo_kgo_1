package gov.kcg.kgo.viewModel.backend.caseManagement.stateUpdate.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "服務案件清單-案件狀態更改 rq")
public class CaseManagementStatusUpdateRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件種類ID")
	private List<String> caseSetIdList;

	/**
	 * Accept-受理中, On-已上架, Off-未上架
	 * 
	 */
	@ApiModelProperty(value = "案件狀態")
	private String status;

	public List<String> getCaseSetIdList() {
		return caseSetIdList;
	}

	public void setCaseSetIdList(List<String> caseSetIdList) {
		this.caseSetIdList = caseSetIdList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
