package gov.kcg.kgo.viewModel.backend.caseManagement.query.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件維護管理-案件查詢 rq")
public class CaseManagementQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關分類代碼")
	private Integer organId;

	@ApiModelProperty(value = "權責機關分類代碼")
	private String ownerOrganId;

	@ApiModelProperty(value = "案件種類ID")
	private String caseSetId;

	@ApiModelProperty(value = "案件名稱")
	private String caseSetName;

	@ApiModelProperty(value = "管理者帳號")
	private String manager;

	public Integer getOrganId() {
		return organId;
	}

	public void setOrganId(Integer organId) {
		this.organId = organId;
	}

	public String getOwnerOrganId() {
		return ownerOrganId;
	}

	public void setOwnerOrganId(String ownerOrganId) {
		this.ownerOrganId = ownerOrganId;
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

}
