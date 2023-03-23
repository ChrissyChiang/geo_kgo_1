package gov.kcg.kgo.viewModel.frontend.caseform.rs.bean;

import java.util.List;
import java.util.Map;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "前臺 案件表單申請-存擋")
public class SaveActionViewForm extends BaseViewForm {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "案件種類ID")
	private String caseSetId;

	@ApiModelProperty(notes = "案件名稱")
	private String caseSetName;

	@ApiModelProperty(notes = "受理機關ID")
	private String caseOrganId;

	@ApiModelProperty(notes = "受理機關")
	private String caseOrganName;

	// @ApiModelProperty(notes = "申請人ID")
	// private String applyUser;

	@ApiModelProperty(notes = "申請人姓名")
	private String applyUserName;

	// A8碼西元日期5碼流水號
	@ApiModelProperty(notes = "申請編號")
	private String caseId;

	@ApiModelProperty(notes = "申請日期時間")
	private String applyDate;

	@ApiModelProperty(notes = "導頁網址")
	private String linkUrl;
	//GEO 20221109 線上預約繳退費相關資訊
	@ApiModelProperty(notes ="場地活動名稱")
	private String siteName;
	@ApiModelProperty(notes = "繳費金額描述")
	private String payDetail;
	@ApiModelProperty(notes ="租借狀態")
	private String rentStatus;
	@ApiModelProperty(notes ="繳費狀態")
	private String paymentStatus;
	@ApiModelProperty(value="功能按鈕清單")
	private List<Map<String,String>> funcList;

	@ApiModelProperty(notes = "錯誤訊息")
	private List<ValidationViewForm> validationMsg;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseOrganId() {
		return caseOrganId;
	}

	public void setCaseOrganId(String caseOrganId) {
		this.caseOrganId = caseOrganId;
	}

	public String getCaseOrganName() {
		return caseOrganName;
	}

	public void setCaseOrganName(String caseOrganName) {
		this.caseOrganName = caseOrganName;
	}

//	public String getApplyUser() {
//		return applyUser;
//	}
//
//	public void setApplyUser(String applyUser) {
//		this.applyUser = applyUser;
//	}
//
	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public List<ValidationViewForm> getValidationMsg() {
		return validationMsg;
	}

	public void setValidationMsg(List<ValidationViewForm> validationMsg) {
		this.validationMsg = validationMsg;
	}

	public String getSiteName() {return siteName;}

	public void setSiteName(String siteName) {this.siteName = siteName;}

	public String getRentStatus() {return rentStatus;}

	public void setRentStatus(String rentStatus) {this.rentStatus = rentStatus;}

	public String getPaymentStatus() {return paymentStatus;}

	public void setPaymentStatus(String paymentStatus) {this.paymentStatus = paymentStatus;}

	public List<Map<String, String>> getFuncList() {return funcList;}

	public void setFuncList(List<Map<String, String>> funcList) {this.funcList = funcList;}

	public String getPayDetail() {return payDetail;}

	public void setPayDetail(String payDetail) {this.payDetail = payDetail;}
}
