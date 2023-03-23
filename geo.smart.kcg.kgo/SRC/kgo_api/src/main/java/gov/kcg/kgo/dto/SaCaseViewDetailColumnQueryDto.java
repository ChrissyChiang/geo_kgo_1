package gov.kcg.kgo.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-SCA案件檢視資料")
@Entity
public class SaCaseViewDetailColumnQueryDto {

	/** 序號 */
	@Id
	@ApiModelProperty(notes = "序號")
	@Column(name = "ROW_NUMBER")
	private String rowNumber;

	/** 欄位名稱 */
	@ApiModelProperty(notes = "欄位名稱")
	@Column(name = "COLUMN_NAME")
	private String columnName;

	/** 真實欄位值 */
	@ApiModelProperty(notes = "真實欄位值")
	@Column(name = "REAL_COLUMN_VALUE")
	private String realColumnValue;

	/** 真實欄位值 */
	@ApiModelProperty(notes = "PDF真實欄位值")
	@Column(name = "REAL_PDF_COLUMN_VALUE")
	private String realPdfColumnValue;

	/** 是否有對應源 */
	@ApiModelProperty(notes = "是否有對應源")
	@Column(name = "IS_MUST_KEY")
	private Boolean IsMustKey;

	/** 設定欄位值 */
	@ApiModelProperty(notes = "設定欄位值")
	@Column(name = "SET_COLUMN_VALUE")
	private String setColumnValue;

	/** 設定欄位型態 */
	@ApiModelProperty(notes = "設定欄位型態")
	@Column(name = "SET_COLUMN_TYPE")
	private String setColumnType;

	/** 設定欄位型態 */
	@ApiModelProperty(notes = "設定欄ID")
	@Column(name = "SET_COLUMN_ID")
	private String setColumnId;

	/** 設定欄位值 */
	@ApiModelProperty(notes = "設定複合欄位值")
	@Column(name = "SET_CCOLUMN_VALUE")
	private String setCcolumnValue;
	/** 設定欄位型態 */
	@ApiModelProperty(notes = "設定複合欄位型態")
	@Column(name = "SET_CCOLUMN_TYPE")
	private String setCcolumnType;

	/** 設定欄位型態 */
	@ApiModelProperty(notes = "設定複合欄ID")
	@Column(name = "SET_CCOLUMN_ID")
	private String setCcolumnId;

	/** 來源 */
	@ApiModelProperty(notes = "來源")
	@Column(name = "IS_SOURCE")
	private Integer isSource;

	@ApiModelProperty(notes = "補正")
	@Column(name = "IS_CORRECT")
	private Integer isCorrect;

	/** 排序 */
	@ApiModelProperty(notes = "排序")
	@Column(name = "COLUMN_ORDER_NUM")
	private Integer columnOrderNum;

	/** 複合欄Row */
	@ApiModelProperty(notes = "ROW")
	@Column(name = "CCOLUMN_ROW_NUM")
	private Integer ccolumnRowNum;

	/** 複合欄位排序 */
	@ApiModelProperty(notes = "複合欄位排序")
	@Column(name = "CCOLUMN_ORDER_NUM")
	private Integer ccolumnOrderNum;

	/** 前文字 */
	@ApiModelProperty(notes = "前文字")
	@Column(name = "F_TEXT")
	private String ftext;

	/** 後文字 */
	@ApiModelProperty(notes = "後文字")
	@Column(name = "B_TEXT")
	private String btext;

	/** 後文字 */
	@ApiModelProperty(notes = "後文字")
	@Column(name = "CORRECT_MEMO")
	private String correctMemo;

	/** 補正值 */
	@ApiModelProperty(notes = "補正值")
	@Column(name = "CORRECT_B_VALUE")
	private String correctBValue;

	/** 補正值 */
	@ApiModelProperty(notes = "補正值")
	@Column(name = "CORRECT_B_PDF_VALUE")
	private String correctBPdfValue;

	/** 結案日期 */
	@ApiModelProperty(notes = "結案日期")
	@Column(name = "FDATE")
	private Timestamp fdate;

