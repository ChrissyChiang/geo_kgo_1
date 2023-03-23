package gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.home.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 服務申請-服務案件申請-初始畫面 View Form
 */
@ApiModel(description = "服務申請-服務案件申請-初始畫面 View Form")
public class ServiceApplyServiceCaseHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關")
	private String applyOrgan;

	@ApiModelProperty(value = "機關名稱")
	private String applyOrganName;

	@ApiModelProperty(value = "科室")
	private String applyUnit;

	@ApiModelProperty(value = "科室名稱")
	private String applyUnitName;

	@ApiModelProperty(value = "申請人")
	private String applyUser;

	@ApiModelProperty(value = "申請人名稱")
	private String applyUserName;

	@ApiModelProperty(value = "審核主管")
	private ComboBox reviewerComboBox;

	@ApiModelProperty(value = "案件服務對象")
	private ComboBox serviceToComboBox;

	@ApiModelProperty(value = "作業流程")
	private ComboBox caseTypeComboBox;

	@ApiModelProperty(value = "作業流程分類")
	private ComboBox caseFlowTypeComboBox;

	@ApiModelProperty(value = "站外連結方式")
	private ComboBox linkTypeComboBox;

	@ApiModelProperty(value = "機關分類")
	private ComboBox organComboBox;

	@ApiModelProperty(value = "角色分類")
	private ComboBox roleComboBox;

	@ApiModelProperty(value = "服務分類")
	private ComboBox serviceComboBox;

	@ApiModelProperty(value = "權責機關(案件管理者機關)")
	private ComboBox ownerOrganComboBox;

	@ApiModelProperty(value = "服務啟用")
	private List<CheckBox> caseSetTypeCheckList;

	public String getApplyOrgan() {
		return applyOrgan;
	}

	public void setApplyOrgan(String applyOrgan) {
		this.applyOrgan = applyOrgan;
	}

	public String getApplyOrganName() {
		return applyOrganName;
	}

	public void setApplyOrganName(String applyOrganName) {
		this.applyOrganName = applyOrganName;
	}

	public String getApplyUnit() {
		return applyUnit;
	}

	public void setApplyUnit(String applyUnit) {
		this.applyUnit = applyUnit;
	}

	public String getApplyUnitName() {
		return applyUnitName;
	}

	public void setApplyUnitName(String applyUnitName) {
		this.applyUnitName = applyUnitName;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public ComboBox getReviewerComboBox() {
		return reviewerComboBox;
	}

	public void setReviewerComboBox(ComboBox reviewerComboBox) {
		this.reviewerComboBox = reviewerComboBox;
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

	public List<CheckBox> getCaseSetTypeCheckList() {
		return caseSetTypeCheckList;
	}

	public void setCaseSetTypeCheckList(List<CheckBox> caseSetTypeCheckList) {
		this.caseSetTypeCheckList = caseSetTypeCheckList;
	}
}
