package gov.kcg.kgo.viewModel.backend.caseHadle.reviewed.view.rs.bean;

import java.util.List;

import gov.kcg.kgo.dto.Activiti.HistoryDataDto;
import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-已審核匣-案件檢視 View Form
 */
@ApiModel(description = "後台案件處理-已審核匣-案件檢視 View Form")
public class CaseHandleReviewedViewViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 已審核匣-案件清單 **/
	@ApiModelProperty(value = "已審核匣-案件清單")
	private CaseHandleCaseQueryDataGrid grid;
	
	/** 已審核匣-案件歷程 **/
	@ApiModelProperty(value = "已審核匣-案件歷程")
	private List<HistoryDataDto> historyList;

	public CaseHandleCaseQueryDataGrid getGrid() {
		return grid;
	}

	public void setGrid(CaseHandleCaseQueryDataGrid grid) {
		this.grid = grid;
	}

	public List<HistoryDataDto> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<HistoryDataDto> historyList) {
		this.historyList = historyList;
	}

}
