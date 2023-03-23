package gov.kcg.kgo.viewModel.backend.accountManagement.accountEdit.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "帳號權限管理-帳號搜尋 rq")
public class AccountManagementEditRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 機關名稱
	 */
	@ApiModelProperty(value = "機關名稱代碼 (必填)",required = true)
	private String organId;

	/**
	 * 單位名稱
	 */
	@ApiModelProperty(value = "單位名稱代碼 (必填)",required = true)
	private String unitId;

	/**
	 * 單位名稱
	 */
	@ApiModelProperty(value = "角色代碼集合")
	private String[] roleId;
	
	/**
	 * 使用者名稱
	 */
	@ApiModelProperty(value = "使用者名稱")
	private String name;

	/**
	 * 使用者帳號
	 */
	@ApiModelProperty(value = "使用者帳號 (必填)",required = true)
	private String userId;

	/**
	 * 電子郵件
	 */
	@ApiModelProperty(value = "電子郵件 (必填)",required = true)
	private String email;

	/**
	 * 公務電子郵件
	 */
	@ApiModelProperty(value = "公務電子郵件 (必填)",required = true)
	private String publicEmail;

	/**
	 * 電話
	 */
	@ApiModelProperty(value = "電話")
	private String tel;

	/**GEO 20211115 add 跨機關檢視*/
	@ApiModelProperty(value = "是否開啟跨機關檢視",required = true)
	private Boolean isAvailableCrossReview;

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String[] getRoleId() {
		return roleId;
	}

	public void setRoleId(String[] roleId) {
		this.roleId = roleId;
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
