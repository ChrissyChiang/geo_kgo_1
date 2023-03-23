package gov.kcg.kgo.viewModel.backend.accountManagement.accountEditHome.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.accountManagement.accountQuery.rs.bean.AccountManagementQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

/**
 * 帳號權限管理-帳號維護(新增/修改)-畫面初始 View Form
 */
@ApiModel(description = "帳號權限管理-帳號維護(新增/修改)-畫面初始 View Form")
public class AccountManagementEditHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/**
	 * 機關名稱 ComboBox
	 */
	@ApiModelProperty(value = "機關comboBox資料")
	private ComboBox organComboBox;

	/**
	 * 單位名稱 ComboBox
	 */
	@ApiModelProperty(value = "單位comboBox資料")
	private ComboBox unitComboBox;

	/**
	 * CheckBox set
	 */
	private List<CheckBox> checkBoxList;

	/**
	 * 使用者名稱
	 */
	@ApiModelProperty(value = "使用者名稱")
	private String name;

	/**
	 * 使用者帳號
	 */
	@ApiModelProperty(value = "使用者帳號 (必填)")
	private String userId;

	/**
	 * 電子郵件
	 */
	@ApiModelProperty(value = "電子郵件 (必填)")
	private String email;

	/**
	 * 公務電子郵件
	 */
	@ApiModelProperty(value = "公務電子郵件 (必填)")
	private String publicEmail;

	/**
	 * 電話
	 */
	@ApiModelProperty(value = "電話")
	private String tel;

	/**GEO 20211115 add 跨機關檢視*/
	@ApiModelProperty(value = "是否啟用跨機關檢視")
	private Boolean isAvailableCrossReview;

	public ComboBox getOrganComboBox() {
		return organComboBox;
	}

	public void setOrganComboBox(ComboBox organComboBox) {
		this.organComboBox = organComboBox;
	}

	public ComboBox getUnitComboBox() {
		return unitComboBox;
	}

	public void setUnitComboBox(ComboBox unitComboBox) {
		this.unitComboBox = unitComboBox;
	}

	public List<CheckBox> getCheckBoxList() {
		return checkBoxList;
	}

	public void setCheckBoxList(List<CheckBox> checkBoxList) {
		this.checkBoxList = checkBoxList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPublicEmail() {
		return publicEmail;
	}

	public void setPublicEmail(String publicEmail) {
		this.publicEmail = publicEmail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Boolean getAvailableCrossReview() {
		return isAvailableCrossReview;
	}

	public void setAvailableCrossReview(Boolean availableCrossReview) {
		isAvailableCrossReview = availableCrossReview;
	}
}
