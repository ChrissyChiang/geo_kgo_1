package gov.kcg.kgo.viewModel.commonApi.flow.common.rs.bean;

import java.io.Serializable;
import java.util.List;

import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean.CaseHandleCaseViewSaCaseApplyDataDataGridComplex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 欄位id、申辦欄位說明、欄位值object.
 */
@ApiModel(description = "欄位id、申辦欄位說明、欄位值object")
public class CaseColumnDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "欄位id", position = 1, example = "name")
	private String columnId;

	@ApiModelProperty(notes = "申辦欄位說明", position = 2, example = "姓名")
	private String columnName;

	@ApiModelProperty(notes = "欄位值", position = 3, example = "王小明")
	private String columnValue;

	private List<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>> complexData;

	public CaseColumnDetail() {
	}

	/**
	 * @param columnName
	 * @param columnValue
	 */
	public CaseColumnDetail(String columnId, String columnName, String columnValue) {
		this.columnId = columnId;
		this.columnName = columnName;
		this.columnValue = columnValue;
	}

	/**
	 * @param columnName
	 * @param columnValue
	 */
	public CaseColumnDetail(String columnId, String columnName, String columnValue, List<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>> complexData) {
		this.columnId = columnId;
		this.columnName = columnName;
		this.columnValue = columnValue;
		this.complexData = complexData;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public List<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>> getComplexData() {
		return complexData;
	}

	public void setComplexData(List<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>> complexData) {
		this.complexData = complexData;
	}
}
