package gov.kcg.kgo.viewModel.backend.caseManagement.edit.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "服務案件清單-案件維護(新增/修改) rq")
public class CaseManagementEditRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "服務案件編號")
	private String caseSetId;

	@ApiModelProperty(value = "案件服務對象")
	private String serviceTo;

	@ApiModelProperty(value = "作業流程")
	private String caseType;

	@ApiModelProperty(value = "案件名稱")
	private String caseSetName;

	@ApiModelProperty(value = "作業流程分類")
	private String caseFlowType;

	@ApiModelProperty(value = "站外連結方式")
	private String linkType;

	@ApiModelProperty(value = "站外連結網址")
	private String linkUrl;

	@ApiModelProperty(value = "機關分類")
	private String organ;

	@ApiModelProperty(value = "角色分類")
	private String role;

	@ApiModelProperty(value = "服務分類")
	private String service;

	@ApiModelProperty(value = "案件管理者")
	private String managerName;

	@ApiModelProperty(value = "服務啟用")
	private List<String> serverCheckList;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getServiceTo() {
		return serviceTo;
	}

	public void setServiceTo(String serviceTo) {
		this.serviceTo = serviceTo;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getCaseFlowType() {
		return caseFlowType;
	}

	public void setCaseFlowType(String caseFlowType) {
		this.caseFlowType = caseFlowType;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public List<String> getServerCheckList() {
		return serverCheckList;
	}

	public void setServerCheckList(List<String> serverCheckList) {
		this.serverCheckList = serverCheckList;
	}

}
