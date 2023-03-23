package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "帳號權限管理-帳號維護(新增/修改)")
@Entity
public class AccountManagementEditDto {

	/**
	 * 機關名稱
	 */
	@ApiModelProperty(notes = "機關名稱")
	@Column(name = "ORGAN_NAME")
	private String organName;

	/**
	 * 單位名稱
	 */
	@ApiModelProperty(notes = "單位名稱")
	@Column(name = "UNIT_NAME")
	private String unitName;

	/**
	 * 使用者名稱
	 */
	@ApiModelProperty(notes = "使用者名稱")
	@Column(name = "NAME")
	private String name;

	/**
	 * 使用者帳號
	 */
	@Id
	@ApiModelProperty(notes = "使用者帳號")
	@Column(name = "USER_ID")
	private String userId;

	/**
	 * 電子郵件
	 */
	@ApiModelProperty(notes = "電子郵件")
	@Column(name = "EMAIL")
	private String email;

	/**
	 * 公務電子郵件
	 */
	@ApiModelProperty(notes = "公務電子郵件")
	@Column(name = "PUBLIC_EMAIL")
	private String publicEmail;

	/**
	 * 電話
	 */
	@Column(name = "TEL")
	@ApiModelProperty(notes = "電話")
	private String tel;

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

}
