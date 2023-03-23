package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.List;

/**
 * GEO 20211109 add 機關審核表單
 * 後台案件處理-案件檢視-服務申辦(SA)案件檢視-機關審核表單資料
 */
@ApiModel(description = "後台案件處理-案件檢視-服務申辦(SA)案件檢視-機關審核表單資料")
public class CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid {

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
	private List<List<CaseHandleCaseViewSaCaseOrganFormApplyDataDataGridComplex>> complex;

	/** 補正前資料 */
	@ApiModelProperty(notes = "補正前資料")
	private String correctBValue;

	/** 欄位類型 */
	@ApiModelProperty(notes = "欄位類型")
	private String columnType;

	@ApiModelProperty(notes = "對應的comment id")
	private String commentId;

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

	public List<List<CaseHandleCaseViewSaCaseOrganFormApplyDataDataGridComplex>> getComplex() {
		return complex;
	}

	public void setComplex(List<List<CaseHandleCaseViewSaCaseOrganFormApplyDataDataGridComplex>> complex) {
		this.complex = complex;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
}
