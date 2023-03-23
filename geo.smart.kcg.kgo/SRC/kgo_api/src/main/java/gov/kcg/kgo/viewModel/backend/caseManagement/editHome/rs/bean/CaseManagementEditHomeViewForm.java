package gov.kcg.kgo.viewModel.backend.caseManagement.editHome.rs.bean;

import java.util.List;
import java.util.Map;

import gov.kcg.kgo.geomodel.CaseSetPayModel;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.CategoryType;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

/**
 * 服務案件清單-案件維護-初始畫面 View Form
 */
@ApiModel(description = "服務案件清單-案件維護-初始畫面 View Form")
public class CaseManagementEditHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "服務案件編號")
	private String caseSetId;

	@ApiModelProperty(value = "案件服務對象")
	private ComboBox serviceToComboBox;

	@ApiModelProperty(value = "作業流程")
	private ComboBox caseTypeComboBox;

	@ApiModelProperty(value = "案件名稱")
	private String caseSetName;

	@ApiModelProperty(value = "作業流程分類")
	private ComboBox caseFlowTypeComboBox;

	@ApiModelProperty(value = "站外連結方式")
	private ComboBox linkTypeComboBox;

	@ApiModelProperty(value = "站外連結網址")
	private String linkUrl;

	@ApiModelProperty(value = "機關分類")
	private ComboBox organComboBox;

	@ApiModelProperty(value = "角色分類")
	private ComboBox roleComboBox;

	@ApiModelProperty(value = "服務分類")
	private ComboBox serviceComboBox;

	@ApiModelProperty(value = "權責機關(案件管理者機關)")
	private ComboBox ownerOrganComboBox;

	@ApiModelProperty(value = "權責機關名稱(案件管理者機關)")
	private String ownerOrganLabel;

	@ApiModelProperty(value = "權責機關代碼(案件管理者機關)")
	private String ownerOrganValue;

	@ApiModelProperty(value = "案件管理者代碼(點選選擇人員按鈕，顯示時一人以上後串『,』ＥＸ：u0001,u0002)")
	private String managerId;

	@ApiModelProperty(value = "案件管理者名稱(點選選擇人員按鈕，顯示時一人以上後串『、』ＥＸ：王小明、李曉芳)")
	private String managerName;

	@ApiModelProperty(value = "服務啟用")
	private List<CheckBox> serverCheckList;

	@ApiModelProperty(value = "城市幣任務清單")
	private List<CityCoinTask> taskList;

	/**GEO 20211030 add */
	@ApiModelProperty(value="是否開啟府內線上服務")
	private Integer isOpenForOrgan;

	/**GEO 20211109 add */
	@ApiModelProperty(value = "是否啟用機關審核表單 1-啟用，0-不啟用")
	private Integer isOpenOrganForm;

	/**GEO 20211115 add */
	@ApiModelProperty(value = "是否啟用跨機關檢視")
	private Boolean isAvailableCrossReview;

	@ApiModelProperty(value = "跨機關檢視")
	private ComboBox crossReviewComboBox;

	@ApiModelProperty(value = "是否啟動流程,true/false") //roy 暫定為是否審核 checkbox
	private Boolean activeFlow;

	@ApiModelProperty(value = "案件類型") // Roy
	private String caseSetCategory;

	@ApiModelProperty(value = "案件類型分類, 一般服務: common , 場地預約: site , 活動預約: activity , 退費服務: refund ")
	private List<Map<String,String>> categoryType ;

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

	public ComboBox getServiceToComboBox() {
		return serviceToComboBox;
	}

	public void setServiceToComboBox(ComboBox serviceToComboBox) {
		this.serviceToComboBox = serviceToComboBox;
	}

	public ComboBox getCaseTypeComboBox() {
		return caseTypeComboBox;
	}

	public void setCaseTypeComboBox(ComboBox caseTypeComboBox) {
		this.caseTypeComboBox = caseTypeComboBox;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public ComboBox getCaseFlowTypeComboBox() {
		return caseFlowTypeComboBox;
	}

	public void setCaseFlowTypeComboBox(ComboBox caseFlowTypeComboBox) {
		this.caseFlowTypeComboBox = caseFlowTypeComboBox;
	}

	public ComboBox getLinkTypeComboBox() {
		return linkTypeComboBox;
	}

	public void setLinkTypeComboBox(ComboBox linkTypeComboBox) {
		this.linkTypeComboBox = linkTypeComboBox;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public ComboBox getOrganComboBox() {
		return organComboBox;
	}

	public void setOrganComboBox(ComboBox organComboBox) {
		this.organComboBox = organComboBox;
	}

	public ComboBox getRoleComboBox() {
		return roleComboBox;
	}

	public void setRoleComboBox(ComboBox roleComboBox) {
		this.roleComboBox = roleComboBox;
	}

	public ComboBox getServiceComboBox() {
		return serviceComboBox;
	}

	public void setServiceComboBox(ComboBox serviceComboBox) {
		this.serviceComboBox = serviceComboBox;
	}

	public ComboBox getOwnerOrganComboBox() {
		return ownerOrganComboBox;
	}

	public void setOwnerOrganComboBox(ComboBox ownerOrganComboBox) {
		this.ownerOrganComboBox = ownerOrganComboBox;
	}

	public String getOwnerOrganLabel() {
		return ownerOrganLabel;
	}

	public void setOwnerOrganLabel(String ownerOrganLabel) {
		this.ownerOrganLabel = ownerOrganLabel;
	}

	public String getOwnerOrganValue() {
		return ownerOrganValue;
	}

	public void setOwnerOrganValue(String ownerOrganValue) {
		this.ownerOrganValue = ownerOrganValue;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public List<CheckBox> getServerCheckList() {
		return serverCheckList;
	}

	public void setServerCheckList(List<CheckBox> serverCheckList) {
		this.serverCheckList = serverCheckList;
	}

	public List<CityCoinTask> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<CityCoinTask> taskList) {
		this.taskList = taskList;
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

	public ComboBox getCrossReviewComboBox() {
		return crossReviewComboBox;
	}

	public void setCrossReviewComboBox(ComboBox crossReviewComboBox) {
		this.crossReviewComboBox = crossReviewComboBox;
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

	public List<Map<String, String>> getCategoryType(){return categoryType;}

	public void setCategoryType(List<Map<String, String>> categoryType) {this.categoryType = categoryType;}

	public CaseSetPayModel getPaySetting() {return paySetting;}

	public void setPaySetting(CaseSetPayModel paySetting) {this.paySetting = paySetting;}

	public Boolean getCityCoin() {
		return cityCoin;
	}

	public void setCityCoin(Boolean cityCoin) {
		this.cityCoin = cityCoin;
	}
}
