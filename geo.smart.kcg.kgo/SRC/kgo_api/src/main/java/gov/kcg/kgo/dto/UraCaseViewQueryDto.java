package gov.kcg.kgo.dto;

import gov.kcg.kgo.model.KgoOrgan;
import gov.kcg.kgo.model.KgoUnit;
import gov.kcg.kgo.model.KgoUraApply;
import gov.kcg.kgo.model.KgoUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@ApiModel(description = "後台案件處理-URA案件檢視資料")
@Entity
public class UraCaseViewQueryDto {

	/** 案件編號 */
	@Id
	@ApiModelProperty(notes = "案件編號")
	@Column(name = "CASE_ID")
	private String caseId;

	/** 申請人所屬的機關名稱 **/
	@ApiModelProperty(notes = "申請人所屬的機關名稱")
	@Column(name = "APPLY_ORGAN")
	private String applyOrgan;

	/** 申請人所屬的科室名稱 */
	@ApiModelProperty(notes = "申請人所屬的科室名稱")
	@Column(name = "APPLY_UNIT")
	private String applyUnit;

	/** 申請人姓名 */
	@ApiModelProperty(notes = "申請人姓名")
	@Column(name = "APPLY_USER")
	private String applyUser;

	/** 聯絡電話 */
	@ApiModelProperty(notes = "聯絡電話")
	@Column(name = "PHONE")
	private String Phone;

	/** 電子郵件 */
	@ApiModelProperty(notes = "電子郵件")
	@Column(name = "EMAIL")
	private String Email;

	/** 申請人角色 (逗號相隔) */
	@ApiModelProperty(notes = "申請人角色(逗號相隔)")
	@Column(name = "APPLY_ROLE")
	private String applyRole;

	/** 案件歷程編號 */
	@ApiModelProperty(notes = "案件歷程編號")
	@Column(name = "PROCESS_ID")
	private String processId;

	public UraCaseViewQueryDto() {
	}

	public UraCaseViewQueryDto(KgoUraApply ua, KgoOrgan o, KgoUnit un, KgoUser us) {
		if (null != ua) {
			this.caseId = ua.getCaseId();
			this.Phone = ua.getPhone();
			this.applyRole = ua.getApplyRole();
			this.processId = ua.getProcessId();
			this.Email = ua.getEmail();
		}
		if (null != un) {
			this.applyUnit = un.getUnitName();
		}
		if (null != o) {
			this.applyOrgan = o.getOrganName();
		}
		if (null != us) {
			this.applyUser = us.getName();
		}
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getApplyOrgan() {
		return applyOrgan;
	}

	public void setApplyOrgan(String applyOrgan) {
		this.applyOrgan = applyOrgan;
	}

	public String getApplyUnit() {
		return applyUnit;
	}

	public void setApplyUnit(String applyUnit) {
		this.applyUnit = applyUnit;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getApplyRole() {
		return applyRole;
	}

	public void setApplyRole(String applyRole) {
		this.applyRole = applyRole;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
}
