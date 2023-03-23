package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-案件檢視-服務申辦(SA)案件檢視-申請資料
 */
@ApiModel(description = "後台案件處理-案件檢視-服務申辦(SA)案件檢視-申請資料")
public class CaseHandleCaseViewSaCaseApplyDataDataGrid {

	/** 欄位名稱 */
	@ApiModelProperty(notes = "案件id(民眾申請案件後獲得)")
	private String caseId;

	/** 欄位名稱 */
	@ApiModelProperty(notes = "案件服務編號")
	private String caseSetId;

	/** 欄位名稱 */
	@ApiModelProperty(notes = "欄位名稱")
	private String columnName;

	/** 欄位值 */
	@ApiModelProperty(notes = "欄位值")
	private String columnValue;

	/** 欄位ID */
	@ApiModelProperty(notes = "欄ID")
	private String columnId;

	/** 是否補正 */
	@ApiModelProperty(notes = "是否補正")
	private Boolean isCorrect;

	/** 補正說明 */
	@ApiModelProperty(notes = "補正說明")
	private String correctMemo;

	@ApiModelProperty(value = "複合欄位資料集合")
	private List<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>> complex;

	/** 補正前資料 */
	@ApiModelProperty(notes = "補正前資料")
	private String correctBValue;

	/** 欄位類型 */
	@ApiModelProperty(notes = "欄位類型")
	private String columnType;

	public CaseHandleCaseViewSaCaseApplyDataDataGrid() {
	}

	public CaseHandleCaseViewSaCaseApplyDataDataGrid(String columnName, String columnValue) {
		this.columnName = columnName;
		this.columnValue = columnValue;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
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

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getCorrectMemo() {
		return correctMemo;
	}

	public void setCorrectMemo(String correctMemo) {
		this.correctMemo = correctMemo;
	}

	public String getCorrectBValue() {
		return correctBValue;
	}

	public void setCorrectBValue(String correctBValue) {
		this.correctBValue = correctBValue;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public List<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>> getComplex() {
		return complex;
	}

	public void setComplex(List<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>> complex) {
		this.complex = complex;
	}

}
