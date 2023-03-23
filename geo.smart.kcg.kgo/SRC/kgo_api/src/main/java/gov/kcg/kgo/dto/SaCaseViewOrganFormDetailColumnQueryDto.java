package gov.kcg.kgo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * GEO 20211109 add 機關審核表單
 * 後台案件處理-SCA案件 機關審核表單檢視資料
 */
@ApiModel(description = "後台案件處理-SCA案件 機關審核表單檢視資料")
@Entity
public class SaCaseViewOrganFormDetailColumnQueryDto {

	@Id
	@ApiModelProperty(notes = "序號")
	@Column(name = "ROW_NUMBER")
	private String rowNumber;

	@ApiModelProperty(notes = "欄位名稱")
	@Column(name = "COLUMN_NAME")
	private String columnName;

	@ApiModelProperty(notes = "真實欄位值")
	@Column(name = "REAL_COLUMN_VALUE")
	private String realColumnValue;

	@ApiModelProperty(notes = "PDF真實欄位值")
	@Column(name = "REAL_PDF_COLUMN_VALUE")
	private String realPdfColumnValue;

	@ApiModelProperty(notes = "是否有對應源")
	@Column(name = "IS_MUST_KEY")
	private Boolean IsMustKey;

	@ApiModelProperty(notes = "設定欄位值")
	@Column(name = "SET_COLUMN_VALUE")
	private String setColumnValue;

	@ApiModelProperty(notes = "設定欄位型態")
	@Column(name = "SET_COLUMN_TYPE")
	private String setColumnType;

	@ApiModelProperty(notes = "設定欄ID")
	@Column(name = "SET_COLUMN_ID")
	private String setColumnId;

	@ApiModelProperty(notes = "設定複合欄位值")
	@Column(name = "SET_CCOLUMN_VALUE")
	private String setCcolumnValue;

	@ApiModelProperty(notes = "設定複合欄位型態")
	@Column(name = "SET_CCOLUMN_TYPE")
	private String setCcolumnType;

	@ApiModelProperty(notes = "設定複合欄ID")
	@Column(name = "SET_CCOLUMN_ID")
	private String setCcolumnId;

	@ApiModelProperty(notes = "來源")
	@Column(name = "IS_SOURCE")
	private Integer isSource;

	@ApiModelProperty(notes = "補正")
	@Column(name = "IS_CORRECT")
	private Integer isCorrect;

	@ApiModelProperty(notes = "排序")
	@Column(name = "COLUMN_ORDER_NUM")
	private Integer columnOrderNum;

	@ApiModelProperty(notes = "ROW")
	@Column(name = "CCOLUMN_ROW_NUM")
	private Integer ccolumnRowNum;

	@ApiModelProperty(notes = "複合欄位排序")
	@Column(name = "CCOLUMN_ORDER_NUM")
	private Integer ccolumnOrderNum;

	@ApiModelProperty(notes = "前文字")
	@Column(name = "F_TEXT")
	private String ftext;

	@ApiModelProperty(notes = "後文字")
	@Column(name = "B_TEXT")
	private String btext;

	@ApiModelProperty(notes = "後文字")
	@Column(name = "CORRECT_MEMO")
	private String correctMemo;

	@ApiModelProperty(notes = "補正值")
	@Column(name = "CORRECT_B_VALUE")
	private String correctBValue;

	@ApiModelProperty(notes = "補正值")
	@Column(name = "CORRECT_B_PDF_VALUE")
	private String correctBPdfValue;

	@ApiModelProperty(notes = "結案日期")
	@Column(name = "FDATE")
	private Timestamp fdate;

	@ApiModelProperty(notes = "對應的comment id")
	@Column(name = "COMMENT_ID")
	private String commentId;

	@ApiModelProperty(notes = "機關審核表單版本號")
	@Column(name = "CASE_FORM_VERSION")
	private Integer caseFormVersion;

	public SaCaseViewOrganFormDetailColumnQueryDto() {
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

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public Integer getCaseFormVersion() {
		return caseFormVersion;
	}

	public void setCaseFormVersion(Integer caseFormVersion) {
		this.caseFormVersion = caseFormVersion;
	}

	@Override
	public String toString() {
		return "SaCaseViewOrganFormDetailColumnQueryDto{" +
				"rowNumber='" + rowNumber + '\'' +
				", columnName='" + columnName + '\'' +
				", realColumnValue='" + realColumnValue + '\'' +
				", realPdfColumnValue='" + realPdfColumnValue + '\'' +
				", IsMustKey=" + IsMustKey +
				", setColumnValue='" + setColumnValue + '\'' +
				", setColumnType='" + setColumnType + '\'' +
				", setColumnId='" + setColumnId + '\'' +
				", setCcolumnValue='" + setCcolumnValue + '\'' +
				", setCcolumnType='" + setCcolumnType + '\'' +
				", setCcolumnId='" + setCcolumnId + '\'' +
				", isSource=" + isSource +
				", isCorrect=" + isCorrect +
				", columnOrderNum=" + columnOrderNum +
				", ccolumnRowNum=" + ccolumnRowNum +
				", ccolumnOrderNum=" + ccolumnOrderNum +
				", ftext='" + ftext + '\'' +
				", btext='" + btext + '\'' +
				", correctMemo='" + correctMemo + '\'' +
				", correctBValue='" + correctBValue + '\'' +
				", correctBPdfValue='" + correctBPdfValue + '\'' +
				", fdate=" + fdate +
				", commentId='" + commentId + '\'' +
				", caseFormVersion=" + caseFormVersion +
				'}';
	}
}
