package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.uraCase.home.rs.bean;

import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseViewCaseHistoryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台案件處理-案件檢視-系統權限申請(URA)案件檢視-初始畫面 View Form
 */
@ApiModel(description = "後台案件處理-案件檢視-系統權限申請(URA)案件檢視-初始畫面 View Form")
public class CaseHandleCaseViewUraCaseHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 案件編號 **/
	@ApiModelProperty(value = "案件編號")
	private String caseId;

	/** 機關 **/
	@ApiModelProperty(value = "機關")
	private String organName;

	/** 科室 **/
	@ApiModelProperty(value = "科室")
	private String unitName;

	/** 申請人 **/
	@ApiModelProperty(value = "申請人")
	private String applicant;

	/** 聯絡電話 **/
	@ApiModelProperty(value = "聯絡電話")
	private String phone;

	/** 電子郵件 **/
	@ApiModelProperty(value = "電子郵件")
	private String email;

	/** 角色 **/
	@ApiModelProperty(value = "角色")
	private String applyRole;

	@ApiModelProperty(value = "歷程圖")
	private String image;

//	/** 額外資料集合 **/
//	@ApiModelProperty(value = "額外資料集合")
//	private List<CaseHandleCaseViewUraCaseExtraDataDataGrid> extraData;

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
//
//	public List<CaseHandleCaseViewUraCaseExtraDataDataGrid> getExtraData() {
//		return extraData;
//	}
//
//	public void setExtraData(List<CaseHandleCaseViewUraCaseExtraDataDataGrid> extraData) {
//		this.extraData = extraData;
//	}
//
	public List<CaseHandleCaseViewCaseHistoryDataGrid> getHistoryData() {
		return historyData;
	}

	public void setHistoryData(List<CaseHandleCaseViewCaseHistoryDataGrid> historyData) {
		this.historyData = historyData;
	}

	public String getApplyRole() {
		return applyRole;
	}

	public void setApplyRole(String applyRole) {
		this.applyRole = applyRole;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
