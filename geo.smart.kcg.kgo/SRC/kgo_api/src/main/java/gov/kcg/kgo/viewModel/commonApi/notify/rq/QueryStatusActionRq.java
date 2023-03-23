package gov.kcg.kgo.viewModel.commonApi.notify.rq;

import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查詢案件狀態 rq")
public class QueryStatusActionRq extends ApiCityCommonRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "案件編號")
	private String caseId;
	
	@ApiModelProperty(value = "申請者")
	private String applyUser;
	
	@ApiModelProperty(value = "手機號碼")
	private String cellPhone;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
}
