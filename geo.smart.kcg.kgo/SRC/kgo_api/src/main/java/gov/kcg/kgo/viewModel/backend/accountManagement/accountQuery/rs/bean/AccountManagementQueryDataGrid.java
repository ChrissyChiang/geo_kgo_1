package gov.kcg.kgo.viewModel.backend.accountManagement.accountQuery.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

/**
 * 帳號權限管理-帳號搜尋
 */
@ApiModel(description = "帳號權限管理-帳號搜尋")
public class AccountManagementQueryDataGrid {

	/**
	 * 使用者帳號
	 */
	@ApiModelProperty(notes = "使用者帳號")
	private String userId;

	/**
	 * 使用者名稱
	 */
	@ApiModelProperty(notes = "使用者名稱")
	private String name;

	/**
	 * 單位名稱
	 */
	@ApiModelProperty(notes = "單位名稱")
	private String unitName;

	/**
	 * 機關名稱
	 */
	@ApiModelProperty(notes = "機關名稱")
	private String organName;

	/**
	 * GEO 20211125 add 跨機關檢視
	 */
	@ApiModelProperty(value = "是否可跨機關檢視")
	private Boolean IsAvailableCrossReview;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public Boolean getAvailableCrossReview() {
		return IsAvailableCrossReview;
	}

	public void setAvailableCrossReview(Boolean availableCrossReview) {
		IsAvailableCrossReview = availableCrossReview;
	}
}
