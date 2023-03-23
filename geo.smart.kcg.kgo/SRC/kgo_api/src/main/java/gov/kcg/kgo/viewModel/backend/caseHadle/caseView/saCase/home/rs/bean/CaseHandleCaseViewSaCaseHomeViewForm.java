package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean;

import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseViewCaseHistoryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * 後台案件處理-案件檢視-服務申辦(SA)案件檢視-初始畫面 View Form
 */
@ApiModel(description = "後台案件處理-案件檢視-服務申辦(SA)案件檢視-初始畫面 View Form")
public class CaseHandleCaseViewSaCaseHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 案件狀態 **/
	@ApiModelProperty(value = "案件狀態")
	private String status;

	/** 案件編號 **/
	@ApiModelProperty(value = "案件編號")
	private String caseId;

	/** 申請時間 **/
	@ApiModelProperty(value = "申請時間")
	private String applyDate;

	/** 案件名稱 **/
	@ApiModelProperty(value = "案件名稱")
	private String caseName;

	/** 繳費狀態 */
	@ApiModelProperty(value = "繳費狀態")
	private Map<String,String> payment_status;

	/** 預約狀態 */
	@ApiModelProperty(value = "預約狀態")
	private String rent_status;

	/** 限辦天數 **/
	@ApiModelProperty(value = "限辦天數")
	private Integer limitDay;

	/** 限辦期間 **/
	@ApiModelProperty(value = "限辦期間")
	private String limitTime;
	
	/** 補正天數 **/
	@ApiModelProperty(value = "補正天數")
	private Integer correctDeadline;

	/** 補正期限 **/
	@ApiModelProperty(value = "補正期限")
	private String correctDate;

	/** 申請資料 **/
	@ApiModelProperty(value = "申請資料")
	private List<CaseHandleCaseViewSaCaseApplyDataDataGrid> applyData;

	/** 附件 **/
	@ApiModelProperty(value = "附件")
	private List<CaseHandleCaseViewSaCaseDownloadDataDataGrid> downloadData;

	/** 處理歷程 **/
	@ApiModelProperty(value = "處理歷程")
	private List<CaseHandleCaseViewCaseHistoryDataGrid> historyData;

	@ApiModelProperty(value = "歷程圖")
	private String image;

	@ApiModelProperty(value = "補正狀態")
	private String state;

	@ApiModelProperty(value = "作業流程分類")
	private String caseFlowType;
	

	@ApiModelProperty(value = "flowId")
	private String flowId;
	
	@ApiModelProperty(value = "是否為動態流程")
	private Boolean isDynamicFlow = false;

	@ApiModelProperty(value = "是否允許退回上一關")
	private Boolean isCanReject = false;

	@ApiModelProperty(value = "是否結案")
	private Boolean isEnd = false;
	
	@ApiModelProperty(value = "流程按鈕顯示名稱 (分文、會簽 or陳核)")
	private String btnDisplayName;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public Integer getLimitDay() {
		return limitDay;
	}

	public void setLimitDay(Integer limitDay) {
		this.limitDay = limitDay;
	}

	public String getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}
	
	public Integer getCorrectDeadline() {
		return correctDeadline;
	}

	public void setCorrectDeadline(Integer correctDeadline) {
		this.correctDeadline = correctDeadline;
	}

	public String getCorrectDate() {
		return correctDate;
	}

	public void setCorrectDate(String correctDate) {
		this.correctDate = correctDate;
	}

	public List<CaseHandleCaseViewSaCaseApplyDataDataGrid> getApplyData() {
		return applyData;
	}

	public void setApplyData(List<CaseHandleCaseViewSaCaseApplyDataDataGrid> applyData) {
		this.applyData = applyData;
	}

	public List<CaseHandleCaseViewSaCaseDownloadDataDataGrid> getDownloadData() {
		return downloadData;
	}

	public void setDownloadData(List<CaseHandleCaseViewSaCaseDownloadDataDataGrid> downloadData) {
		this.downloadData = downloadData;
	}

	public List<CaseHandleCaseViewCaseHistoryDataGrid> getHistoryData() {
		return historyData;
	}

	public void setHistoryData(List<CaseHandleCaseViewCaseHistoryDataGrid> historyData) {
		this.historyData = historyData;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getCaseFlowType() {
		return caseFlowType;
	}

	public void setCaseFlowType(String caseFlowType) {
		this.caseFlowType = caseFlowType;
	}

	public Boolean getIsCanReject() {
		return isCanReject;
	}

	public void setIsCanReject(Boolean isCanReject) {
		this.isCanReject = isCanReject;
	}

	public Boolean getIsDynamicFlow() {
		return isDynamicFlow;
	}

	public void setIsDynamicFlow(Boolean isDynamicFlow) {
		this.isDynamicFlow = isDynamicFlow;
	}

	public Boolean getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(Boolean isEnd) {
		this.isEnd = isEnd;
	}

	public String getBtnDisplayName() {
		return btnDisplayName;
	}

	public void setBtnDisplayName(String btnDisplayName) {this.btnDisplayName = btnDisplayName;}

	public Map<String, String> getPayment_status() {return payment_status;}

	public void setPayment_status(Map<String, String> payment_status) {this.payment_status = payment_status;}

	public String getRent_status() {return rent_status;}

	public void setRent_status(String rent_status) {this.rent_status = rent_status;}
}
