package gov.kcg.kgo.viewModel.backend.caseManagement.caseSave.rq;

import gov.kcg.kgo.geomodel.CaseSetPayModel;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

@ApiModel(description = "服務案件清單-案件維護-儲存 rq")
public class CaseManagementCaseSaveRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "服務案件編號")
	private String caseSetId;

	@ApiModelProperty(value = "案件服務對象")
	private String serviceTo;

	@ApiModelProperty(value = "案件名稱")
	private String caseSetName;

	@ApiModelProperty(value = "作業流程")
	private String caseType;

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

	@ApiModelProperty(value = "權責機關代碼")
	private String ownerOrgan;
	
	@ApiModelProperty(value = "作業流程 - 動態流程flowId")
	private String flowId;

	@ApiModelProperty(value = "是否啟用線上服務 1-啟用，0-不啟用",required = true)
	private Integer isOpenForOrgan;

	/**GEO 20211109 add */
	@ApiModelProperty(value = "是否啟用機關審核表單 1-啟用，0-不啟用",required = true)
	private Integer isOpenOrganForm;

	/**GEO 20211115 add 跨機關檢視*/
	@ApiModelProperty(value = "是否啟用跨機關檢視",required = true)
	private Boolean isAvailableCrossReview;

	@ApiModelProperty(value = "可跨機關檢視的機關代碼，以 「,」 隔開")
	private String crossReview;

	/**
	 * 多人的話以 , 隔開
	 */
	@ApiModelProperty(value = "案件管理者代碼(點選選擇人員按鈕，顯示時一人以上後串『,』ＥＸ：u0001,u0002)")
	private String managerId;

	/**
	 * 多項的話以 , 隔開
	 */
	@ApiModelProperty(value = "啟用服務項目") //roy
	private String caseSetType;
	
	@ApiModelProperty(value = "是否啟動流程,true/false") //roy 暫定為是否審核 checkbox
	private Boolean activeFlow;
	
	@ApiModelProperty(value = "案件類型, 一般服務: common , 場地預約: site , 活動預約: activity , 退費服務: refund ") // Roy
	private String caseSetCategory;		

	@ApiModelProperty(value="費用設定")
	private CaseSetPayModel paySetting;

	@ApiModelProperty(value = "城市幣啟用")
	private Boolean cityCoin;

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

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
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

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getOwnerOrgan() {
		return ownerOrgan;
	}

	public void setOwnerOrgan(String ownerOrgan) {
		this.ownerOrgan = ownerOrgan;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getCaseSetType() {
		return caseSetType;
	}

	public void setCaseSetType(String caseSetType) {
		this.caseSetType = caseSetType;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public Integer getIsOpenForOrgan() {
		return isOpenForOrgan;
	}

	public void setIsOpenForOrgan(Integer isOpenForOrgan) {
		this.isOpenForOrgan = isOpenForOrgan;
	}

	public Integer getIsOpenOrganForm() {
		return isOpenOrganForm;
	}

	public void setIsOpenOrganForm(Integer isOpenOrganForm) {
		this.isOpenOrganForm = isOpenOrganForm;
	}

	public Boolean getAvailableCrossReview() {
		return isAvailableCrossReview;
	}

	public void setAvailableCrossReview(Boolean availableCrossReview) {
		isAvailableCrossReview = availableCrossReview;
	}

	public String getCrossReview() {
		return crossReview;
	}

	public void setCrossReview(String crossReview) {
		this.crossReview = crossReview;
	}
	
	public Boolean getIsAvailableCrossReview() {
		return isAvailableCrossReview;
	}

	public void setIsAvailableCrossReview(Boolean isAvailableCrossReview) {
		this.isAvailableCrossReview = isAvailableCrossReview;
	}

	public Boolean getActiveFlow() {
		return activeFlow;
	}

	public void setActiveFlow(Boolean activeFlow) {
		this.activeFlow = activeFlow;
	}
	
	public String getCaseSetCategory() {
		return caseSetCategory;
	}

	public void setCaseSetCategory(String caseSetCategory) {
		this.caseSetCategory = caseSetCategory;
	}

	public CaseSetPayModel getPaySetting() {
		return paySetting;
	}

	public void setPaySetting(CaseSetPayModel paySetting) {
		this.paySetting = paySetting;
	}

	public Boolean getCityCoin() {
		return cityCoin;
	}

	public void setCityCoin(Boolean cityCoin) {
		this.cityCoin = cityCoin;
	}

	@Override
	public String toString() {
		return "CaseManagementCaseSaveRq{" +
				"caseSetId='" + caseSetId + '\'' +
				", serviceTo='" + serviceTo + '\'' +
				", caseSetName='" + caseSetName + '\'' +
				", caseType='" + caseType + '\'' +
				", caseFlowType='" + caseFlowType + '\'' +
				", linkType='" + linkType + '\'' +
				", linkUrl='" + linkUrl + '\'' +
				", organ='" + organ + '\'' +
				", role='" + role + '\'' +
				", service='" + service + '\'' +
				", ownerOrgan='" + ownerOrgan + '\'' +
				", flowId='" + flowId + '\'' +
				", isOpenForOrgan=" + isOpenForOrgan +
				", managerId='" + managerId + '\'' +
				", caseSetType='" + caseSetType + '\'' +
				", cityCoin='" + cityCoin + '\'' +
				'}';
	}
}

