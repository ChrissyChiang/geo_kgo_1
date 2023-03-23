package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rs.bean;

import java.util.List;

import gov.kcg.kgo.dto.CaseMainQueryCaseViewListDto;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-案件檢視-案件查詢 View Form
 */
@ApiModel(description = "後台案件處理-案件檢視-案件查詢 View Form")
public class QueryCaseByOrganViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 案件檢視-案件清單 **/
	@ApiModelProperty(value = "案件檢視-案件清單")
	private List<CaseMainQueryCaseViewListDto> grid;

	public List<CaseMainQueryCaseViewListDto> getGrid() {
		return grid;
	}

	public void setGrid(List<CaseMainQueryCaseViewListDto> grid) {
		this.grid = grid;
	}

}
