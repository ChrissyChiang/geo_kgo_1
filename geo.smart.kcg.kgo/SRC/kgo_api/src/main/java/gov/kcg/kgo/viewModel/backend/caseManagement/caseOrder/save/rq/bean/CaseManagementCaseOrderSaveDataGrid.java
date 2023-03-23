package gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.save.rq.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務案件清單-案件排序-修改 rq data grid"
 */
@ApiModel(description = "服務案件清單-案件排序-修改 rq data grid")
public class CaseManagementCaseOrderSaveDataGrid {

	/** 案件順序 */
	@ApiModelProperty(notes = "案件順序")
	private Integer order;

	/** 案件種類ID */
	@ApiModelProperty(notes = "案件種類ID")
	private String caseSetId;

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

}
