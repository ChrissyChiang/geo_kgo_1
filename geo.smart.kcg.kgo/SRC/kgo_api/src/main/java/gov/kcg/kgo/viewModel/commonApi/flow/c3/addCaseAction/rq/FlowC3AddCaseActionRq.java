package gov.kcg.kgo.viewModel.commonApi.flow.c3.addCaseAction.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import gov.kcg.kgo.viewModel.commonApi.flow.common.rs.bean.CaseColumnDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "提供平台案件處理區呼叫使用，C-3流程 新增案件 rq")
public class FlowC3AddCaseActionRq extends ApiCityCommonRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "服務案件編號")
	private String caseSetId;
	
	@ApiModelProperty(value = "申請人員")
	private String applyUser;

	@ApiModelProperty(value = "申請人員姓名")
	private String applyUserName;
	
	@ApiModelProperty(value = "申請日期 format: yyyy/MM/dd")
	private String applyDate;
	
	@ApiModelProperty(value = "受理機關")
	private String caseOrgan;

	@ApiModelProperty(notes = "申辦欄位資料")
	private List<CaseColumnDetail> caseColumns;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
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

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getCaseOrgan() {
		return caseOrgan;
	}

	public void setCaseOrgan(String caseOrgan) {
		this.caseOrgan = caseOrgan;
	}

	public List<CaseColumnDetail> getCaseColumns() {
		return caseColumns;
	}

	public void setCaseColumns(List<CaseColumnDetail> caseColumns) {
		this.caseColumns = caseColumns;
	}
}