	/** MyDataColumn */
	@Column(name = "MyDataColumn", length = 50)
	private String myDataColumn;

	/** MyDataId */
	@Column(name = "MyDataId", length = 50)
	private String myDataId;

	public SaCaseViewDetailColumnQueryDto() {
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getRealColumnValue() {
		return realColumnValue;
	}

	public void setRealColumnValue(String realColumnValue) {
		this.realColumnValue = realColumnValue;
	}

	public Boolean getMustKey() {
		return IsMustKey;
	}

	public void setMustKey(Boolean mustKey) {
		IsMustKey = mustKey;
	}

	public String getSetColumnValue() {
		return setColumnValue;
	}

	public void setSetColumnValue(String setColumnValue) {
		this.setColumnValue = setColumnValue;
	}

	public String getSetColumnType() {
		return setColumnType;
	}

	public void setSetColumnType(String setColumnType) {
		this.setColumnType = setColumnType;
	}

	public String getSetColumnId() {
		return setColumnId;
	}

	public void setSetColumnId(String setColumnId) {
		this.setColumnId = setColumnId;
	}

	public Integer getIsSource() {
		return isSource;
	}

	public void setIsSource(Integer isSource) {
		this.isSource = isSource;
	}

	public String getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Integer getColumnOrderNum() {
		return columnOrderNum;
	}

	public void setColumnOrderNum(Integer columnOrderNum) {
		this.columnOrderNum = columnOrderNum;
	}

	public Integer getCcolumnOrderNum() {
		return ccolumnOrderNum;
	}

	public void setCcolumnOrderNum(Integer ccolumnOrderNum) {
		this.ccolumnOrderNum = ccolumnOrderNum;
	}

	public String getFtext() {
		return ftext;
	}

	public void setFtext(String ftext) {
		this.ftext = ftext;
	}

	public String getBtext() {
		return btext;
	}

	public void setBtext(String btext) {
		this.btext = btext;
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

	public Timestamp getFdate() {
		return fdate;
	}

	public void setFdate(Timestamp fdate) {
		this.fdate = fdate;
	}

	public String getSetCcolumnType() {
		return setCcolumnType;
	}

	public void setSetCcolumnType(String setCcolumnType) {
		this.setCcolumnType = setCcolumnType;
	}

	public String getSetCcolumnId() {
		return setCcolumnId;
	}

	public void setSetCcolumnId(String setCcolumnId) {
		this.setCcolumnId = setCcolumnId;
	}

	public Integer getCcolumnRowNum() {
		return ccolumnRowNum;
	}

	public void setCcolumnRowNum(Integer ccolumnRowNum) {
		this.ccolumnRowNum = ccolumnRowNum;
	}

	public String getSetCcolumnValue() {
		return setCcolumnValue;
	}

	public void setSetCcolumnValue(String setCcolumnValue) {
		this.setCcolumnValue = setCcolumnValue;
	}

	public Boolean getIsMustKey() {
		return IsMustKey;
	}

	public void setIsMustKey(Boolean isMustKey) {
		IsMustKey = isMustKey;
	}

	public Integer getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Integer isCorrect) {
		this.isCorrect = isCorrect;
	}

	public String getMyDataColumn() {
		return myDataColumn;
	}

	public void setMyDataColumn(String myDataColumn) {
		this.myDataColumn = myDataColumn;
	}

	public String getMyDataId() {
		return myDataId;
	}

	public void setMyDataId(String myDataId) {
		this.myDataId = myDataId;
	}

	public String getRealPdfColumnValue() {
		return realPdfColumnValue;
	}

	public void setRealPdfColumnValue(String realPdfColumnValue) {
		this.realPdfColumnValue = realPdfColumnValue;
	}

	public String getCorrectBPdfValue() {
		return correctBPdfValue;
	}

	public void setCorrectBPdfValue(String correctBPdfValue) {
		this.correctBPdfValue = correctBPdfValue;
	}
}
