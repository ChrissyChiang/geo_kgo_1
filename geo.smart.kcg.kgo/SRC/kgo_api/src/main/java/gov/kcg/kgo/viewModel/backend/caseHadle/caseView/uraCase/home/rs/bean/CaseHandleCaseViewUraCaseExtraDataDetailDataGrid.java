package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.uraCase.home.rs.bean;

import javax.persistence.Column;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-案件檢視-服務申辦(URA)案件檢視-額外群組欄位資料
 */
@ApiModel(description = "後台案件處理-案件檢視-服務申辦(URA)案件檢視-額外群組欄位資料")
public class CaseHandleCaseViewUraCaseExtraDataDetailDataGrid {

	/** 欄位名稱 */
	@ApiModelProperty(notes = "欄位名稱")
	@Column(name = "COLUMN_NAME")
	private String columnName;

	/** 欄位型態 **/
	@ApiModelProperty(notes = "欄位型態")
	@Column(name = "COLUMN_TYPE")
	private String columnType;

	/** 欄位長度 */
	@ApiModelProperty(notes = "欄位長度")
	@Column(name = "LENGTH")
	private Integer length;

	/** 對應欄位 */
	@ApiModelProperty(notes = "對應欄位")
	@Column(name = "COLUMN_ID")
	private String columnId;

	/** 是否必填 */
	@ApiModelProperty(notes = "是否必填")
	@Column(name = "IS_MUST_KEY")
	private Boolean isMustKey;

	/** 欄位值 */
	@ApiModelProperty(notes = "欄位值")
	@Column(name = "COLUMN_VALUE")
	private String columnValue;

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

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public Boolean getIsMustKey() {
		return isMustKey;
	}

	public void setIsMustKey(Boolean isMustKey) {
		this.isMustKey = isMustKey;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

}
