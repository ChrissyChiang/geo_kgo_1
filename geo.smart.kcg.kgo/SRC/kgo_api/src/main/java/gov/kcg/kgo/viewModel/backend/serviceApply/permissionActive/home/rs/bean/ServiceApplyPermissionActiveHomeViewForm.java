package gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 服務申請-權限開通申請--初始畫面 View Form
 */
@ApiModel(description = "服務申請-權限開通申請--初始畫面 View Form")
public class ServiceApplyPermissionActiveHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關")
	private String organ;

	@ApiModelProperty(value = "機關名稱")
	private String organName;

	@ApiModelProperty(value = "科室")
	private String unit;

	@ApiModelProperty(value = "科室名稱")
	private String unitName;

	@ApiModelProperty(value = "申請人")
	private String applyUser;

	@ApiModelProperty(value = "申請人名稱")
	private String applyUserName;

	@ApiModelProperty(value = "申請角色")
	private List<CheckBox> applyRoleCheckBox;

	@ApiModelProperty(value = "審核主管")
	private ComboBox reviewerComboBox;

	@ApiModelProperty(value = "系統管理者主管")
	private ComboBox reviewer2ComboBox;

	/** GEO 20211115 add 非市府員工登入後台 */
	@ApiModelProperty(value = "Email")
	private String email;


	/** GEO 20211115 add 非市府員工登入後台 */
	@ApiModelProperty(value = "公務Email")
	private String publicEmail;

	/** GEO 20211115 add 非市府員工登入後台 */
	@ApiModelProperty(value = "電話")
	private String phoneNumber;

	/** GEO 20211115 add 非市府員工登入後台 */
	@ApiModelProperty(value = "登入方式")
	private String loginType;

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	public List<CheckBox> getApplyRoleCheckBox() {
		return applyRoleCheckBox;
	}

	public void setApplyRoleCheckBox(List<CheckBox> applyRoleCheckBox) {
		this.applyRoleCheckBox = applyRoleCheckBox;
	}

	public ComboBox getReviewerComboBox() {
		return reviewerComboBox;
	}

	public void setReviewerComboBox(ComboBox reviewerComboBox) {
		this.reviewerComboBox = reviewerComboBox;
	}

	public ComboBox getReviewer2ComboBox() {
		return reviewer2ComboBox;
	}

	public void setReviewer2ComboBox(ComboBox reviewer2ComboBox) {
		this.reviewer2ComboBox = reviewer2ComboBox;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getPublicEmail() {
		return publicEmail;
	}

	public void setPublicEmail(String publicEmail) {
		this.publicEmail = publicEmail;
	}
}
