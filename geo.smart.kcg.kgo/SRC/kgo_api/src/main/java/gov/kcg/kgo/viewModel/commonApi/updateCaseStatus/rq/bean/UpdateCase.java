package gov.kcg.kgo.viewModel.commonApi.updateCaseStatus.rq.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "案件更新 request Model")
public class UpdateCase {
	/** (Ex: A12020091400001) */
	@ApiModelProperty(value = "案件編號", position = 1, example = "A12020091400001")
	private String caseId;

	@ApiModelProperty(value = "案件狀態,  P：處理中 ; F：結案 ;  W：待處理 ; C：待補正 ; O：其他", position = 2)
	private String status;

	@ApiModelProperty(value = "狀態說明(status=O時，此欄為必填)", position = 3)
	private String statusDesc;

	@ApiModelProperty(value = "承辦人員", position = 4)
	private String caseOfficer;

	@ApiModelProperty(value = "結案日期 YYYYMMDDHHMMSS", position = 5)
	private String fDate;

	@ApiModelProperty(value = "是否通知申請人 Y/N", position = 6)
	private String isNotify;

	@ApiModelProperty(value = "原系統案件編號", position = 7)
	private String oCaseId;

	@ApiModelProperty(value = "申請者", position = 8)
	private String applyUser;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getCaseOfficer() {
		return caseOfficer;
	}

	public void setCaseOfficer(String caseOfficer) {
		this.caseOfficer = caseOfficer;
	}

	public String getfDate() {
		return fDate;
	}

	public void setfDate(String fDate) {
		this.fDate = fDate;
	}

	public String getIsNotify() {
		return isNotify;
	}

	public void setIsNotify(String isNotify) {
		this.isNotify = isNotify;
	}

	public String getoCaseId() {
		return oCaseId;
	}

	public void setoCaseId(String oCaseId) {
		this.oCaseId = oCaseId;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	@Override
	public String toString() {
		return "UpdateCase{" +
				"caseId='" + caseId + '\'' +
				", status='" + status + '\'' +
				", statusDesc='" + statusDesc + '\'' +
				", caseOfficer='" + caseOfficer + '\'' +
				", fDate='" + fDate + '\'' +
				", isNotify='" + isNotify + '\'' +
				", oCaseId='" + oCaseId + '\'' +
				", applyUser='" + applyUser + '\'' +
				'}';
	}
}
