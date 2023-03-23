package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 案件進度查詢-明細-欄位資料
 */
@ApiModel(description = "案件進度查詢-明細-欄位資料")
public class CaseProcessSearchDetailCasesetColumn {

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
	@ApiModelProperty(value = "需要補正 (Y/N)")
	private String isCorrect;

	/** 補正說明 **/
	@ApiModelProperty(value = "補正說明")
	private String correctMemo;

	/** 欄位長度 **/
	@ApiModelProperty(value = "欄位長度")
	private Integer Length;

	/** 是否必填 **/
	@ApiModelProperty(value = "是否必填")
	private String IsMustKey;

	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	/** 複合欄位 **/
	@ApiModelProperty(value = "群組欄位")
	private List<List<CaseProcessSearchDetailCasesetColumnChild>> caseProcessSearchDetailCasesetColumnChildren;

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

	public Integer getLength() {
		return Length;
	}

	public void setLength(Integer length) {
		Length = length;
	}

	public void setIsMustKey(String isMustKey) {
		IsMustKey = isMustKey;
	}

	public String getIsMustKey() {
		return IsMustKey;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public List<List<CaseProcessSearchDetailCasesetColumnChild>> getCaseProcessSearchDetailCasesetColumnChildren() {
		return caseProcessSearchDetailCasesetColumnChildren;
	}

	public void setCaseProcessSearchDetailCasesetColumnChildren(List<List<CaseProcessSearchDetailCasesetColumnChild>> caseProcessSearchDetailCasesetColumnChildren) {
		this.caseProcessSearchDetailCasesetColumnChildren = caseProcessSearchDetailCasesetColumnChildren;
	}
}
