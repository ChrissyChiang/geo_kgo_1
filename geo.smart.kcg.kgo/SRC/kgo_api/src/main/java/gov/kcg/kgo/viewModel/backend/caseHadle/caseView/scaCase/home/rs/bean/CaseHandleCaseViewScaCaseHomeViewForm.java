package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.scaCase.home.rs.bean;

import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseViewCaseHistoryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台案件處理-案件檢視-服務案件新增(SCA)案件檢視-初始畫面 View Form
 */
@ApiModel(description = "後台案件處理-案件檢視-服務案件新增(SCA)案件檢視-初始畫面 View Form")
public class CaseHandleCaseViewScaCaseHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 案件編號 **/
	@ApiModelProperty(value = "案件編號")
	private String caseId;

	/** 申請機關 **/
	@ApiModelProperty(value = "申請機關")
	private String organName;

	/** 申請科室 **/
	@ApiModelProperty(value = "申請科室")
	private String unitName;

	/** 案件編號 **/
	@ApiModelProperty(value = "申請人")
	private String applicant;

	/** 聯絡電話 **/
	@ApiModelProperty(value = "聯絡電話")
	private String phone;

	/** 電子郵件 **/
	@ApiModelProperty(value = "電子郵件")
	private String email;

	/** 審核主管 **/
	@ApiModelProperty(value = "審核主管")
	private String supervisor;

	/** 案件名稱 **/
	@ApiModelProperty(value = "案件名稱")
	private String caseName;

	/** 整合流程分類 **/
	@ApiModelProperty(value = "整合流程分類")
	private String processClassification;

	/** 站外連結方式 **/
	@ApiModelProperty(value = "站外連結方式")
	private String linkType;

	/** 站外連結網址 **/
	@ApiModelProperty(value = "站外連結網址")
	private String linkUrl;

	/** 機關分類 **/
	@ApiModelProperty(value = "機關分類")
	private String organClassify;

	/** 角色分類 **/
	@ApiModelProperty(value = "角色分類")
	private String roleClassify;

	/** 服務分類 **/
	@ApiModelProperty(value = "服務分類")
	private String serviceCalssify;

	/** 權責機關 **/
	@ApiModelProperty(value = "權責機關")
	private String ownerOrgan;

	/** 案件管理者 **/
	@ApiModelProperty(value = "案件管理者")
	private String managerName;

	/** 服務啟用CheckBox集合 **/
	@ApiModelProperty(value = "服務啟用CheckBox集合")
	private List<CheckBox> checkBoxList;

	/** 歷程圖 **/
	@ApiModelProperty(value = "歷程圖")
	private String image;

	/** 處理歷程 **/
	@ApiModelProperty(value = "處理歷程")
	private List<CaseHandleCaseViewCaseHistoryDataGrid> historyData;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getProcessClassification() {
		return processClassification;
	}

	public void setProcessClassification(String processClassification) {
		this.processClassification = processClassification;
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

	public String getOrganClassify() {
		return organClassify;
	}

	public void setOrganClassify(String organClassify) {
		this.organClassify = organClassify;
	}

	public String getRoleClassify() {
		return roleClassify;
	}

	public void setRoleClassify(String roleClassify) {
		this.roleClassify = roleClassify;
	}

	public String getServiceCalssify() {
		return serviceCalssify;
	}

	public void setServiceCalssify(String serviceCalssify) {
		this.serviceCalssify = serviceCalssify;
	}

	public String getOwnerOrgan() {
		return ownerOrgan;
	}

	public void setOwnerOrgan(String ownerOrgan) {
		this.ownerOrgan = ownerOrgan;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public List<CheckBox> getCheckBoxList() {
		return checkBoxList;
	}

	public void setCheckBoxList(List<CheckBox> checkBoxList) {
		this.checkBoxList = checkBoxList;
	}

	public List<CaseHandleCaseViewCaseHistoryDataGrid> getHistoryData() {
		return historyData;
	}

	public void setHistoryData(List<CaseHandleCaseViewCaseHistoryDataGrid> historyData) {
		this.historyData = historyData;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
