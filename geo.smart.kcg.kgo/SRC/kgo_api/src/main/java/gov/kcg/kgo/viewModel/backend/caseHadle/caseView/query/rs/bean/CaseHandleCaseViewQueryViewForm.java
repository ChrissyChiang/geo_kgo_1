package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rs.bean;

import gov.kcg.kgo.geomodel.GeoCaseViewFieldCheckModel;
import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台案件處理-案件檢視-案件查詢 View Form
 */
@ApiModel(description = "後台案件處理-案件檢視-案件查詢 View Form")
public class CaseHandleCaseViewQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 案件檢視-案件清單 **/
	@ApiModelProperty(value = "案件檢視-案件清單")
	private List<CaseHandleCaseQueryDataGrid> grid;

	/** GEO 20211102 add for 欄位勾選 */
	@ApiModelProperty(value = "是否為單一案件")
	public boolean onlyOneCaseSetType;

	/** GEO 20211102 add for 欄位勾選 */
	@ApiModelProperty(value = "勾選欄位清單")
	public  List<GeoCaseViewFieldCheckModel> fieldCheckList;

	public List<CaseHandleCaseQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<CaseHandleCaseQueryDataGrid> grid) {
		this.grid = grid;
	}

	public boolean isOnlyOneCaseSetType() {
		return onlyOneCaseSetType;
	}

	public void setOnlyOneCaseSetType(boolean onlyOneCaseSetType) {
		this.onlyOneCaseSetType = onlyOneCaseSetType;
	}

	public List<GeoCaseViewFieldCheckModel> getFieldCheckList() {
		return fieldCheckList;
	}

	public void setFieldCheckList(List<GeoCaseViewFieldCheckModel> fieldCheckList) {
		this.fieldCheckList = fieldCheckList;
	}
}
