package gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待審核匣-補正作業-明細-欄位資料
 */
@ApiModel(description = "後台案件處理-待審核匣-補正作業-明細-欄位資料")
public class CaseHandleCorrectCasesetColumn {

	
	/** 欄位ID **/
    @ApiModelProperty(value = "欄位ID")
    private String columnId;

    /** 欄位名稱 **/
    @ApiModelProperty(value = "欄位名稱")
    private String columnName;

    /** 欄位種類 **/
    @ApiModelProperty(value = "欄位種類")
    private String columnType;

    /** 欄位設定值 **/
    @ApiModelProperty(value = "欄位設定值")
    private String columnValue;

    /** 欄位資料 **/
    @ApiModelProperty(value = "欄位資料")
    private String columnDetailValue;

    /** 需要補正 **/
    @ApiModelProperty(value = "需要補正")
    private String isCorrect;

    /** 補正說明 **/
    @ApiModelProperty(value = "補正說明")
    private String correctMemo;

    /** 欄位長度 **/
    @ApiModelProperty(value = "欄位長度")
    private String Length;

    /** 是否必填 **/
    @ApiModelProperty(value = "是否必填")
    private String IsMustKey;

    /** 複合欄位 **/
    @ApiModelProperty(value = "群組欄位")
    private List<CaseHandleCorrectCasesetColumnChild> caseHandleCorrectCasesetColumnChildren;

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

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public String getColumnDetailValue() {
		return columnDetailValue;
	}

	public void setColumnDetailValue(String columnDetailValue) {
		this.columnDetailValue = columnDetailValue;
	}

	public String getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(String isCorrect) {
		this.isCorrect = isCorrect;
	}

	public String getCorrectMemo() {
		return correctMemo;
	}

	public void setCorrectMemo(String correctMemo) {
		this.correctMemo = correctMemo;
	}

	public String getLength() {
		return Length;
	}

	public void setLength(String length) {
		Length = length;
	}

	public String getIsMustKey() {
		return IsMustKey;
	}

	public void setIsMustKey(String isMustKey) {
		IsMustKey = isMustKey;
	}

	public List<CaseHandleCorrectCasesetColumnChild> getCaseHandleCorrectCasesetColumnChildren() {
		return caseHandleCorrectCasesetColumnChildren;
	}

	public void setCaseHandleCorrectCasesetColumnChildren(
			List<CaseHandleCorrectCasesetColumnChild> caseHandleCorrectCasesetColumnChildren) {
		this.caseHandleCorrectCasesetColumnChildren = caseHandleCorrectCasesetColumnChildren;
	}

}
