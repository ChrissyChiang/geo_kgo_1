package gov.kcg.kgo.viewModel.commonApi.correctStatus.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import gov.kcg.kgo.viewModel.commonApi.common.rs.bean.CaseColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "寫入補正案件 rq")
public class CorrectStatusActionRq extends ApiCityCommonRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "案件編號", position=1)
	private String caseId;
	
	@ApiModelProperty(value = "補正期限", position=2)
	private Integer correctDeadline;
	
	@ApiModelProperty(value = "狀態說明", position=4)
	private String statusDesc;
	
	@ApiModelProperty(value = "待補正欄位清單", position=5)
	private List<CaseColumn> correctColumns;


	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Integer getCorrectDeadline() {
		return correctDeadline;
	}

	public void setCorrectDeadline(Integer correctDeadline) {
		this.correctDeadline = correctDeadline;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public List<CaseColumn> getCorrectColumns() {
		return correctColumns;
	}

	public void setCorrectColumns(List<CaseColumn> correctColumns) {
		this.correctColumns = correctColumns;
	}
}
